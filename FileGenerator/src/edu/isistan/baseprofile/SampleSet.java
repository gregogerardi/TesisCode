package edu.isistan.baseprofile;

public class SampleSet {

    private String batterySamples;
    private String cpuSamples;

    public SampleSet(String battSamples, String cpuSamples) {
        this.batterySamples = battSamples;
        this.cpuSamples = cpuSamples;
    }

    @Override
    public String toString() {
        return "SampleSet [batterySamples=" + batterySamples + ", cpuSamples="
                + cpuSamples + "]";
    }

    public String getBatterySamples() {
        return batterySamples;
    }

    public void setBatterySamples(String batterySamples) {
        this.batterySamples = batterySamples;
    }

    public String getCpuSamples() {
        return cpuSamples;
    }

    public void setCpuSamples(String cpuSamples) {
        this.cpuSamples = cpuSamples;
    }

}
