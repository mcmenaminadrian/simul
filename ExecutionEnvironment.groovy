package simul

abstract class ExecutionEnvironment extends Script {

	def binding
	
	ExecutionEnvironment(def parent, def core)
	{
		binding = new Binding(simulation: parent, cpu: core)
	}
}
