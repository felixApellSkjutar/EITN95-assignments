import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0, numberInQueue2 = 0, accumulated2 = 0, noMeasurements2 = 0, noReject1 = 0, noCustomers1 = 0;
	Random slump = new Random(); // This is just a random number generator
	
	
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
			case MEASURE2:
				measure2();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		//Task1: Arrival konstant
		double arrivalTime = 5; //Change this to change arrival time to Q1
		if(numberInQueue < 10) {
			if (numberInQueue == 0)
				insertEvent(READY, time + expDist(2.1));
			numberInQueue++;
			noCustomers1++;
		} else {
			noReject1++;
		}
		insertEvent(ARRIVAL, time + arrivalTime);
	}

	private void ready(){
		numberInQueue--;
		insertEvent(ARRIVAL2, time);
		if (numberInQueue > 0)
			insertEvent(READY, time + expDist(2.1));
	}
	
	private void measure(){
		accumulated = accumulated + numberInQueue;
		noMeasurements++;
		insertEvent(MEASURE, time + slump.nextDouble()*10);
	}

	private void arrival2(){
		if (numberInQueue2 == 0)
			insertEvent(READY2, time + 2);
		numberInQueue2++;
	}

	private void ready2(){
		numberInQueue2--;
		if (numberInQueue2 > 0)
			insertEvent(READY2, time + 2);
	}

	private void measure2(){
		accumulated2 = accumulated2 + numberInQueue2;
		noMeasurements2++;
		insertEvent(MEASURE2, time + expDist(5));
	}

	private double  expDist(double mean) {
		double u = slump.nextDouble();
		return (Math.log(1 - u)*(-mean));
	}
}