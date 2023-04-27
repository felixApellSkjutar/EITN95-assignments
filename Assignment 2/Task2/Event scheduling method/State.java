import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int  nmbrUnhandledPresc = 0, numberInQueue = 0, accumulated = 0, noMeasurements = 0;

	Random slump = new Random(); // This is just a random number generator
	
	double mean = 15; //mean inter-arrival time: 4/hour
	double maxArrivalTime = 480; //the pharmacy is open for 8h
	double lastPresctiptionTime = 0;
	public List<Double> iterations = new ArrayList<Double>();
	public List<Double> prescTimes = new ArrayList<Double>();
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				//if (x.eventTime < maxArrivalTime)
				arrival();
				break;
			case READY:
				ready();
				break;
			case RESET:
				reset();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		if (numberInQueue == 0){
			double serviceTime = prescriptonFill(slump);
			prescTimes.add(serviceTime);
			insertEvent(READY, time + serviceTime);
		}
		nmbrUnhandledPresc++;
		numberInQueue++;
		double nextTime = time + expDist(mean);
		if (nextTime < maxArrivalTime)
			insertEvent(ARRIVAL, nextTime);
		else
			insertEvent(RESET, nextTime);
	}
	
	private void ready(){
		numberInQueue--;
		nmbrUnhandledPresc--;
		lastPresctiptionTime = time;
		if (numberInQueue > 0){
			double serviceTime = prescriptonFill(slump);
			prescTimes.add(serviceTime);
			insertEvent(READY, time + serviceTime);
		}
	//	if (time >= maxArrivalTime && nmbrUnhandledPresc == 0) {
	//		insertEvent(RESET, time);
	//	}
	}
	
	private void measure(){
		accumulated = accumulated + numberInQueue;
		noMeasurements++;
		insertEvent(MEASURE, time + slump.nextDouble()*10);
	}

	private void reset() {
		iterations.add(lastPresctiptionTime);
		time = 0;
		nmbrUnhandledPresc = 0;
		numberInQueue = 0;
		insertEvent(ARRIVAL, 0);
        insertEvent(MEASURE, 5);
	}

	private double expDist(double mean) {
		double u = slump.nextDouble();
		return Math.log(1 - u)*(-mean);
	}

	private double prescriptonFill(Random slump) {
		//uniformly distributed between 10-20 min
		return ((new Random()).nextDouble(11)) + 10;
	}
}