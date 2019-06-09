package edu.isistan.baseprofile;

public interface ProfileReader {

    void readSample();

    void closeReader();

    long getCurrentSampleTime();

    long getPreviousSampleTime();

    String currentSample();

    String previousSample();

}
