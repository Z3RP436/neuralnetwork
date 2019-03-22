package network.neuron;

import network.layers.Connection;

import java.util.LinkedList;

public class InputNeuron extends Neuron{
	private double value;

	public InputNeuron() {
	}

	public InputNeuron(Connection[] connections) {
		super(connections);
	}

	@Override public double fire() {
		return value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
