package simul

class ExecutionEnvironment extends Script {

	def eeBinding
	def cpu
	def simulation
	
	ExecutionEnvironment(def parent, def core)
	{
		cpu = core
		simulation = parent
		
		eeBinding = new Binding(simulation: parent, cpu: core,
			*: RegList.values().collectEntries{ [(it.name()): it.registerNumber]}
			)
	}
	
	//closures
	def add = {regD, regA, regB ->
		
		def longA = cpu.getRegisterValue(regA)
		def longB = cpu.getRegisterValue(regB)
		def longD = (longA + longB) &0xFFFFFFFF
		cpu.setRegisterValue(regD, longD) 
	}
	
	def run()
	{
		simulation.waitToStart()
		setBinding(eeBinding)
		add r9, r0, r3
	}
}
