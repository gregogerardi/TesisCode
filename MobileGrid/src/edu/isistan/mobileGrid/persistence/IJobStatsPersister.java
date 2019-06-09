package edu.isistan.mobileGrid.persistence;

import edu.isistan.mobileGrid.persistence.DBEntity.JobStatsTuple;

import java.sql.SQLException;

public interface IJobStatsPersister extends SQLSessionFactory {

    void insertJobStats(SQLSession session, JobStatsTuple jobStats) throws SQLException;

    void updateJobStats(SQLSession session, JobStatsTuple jobStats) throws SQLException;

}
