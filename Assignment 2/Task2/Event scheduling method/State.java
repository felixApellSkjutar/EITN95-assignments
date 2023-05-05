import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int  numberInQueue = 0, accumulated = 0, noMeasurements = 0;

	Random slump = new Random(); // This is just a random number generator
	
	int arr = 0, r = 0;
	double lambda = 4.0/60, lastArrival = 0, qSize = 0; //mean inter-arrival time: 4/hour
	double maxArrivalTime = 17*60.0; //the pharmacy is open for 8h
	public List<Double> iterations = new ArrayList<Double>();
	public List<Double> qTimes = new ArrayList<Double>();
	public LinkedList<Double> arrivalTimes = new LinkedList<>();
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				if (time <= maxArrivalTime) {
					arr++;
					arrival();
				}
				break;
			case READY:
				r++;
				ready();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		arrivalTimes.addLast(time);
		qSize = numberInQueue;
		if (numberInQueue == 0){
			double serviceTime = prescriptonFill(slump);
			insertEvent(READY, time + serviceTime);
		}
		numberInQueue++;
		double nextTime = time + expDist(lambda);
		insertEvent(ARRIVAL, nextTime);
	}
	
	private void ready(){
		lastArrival = time;
		qTimes.add(time - arrivalTimes.pop());
		numberInQueue--;
		if (numberInQueue > 0){
			double serviceTime = prescriptonFill(slump);
			insertEvent(READY, time + serviceTime);
		}
		//System.out.println("in ready: " + r + " arr: " + arr + " numIn Kö: " + numberInQueue + " time: " + time);
	}


	private double expDist(double lambda) {
		double u = slump.nextDouble();
		double mm = Math.log(1 - u)*(-1.0/lambda);
		return mm;
	}


	private double prescriptonFill(Random slump) {
		//uniformly distributed between 10-20 min
		return ((new Random()).nextDouble())*10.0 + 10.0;
	}
}