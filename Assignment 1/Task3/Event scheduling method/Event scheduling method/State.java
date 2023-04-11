import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0, numberInQueue2 = 0, customersServed = 0;
	public double totalTime = 0.0;
	Random slump = new Random(); // This is just a random number generator
	
	LinkedList<Double> times = new LinkedList<Double>();
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case READY:
				ready();
				break;
			case MEASURE:
				measure();
				break;
			case ARRIVAL2:
				arrival2();
				break;
			case READY2:
				ready2();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		//Task3: Exponentially distributed arrival times
		double arrivalTime = 1.1; //Change this to change arrival time to Q1
		if (numberInQueue == 0)
			insertEvent(READY, time + expDist(1));
		numberInQueue++;
		times.addLast(time);
		insertEvent(ARRIVAL, time + expDist(arrivalTime));
	}

	private void ready(){
		numberInQueue--;
		insertEvent(ARRIVAL2, time);
		if (numberInQueue > 0)
			insertEvent(READY, time + expDist(1));
	}
	
	private void arrival2(){
		if (numberInQueue2 == 0)
		insertEvent(READY2, time + expDist(1));
		numberInQueue2++;
	}
	
	private void ready2(){
		numberInQueue2--;
		customersServed++;
		if (numberInQueue2 > 0)
		insertEvent(READY2, time + expDist(1));
		totalTime += time - times.poll();
	}
	
	private void measure(){
		accumulated = accumulated + numberInQueue + numberInQueue2;
		noMeasurements++;
		insertEvent(MEASURE, time + expDist(5));
	}

	private double  expDist(double mean) {
		double u = slump.nextDouble();
		return (Math.log(1 - u)*(-mean));
	}
}