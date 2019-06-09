package edu.isistan.connectioneventsgenerator.intervalcalculators;

public class AlwaysConnectedInterval extends IntervalCalculator {
    public AlwaysConnectedInterval(String args) {
    }

    @Override
    long getNextConnectionEvent() {
        return 0;
    }

    @Override
    long getNextDisconnectionEvent() {
        return Long.MAX_VALUE;
    }
}
