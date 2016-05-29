package simul

import groovy.transform.Synchronized

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
	static long TARGET_COUNT = CORE_COUNT
	static long run_count = 0
	static long tick_count = 0
	static BigInteger clock = 0

	Noc(def cores = CORE_COUNT, def memSize = TOTAL_MEM_SIZE,
		def endianess = DEFAULT_ENDIAN)
	{
		//0 for little endian, 1 for big endian
		endian = endianess
		globalMemory = new MemoryArray(this, memSize, 0)
		coreCount = cores
	}
	
	//make sure all core start together
	def synchronized waitToStart()
	{
		run_count++
		if (run_count < TARGET_COUNT) {
			try {
				wait()
			} catch (InterruptedException e) {}
		} else {
			run_count = 0
			notifyAll()
		}
	}
	
	def synchronized tick()
	{
		tick_count++
		if (tick_count < TARGET_COUNT) {
			try {
				wait()
			} catch (InterruptedException e) {}
			
		} else {
			clock++
			tick_count = 0
			notifyAll()
		}
	}
	
	def attachMux(def mux)
	{
		def lHRange = mux.lowRange.from..mux.highRange.from
		def core = coreArray.findAll(lHRange.contains(it.index))
		core[0].attachMuxBuffer(mux.bottomBufferLeft)
		core[1].attachMuxBuffer(mux.bottomBufferRight)
	}

}

	def stuff = new Noc()
	stuff.coreArray = []
	for (coreNumb in 1..stuff.coreCount) {
		stuff.coreArray << new Core(stuff, coreNumb - 1)
	}
	new Mux(stuff, null, 1..stuff.coreCount, 0)
	
	stuff.coreArray.each {it.startUp()}
	println "Clock is ${stuff.clock}"
	