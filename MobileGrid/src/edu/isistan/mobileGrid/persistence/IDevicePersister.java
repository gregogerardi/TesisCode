package edu.isistan.mobileGrid.persistence;

import edu.isistan.mobileGrid.persistence.DBEntity.DeviceTuple;

import java.sql.SQLException;

public interface IDevicePersister extends SQLSessionFactory {

    void saveDeviceIntoMemory(String name, DeviceTuple deviceTuple);

    void insertDevice(SQLSession session, DeviceTuple device) throws SQLException;

    void insertInMemoryDeviceTuples(SQLSession session);

    DeviceTuple getDevice(String name);

}
