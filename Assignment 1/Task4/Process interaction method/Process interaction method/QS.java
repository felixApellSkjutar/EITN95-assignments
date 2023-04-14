import java.util.*;

class QS extends Proc{
	public int numberInQueue = 0, accumulated, nmbrMeasurements;
	public Proc sendTo;
	Random slump = new Random();
	double skipQueFactor = 0.1;

	int  arrivals = 0, servedSpecial = 0, servedNormal = 0;
	public double normalAccQuetime = 0, specialAccQuetime = 0;

	LinkedList<Double> queTime = new LinkedList<Double>();
	LinkedList<Double> queTimeSpecial = new LinkedList<Double>();

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case ARRIVAL:
				arrival();
				break;

			case READY:
				ready();
			break;

			case MEASURE:{
				nmbrMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
			} break;
		}
	}

	private void ready() {
		
		if (queTimeSpecial.size() > 0) {
			specialAccQuetime += time - queTimeSpecial.pop();
			servedSpecial++;
		} else {
			normalAccQuetime += time - queTime.pop();
			servedNormal++;
		}
		numberInQueue--;
		
		if (numberInQueue > 0){
			SignalList.SendSignal(READY, this, time + expDist(4, slump));
		}
	}

	private void arrival() {
		if(slump.nextDouble() < skipQueFactor) {
			queTimeSpecial.addLast(time);
		} else {
			queTime.addLast(time);
		}

		numberInQueue++;
		arrivals++;

		if (numberInQueue == 1){
			SignalList.SendSignal(READY,this, time + expDist(4, slump));
		}
	}
}