package edu.isistan.gridgain.information.comparator.seas;

import edu.isistan.mobileGrid.node.Device;
import edu.isistan.seas.proxy.DeviceComparator;

import java.util.HashMap;
import java.util.Map;

public class MultipleComparator extends DeviceComparator {

    private Map<DeviceComparator, Double> comparatorsPriorities = new HashMap<>();

    public MultipleComparator(Map<DeviceComparator, Double> comparatorsPriorities) throws Exception {
        double acumWeight = 0d;
        for (Double d : comparatorsPriorities.values()) {
            acumWeight += d;
        }
        if (acumWeight != 1d) {
            throw new Exception("The weights of comparators do not acum one");
        } else {
            this.comparatorsPriorities = comparatorsPriorities;
        }
    }

    //todo fix the constructor
    public MultipleComparator(String args) {
/*        String comparators[] = args.split(":");
        for (String comparatorAndArgs: comparators){
            String comparatorClass = comparatorAndArgs.split("<")[0];
            String comparatorArg = comparatorAndArgs.split("<")[1];
        }
        String compClazz[] = (this.line.split(" ")[1].trim()).split("-");
        String clazzName = compClazz[0];
        simulationTuple.setComparator(clazzName);
        Class<DeviceComparator> clazz = (Class<DeviceComparator>) Class.forName(clazzName);
        DeviceComparator comp;
        if (compClazz.length > 1) {//has arguments
            String clazzArg = compClazz[1];
            comp = clazz.getConstructor(String.class).newInstance(clazzArg);
        } else {
            comp = clazz.newInstance();
        }
        this.setProperties(comp, clazz, this.line.split(" "), 2);
        this.simLock.lock();
        ((GridEnergyAwareLoadBalancing) SchedulerProxy.PROXY).setDevComp(comp);
        this.simLock.unlock();
        this.nextLine();*/
    }

    @Override
    public double getValue(Device device) {
        double value = 0;
        for (Map.Entry<DeviceComparator, Double> e : comparatorsPriorities.entrySet()) {
            value += e.getKey().getValue(device) * e.getValue();
        }
        return value;
    }
}
