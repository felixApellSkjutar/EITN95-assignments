import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 5);

		/*
		 * PLANEN:
		 * 
		 * öppettider 09:00 - 17:00
		 * Räkna tiden i minuter: 8 timmar -> 480 minuter
		 * ta emot recept mellan t = 0 -> t = 480
		 * Kunder kommer enligt Poisson med rate 4/timme dvs mean = 15 min
		 * Varje arrival: hantera denna kund
		 * 		generera nästa tid
		 * 		if nästa tid större än 480, generera event för att fylla recept på t = 480 + dist
		 * När kö med recept tom -> avsluta simulering och rapportera tiden.
		 */
        

        // The main simulation loop
    	while (actState.iterations.size() < 100000){
			
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
			
			
			
//			System.out.println("KÖÖ " + actState.numberInQueue);
//			System.out.println("RECEPT " + actState.nmbrUnhandledPresc);
//			System.out.println("EVENT " + actEvent.eventType);
    	}
    	
    	// Printing the result of the simulation, in this case a mean value
    	System.out.println("Average nmnbr customers in queue: " + 1.0*actState.accumulated/actState.noMeasurements);
		
		List<Double> ds = actState.iterations;
		//DoubleStream ds = actState.iterations.stream().mapToDouble(Double::doubleValue);
    	// System.out.println(actState.iterations);
		
		long N = ds.stream().count();
		double mean = ds.stream().mapToDouble(Double::doubleValue).sum() / N;
		double variance = ds.stream().mapToDouble(Double::doubleValue).map(x -> x - mean).map(x -> x * x).sum() / N;
		double stdDev = Math.sqrt(variance);
		double confInt = 1.96 * stdDev / Math.sqrt(N);

		List<Double> prescTimes = actState.prescTimes;
		long Nb = prescTimes.stream().count();
		double meanB = prescTimes.stream().mapToDouble(Double::doubleValue).sum() / Nb;
		double varianceB = prescTimes.stream().mapToDouble(Double::doubleValue).map(x -> x - meanB).map(x -> x * x).sum() / Nb;
		double stdDevB = Math.sqrt(varianceB);
		double confIntB = 1.96 * stdDevB / Math.sqrt(Nb);

		System.out.println("Mean: " + mean);
		System.out.println("Variance: " + variance);
		System.out.println("Standard deviation: " + stdDev);
		System.out.println("Confidence interval: " + confInt);
		System.out.println("Confidence interval upper bound: " + (mean + confInt));
		System.out.println("Confidence interval lower bound: " + (mean - confInt));

		System.out.println("--------------------");

		System.out.println("Mean: " + meanB);
		System.out.println("Variance: " + varianceB);
		System.out.println("Standard deviation: " + stdDevB);
		System.out.println("Confidence interval: " + confIntB);
		System.out.println("Confidence interval upper bound: " + (meanB + confIntB));
		System.out.println("Confidence interval lower bound: " + (meanB - confIntB));
		
    }
	
}