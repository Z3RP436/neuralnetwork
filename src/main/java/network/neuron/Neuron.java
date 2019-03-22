package network.neuron;

import network.layers.AbstractLayer;
import network.layers.Connection;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;

public class Neuron {
	private AbstractLayer layer;
	private Connection[] connections;

	public Neuron() {
	}

	public Neuron(Connection[] connections) {
		this.connections = connections;
	}

	public double fire(){
		double value = 0;
		for(Connection connection: connections){
			value += connection.getNeuron().fire() * connection.getWeight();
		}
		return layer.getActivationFunction().activate((value + layer.getBias() * 1));
	}

	public double backFire(){
		double value = 0;
		for(Connection connection: connections){
			value += layer.getActivationFunction().derivativeActivate(connection.getNeuron().fire() * connection.getWeight());
		}
		return value;
	}

	public Connection[] getConnections() {
		return connections;
	}

	public void setConnections(Connection[] connections) {
		this.connections = connections;
	}

	public AbstractLayer getLayer() {
		return layer;
	}

	public void setLayer(AbstractLayer layer) {
		this.layer = layer;
	}
}
