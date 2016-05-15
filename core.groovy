package simul

class Core {

	private def localMemory
	private def registerFile
	private def simulation
	private static def order = 0
	private int row
	private int column
	private def index
	def executionEnvironment
		
	public
	Core(def parent, def number, def totalNumber = 256, def memorySizeKB = 16, 
		def memoryStartAddress = 0xA0000000)
	{
		if (order == 0) {
			def root = Math.sqrt(totalNumber)
			Integer iRoot = root
			if (iRoot == root) {
				order = iRoot
			} else {
				while (totalNumber > 1) {
					totalNumber >>= 1
					order++
				}
			}
		}
		row = number / order
		column = number % order
		index = number
		simulation = parent
		localMemory = new MemoryArray(parent, memorySizeKB * 1024, memoryStartAddress)
		executionEnvironment = new ExecutionEnvironment(parent, this)
	}
	
}
