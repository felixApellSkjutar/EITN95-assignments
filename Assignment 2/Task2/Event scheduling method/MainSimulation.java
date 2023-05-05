import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
		
		List<Double> endTimes = new ArrayList<Double>();
		List<Double> serviceTimes = new ArrayList<Double>();
        // The main simulation loop
		State actState = new State(); 
    	for (int i = 0; i < 1000; i++){
			double startingTime = 9.0*60; 
			time = startingTime;
			insertEvent(ARRIVAL, startingTime); 
			
			while (true){
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
				if (time >= actState.maxArrivalTime && actState.numberInQueue == 0){
					//System.out.println("in ready: " + actState.r + " arr: " + actState.arr + " numIn Kö: " + actState.numberInQueue + " time: " + time + " arrtimes " + actState.arrivalTimes );
					break;
				}
			}
			serviceTimes.addAll(actState.qTimes);
			//System.out.println("nmKö: " +actState.numberInQueue);
			endTimes.add(time);
			actState = new State();
			time = 0;
    	}
    	
		
		//List<Double> ds = actState.iterations;
		//DoubleStream ds = actState.iterations.stream().mapToDouble(Double::doubleValue);
    	// System.out.println(actState.iterations);
		
		// long N = ds.stream().count();
		// double mean = ds.stream().mapToDouble(Double::doubleValue).sum() / N;
		// double variance = ds.stream().mapToDouble(Double::doubleValue).map(x -> x - mean).map(x -> x * x).sum() / N;
		// double stdDev = Math.sqrt(variance);
		// double confInt = 1.96 * stdDev / Math.sqrt(N);

		// List<Double> prescTimes = actState.prescTimes;
		// long Nb = prescTimes.stream().count();
		// double meanB = prescTimes.stream().mapToDouble(Double::doubleValue).sum() / Nb;
		// double varianceB = prescTimes.stream().mapToDouble(Double::doubleValue).map(x -> x - meanB).map(x -> x * x).sum() / Nb;
		// double stdDevB = Math.sqrt(varianceB);
		// double confIntB = 1.96 * stdDevB / Math.sqrt(Nb);

		
		// List<Double> queuetimes = actState.qTimes;
		// Double max = queuetimes.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
		// Double min = queuetimes.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
		// long NQ = queuetimes.stream().count();
		// double meanQ = queuetimes.stream().mapToDouble(Double::doubleValue).sum() / NQ;
		// double varianceQ = queuetimes.stream().mapToDouble(Double::doubleValue).map(x -> x - meanQ).map(x -> x * x).sum() / NQ;
		// double stdDevQ = Math.sqrt(varianceQ);
		// double confIntQ = 1.96 * stdDevQ / Math.sqrt(NQ);
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
		
		
		// System.out.println("Data size: " + NQ);
		// System.out.println("max: " + max);
		// System.out.println("min: " + min);
		// System.out.println("Mean: " + meanQ);
		// System.out.println("Variance: " + varianceQ);
		// System.out.println("Standard deviation: " + stdDevQ);
		// System.out.println("Confidence interval: " + confIntQ);
		// System.out.println("Confidence interval upper bound: " + (meanQ + confIntQ));
		// System.out.println("Confidence interval lower bound: " + (meanQ - confIntQ));

//		System.out.println(endTimes);
    }
	
}