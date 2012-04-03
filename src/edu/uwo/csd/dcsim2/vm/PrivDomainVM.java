package edu.uwo.csd.dcsim2.vm;

import edu.uwo.csd.dcsim2.application.Application;
import edu.uwo.csd.dcsim2.core.Utility;

public class PrivDomainVM extends VM {

	public PrivDomainVM(VMDescription vmDescription, Application application) {
		super(vmDescription, application);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void logInfo() {
		logger.debug("PRIV  CPU[" + Utility.roundDouble(resourcesInUse.getCpu(), 2) + 
				"/" + vmAllocation.getCpu() + 
				"/" + Utility.roundDouble(application.getResourceDemand().getCpu(), 2) + "] " + 
				"BW[" + Utility.roundDouble(resourcesInUse.getBandwidth(), 2) + 
				"/" + vmAllocation.getBandwidth() + 
				"/" + Utility.roundDouble(application.getResourceDemand().getBandwidth(), 2) + "] " + 
				"MEM[" + resourcesInUse.getMemory() + 
				"/" + vmAllocation.getMemory() + "] " +
				"STORAGE[" + resourcesInUse.getStorage() + 
				"/" + vmAllocation.getStorage() + "]");
	}
	
}