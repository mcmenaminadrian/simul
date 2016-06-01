package simul

class Packet {

	def memory = []
	long soughtAddress
	def soughtLength
	def originatingProcessor
	
	Packet(address, length, processor)
	{
		soughtAddress = address & 0xFFFFFFFF
		soughtLength = length
		originatingProcessor = processor
	}
}
