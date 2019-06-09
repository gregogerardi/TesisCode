package edu.isistan.mobileGrid.persistence;

import edu.isistan.mobileGrid.persistence.DBEntity.SimulationTuple;

import java.sql.SQLException;

public interface ISimulationPersister extends SQLSessionFactory {

    void insertSimulation(SQLSession session, SimulationTuple simulationTuple) throws SQLException;

    void updateSimulation(SQLSession session, SimulationTuple simulationTuple) throws SQLException;
}
