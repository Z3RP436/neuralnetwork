package network.activationfunction;

public class FastSigmoid implements ActivationFunction{
	@Override public double activate(double x) {
		return x / (1 + Math.abs(x));
	}

	@Override public double derivativeActivate(double x) {
		return 0;
	}
}
