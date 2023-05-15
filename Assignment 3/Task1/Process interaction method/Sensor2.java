import java.util.*;

import javax.swing.UIDefaults.ActiveValue;

import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation 

class Sensor2 extends Proc{
	public static ArrayList<Sensor2> active_sensors = new ArrayList<Sensor2>(); 

	private double x;
	private double y;
	private double radius;
	private double lambda;
	private double ub;
	private double lb;
	private double messageTime;
	private Random rand = new Random();

	public Proc sendTo; //Where to send sensor signal
	
	public Sensor2(double x, double y, double radius, double lambda, double messageTime, Proc sendTo, double lb, double ub) {
		this.x = x;
		this.y = y;
		this.radius = radius*1000;
		this.lambda = lambda;
		this.messageTime = messageTime;
		this.sendTo = sendTo;
		this.lb = lb;
		this.ub = ub;
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

	public double getMessageTime() {
		return this.messageTime;
	}

	public double getExpDist() {
		return Math.log(1 - rand.nextDouble())*-lambda;
	}

	public boolean checkCollision(Sensor2 other) {
		double xDiff = Math.pow(other.getX() - this.getX(),2);
		double yDiff = Math.pow(other.getY() - this.getY(), 2);
		
		if (Math.sqrt(xDiff + yDiff) < other.getRadius()) {
			return true;
		}
		return false;
	}

	private double uniformSleep() {
		return lb + (ub - lb)*rand.nextDouble();
	}

	//What to do when a signal arrives
	public void TreatSignal(Signal x){
		switch (x.signalType){

			case START_REPORT:{
				SignalList.SendSignal(START_SIGNAL, this, sendTo, time);
				SignalList.SendSignal(CHECK_CHANNEL, this, this, time + messageTime + getExpDist());
			} break;

			case CHECK_CHANNEL:
				//Check if any sensor within radius r is transimitting signal
				for (Sensor2 s : active_sensors) {
					if(this.checkCollision(s)) {
						SignalList.SendSignal(CHECK_CHANNEL, this, this, time + uniformSleep());
						return;
					}
				}
				//If no collision, send signal
				SignalList.SendSignal(START_REPORT, this, this, time);
				break;
		}
	}
}