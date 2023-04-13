import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{

	static Random slump = new Random();
 
    public static void main(String[] args) throws IOException {
    	Event actEvent = new Event();
		actEvent.eventTime = 0;
		actEvent.eventType = ARRIVAL;
    	State actState = new State(); // The state that shoud be used
        
		insertEvent(ARRIVAL, 0);
        // The main simulation loop
		while (actState.noMeasurements < 100000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation, in this case a mean value
    	System.out.println("Mean value of breakdown time " + 1.0*actState.accumulated/actState.noMeasurements);

    }
}