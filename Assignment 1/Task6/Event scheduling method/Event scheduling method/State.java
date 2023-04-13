import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, noMeasurements = 0;
	public double accumulated = 0;
	Random slump = new Random(); // This is just a random number generator
	boolean[] check = new boolean[5];
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case COMPONENT1:
				component1Breakdown();
				suicide(COMPONENT1);
				break;
			case COMPONENT2:
				suicide(COMPONENT2);
				break;
			case COMPONENT3:
				component3Breakdown();
				suicide(COMPONENT3);
				break;
			case COMPONENT4:
				suicide(COMPONENT4);
				break;
			case COMPONENT5:
				suicide(COMPONENT5);
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	double[] componenteventTimes = new double[5];
	double timeOfDeath = 0;

	public void arrival(){
		for (int i = 0; i < 5; i++) {
			componenteventTimes[i] = 1.0 + slump.nextDouble(4);
			check[i] = false;
		}
		insertEvent(COMPONENT1, componenteventTimes[0]);
		insertEvent(COMPONENT2, componenteventTimes[1]);
		insertEvent(COMPONENT3, componenteventTimes[2]);
		insertEvent(COMPONENT4, componenteventTimes[3]);
		insertEvent(COMPONENT5, componenteventTimes[4]);
	}
		

	private void component1Breakdown() {

		if(componenteventTimes[0] < componenteventTimes[1]) {
			insertEvent(COMPONENT2, componenteventTimes[0]);
			componenteventTimes[1] = componenteventTimes[0];
			check[1] = true;
		}
		if(componenteventTimes[0] < componenteventTimes[4]){
			insertEvent(COMPONENT5, componenteventTimes[0]);
			componenteventTimes[4] = componenteventTimes[0];
			check[4] = true;
		}
		checkComps();
	}

	private void component3Breakdown() {
		if(componenteventTimes[2] < componenteventTimes[3]) {
			insertEvent(COMPONENT4, componenteventTimes[2]);
			componenteventTimes[3] = componenteventTimes[2];
			check[3] = true;
		}
		checkComps();
	}

	private void suicide(int ind) {
		check[ind] = true;
		checkComps();
	}

	public boolean checkComps() {
			for(int i = 0; i < check.length; i++) {
				if (!check[i]) {
					return false;
				}
			}
			insertEvent(MEASURE, Arrays.stream(componenteventTimes).max().getAsDouble());
			return true;
		}
	
	private void measure(){
		noMeasurements++;
		double measure = Arrays.stream(componenteventTimes).max().getAsDouble(); 
		accumulated += measure;
		insertEvent(ARRIVAL, 0);
	}
}