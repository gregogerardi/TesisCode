package edu.isistan.gridgain.information.comparator.seas;

import edu.isistan.mobileGrid.node.Device;
import edu.isistan.mobileGrid.node.SchedulerProxy;
import edu.isistan.seas.proxy.DeviceComparator;
import edu.isistan.simulator.Simulation;

public class ESMASFJComparator extends DeviceComparator {

    @Override
    public double getValue(Device device) {
        double nJobs = SchedulerProxy.PROXY.getIncomingJobs(device) + device.getNumberOfJobs() + 1;
        double mips = device.getMIPS();
//        double timePerJob = (92692904d / mips) * 1000d;
        double timePerJob = (2873531614d / mips) * 1000d;
        double timeElapsed = Simulation.getTime() - SchedulerProxy.PROXY.getTimeOffLastConnectionScore(device);
        double score = SchedulerProxy.PROXY.getConnectionScore(device);
        return (score - timeElapsed ) - (nJobs * timePerJob);
    }
}
