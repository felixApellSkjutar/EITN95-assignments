import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberA = 0, accumulated = 0, noMeasurements = 0, numberB = 0;
	public double lambda = 150;
	public double serviceTimeA = 0.002; 	// XA in task
	public double serviceTimeB = 0.004; 	// XA in task
	Random slump = new Random(); // This is just a random number generator
	public int d = 1; 						// delay
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_A:
				arrivalA();
				break;
			case READY_A:
				readyA();
				break;
			case MEASURE_A:
				measure();
				break;
			case ARRIVAL_B:
				arrivalB();
				break;
			case READY_B:
				readyB();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrivalA(){
		double arrivalTime = expDist(1.0/lambda);
		// If there are nothing in BufferA or B -> move to serverA
		if (numberA + numberB == 0) {
			insertEvent(READY_A, time + serviceTimeA);
		}
		numberA++;
		insertEvent(ARRIVAL_A, time + arrivalTime);
	}

	private void readyA(){
		numberA--;
		insertEvent(ARRIVAL_B, time + d);
		// insertEvent(ARRIVAL_B, time + expDist(1.0));
		// Prio B
		// if (numberB > 0) {
		// 	insertEvent(READY_B, time + serviceTimeB);
		// } else if (numberA > 0) {
		// 	insertEvent(READY_A, time + serviceTimeA);
		// }
		//Prio A
		if (numberA > 0) {
			insertEvent(READY_A, time + serviceTimeA);
		} else if (numberB > 0) {
			insertEvent(READY_B, time + serviceTimeB);
		}
	}
	
	private void arrivalB(){
		if (numberA + numberB == 0) {
			insertEvent(READY_B, time + serviceTimeB);
		}
		numberB++;
	}
	
	private void readyB(){
		numberB--;
		//Prio B
		// if (numberB > 0) {
		// 	insertEvent(READY_B, time + serviceTimeB);
		// } else if (numberA > 0) {
		// 	insertEvent(READY_A, time + serviceTimeA);
		// }
		// Prio A
		if (numberA > 0) {
			insertEvent(READY_A, time + serviceTimeA);
		} else if (numberB > 0) {
			insertEvent(READY_B, time + serviceTimeB);
		}
	}
	
	private void measure(){
		accumulated = accumulated + numberA + numberB;
		noMeasurements++;
		insertEvent(MEASURE_A, time + 0.1);
	}

	private double expDist(double mean) {
		double u = slump.nextDouble();
		return Math.log(1 - u)*(-mean);
	}
}