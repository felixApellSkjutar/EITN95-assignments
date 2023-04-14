import java.util.Random;

public abstract class Proc extends Global{
	public abstract void TreatSignal(Signal x);
	
	public double expDist(double mean, Random slump) {
		double u = slump.nextDouble();
		return (Math.log(1 - u)*(-mean));
	}
}

