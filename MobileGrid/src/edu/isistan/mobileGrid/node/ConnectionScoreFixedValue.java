package edu.isistan.mobileGrid.node;
public class ConnectionScoreFixedValue implements ConnectionScoreCalculator {
    private double score;

    public ConnectionScoreFixedValue(String[] args) {
        this.score = Long.parseLong(args[0]);
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
