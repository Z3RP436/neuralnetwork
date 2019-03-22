package network.layers;

import network.activationfunction.ActivationFunction;
import network.activationfunction.Sigmoid;
import network.neuron.Neuron;

import java.util.LinkedList;

public class AbstractLayer {
	private ActivationFunction activationFunction = new Sigmoid();
	private Neuron[] neurons;
	private double bias;

	public AbstractLayer(Neuron[] neurons) {
		this.neurons = neurons;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}

	public Neuron[] getNeurons() {
		return neurons;
	}

	public void setNeurons(Neuron[] neurons) {
		this.neurons = neurons;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}
}