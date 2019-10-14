package edu.isistan.gridgain.information.comparator.seas;

import edu.isistan.mobileGrid.node.Device;
import edu.isistan.mobileGrid.node.SchedulerProxy;
import edu.isistan.seas.proxy.DeviceComparator;
import edu.isistan.simulator.Simulation;

public class ESMASComparator extends DeviceComparator {

    @Override
    public double getValue(Device device) {
        double nJobs = SchedulerProxy.PROXY.getIncomingJobs(device) + device.getNumberOfJobs() + 1;
        return (SchedulerProxy.PROXY.getConnectionScore(device) -  (Simulation.getTime() - SchedulerProxy.PROXY.getTimeOffLastConnectionScore(device)))/ nJobs;
    }
}
