package simul

class MemoryArray {
	
	private
	def startAddress
	def endAddress
	def arraySize
	def mArray = [:]
	def simulation
	
	public
	
	MemoryArray(def parent, def arraySize, def startAddress)
	{
		this.startAddress = startAddress
		this.endAddress = startAddress + arraySize - 1
		this.arraySize = arraySize
		simulation = parent	
	}
	
	def inRange(def address)
	{
		if (address < startAddress || address > endAddress) {
			return false
		} else {
			return true
		}
	}

	long getByte(def address)
	{
		def byteResult = mArray[address]
		if (byteResult == null) {
			if (!inRange(address)) {
				println "Out of range in getByte() at $address"
				throw new ArrayIndexOutOfBoundsException()
			}
			else {
				mArray[address] = 0 as byte
				byteResult = 0
			}
		}
		return byteResult & 0xFF
	}
	
	void setByte(def address, byte byteIn)
	{
		if (!inRange(address)) {
			println "Out of range in setByte() at $address"
			throw new ArrayIndexOutOfBoundsException()
		}
		mArray[address] = byteIn
	}
	
	def getHalfWord(def address, def test = true)
	{
		if (test & ((address >> 1) << 1) != address) {
			println "Unaligned read getHalfWord() at $address"
			throw new IllegalArgumentException()
		}
		try {
			if (simulation.endian) {
					long halfWord = getByte(address) * 0x100
					halfWord += getByte(address + 1)
					return halfWord
			} else {
				long halfWord = getByte(address)
				halfWord += getByte(address + 1) * 0x100
				return halfWord
			}
		}
		catch (e) {
			println "getHalfWord() failed at $address"
			throw e
		}
	}
	
	void setHalfWord(def address, def halfWordIn, def test = true)
	{
		if (test & ((address >>> 1) << 1) != address) {
			println "Unaligned write setHalfWord() at $address"
			throw new IllegalArgumentException()
		}
		try {
			long lowByte = halfWordIn & 0xFF
			setByte(address, lowByte as byte)
			long hiByte = (halfWordIn >>> 0x08) & 0xFF
			setByte(address + 1, hiByte as byte)
		}
		catch (e) {
			println "setHalfWord() failed at $address"
			throw e
		}
	}
	
	def getWord(def address)
	{
		if (((address >>>2) << 2) != address) {
			println "Unaligned read - getWord() at $address"
			throw new IllegalArgumentException()
		}
		try {
			if (simulation.endian) {
				long theWord = (getHalfWord(address, false) * 0x10000) & 0xFFFFFFFF
				theWord += getHalfWord(address + 2, false)
				return theWord
			} else {
				long theWord = getHalfWord(address, false)
				theWord += ((getHalfWord(address + 2) * 0x10000) & 0xFFFFFFFF)
				return theWord
			}
		}
		catch (e) {
			println "getWord() failed at address $address"
			throw e
		}
	}
	
	def setWord(def address, def wordIn)
	{
		if (((address >>> 2) << 2) != address) {
			println "Unaligned write - setWord() at $address"
			throw new IllegalArgumentException()
		}
		long value = wordIn & 0xFFFFFFFF
		try {
			setHalfWord(address, wordIn, false)
			def hiHalfWord = wordIn >> 16
			setHalfWord(address + 2, hiHalfWord, false)
		}
		catch (e) {
			println "setWord() failed at address $address"
			throw e
		}
	}

}