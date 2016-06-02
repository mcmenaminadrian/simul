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
	def simulation
	
	Mux(def noc, def parentMux, def boundRange, def layer, def left = false)
	{
		this.layer = layer
		simulation = noc
		muxAbove = parentMux
		def rSize = boundRange.size()
		lowRange = boundRange.from..(boundRange.from + (rSize/2) - 1)
		highRange = (lowRange.to + 1)..boundRange.to
		topBuffer = new Buffer(this, left)
		bottomBufferLeft = new Buffer(this, true, topBuffer)
		bottomBufferRight = new Buffer(this, false, topBuffer)
		if (lowRange.size() == 1) {
			try {
				noc.attachMux(this)
			} catch (NullPointerException e) {
				println "Could not build Bluetree - null pointer"
			}
		} else {
			layer++
			muxLowerLeft = new Mux(noc, this, lowRange, layer, true)
			muxLowerRight = new Mux(noc, this, highRange, layer, false)
		}
		
	}
}
