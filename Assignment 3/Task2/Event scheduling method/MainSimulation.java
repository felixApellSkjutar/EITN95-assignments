import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that shoud be used
    	int runs = 0;
		double totalTime = 0;
		ArrayList<Double> times = new ArrayList<>();
		double mean = 0.0;  
		double stdDev = 0.0;
		double interval = 0.0;// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 5);
        
        // The main simulation loop
    	while (interval > 2 || stdDev == 0){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);

			for(int i = 0; i < 20; i++){
				Student s = new Student();
				students.add(s);
			}
			// The real simulation
			while (actState.balance < 2000000){

				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}

			runs++;
			totalTime += time;
			times.add(time);

			mean = totalTime/runs;

			// Standard deviation
			stdDev = 0.0;

			for (double time : times){
				stdDev += Math.pow(time-mean, 2); 
			}
			stdDev = Math.sqrt(stdDev/runs);
			System.out.println("stdDev: " + stdDev);

			// with z-distribution
			interval = 1.960*stdDev/Math.sqrt(runs);

			time = 0;

    	}
    	
    	// Printing the result of the simulation, in this case a mean value
    	System.out.println(1.0*actState.accumulated/actState.noMeasurements);
    }
}