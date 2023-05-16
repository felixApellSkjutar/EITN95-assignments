import java.util.*;
import java.io.*;

class Gateway extends Proc{

	public HashMap<Sensor, Boolean> activeSensors = new HashMap<Sensor, Boolean>();
	public List<Double> packetLosses = new ArrayList<Double>();

	public int totalSignals, successfulSignals, failedSignals, noMeasurements;
	public Proc sendTo;
	Random rand = new Random();
	public double confidenceInterval = 1.0;

	public boolean checkCollision(Sensor s1, Sensor s2) {
		double xDiff = Math.pow(s1.getX() - s2.getX(),2);
		double yDiff = Math.pow(s1.getY() - s2.getY(), 2);
		
		if (Math.sqrt(xDiff + yDiff) < s1.getRadius()) {
			return true;
		}
		return false;
	}

	public double confidenceInterval(List<Double> list) {
		int N = list.size();
		double mean = list.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
		double var = 0.0;
		for (double d : list) {
			var += Math.pow(d - mean, 2);
		}
		var = var / N;
		double std = Math.sqrt(var);
		return 1.96 * (std / Math.sqrt(N));
	}

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case START_SIGNAL:{
				totalSignals++;
				activeSensors.put((Sensor) x.origin, false);
				SignalList.SendSignal(RECIEVE_SIGNAL, x.origin, this, time + ((Sensor)x.origin).getMessageTime());
			} break;

			case RECIEVE_SIGNAL:{
				boolean collides = activeSensors.remove((Sensor) x.origin);
				for (Map.Entry<Sensor, Boolean> s: activeSensors.entrySet()) {
					// if (checkCollision(s.getKey(), (Sensor) x.origin)) {
					// 	s.setValue(true);
					// 	collides = true;
					// }
					s.setValue(true);
					collides = true;
				}
				if (collides) { //true if collision
					failedSignals++;
				} else {
					successfulSignals++;
				}
			} break;

			case MEASURE:{
				noMeasurements++;
				double packetLoss = 1.0*failedSignals / totalSignals;
				packetLosses.add(packetLoss);
				confidenceInterval = confidenceInterval(packetLosses);
				SignalList.SendSignal(MEASURE, this, this, time + 2*rand.nextDouble());
			} break;
		}
	}
}