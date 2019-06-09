package edu.isistan.mobileGrid.persistence;

import edu.isistan.mobileGrid.persistence.DBEntity.JobTransfer;

import java.sql.SQLException;

public interface IJobTransferedPersister extends SQLSessionFactory {

    void insertJobTransfered(SQLSession session, JobTransfer jobTransfer) throws SQLException;

}
