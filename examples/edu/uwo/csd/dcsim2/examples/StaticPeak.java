package edu.uwo.csd.dcsim2.examples;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import edu.uwo.csd.dcsim2.*;
import edu.uwo.csd.dcsim2.core.*;
import edu.uwo.csd.dcsim2.vm.*;

public class StaticPeak extends DCSimulationTask {

	private static Logger logger = Logger.getLogger(StaticPeak.class);
	
	public static void main(String args[]) {
		
		Simulation.initializeLogging();

		Collection<SimulationTask> completedTasks;
		SimulationExecutor executor = new SimulationExecutor();
		
		executor.addTask(new StaticPeak("staticpeak-1", 1088501048448116498l));
//		executor.addTask(new StaticPeak("staticpeak-2", 3081198553457496232l));
//		executor.addTask(new StaticPeak("staticpeak-3", -2485691440833440205l));
//		executor.addTask(new StaticPeak("staticpeak-4", 2074739686644571611l));
//		executor.addTask(new StaticPeak("staticpeak-5", -1519296228623429147l));
		
		completedTasks = executor.execute();
		
		for(SimulationTask task : completedTasks) {
			logger.info(task.getName());
			ExampleHelper.printMetrics(task.getResults());
		}
		
	}
	
	public StaticPeak(String name, long randomSeed) {
		super(name, 864000000);
		this.setMetricRecordStart(86400000);
		this.setRandomSeed(randomSeed);
	}


	@Override
	public void setup(DataCentreSimulation simulation) {
		DataCentre dc = ExampleHelper.createDataCentre(simulation);
		simulation.addDatacentre(dc);
		
		ArrayList<VMAllocationRequest> vmList = ExampleHelper.createVmList(simulation, false);
		
		ExampleHelper.placeVms(vmList, dc);

	}
	
}
