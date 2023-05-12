import java.util.*;
import java.io.*;

class Gateway extends Proc{
	public int numberInQueue = 0, accumulated, noMeasurements;
	public Proc sendTo;
	Random slump = new Random();

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case START_SIGNAL:{
				numberInQueue++;
				if (numberInQueue == 1){
					SignalList.SendSignal(RECIEVE_SIGNAL,this, time + 0.2*slump.nextDouble());
				}
			} break;

			case RECIEVE_SIGNAL:{
				numberInQueue--;
				if (sendTo != null){
					SignalList.SendSignal(START_SIGNAL, sendTo, time);
				}
				if (numberInQueue > 0){
					SignalList.SendSignal(RECIEVE_SIGNAL, this, time + 0.2*slump.nextDouble());
				}
			} break;

			case MEASURE:{
				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
			} break;
		}
	}
}