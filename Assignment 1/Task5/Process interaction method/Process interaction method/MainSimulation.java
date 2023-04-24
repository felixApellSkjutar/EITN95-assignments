import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

    	//Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();

    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
    	Q1.sendTo = null;
		QS Q2 = new QS();
    	Q2.sendTo = null;
		QS Q3 = new QS();
    	Q3.sendTo = null;
		QS Q4 = new QS();
    	Q4.sendTo = null;
		QS Q5 = new QS();
    	Q5.sendTo = null;

    	Gen Generator = new Gen();
    	Generator.lambda = 1/2.0;
		Generator.sendToList = new ArrayList<QS>(Arrays.asList(Q1, Q2, Q3, Q4, Q5));
    	
		//H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.
    	//To start the simulation the first signals are put in the signal list

    	SignalList.SendSignal(READY, Generator, time);
    	SignalList.SendSignal(MEASURE, Q1, time);
		SignalList.SendSignal(MEASURE, Q2, time);
		SignalList.SendSignal(MEASURE, Q3, time);
		SignalList.SendSignal(MEASURE, Q4, time);
		SignalList.SendSignal(MEASURE, Q5, time);


    	// Detta �r simuleringsloopen:
    	// This is the main loop

    	while (time < 100000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:

    	System.out.println("Mean nmbr customers sys 1: " + 1.0*Q1.accumulated/Q1.noMeasurements);
		System.out.println("Mean nmbr customers sys 2: " + 1.0*Q2.accumulated/Q2.noMeasurements);
		System.out.println("Mean nmbr customers sys 3: " + 1.0*Q3.accumulated/Q3.noMeasurements);
		System.out.println("Mean nmbr customers sys 4: " + 1.0*Q4.accumulated/Q4.noMeasurements);
		System.out.println("Mean nmbr customers sys 5: " + 1.0*Q5.accumulated/Q5.noMeasurements);
		System.out.println("Mean nmbr customers total: " + 1.0*(Q1.accumulated+Q2.accumulated+Q3.accumulated+Q4.accumulated+Q5.accumulated)/(Q1.noMeasurements+Q2.noMeasurements+Q3.noMeasurements+Q4.noMeasurements+Q5.noMeasurements));
    }
}