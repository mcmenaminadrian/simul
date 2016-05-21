package simul

class Simulation {
	def globalMemory
	def signalNetwork
	def processors
	def blueTree
	def coreArray
	def outputDevice
	def endian
	def coreCount
	static CORE_COUNT = 256
	static TOTAL_MEM_SIZE = 0x100000000
	static DEFAULT_ENDIAN = 0
	static LOCAL_MEM_SIZE = 16
	static LOCAL_MEM_START = 0xA0000000

	Simulation(def cores = this.CORE_COUNT, def memSize = this.TOTAL_MEM_SIZE,
		def endianess = this.DEFAULT_ENDIAN)
	{
		//0 for little endian, 1 for big endian
		endian = endianess
		globalMemory = new MemoryArray(this, memSize, 0)
		coreCount = cores
	}	

}


	def stuff = new Simulation()
	stuff.coreArray = []
	for (coreNumb in 1..stuff.coreCount) {
		stuff.coreArray << new Core(stuff, coreNumb - 1)
	}
	 
