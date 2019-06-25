package edu.isistan.connectioneventsgenerator.intervalcalculators;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.concurrent.ThreadLocalRandom;

public class FixedNormalDistributionInterval extends IntervalCalculator {

    private static boolean parametersSetted;
    private static long maxTimeToConnect;
    private static long minTimeToConnect;
    private static long maxStdTimeToConnect;
    private static long minStdTimeToConnect;
    private static long maxStdTimeToDisconnect;
    private static long minStdTimeToDisconnect;
    private final NormalDistribution normalDistributionToConnection;
    private final NormalDistribution normalDistributionToDisconnection;

    public FixedNormalDistributionInterval(long mediaScore, long maxTimeToConnect, long minTimeToConnect, long maxStdTimeToConnect, long minStdTimeToConnect, long maxStdTimeToDisconnect, long minStdTimeToDisconnect) {
        if (!isParametersSetted())
            setParameters(maxTimeToConnect, minTimeToConnect, maxStdTimeToConnect, minStdTimeToConnect, maxStdTimeToDisconnect, minStdTimeToDisconnect);
        long averageTimeToConnect = ThreadLocalRandom.current().nextLong(minTimeToConnect, maxTimeToConnect);
        double stdTimeToConnect = ThreadLocalRandom.current().nextLong(minStdTimeToConnect, maxStdTimeToConnect);
        double stdTimeToDisconnect = ThreadLocalRandom.current().nextLong(minStdTimeToDisconnect, maxStdTimeToDisconnect);
        normalDistributionToConnection = new NormalDistribution(averageTimeToConnect,stdTimeToConnect);
        normalDistributionToDisconnection = new NormalDistribution(mediaScore,stdTimeToDisconnect);
    }

    public FixedNormalDistributionInterval(String args,long mediaScore) {
        String[] arguments = args.split("-");
        if (arguments.length != 8) throw new IllegalArgumentException("Wrong amount of arguments");
        else {
            if (!isParametersSetted())
                setParameters(Long.parseLong(arguments[0]), Long.parseLong(arguments[1]), Long.parseLong(arguments[2]), Long.parseLong(arguments[3]),Long.parseLong(arguments[4]), Long.parseLong(arguments[5]));
            long averageTimeToConnect = ThreadLocalRandom.current().nextLong(minTimeToConnect, maxTimeToConnect);
            double stdTimeToConnect = ThreadLocalRandom.current().nextLong(minStdTimeToConnect, maxStdTimeToConnect);
            double stdTimeToDisconnect = ThreadLocalRandom.current().nextLong(minStdTimeToDisconnect, maxStdTimeToDisconnect);
            normalDistributionToConnection = new NormalDistribution(averageTimeToConnect,stdTimeToConnect);
            normalDistributionToDisconnection = new NormalDistribution(mediaScore,stdTimeToDisconnect);
        }
    }

    private void setParameters(long maxTimeToConnect, long minTimeToConnect, long maxStdTimeToConnect, long minStdTimeToConnect, long maxStdTimeToDisconnect, long minStdTimeToDisconnect) {
        FixedNormalDistributionInterval.maxTimeToConnect = maxTimeToConnect;
        FixedNormalDistributionInterval.minTimeToConnect = minTimeToConnect;
        FixedNormalDistributionInterval.maxStdTimeToConnect = maxStdTimeToConnect;
        FixedNormalDistributionInterval.minStdTimeToConnect = minStdTimeToConnect;
        FixedNormalDistributionInterval.maxStdTimeToDisconnect = maxStdTimeToDisconnect;
        FixedNormalDistributionInterval.minStdTimeToDisconnect = minStdTimeToDisconnect;
        parametersSetted = true;
    }

    @Override
    long getNextConnectionEvent() {
        long result=(long)normalDistributionToConnection.sample();
        return result>=0?result:0;
    }

    @Override
    long getNextDisconnectionEvent() {
        long result=(long)normalDistributionToDisconnection.sample();
        return result>=0?result:0;
    }

    public boolean isParametersSetted() {
        return parametersSetted;
    }
}

