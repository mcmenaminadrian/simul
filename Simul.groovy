package simul

class Simulation {
	def globalMemory
	def signalNetwork
	def blueTree
	def coreArray
	def outputDevice
	def endian

	Simulation()
	{
		//0 for little endian, 1 for big endian
		endian = 0 
		globalMemory = new MemoryArray(this, 0x100, 0)
	}	

}

	blah = new Simulation();
	blah.globalMemory.setWord(0x90, 0xFFFFFF00)
	println blah.globalMemory.getWord(0x90)
	println blah.globalMemory.getHalfWord(0x90)
	println blah.globalMemory.getHalfWord(0x92)
	println blah.globalMemory.getByte(0x91)

 