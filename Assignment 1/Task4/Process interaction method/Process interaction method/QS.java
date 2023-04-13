import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int numberInQueue = 0, accumulated, nmbrMeasurements;
	public Proc sendTo;
	Random slump = new Random();
	double skipQueFactor = 0.1;

	int specialQue = 0, totalArrivedSpecial = 0, normalQue = 0, totalArrivedNormal = 0, totalHandeledSpecial = 0, totalHandeledNormal = 0;
	public double normalAccQuetime = 0, specialAccQuetime = 0;

	LinkedList<Double> queTime = new LinkedList<Double>();

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case ARRIVAL:{
				//I arrival, lägg till arrivaltime längst bak i lista
				//I ready - poppa arrivaltime från listan, mät hur länge de köat och summera hela den skiten
				//Sen kan vi mäta hur länge de köat i average genom att dividera med antalet hanetarde av varje typ.
				
				if(specialArrival()) {
					specialQue++;
					totalArrivedSpecial++;
				} else {
					normalQue++;
					totalArrivedNormal++;
				}

				numberInQueue++;
				//Add current time of arrival last in queTime
				//When ready, pop first in queTime, measure how long it has been in queue and add to total
				queTime.addLast(time);

				if (numberInQueue == 1){
					SignalList.SendSignal(READY,this, time + expDist(4, slump));
				}
			} break;

			case READY:{

				if (specialQue > 0) {
					specialAccQuetime += time - queTime.pop();
					specialQue--;
					totalHandeledSpecial++;
				} else {
					normalAccQuetime += time - queTime.pop();
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