package edu.isistan.mobileGrid.persistence;

public interface SQLSession {

    void close();

    void commit();

}
