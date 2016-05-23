package simul

class Noc {
	def globalMemory
	def signalNetwork
	def processors
	def blueTree
	def coreArray
	def outputDevice
	def endian
	def coreCount
	static final long CORE_COUNT = 256
	static final long TOTAL_MEM_SIZE = 0x100000000
	static final long DEFAULT_ENDIAN = 0
	static final long LOCAL_MEM_SIZE = 16
	static final long LOCAL_MEM_START = 0xA0000000

	Noc(def cores = CORE_COUNT, def memSize = TOTAL_MEM_SIZE,
		def endianess = DEFAULT_ENDIAN)
	{
		//0 for little endian, 1 for big endian
		endian = endianess
		globalMemory = new MemoryArray(this, memSize, 0)
		coreCount = cores
	}	

}

	println "Just run"

	def stuff = new Noc()
	stuff.coreArray = []
	for (coreNumb in 1..stuff.coreCount) {
		stuff.coreArray << new Core(stuff, coreNumb - 1)
	}