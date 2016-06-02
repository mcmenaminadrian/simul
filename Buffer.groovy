package simul



class Buffer {
	def inUse
	def storedPacket
	def nextInChain
	def mux
	def left
	
	Buffer(def mux, def left = true, def above = null)
	{
		this.mux = mux
		this.left = left
		inUse = false
		nextInChain = above 
	}
	
	@groovy.transform.Synchronized
	boolean testAndSet()
	{
		if (inUse) {
			return true
		} else {
			inUse = true
			return false
		}
	}
	
	@groovy.transform.Synchronized
	def unSet()
	{
		inUse = false
	}
	
	def requestFromDDR()
	{
		println "Ticks now at ${simulation.ticks}"
	}
	
	def fetchMemory(def packetIn, def previousBuffer = null)
	{
		while (testAndSet())
		{
			mux.simulation.tick()
		}
		if (previousBuffer) {
			previousBuffer.unSet()
		}
		mux.simulation.tick()
		if (nextInChain) {
			nextInChain.fetchMemory(packetIn, this)
		} else {
			if (mux.layer == 0) {
				return requestFromDDR(packetIn)
			} else {
				if (left) {
					mux.muxAbove.bottomBufferLeft.fetchMemory(packetIn)
				} else {
					mux.muxAbove.bottomBufferRight.fetchMemory(packetIn)
				}
			}
		}
	}
	
	@groovy.transform.Synchronized
	boolean testAndUse(def packetIn)
	{
		if (inUse) {
			return true
		} else {
			storedPacket = packetIn
			inUse = true
		}
		return false
	}
	
	
	
	
	
}
