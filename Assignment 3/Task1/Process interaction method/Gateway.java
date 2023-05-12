import java.util.*;
import java.io.*;

class Gateway extends Proc{

	public List<Proc> activeSensors = new ArrayList<Proc>();

	public int successfulSignals, failedSignals, noMeasurements;
	public Proc sendTo;
	Random rand = new Random();

	public boolean checkCollision(Sensor s1, Sensor s2) {
		double xDiff = Math.pow(Math.abs(s1.getX() - s2.getX()),2);
		double yDiff = Math.pow(Math.abs(s1.getY() - s2.getY()), 2);

		if (Math.sqrt(xDiff + yDiff) < s1.getRadius()) {
			return true;
		}
		return false;
	}

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case START_SIGNAL:{
				boolean collision = false;
				for (Proc s : activeSensors) {
					if (checkCollision((Sensor) s, (Sensor) x.origin)) {
						activeSensors.remove(s);
						collision = true;
						failedSignals++;
					}
				}
				if (!collision) {
					activeSensors.add(x.origin);
					successfulSignals++;
				} 
			} break;

			case RECIEVE_SIGNAL:{
				
			} break;

			case MEASURE:{
				noMeasurements++;
				SignalList.SendSignal(MEASURE, this, this, time + 2*rand.nextDouble());
			} break;
		}
	}
}