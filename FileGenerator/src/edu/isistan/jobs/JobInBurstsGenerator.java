package edu.isistan.jobs;

import org.apache.commons.math3.distribution.NormalDistribution;

public class JobInBurstsGenerator {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length<5)
			System.err.println("nOfJobs minTime maxTime minOps maxOps minInput maxInput minOutput maxOutput jobArrivalTotalLenghtTime NormalDistMean NormalDistStd");
		
		int nJobs=Integer.parseInt(args[0]);
		long minOps=Long.parseLong(args[1]); 
		long maxOps=Long.parseLong(args[2]);
		int minInput=Integer.parseInt(args[3]);
		int maxInput=Integer.parseInt(args[4]);
		int minOutput=Integer.parseInt(args[5]);
		int maxOutput=Integer.parseInt(args[6]);
		long jobArrivalTotalLenghtTime = Long.parseLong(args[7]);
		long jobArrivalStartTime = Long.parseLong(args[8]);
		double normalDistMean = Double.parseDouble(args[9]);
		double normalDistStd = Double.parseDouble(args[10]);
		
		System.out.println("#"+JobInBurstsGenerator.class.getName());
		System.out.println("#nOfJobs minTime maxTime minOps maxOps minInput maxInput minOutput maxOutput jobArrivalTotalLenghtTime NormalDistMean NormalDistStd");
		System.out.println("#"+nJobs+" "+minOps+" "+maxOps+" "+minInput+" "+maxInput+" "+minOutput+" "+maxOutput+" "+jobArrivalTotalLenghtTime+" "+(long)normalDistMean+" "+(long)normalDistStd);
		JobInBurstsGenerator jg=new JobInBurstsGenerator();
		jg.generateJobs(nJobs, minOps, maxOps, minInput, maxInput, minOutput, maxOutput, jobArrivalTotalLenghtTime, jobArrivalStartTime, normalDistMean, normalDistStd);
		
	}
	
	public void generateJobs(int cant, long minOps, long maxOps,int minInput, int maxInput, int minOutput, int maxOutput, long jobArrivalTotalLenghtTime,long jobArrivalStartsTime, double normalDistMean, double normalDistStd){
				
		NormalDistribution timeBtwBurstsDistrib = new NormalDistribution(normalDistMean,normalDistStd);	
		long nextBurstTime = 0;
		int i = 0;
		long lastBurstElapsedTimeSample = (long) timeBtwBurstsDistrib.sample();
		nextBurstTime += lastBurstElapsedTimeSample;
		
		while (nextBurstTime < jobArrivalTotalLenghtTime) {
			
			for(int j = 0; j < ((lastBurstElapsedTimeSample / 1000) * cant); j++) {				
				JobInformation job = this.generateNewJob(i, minOps, maxOps,nextBurstTime+jobArrivalStartsTime,nextBurstTime+jobArrivalStartsTime,minInput,maxInput,minOutput,maxOutput);
				System.out.println(job.toString());
				i++;
			}
			//System.out.println(nextBurstTime+";END_JOB_BURST");
				
			lastBurstElapsedTimeSample = (long)timeBtwBurstsDistrib.sample();
			nextBurstTime += lastBurstElapsedTimeSample;
		}
		
	}

	private JobInformation generateNewJob(int id, long minOps, long maxOps,
			long minTime, long maxTime, int minInput, int maxInput,
			int minOutput, int maxOutput) {
		long ops = ( (long) ( Math.random()*(maxOps-minOps)) ) + minOps;
		long time =( (long) ( Math.random()*(maxTime-minTime)) ) + minTime;
		int input =( (int) ( Math.random()*(maxInput-minInput)) ) + minInput;
		int output =( (int) ( Math.random()*(maxOutput-minOutput)) ) + minOutput;
		return new JobInformation(time,id,ops,input,output);
	}	
	
}
