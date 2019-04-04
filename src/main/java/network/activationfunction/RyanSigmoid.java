package network.activationfunction;

public class RyanSigmoid implements ActivationFunction{

	@Override public double activate(double x) {
		return (1/( 1 + Math.pow(Math.E,(-1*x))));
	}

	@Override public double derivativeActivate(double x) {
		return 0;
	}
}
