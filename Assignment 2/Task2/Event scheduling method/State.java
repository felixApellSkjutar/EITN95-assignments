import java.util.*;

class State extends GlobalSimulation{
	
	public int  numberInQueue = 0;

	Random slump = new Random(); // This is just a random number generator
	
	double lambda = 4.0/60; //mean inter-arrival time: 4 customers per hour
	double maxArrivalTime = 17*60.0; //the pharmacy is open until 17:00
	
	public List<Double> qTimes = new ArrayList<Double>();
	public LinkedList<Double> arrivalTimes = new LinkedList<>();

	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				if (time <= maxArrivalTime) {
					arrival();
				}
				break;
			case READY:
				ready();
				break;
		}
	}
	
	private void arrival(){
		arrivalTimes.addLast(time);
		if (numberInQueue == 0){
			double serviceTime = prescriptonFill(slump);
			insertEvent(READY, time + serviceTime);
		}
		numberInQueue++;
		double nextTime = time + expDist(lambda);
		insertEvent(ARRIVAL, nextTime);
	}
	
	private void ready(){
		qTimes.add(time - arrivalTimes.pop());
		numberInQueue--;
		if (numberInQueue > 0){
			double serviceTime = prescriptonFill(slump);
			insertEvent(READY, time + serviceTime);
		}
	}


	private double expDist(double lambda) {
		double u = slump.nextDouble();
		return Math.log(1 - u)*(-1.0/lambda);
	}


	private double prescriptonFill(Random slump) {
		//uniformly distributed between 10-20 min
		return ((new Random()).nextDouble())*10.0 + 10.0;
	}
}