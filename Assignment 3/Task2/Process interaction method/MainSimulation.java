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
		int runs = 0;
		double totalTime = 0;
		ArrayList<Double> times = new ArrayList<>();
		double mean = 0.0;  
		double stdDev = 0.0;
		double interval = 0.0;// Some events must be put in the event list at the beginning
        
    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
    	Q1.sendTo = null;

    	//H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.
    	//To start the simulation the first signals are put in the signal list

		while (interval > 2 || stdDev == 0){
			for (int i = 0; i < 20; i++){
				Student s = new Student(i);
				Q1.students.add(s);

				SignalList.SendSignal(WALK, Q1, time, s);
			}
			//SignalList.SendSignal(MEASURE, Q1, time);


    	// Detta �r simuleringsloopen:
    	// This is the main loop

    	while (time < 100000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
			System.out.print(actSignal.destination);
    		actSignal.destination.TreatSignal(actSignal);
    	}
		runs++;
			totalTime += time;
			times.add(time);

			mean = totalTime/runs;

			// Standard deviation
			stdDev = 0.0;

			for (double time : times){
				stdDev += Math.pow(time-mean, 2); 
			}
			stdDev = Math.sqrt(stdDev/runs);
			System.out.println("stdDev: " + stdDev);

			// with z-distribution
			interval = 1.960*stdDev/Math.sqrt(runs);

			time = 0;

    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:

    	
		System.out.println(runs);
		System.out.println("stdDev: " + stdDev);
		System.out.println("interval: " + interval);


		System.out.println("mean: " + mean);
    }
}