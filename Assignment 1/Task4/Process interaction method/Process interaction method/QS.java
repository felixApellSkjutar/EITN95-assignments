import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int numberInQueue = 0, accumulated, nmbrMeasurements;
	public Proc sendTo;
	Random slump = new Random();
	double skipQueFactor = 0.3;

	int specialQue = 0, totalArrivedSpecial = 0, normalQue = 0, totalArrivedNormal = 0, totalHandeledSpecial = 0, totalHandeledNormal = 0;

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case ARRIVAL:{
				
				if(specialArrival()) {
					specialQue++;
					totalArrivedSpecial++;
				} else {
					normalQue++;
					totalArrivedNormal++;
				}

				numberInQueue++;

				if (numberInQueue == 1){
					SignalList.SendSignal(READY,this, time + expDist(4, slump));
				}
			} break;

			case READY:{

				if (specialQue > 0) {
					specialQue--;
					totalHandeledSpecial++;
				} else {
					normalQue--;
					totalHandeledNormal++;
				}
				numberInQueue--;
				
				if (numberInQueue > 0){
					SignalList.SendSignal(READY, this, time + expDist(4, slump));
				}
			} break;

			case MEASURE:{
				nmbrMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
			} break;
		}
	}

	private boolean specialArrival() {

		int len = 100;
		int ran = slump.nextInt(len);
		if (ran < skipQueFactor*len) {
			return true;
		}
		return false;

	}
}