package edu.isistan.connectioneventsgenerator.intervalcalculators;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.concurrent.ThreadLocalRandom;

public class NormalDistributionInterval extends IntervalCalculator {

    private static boolean parametersSetted;
    private static long maxTimeToConnect;
    private static long minTimeToConnect;
    private static long maxTimeToDisconnect;
    private static long minTimeToDisconnect;
    private static long maxStdTimeToConnect;
    private static long minStdTimeToConnect;
    private static long maxStdTimeToDisconnect;
    private static long minStdTimeToDisconnect;
    private final NormalDistribution normalDistributionToConnection;
    private final NormalDistribution normalDistributionToDisconnection;

    public NormalDistributionInterval(long maxTimeToConnect, long minTimeToConnect, long maxTimeToDisconnect, long minTimeToDisconnect, long maxStdTimeToConnect, long minStdTimeToConnect, long maxStdTimeToDisconnect, long minStdTimeToDisconnect) {
        if (!isParametersSetted())
            setParameters(maxTimeToConnect, minTimeToConnect, maxTimeToDisconnect, minTimeToDisconnect, maxStdTimeToConnect, minStdTimeToConnect, maxStdTimeToDisconnect, minStdTimeToDisconnect);
        long averageTimeToConnect = ThreadLocalRandom.current().nextLong(minTimeToConnect, maxTimeToConnect);
        long averageTimeToDisconnect = ThreadLocalRandom.current().nextLong(minTimeToDisconnect, maxTimeToDisconnect);
        double stdTimeToConnect = ThreadLocalRandom.current().nextLong(minStdTimeToConnect, maxStdTimeToConnect);
        double stdTimeToDisconnect = ThreadLocalRandom.current().nextLong(minStdTimeToDisconnect, maxStdTimeToDisconnect);
        normalDistributionToConnection = new NormalDistribution(averageTimeToConnect,stdTimeToConnect);
        normalDistributionToDisconnection = new NormalDistribution(averageTimeToDisconnect,stdTimeToDisconnect);
    }

    public NormalDistributionInterval(String args) {
        String[] arguments = args.split("-");
        if (arguments.length != 8) throw new IllegalArgumentException("Wrong amount of arguments");
        else {
            if (!isParametersSetted())
                setParameters(Long.parseLong(arguments[0]), Long.parseLong(arguments[1]), Long.parseLong(arguments[2]), Long.parseLong(arguments[3]),Long.parseLong(arguments[4]), Long.parseLong(arguments[5]), Long.parseLong(arguments[6]), Long.parseLong(arguments[7]));
            long averageTimeToConnect = ThreadLocalRandom.current().nextLong(minTimeToConnect, maxTimeToConnect);
            long averageTimeToDisconnect = ThreadLocalRandom.current().nextLong(minTimeToDisconnect, maxTimeToDisconnect);
            double stdTimeToConnect = ThreadLocalRandom.current().nextLong(minStdTimeToConnect, maxStdTimeToConnect);
            double stdTimeToDisconnect = ThreadLocalRandom.current().nextLong(minStdTimeToDisconnect, maxStdTimeToDisconnect);
            normalDistributionToConnection = new NormalDistribution(averageTimeToConnect,stdTimeToConnect);
            normalDistributionToDisconnection = new NormalDistribution(averageTimeToDisconnect,stdTimeToDisconnect);
        }
    }

    private void setParameters(long maxTimeToConnect, long minTimeToConnect, long maxTimeToDisconnect, long minTimeToDisconnect, long maxStdTimeToConnect, long minStdTimeToConnect, long maxStdTimeToDisconnect, long minStdTimeToDisconnect) {
        NormalDistributionInterval.maxTimeToConnect = maxTimeToConnect;
        NormalDistributionInterval.minTimeToConnect = minTimeToConnect;
        NormalDistributionInterval.maxTimeToDisconnect = maxTimeToDisconnect;
        NormalDistributionInterval.minTimeToDisconnect = minTimeToDisconnect;
        NormalDistributionInterval.maxStdTimeToConnect = maxStdTimeToConnect;
        NormalDistributionInterval.minStdTimeToConnect = minStdTimeToConnect;
        NormalDistributionInterval.maxStdTimeToDisconnect = maxStdTimeToDisconnect;
        NormalDistributionInterval.minStdTimeToDisconnect = minStdTimeToDisconnect;
        parametersSetted = true;
    }

    @Override
    long getNextConnectionEvent() {
        return (long)normalDistributionToConnection.sample();
    }

    @Override
    long getNextDisconnectionEvent() {
        return (long) normalDistributionToDisconnection.sample();
    }

    public boolean isParametersSetted() {
        return parametersSetted;
    }
}

