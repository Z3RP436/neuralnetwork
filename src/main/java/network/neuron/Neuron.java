package network.neuron;

import network.layers.AbstractLayer;
import network.layers.Connection;

import javax.xml.bind.annotation.XmlTransient;

public class Neuron {
	@XmlTransient
	private AbstractLayer layer;
	@XmlTransient
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

	@XmlTransient
	public AbstractLayer getLayer() {
		return layer;
	}

	public void setLayer(AbstractLayer layer) {
		this.layer = layer;
	}
}
