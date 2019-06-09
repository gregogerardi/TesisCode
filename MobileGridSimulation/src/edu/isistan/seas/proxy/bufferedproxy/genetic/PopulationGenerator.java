package edu.isistan.seas.proxy.bufferedproxy.genetic;

import edu.isistan.mobileGrid.jobs.Job;
import edu.isistan.mobileGrid.node.Device;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PopulationGenerator {

    public static SimpleGASchedulerProxy gaProxy;
    public static HashMap<Integer, Device> devicesId;
    public static HashMap<Device, Integer> devicesObjects;

    public PopulationGenerator() {

    }

    public abstract ArrayList<Short[]> generatePopulation(ArrayList<Job> jobs, int totalIndividuals, int individualChromosomes, int chromoMaxValue);
}
