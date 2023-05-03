import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;

	public double monthlyRate = 1 + 0.3/12.0;
	public double balance = 0;
	public double monthlyDeposit = 5000; //10000 or 20000

	Random slump = new Random(); // This is just a random number generator
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case DEPOSIT:
				monthly_deposit();
				break;
			case CHOICE:
				choice();
				break;
			case SELL25:
				valueDrop(0.75);
				break;
			case SELL40:
				valueDrop(0.5);
				break;
			case SELL50:
				valueDrop(0.4);
				break;
			case NOSELL:
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void monthly_deposit(){
		balance = balance*monthlyRate + monthlyDeposit;
        insertEvent(DEPOSIT, time + 1);  

	}

	private void choice(){
		// May need to be fix
		double slumpTime = 4*12*slump.nextDouble(); // 4 years*12months/year
		int caseChoice = slump.nextInt(100);
		if(caseChoice < 10){
			insertEvent(SELL25, time+slumpTime);
		} else if (caseChoice < 35){
			insertEvent(SELL40, time+slumpTime);
		} else if(caseChoice < 50){
			insertEvent(SELL50, time+slumpTime);
		} else {
			insertEvent(NOSELL, time+slumpTime);
		}
	}

	private void valueDrop(double valueDrop){
		balance *= valueDrop;
        insertEvent(CHOICE, time);  
	}
	
}