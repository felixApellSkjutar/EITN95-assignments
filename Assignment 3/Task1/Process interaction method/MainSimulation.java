 import java.util.*;
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
 			String path = "config_1000"; // Choose config file to run
             FileInputStream fis = new FileInputStream(path);
             config.load(fis);
             fis.close();
         } catch (Exception e){};
		
         int n = Integer.parseInt(config.getProperty("n")); 			// Number of sensors
         double Tp =  Double.parseDouble(config.getProperty("Tp")); 	// Time it takes to send the message
         double ts = Double.parseDouble(config.getProperty("ts")); 	// Sleep interval for the sensors
         double r = Double.parseDouble(config.getProperty("r")); 	// Radius where transmissions can collide
		
 		Sensor[] sensor_nodes = new Sensor[n];
 		for (int i = 0; i < n; i++) {
 			String[] point = config.getProperty("point_" + Integer.toString(i)).split(" ");
 			Sensor sensor_node = new Sensor(Double.parseDouble(point[0]), Double.parseDouble(point[1]), r, ts, Tp, Gateway);
 			SignalList.SendSignal(START_REPORT, null, sensor_node, time + sensor_node.getExpDist());
 			sensor_nodes[i] = sensor_node;
 		}
		
 		System.out.println("n = " + n);
 		System.out.println("Tp = " + Tp);
 		System.out.println("ts = " + ts);
 		System.out.println("radius = " + r);
		
    	//To start the simulation the first signals are put in the signal list
    	SignalList.SendSignal(MEASURE, null, Gateway, time + 10);

    	// This is the main loop
    	while (time < 20000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

     	//The result of the simulation is printed below:
 		System.out.println("Number of total signals going through the network: " + Gateway.totalSignals);
 		System.out.println("Number of succesful signals going through the network: " + Gateway.successfulSignals);
 		System.out.println("Number of failed signals going through the network: " + Gateway.failedSignals);
 		System.out.println("Arrival rate: " + 1.0*Gateway.totalSignals/time);
     	System.out.println("Calculated throughput: " + 1.0*Gateway.successfulSignals/time);
		
		double lambda_p = (1.0*Gateway.totalSignals/time);
		double T_put = lambda_p * Math.exp(-2*lambda_p);
		double packetLoss = 1.0*Gateway.failedSignals/Gateway.totalSignals;
    	System.out.println("Throughput: " + T_put);
		System.out.println("Packet loss: " + packetLoss);
		System.out.println("Confidence interval: " + Gateway.confidenceInterval);

    }
}
