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
	
	def requestFromDDR(def packetIn)
	{
		println "Ticks now at ${mux.simulation.clock}"
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
			return nextInChain.fetchMemory(packetIn, this)
		} else {
			if (mux.layer == 0) {
				unSet()
				return requestFromDDR(packetIn)
			} else {
				if (left) {
					return mux.muxAbove.bottomBufferLeft.fetchMemory(
						packetIn, this)
				} else {
					return mux.muxAbove.bottomBufferRight.fetchMemory(
						packetIn, this)
				}
			}
		}
	}
	
	
}
