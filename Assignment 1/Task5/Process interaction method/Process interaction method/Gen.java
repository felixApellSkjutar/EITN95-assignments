import java.util.*;
import java.io.*;

//Denna klass �rver Proc, det g�r att man kan anv�nda time och signalnamn utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation 

class Gen extends Proc{

	//Slumptalsgeneratorn startas:
	//The random number generator is started:
	Random slump = new Random();

	//Generatorn har tv� parametrar:
	//There are two parameters:
	public List<QS> sendToList = new ArrayList<QS>();
	public Proc sendTo;    //Anger till vilken process de genererade kunderna ska skickas //Where to send customers
	public double lambda;  //Hur m�nga per sekund som ska generas //How many to generate per second
	private Iterator<QS> it = sendToList.iterator();

	//H�r nedan anger man vad som ska g�ras n�r en signal kommer //What to do when a signal arrives
	public void TreatSignal(Signal x){
		switch (x.signalType){
			case READY:{
				sendTo = nextQueue();
				SignalList.SendSignal(ARRIVAL, sendTo, time);
				SignalList.SendSignal(READY, this, time + (2.0/lambda)*slump.nextDouble());}
				break;
		}
	}

	private Proc nextQueue() {
		return smartDispatch();
	}

	private Proc randomDispatch() {
		int queue = slump.nextInt(5);
		return sendToList.get(queue);
	}

	private Proc roundRobinDispatch() {
		if (it.hasNext()) {
			return it.next();
		} else {
			it = sendToList.iterator();
			return it.next();
		}
	}

	private Proc smartDispatch() {
		int min = Integer.MAX_VALUE;
		List<QS> tieBreakers = new ArrayList<QS>();
		for(QS p : sendToList) {
			if (p.numberInQueue < min){
				min = p.numberInQueue;
			}
		}
		for (QS p : sendToList) {
			if (p.numberInQueue == min) {
				tieBreakers.add(p);
			}
		}
		int queue = slump.nextInt(tieBreakers.size());
		return tieBreakers.get(queue);
	}
}