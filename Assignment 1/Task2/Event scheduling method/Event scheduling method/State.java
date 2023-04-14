import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInBufferA = 0, accumulated = 0, noMeasurements = 0, numberInBufferB = 0, accumulated2 = 0, noMeasurements2 = 0, noCustomers1 = 0;
	public double lambda = 150;
	public double serviceTimeA = 0.002; 	// XA in task
	public double serviceTimeB = 0.004; 	// XA in task
	public int d = 1; 						// delay
	Random slump = new Random(); // This is just a random number generator
	
	
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
				measureA();
				break;
			case ARRIVAL_B:
				arrivalB();
				break;
			case READY_B:
				readyB();
				break;
			case MEASURE_B:
				measureB();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrivalA(){
		double arrivalTime = expDist(1.0*1/lambda);
		// If there are nothing in BufferA or B -> move to serverA
			if (numberInBufferA + numberInBufferB == 0)
				insertEvent(READY_A, time + serviceTimeA);
			numberInBufferA++;
			noCustomers1++;
		
		insertEvent(ARRIVAL_A, time + arrivalTime);
	}

	private void readyA(){
		numberInBufferA--;
		if (numberInBufferB > 0)
			insertEvent(READY_B, time + serviceTimeB);
		insertEvent(ARRIVAL_B, time + d);
	}
	
	private void measureA(){
		accumulated = accumulated + numberInBufferA;
		noMeasurements++;
		insertEvent(MEASURE_A, time + 0.1);
	}

	private void arrivalB(){
		if (numberInBufferB == 0)
			insertEvent(READY_B, time + serviceTimeB);
		numberInBufferB++;
	}

	private void readyB(){
		numberInBufferB--;
		if (numberInBufferB > 0)
			insertEvent(READY_B, time + serviceTimeB);
		else if(numberInBufferA > 0)
			insertEvent(ARRIVAL_A, time + serviceTimeA);
	}

	private void measureB(){
		accumulated2 = accumulated2 + numberInBufferB;
		noMeasurements2++;
		insertEvent(MEASURE_B, time + 0.1);
	}

	private double  expDist(double mean) {
		double u = slump.nextDouble();
		return (Math.log(1 - u)*(-mean));
	}
}