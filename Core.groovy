package simul


enum RegList {
	r0(0), r1(1), r2(2), r3(3),
	r4(4), r5(5), r6(6), r7(7),
	r8(8), r9(9), r10(10), r11(11),
	r12(12), r13(13), r14(14), r15(15),
	r16(16), r17(17), r18(18), r19(19),
	r20(20), r21(21), r22(22), r23(23),
	r24(24), r25(25), r26(26), r27(27),
	r28(28), r29(29), r30(30), r31(31)
	
	def registerNumber
	
	private RegList(def numb)
	{
		registerNumber = numb
	}
}



class Core {

	private static MAXREG = 32
	private def localMemory
	private def simulation
	private static def order = 0
	private int row
	private int column
	private def index
	private def registerList = [0L]
	private def threadArray = []
	def bufferUp
	
	def executionEnvironment
	
	
	Core(def parent, def number, def totalNumber = Noc.CORE_COUNT,
		def memorySizeKB = Noc.LOCAL_MEM_SIZE,
		def memoryStartAddress = Noc.LOCAL_MEM_START)
	{
		if (order == 0) {
			def root = Math.sqrt(totalNumber)
			Integer iRoot = root
			if (iRoot == root) {
				order = iRoot
			} else {
				while (totalNumber > 1) {
					totalNumber >>= 1
					order++
				}
			}
		}
		(2 .. MAXREG).each {
			registerList << 0
		}
		row = number / order
		column = number % order
		index = number
		simulation = parent
		localMemory = new MemoryArray(parent, memorySizeKB * 1024, memoryStartAddress)
	}
	
	def startUp()
	{
		executionEnvironment = new ExecutionEnvironment(simulation, this)
		def threadIt = Thread.start {
			executionEnvironment.run()
		}
	}
	
	def attachBuffer(def buffer)
	{
		bufferUp = buffer
	}
	
	def getRegisterValue(def regIn)
	{
		if (regIn == 0) {
			return 0
		}
		
		if (regIn < 0 || regIn > MAXREG) {
			println "Bad register read $regIn on core $index"
			throw new IllegalArgumentException()
		}
		
		return (registerList[regIn] & 0xFFFFFFFF)
	}
	
	def setRegisterValue(def regOut, def value)
	{
		if (regOut == 0) {
			return
		}
		if (regOut< 0 || regOut > MAXREG) {
			println "Bad register write $regOut on core $index"
			throw new IllegalArgumentException()
		}
		
		if (value != (value & 0xFFFFFFFF)) {
			println "Bad value write to register $regOut on core $index"
			throw new IllegalArgumentException()
		}
		
		registerList[regOut] = (value & 0xFFFFFFFF)
	}

}
