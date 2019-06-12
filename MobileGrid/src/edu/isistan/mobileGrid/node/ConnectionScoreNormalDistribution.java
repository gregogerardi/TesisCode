package edu.isistan.mobileGrid.node;
import org.apache.commons.math3.distribution.NormalDistribution;

public class ConnectionScoreNormalDistribution implements ConnectionScoreCalculator {
    private double score;

    public ConnectionScoreNormalDistribution(String[] args) {

        this.score = new NormalDistribution(Double.parseDouble(args[0]),Double.parseDouble(args[1])).sample();
    }

    @Override
    public void average(long time, boolean connect) {
    //does not use time and connect, always return a pre-setted score
    }

    @Override
    public double getScore() {
        return score;
    }
}
