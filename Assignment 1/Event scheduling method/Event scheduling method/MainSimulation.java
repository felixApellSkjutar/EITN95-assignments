import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 5);
		insertEvent(MEASURE2, 6);
        
        // The main simulation loop
    	while (time < 6000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation, in this case a mean value
    	System.out.println("Mean value of customers in queue1 " + 1.0*actState.accumulated/actState.noMeasurements);
		System.out.println("Mean value of customers in queue2 " + 1.0*actState.accumulated2/actState.noMeasurements2);
		System.out.println("Number of measurements in queue2: " + actState.noMeasurements2);
    }
}