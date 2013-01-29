package edu.uwo.csd.dcsim.host.scheduler;

import edu.uwo.csd.dcsim.host.Host;
import edu.uwo.csd.dcsim.vm.VMAllocation;
import edu.uwo.csd.dcsim.vm.VirtualResources;

public abstract class ResourceScheduler {

	protected Host host;
	private ResourceSchedulerState state;
	private int remainingCpu;
		
	public enum ResourceSchedulerState {READY, COMPLETE;}
		
	/**
	 * Initialize scheduling, including resetting scheduled resources from last time interval.
	 */
	public final void initScheduling() {
		
		VirtualResources scheduledResources = new VirtualResources();
		
		//reset the scheduled resources of the privileged domain
		VMAllocation privAlloc = host.getPrivDomainAllocation();
		
		scheduledResources.setCpu(0); //set CPU to 0, as this will be scheduled in rounds later
		
		//at present, all other resources are allocated their full allocation
		scheduledResources.setBandwidth(privAlloc.getBandwidth());
		scheduledResources.setMemory(privAlloc.getMemory());
		scheduledResources.setStorage(privAlloc.getStorage());
		
		privAlloc.getVm().scheduleResources(scheduledResources);
		
		//for each VM in the host, reset the resources that are current scheduled for its use
		for (VMAllocation vmAlloc : host.getVMAllocations()) {
			scheduledResources = new VirtualResources();
			
			scheduledResources.setCpu(0); //set CPU to 0, as this will be scheduled in rounds later
			
			//at present, all other resources are scheduled their full allocation
			scheduledResources.setBandwidth(vmAlloc.getBandwidth());
			scheduledResources.setMemory(vmAlloc.getMemory());
			scheduledResources.setStorage(vmAlloc.getStorage());
			
			vmAlloc.getVm().scheduleResources(scheduledResources);
		}
		
		//set the remaining CPU to the total CPU available
		remainingCpu = host.getTotalCpu();
		
		//indicate that the resource scheduler is ready to schedule resources
		state = ResourceSchedulerState.READY;
		
		beginScheduling();
	}
	
	protected abstract void beginScheduling();
	public abstract void schedulePrivDomain();
	public abstract void beginRound();
	public abstract boolean scheduleVM(VMAllocation vmAlloc);
	
	protected final void scheduleCpu(int cpu) {
		remainingCpu -= cpu;
		
		if (remainingCpu <= 0) {
			state = ResourceSchedulerState.COMPLETE;
		}

		if (remainingCpu < 0)
			throw new RuntimeException("Resource Scheduler on Host #" + host.getId() + " used more CPU than available");
	}
	
	protected int getRemainingCpu() {
		return remainingCpu;
	}
	
	public void setHost(Host host) {
		this.host = host;
	}
	
	public ResourceSchedulerState getState() {
		return state;
	}
	
}
