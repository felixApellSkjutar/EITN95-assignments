import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global{

	public static void main(String[] args) throws IOException {
		
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
        
    	QS Q1 = new QS();
    	Q1.sendTo = null;

		HashMap<Integer, Integer> studentAssociations = new HashMap<>();

		while (runs < 1000){
			
			Q1.students.clear();
			for (int i = 0; i < 20; i++){
				Student s = new Student(i);
				Q1.students.add(s);
				
				SignalList.SendSignal(WALK, Q1, time, s);
			}

			while (!Q1.finished()){
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
			}
			
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

		// Write result for plots to file
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
    	for(int i = 1; i < studentAssociations.size(); i++){
			writer.println(i + " " + studentAssociations.get(i));
		}
		writer.close();
    }
}