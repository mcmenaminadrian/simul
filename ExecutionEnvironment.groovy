package simul

class ExecutionEnvironment extends Script {

	def eeBinding
	def cpu
	
	ExecutionEnvironment(def parent, def core)
	{
		eeBinding = new Binding(simulation: parent, cpu: core)
	}
	
	//closures
	
	
	def run()
	{}
}
