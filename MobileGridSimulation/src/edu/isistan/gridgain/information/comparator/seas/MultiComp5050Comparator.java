package edu.isistan.gridgain.information.comparator.seas;

import edu.isistan.mobileGrid.node.Device;
import edu.isistan.seas.proxy.DeviceComparator;
import edu.isistan.seas.util.ProgressiveStandardizer;

import java.util.HashMap;
import java.util.Map;

public class MultiComp5050Comparator extends DeviceComparator {
    private MultipleComparator multipleComparator;

    public MultiComp5050Comparator() throws Exception {
        StandarComparator standarComparatorMob = new StandarComparator(
                new ProgressiveStandardizer(),
                new EnhancedMobilityComparatorWithAcumJobsAndKnownJobSize());
        StandarComparator standarComparatorSeas = new StandarComparator(
                new ProgressiveStandardizer(),
                new EnhancedSEASComparator());
        Map <DeviceComparator, Double> comparatorsPriorities = new HashMap<>();
        comparatorsPriorities.put(standarComparatorMob,0.5d);
        comparatorsPriorities.put(standarComparatorSeas,0.5d);
        this.multipleComparator = new MultipleComparator(comparatorsPriorities);

    }

    @Override
    public double getValue(Device device) {
        return multipleComparator.getValue(device);
    }
}
