import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;

		// Task variables
		int N = 100, x = 10, T = 4, M = 4000;
		double lambda = 4.0;

    	State actState = new State(N, x, lambda, T); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);
        insertEvent(MEASURE, T);
        
        // The main simulation loop
    	while (actState.numberOfMeasurements < M){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}

		actState.fw.close();
    	
    	// Printing the result of the simulation, in this case a mean value
    	System.out.println("Mean number of customers: " + 1.0*actState.accumulated/actState.numberOfMeasurements);
    }
}