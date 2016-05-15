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

	Simulation(def cores = 256, def memSize = 0x100000000, def endianess = 0)
	{
		//0 for little endian, 1 for big endian
		endian = endianess
		globalMemory = new MemoryArray(this, memSize, 0)
		coreCount = cores
	}	

}


	def stuff = new Simulation()
	stuff.coreArray = []
	for (coreNumb in 1..stuff.coreCount) {stuff.coreArray <<
		new Core(stuff, coreNumb - 1)}
	 
