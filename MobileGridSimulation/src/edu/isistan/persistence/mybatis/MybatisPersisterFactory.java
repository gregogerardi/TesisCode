package edu.isistan.persistence.mybatis;

import edu.isistan.mobileGrid.persistence.*;

public class MybatisPersisterFactory implements IPersisterFactory {

    IDevicePersister devicePersister = null;
    IJobStatsPersister jobStatsPersister = null;
    IJobTransferedPersister jobTransferedPersister = null;
    ISimulationPersister simPersister = null;

    @Override
    public IDevicePersister getDevicePersister() {
        if (devicePersister == null)
            devicePersister = new DevicePersister();
        return devicePersister;
    }

    @Override
    public IJobStatsPersister getJobStatsPersister() {
        if (jobStatsPersister == null)
            jobStatsPersister = new JobStatsPersister();
        return jobStatsPersister;
    }

    @Override
    public IJobTransferedPersister getJobTransferedPersister() {
        if (jobTransferedPersister == null)
            jobTransferedPersister = new JobTransferedPersister();
        return jobTransferedPersister;
    }

    @Override
    public ISimulationPersister getSimulationPersister() {
        if (simPersister == null)
            simPersister = new SimulationPersister();
        return simPersister;
    }

}
