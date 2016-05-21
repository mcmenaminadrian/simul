package simul

class Core {

	private static MAXREG = 32
	private def localMemory
	private def simulation
	private static def order = 0
	private int row
	private int column
	private def index

	
	enum RegList {
		REG0(0), REG1(1), REG2(2), REG3(3),
		REG4(4), REG5(5), REG6(6), REG7(7),
		REG8(8), REG9(9), REG10(10), REG11(11),
		REG12(12), REG13(13), REG14(14), REG15(15),
		REG16(16), REG17(17), REG18(18), REG19(19),
		REG20(20), REG21(21), REG22(22), REG23(23),
		REG24(24), REG25(25), REG26(26), REG27(27),
		REG28(28), REG29(29), REG30(30), REG31(31)
		
		def registerNumber
		long value
		
		private RegList(def numb)
		{
			registerNumber = numb
			value = 0
		}
	}
	
	def executionEnvironment
	
	
	Core(def parent, def number, def totalNumber = Simulation.CORE_COUNT,
		def memorySizeKB = Simulation.LOCAL_MEM_SIZE, 
		def memoryStartAddress = Simulation.LOCAL_MEM_START)
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
		row = number / order
		column = number % order
		index = number
		simulation = parent
		localMemory = new MemoryArray(parent, memorySizeKB * 1024, memoryStartAddress)
		executionEnvironment = new ExecutionEnvironment(parent, this)
	}
	
	def getRegisterValue(def regIn)
	{
		if (regIn == REG0) {
			return 0
		}
		
		if (regIn.registerNumber < 0 || regIn.registerNumber > MAXREG) {
			println "Bad register read $regIn on core $index"
			throw new IllegalArgumentException()
		}
		
		return (regIn.value & 0xFFFFFFFF)
	}
	
	def setRegisterValue(def regOut, def value)
	{
		if (regOut == REG0) {
			return
		}
		if (regOut.registerNumber < 0 || regOut.registerNumber > MAXREG) {
			println "Bad register write $regOut on core $index"
			throw new IllegalArgumentException()
		}
		
		if (value != value & 0xFFFFFFFF) {
			println "Bad value write to register $regOut on core $index"
			throw new IllegalArgumentException()
		}
		
		regOut.value = (value & 0xFFFFFFFF)
	}
	
	def readMemoryAddress(def memAddress)
	{
		if (memAddress < 0 || memAddress >= Simulation.TOTAL_MEM_SIZE) {
			println "Bad memory read on core $index at address $memAddress"
			throw new IllegalArgumentException()
		}
		
	}
	
}