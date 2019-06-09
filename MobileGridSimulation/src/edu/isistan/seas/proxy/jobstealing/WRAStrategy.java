package edu.isistan.seas.proxy.jobstealing;

import edu.isistan.mobileGrid.node.Device;
import edu.isistan.seas.proxy.DeviceComparator;

import java.util.Collection;
import java.util.Iterator;

public class WRAStrategy implements StealingStrategy {

    @Override
    public Device getVictim(StealerProxy sp, Device stealer) {
        Collection<Device> devices = sp.getDevices();
        if (devices.size() == 0) return null;
        DeviceComparator comparator = sp.getDevComp();
        Iterator<Device> iterator = devices.iterator();
        Device current = iterator.next();
        for (Device next : devices)
            if ((comparator.compare(current, next) > 0) && (next.getWaitingJobs() > 0) && (next != stealer))
                current = next;
        if (current.getWaitingJobs() == 0) return null;
        if (current == stealer) return null;
        return current;
    }

}
