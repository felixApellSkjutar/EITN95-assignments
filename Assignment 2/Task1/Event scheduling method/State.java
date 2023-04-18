import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberOfCustomers = 0, accumulated = 0, numberOfMeasurements = 0;

	Random slump = new Random(); // This is just a random number generator
	
	private int N; 			// Number of parallell servers
	private int x; 			// Service time of a customer in seconds
	private double lambda; 	// Poisson process rate
	private int T; 			// Time between measurements
	private File file;		// File containing the measurements
	public FileWriter fw;	// FileWriter to write to file

	State(int N, int x, double lambda, int T) {
		this.N = N;
		this.x = x;
		this.lambda = lambda;
		this.T = T;
		try {
			file = new File("measurement_result.txt");
			fw = new FileWriter(file);
		} catch (IOException e) {
			System.out.println("Error creating file/filewriter");
			e.printStackTrace();
		}
	}
	
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
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		if (numberOfCustomers < N)
			insertEvent(READY, time + x);
			numberOfCustomers++;
		insertEvent(ARRIVAL, time + poissonDist(lambda));
	}
	
	private void ready(){
		numberOfCustomers--;
	}
	
	private void measure(){
		accumulated = accumulated + numberOfCustomers;
		numberOfMeasurements++;
		try {
			fw.write(numberOfCustomers + ", " + numberOfMeasurements + "\n");
		} catch (IOException e) {
			System.out.println("Error writing to file");
			e.printStackTrace();
		}
		insertEvent(MEASURE, time + T);
	}

	private double  poissonDist(double mean) {
		double u = slump.nextDouble();
		return -Math.log(1 - u)/mean;
	}
}