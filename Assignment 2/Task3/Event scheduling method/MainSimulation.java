import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState; // The state that shoud be used
		int i = 0;
		double totalTime = 0;
		ArrayList<Double> times = new ArrayList<>();
		double mean = 0.0;  
		double stdDev = Double.MAX_VALUE;
		while(stdDev*2 > 2 || stdDev == 0){
			actState = new State();
    		// Some events must be put in the event list at the beginning
			insertEvent(DEPOSIT, 0);  
			insertEvent(CHOICE, 0);

			// The main simulation loop
			while (actState.balance < 2000000){
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}
			
			i++;
			totalTime += time;
			times.add(time);

			mean = totalTime/i;

			// Standard deviation
			stdDev = 0.0;

			for (double time : times){
				stdDev += Math.pow(time-mean, 2); 
			}
			stdDev = Math.sqrt(stdDev/i);
			System.out.println("stdDev: " + stdDev);
			time = 0;

		}
		System.out.println(i);
		System.out.println("stdDev: " + stdDev);


		System.out.println("mean: " + mean);

    }
}