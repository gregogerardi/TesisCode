package edu.isistan.gridgain.information.comparator.seas;

import edu.isistan.mobileGrid.node.Device;
import edu.isistan.mobileGrid.node.SchedulerProxy;
import edu.isistan.seas.proxy.DeviceComparator;

public class MobilityComparatorWithAcumJobsAndMips extends DeviceComparator {

    @Override
    public double getValue(Device device) {
        double mips = device.getMIPS();
        double nJobs = SchedulerProxy.PROXY.getIncomingJobs(device) + device.getNumberOfJobs() + 1;
        return (SchedulerProxy.PROXY.getConnectionScore(device) * mips) / nJobs;
    }
}
