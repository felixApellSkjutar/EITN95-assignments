import java.util.*;
import java.io.*;

class Gateway2 extends Proc{

	public HashMap<Sensor2, Boolean> activeSensors = new HashMap<Sensor2, Boolean>();

	public int totalSignals, successfulSignals, failedSignals, noMeasurements;
	public Proc sendTo;
	Random rand = new Random();

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case START_SIGNAL:{
				totalSignals++;
				activeSensors.put((Sensor2) x.origin, false);
				Sensor2.active_sensors.add((Sensor2) x.origin);
				SignalList.SendSignal(RECIEVE_SIGNAL, x.origin, this, time + ((Sensor2)x.origin).getMessageTime());
			} break;

			case RECIEVE_SIGNAL:{
				boolean collides = activeSensors.remove((Sensor2) x.origin);
				Sensor2.active_sensors.remove((Sensor2) x.origin);
				for (Map.Entry<Sensor2, Boolean> s: activeSensors.entrySet()) {
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
				SignalList.SendSignal(MEASURE, this, this, time + 2*rand.nextDouble());
			} break;
		}
	}
}