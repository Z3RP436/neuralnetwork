package network.layers;

import network.neuron.Neuron;

import javax.xml.bind.annotation.XmlTransient;

public class Connection {
	@XmlTransient
	private Neuron neuron;
	private double weight;

	public Connection() {
	}

	public Connection(Neuron neuron, double weight) {
		this.neuron = neuron;
		this.weight = weight;
	}

	@XmlTransient
	public Neuron getNeuron() {
		return neuron;
	}

	public void setNeuron(Neuron neuron) {
		this.neuron = neuron;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
