package edu.isistan.gridgain.information.comparator.seas;

import edu.isistan.mobileGrid.node.Device;
import edu.isistan.mobileGrid.node.SchedulerProxy;
import edu.isistan.seas.proxy.DeviceComparator;

public class MobilityComparatorWithAcumJobs extends DeviceComparator {

    @Override
    public double getValue(Device device) {
        double nJobs = SchedulerProxy.PROXY.getIncomingJobs(device) + device.getNumberOfJobs() + 1;
        return SchedulerProxy.PROXY.getConnectionScore(device) / nJobs;
    }
}
