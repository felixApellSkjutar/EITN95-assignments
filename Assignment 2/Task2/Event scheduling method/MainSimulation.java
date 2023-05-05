import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
		
		List<Double> endTimes = new ArrayList<Double>();
		List<Double> serviceTimes = new ArrayList<Double>();
        // The main simulation loop
		State actState = new State(); 
    	for (int i = 0; i < 10000; i++){
			double startingTime = 9.0*60; //First customer arrives at 9:00 
			time = startingTime; 

			insertEvent(ARRIVAL, startingTime); 
			
			while (true){
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
				if (time >= actState.maxArrivalTime && actState.numberInQueue == 0){
					break;
				}
			}
			serviceTimes.addAll(actState.qTimes);
			endTimes.add(time);
			actState = new State(); //Every day we start a new simulation
    	}
    	
		long N = endTimes.stream().count();
		double mean = endTimes.stream().mapToDouble(Double::doubleValue).sum() / N;
		double variance = endTimes.stream().mapToDouble(Double::doubleValue).map(x -> x - mean).map(x -> x * x).sum() / N;
		double stdDev = Math.sqrt(variance);
		double confInt = 1.96 * stdDev / Math.sqrt(N);
		Double max = endTimes.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
		Double min = endTimes.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
		
		System.out.println("size endtimes: " + endTimes.size());
		System.out.println("Mean: " + mean);
		System.out.println("Variance: " + variance);
		System.out.println("Standard deviation: " + stdDev);
		System.out.println("Confidence interval: " + confInt);
		System.out.println("Confidence interval upper bound: " + (mean + confInt));
		System.out.println("Confidence interval lower bound: " + (mean - confInt));
		System.out.println("max: " + max);
		System.out.println("min: " + min);
		
		
		System.out.println("--------------------");

		long Nb = serviceTimes.stream().count();
		double meanB = serviceTimes.stream().mapToDouble(Double::doubleValue).sum() / Nb;
		double varianceB = serviceTimes.stream().mapToDouble(Double::doubleValue).map(x -> x - meanB).map(x -> x * x).sum() / Nb;
		double stdDevB = Math.sqrt(varianceB);
		double confIntB = 1.96 * stdDevB / Math.sqrt(Nb);
		Double maxB = serviceTimes.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
		Double minB = serviceTimes.stream().mapToDouble(Double::doubleValue).min().getAsDouble();

		System.out.println("size servicetimes: " + serviceTimes.size());
		System.out.println("Mean: " + meanB);
		System.out.println("Variance: " + varianceB);
		System.out.println("Standard deviation: " + stdDevB);
		System.out.println("Confidence interval: " + confIntB);
		System.out.println("Confidence interval upper bound: " + (meanB + confIntB));
		System.out.println("Confidence interval lower bound: " + (meanB - confIntB));
		System.out.println("max: " + maxB);
		System.out.println("min: " + minB);
		
    }
	
}