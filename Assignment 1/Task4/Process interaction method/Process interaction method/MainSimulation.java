import java.io.*;

public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

    	Signal actSignal;
    	new SignalList();

    	QS Q1 = new QS();
    	Q1.sendTo = null;

    	Gen Generator = new Gen();
    	Generator.lambda = 1.0/5; 
    	Generator.sendTo = Q1; 

    	SignalList.SendSignal(READY, Generator, time);
    	SignalList.SendSignal(MEASURE, Q1, time);

    	while (Q1.arrivals <= 1000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

		System.out.println("Time:" + time);
		System.out.println("Mean number of customers in queuing system: " + 1.0*Q1.accumulated/Q1.nmbrMeasurements);
		System.out.println("Average queuing time normal: " + (Q1.normalAccQuetime)/Q1.servedNormal);
		System.out.println("Average queuing time special: " + (Q1.specialAccQuetime)/Q1.servedSpecial);

    }
}