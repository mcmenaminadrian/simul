package simul

abstract class ExecutionEnvironment extends Script {

	def eeBinding
	
	ExecutionEnvironment(def parent, def core)
	{
		eeBinding = new Binding(simulation: parent, cpu: core)
	}
	
	def run()
	{}
}
