package simul

class Mux {

	def layer
	def lowRange
	def highRange
	def muxAbove
	def muxLowerLeft
	def muxLowerRight
	def topBuffer
	def bottomBufferLeft
	def bottomBufferRight
	
	Mux(def noc, def parentMux, def boundRange, def layer)
	{
		this.layer = layer
		muxAbove = parentMux
		def rSize = boundRange.size()
		lowRange = boundRange.from..(boundRange.from + (rSize/2) - 1)
		highRange = (lowRange.to + 1)..boundRange.to
		topBuffer = new Buffer()
		bottomBufferLeft = new Buffer()
		bottomBufferRight = new Buffer()
		if (lowRange.size() == 1) {
			noc.attachMux(this)
		} else {
			new Mux(noc, this, lowRange, ++layer)
			new Mux(noc, this, highRange, layer)
		}
		
	}
}
