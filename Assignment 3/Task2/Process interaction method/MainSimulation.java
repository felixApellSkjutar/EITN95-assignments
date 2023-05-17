import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

	public static void main(String[] args) throws IOException {
		
		//Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
    	// signal list in the main loop below.
		
		int runs = 0;
    	Signal actSignal;
    	new SignalList();
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


		HashMap<Integer, Integer> studentAssociations = new HashMap<>();

		while (runs < 1000){
			
			Q1.students.clear();
			for (int i = 0; i < 20; i++){
				Student s = new Student(i);
				Q1.students.add(s);
				
				SignalList.SendSignal(WALK, Q1, time, s);
			}
			//System.out.println(Q1.students.size());

			// Detta �r simuleringsloopen:
			// This is the main loop

			//int c = 0;
			while (!Q1.finished()){
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
				// System.out.println("c " + c); 
				//   if (c %10 == 0) {
				//    	for (Student s : Q1.students){
				//    		System.out.println(s.getID() + " " + Arrays.toString(s.relationships));
				//    	}
				//    }
				//    c++;
			}
			
			//PLocka ut alla tider som studenter spenderat med andra studenter
			//När vi gjort alla simuleringar så kan vi räkna ut medelvärde och standardavvikelse


			for (Student s : Q1.students){
				for (int i : s.relationships) {
					if (i != 0) {
						if (studentAssociations.containsKey(i)){
							studentAssociations.put(i, studentAssociations.get(i) + 1);
						} else {
							studentAssociations.put(i, 1);
						}
					}
				}
			}

			runs++;
			// totalTime += time;
			// times.add(time);

			// mean = totalTime/runs;


			// // Standard deviation
			// double variance = 0.0;
			// stdDev = 0.0;

			// for (double t : times){
			// 	variance += Math.pow(t-mean, 2); 
			// }
			// variance = variance/runs;

			// stdDev = Math.sqrt(variance);


			// // with z-distribution
			// interval = 1.960*stdDev/Math.sqrt(runs);

			time = 0;

			// System.out.println("runs: " + runs + " mean: " + mean + " stdDev: " + stdDev + " interval: " + interval);
			// System.out.println("studentAssocisatiions: " + studentAssociations);
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:

		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
    	for(int i = 1; i < studentAssociations.size(); i++){
			writer.println(i + " " + studentAssociations.get(i));
		}
		writer.close();

//		System.out.println(studentAssociations);
		// System.out.println("stdDev: " + stdDev);
		// System.out.println("interval: " + interval);


		// System.out.println("mean: " + mean);
    }
}