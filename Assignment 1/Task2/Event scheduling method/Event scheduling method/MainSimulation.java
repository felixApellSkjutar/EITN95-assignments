import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL_A, 0);  
        insertEvent(MEASURE_A, 5);
        insertEvent(MEASURE_B, 5);
        
        // The main simulation loop
    	while (actState.noMeasurements < 1200){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation, in this case a mean value
		System.out.println("Mean value of customers in Buffern " + 1.0*(actState.accumulated+actState.accumulated2)/(actState.noMeasurements+actState.noMeasurements2));

    }
}