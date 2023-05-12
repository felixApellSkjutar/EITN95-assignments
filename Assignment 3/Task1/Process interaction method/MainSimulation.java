import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {
		
		// Here the gateway instance is created
		Gateway Gateway = new Gateway();
		Gateway.sendTo = null;

		// The signal list is started and actSignal is declared. actSignal is the latest signal that has been fetched from the 
		// signal list in the main loop below.
		Signal actSignal;
		new SignalList();

		// Read config file
		Properties config = new Properties();
        try {
			String path = "config_1000";
            FileInputStream fis = new FileInputStream(path);
            config.load(fis);
            fis.close();
        } catch (Exception e){};
		
        int n = Integer.parseInt(config.getProperty("n"));
        double Tp =  Double.parseDouble(config.getProperty("Tp"));
        double ts = Double.parseDouble(config.getProperty("ts"));
        double r = Double.parseDouble(config.getProperty("r"));
		
		Sensor[] sensor_nodes = new Sensor[n];
		for (int i = 0; i < n; i++) {
			String[] point = config.getProperty("point_" + Integer.toString(i)).split(" ");
			Sensor sensor_node = new Sensor(Double.parseDouble(point[0]), Double.parseDouble(point[1]), r, ts, Gateway);
			SignalList.SendSignal(START_REPORT, null, sensor_node, Tp);
			sensor_nodes[i] = sensor_node;
		}
		
		System.out.println(n);
		System.out.println(Tp);
		System.out.println(ts);
		System.out.println(r);
		
		


    	// Sensor Generator = new Sensor();
    	// Generator.lambda = 9; // Generator shall generate 9 customers per second
    	// Generator.sendTo = Gateway; // The generated customers shall be sent to Gateway

    	//H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.
    	//To start the simulation the first signals are put in the signal list

    	// SignalList.SendSignal(SEND, Generator, time);
    	SignalList.SendSignal(MEASURE, null, Gateway, time);

    	// This is the main loop
    	while (time < 100000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:

    	System.out.println("Mean number of customers in queuing system: " + 1.0*Gateway.noMeasurements);

    }
}