import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation2 extends Global{

    public static void main(String[] args) throws IOException {
		
		// Here the gateway instance is created
		Gateway2 gateway2 = new Gateway2();
		gateway2.sendTo = null;

		// The signal list is started and actSignal is declared. actSignal is the latest signal that has been fetched from the 
		// signal list in the main loop below.
		Signal actSignal;
		new SignalList();

		// Read config file
		Properties config = new Properties();
        try {
			String path = "config_3000"; // Choose config file to run
            FileInputStream fis = new FileInputStream(path);
            config.load(fis);
            fis.close();
        } catch (Exception e){};
		
        int n = Integer.parseInt(config.getProperty("n")); 			// Number of sensors
        double Tp =  Double.parseDouble(config.getProperty("Tp")); 	// Time it takes to send the message
        double ts = Double.parseDouble(config.getProperty("ts")); 	// Sleep interval for the sensors
        double r = Double.parseDouble(config.getProperty("r")); 	// Radius where transmissions can collide
		
		double lb = 10, ub = 50;

		Sensor2[] sensor_nodes = new Sensor2[n];
		for (int i = 0; i < n; i++) {
			String[] point = config.getProperty("point_" + Integer.toString(i)).split(" ");
			Sensor2 sensor_node = new Sensor2(Double.parseDouble(point[0]), Double.parseDouble(point[1]), r, ts, Tp, gateway2, lb, ub);
			SignalList.SendSignal(CHECK_CHANNEL, null, sensor_node, time + sensor_node.getExpDist());
			sensor_nodes[i] = sensor_node;
		}
		
		System.out.println("n = " + n);
		System.out.println("Tp = " + Tp);
		System.out.println("ts = " + ts);
		System.out.println("radius = " + r);
		
    	//To start the simulation the first signals are put in the signal list
    	SignalList.SendSignal(MEASURE, null, gateway2, time);

    	// This is the main loop
    	while (time < 100000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	//The result of the simulation is printed below:
		System.out.println("Number of total signals going through the network: " + gateway2.totalSignals);
		System.out.println("Number of succesful signals going through the network: " + gateway2.successfulSignals);
		System.out.println("Number of failed signals going through the network: " + gateway2.failedSignals);
		System.out.println("Arrival rate: " + 1.0*gateway2.totalSignals/time);
    	System.out.println("Calculated throughput: " + 1.0*gateway2.successfulSignals/time);
		
		double lambda_p = (1.0*gateway2.totalSignals/time);
		double T_put = lambda_p * Math.exp(-2*lambda_p);
    	System.out.println("Throughput: " + T_put);


		double packetLossProb = gateway2.failedSignals/(1.0*gateway2.totalSignals);
		System.out.println("Packet Loss Probability: " + packetLossProb);

    }
}