package simul



class Buffer {
	def inUse
	def storedPacket
	
	Buffer()
	{
		inUse = false
	}
	
	@groovy.transform.Synchronized
	testAndUse(def packetIn)
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
