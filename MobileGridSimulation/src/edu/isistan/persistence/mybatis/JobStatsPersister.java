package edu.isistan.persistence.mybatis;

import edu.isistan.mobileGrid.persistence.DBEntity.JobStatsTuple;
import edu.isistan.mobileGrid.persistence.IJobStatsPersister;
import edu.isistan.mobileGrid.persistence.SQLSession;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;

public class JobStatsPersister extends IbatisSQLSessionFactory implements IJobStatsPersister {


    @Override
    public void insertJobStats(SQLSession session, JobStatsTuple jobStats)
            throws SQLException {
        SqlSession ibatisSession = ((IbatisSQLSession) session).unwrap();
        try {
            ibatisSession.insert("insertJobStats", jobStats);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateJobStats(SQLSession session, JobStatsTuple jobStats)
            throws SQLException {
        // TODO Auto-generated method stub

    }

}
