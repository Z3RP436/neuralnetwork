package network.layers;

import network.activationfunction.ActivationFunction;
import network.activationfunction.FastSigmoid;
import network.activationfunction.RyanSigmoid;
import network.activationfunction.Sigmoid;
import network.neuron.Neuron;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "layer")
@XmlSeeAlso({FastSigmoid.class,Sigmoid.class,RyanSigmoid.class})
public class AbstractLayer {
	@XmlTransient
	private AbstractLayer layerBefore;
	private ActivationFunction activationFunction;
	private Neuron[] neurons;
	@XmlTransient
	private double bias;

	public AbstractLayer() {
	}

	public AbstractLayer(Neuron[] neurons) {
		this.neurons = neurons;
	}

	@XmlElement(type=Object.class)
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

	@XmlTransient
	public AbstractLayer getLayerBefore() {
		return layerBefore;
	}

	public void setLayerBefore(AbstractLayer layerBefore) {
		this.layerBefore = layerBefore;
	}
}
