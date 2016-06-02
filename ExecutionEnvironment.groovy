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
		simulation.tick()
	}
	
	def run()
	{
		setBinding(eeBinding)
		simulation.waitToStart()
		cpu.fetchMemoryAddress(0x1233)
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		add r9, r0, r3
		add r10, r9, r7
		add r15, r12, r0
		add r19, r0, r0
		for (x in 1..1000) {
			add r9, r0, r3
			add r10, r9, r7
			add r15, r12, r0
		}
		add r19, r0, r0
		println "Completed thread with count at ${simulation.clock}"
	}
}
