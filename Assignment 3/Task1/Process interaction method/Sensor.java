import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation 

class Sensor extends Proc{

	private double x;
	private double y;
	private double radius;
	private double lambda;
	private Random rand = new Random();

	public Proc sendTo; //Where to send sensor signal
	
	public Sensor(double x, double y, double radius, double lambda, Proc sendTo) {
		this.x = x;
		this.y = y;
		this.radius = radius*1000;
		this.lambda = lambda;
		this.sendTo = sendTo;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getRadius() {
		return this.radius;
	}

	public double getExpDist() {
		return Math.log(1 - rand.nextDouble())*-lambda;
	}
	

	//What to do when a signal arrives
	public void TreatSignal(Signal x){
		switch (x.signalType){

			case START_REPORT:{

				SignalList.SendSignal(START_SIGNAL, this, sendTo, time);
				// SignalList.SendSignal(RECIEVE, this, time + getExpDist());
			} break;

			case STOP_REPORT:{
				// SignalList.SendSignal(SEND, sendTo, time);
				// SignalList.SendSignal(RECIEVE, this, time + getExpDist());
			} break;
		}
	}
}