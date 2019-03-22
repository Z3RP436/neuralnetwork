package network.activationfunction;

public class Sigmoid implements ActivationFunction{

	@Override public double activate(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	@Override public double derivativeActivate(double x) {
		return (Math.exp(-x))/ Math.pow((1+Math.exp(-x)),2);
	}
}
