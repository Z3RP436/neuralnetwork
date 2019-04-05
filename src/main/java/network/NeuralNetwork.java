package network;

import network.layers.HiddenLayer;
import network.layers.InputLayer;
import network.layers.OutputLayer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "neural-network")
public class NeuralNetwork {
	private InputLayer inputLayer;
	private OutputLayer outputLayer;
	private HiddenLayer[] hiddenLayers;

	public InputLayer getInputLayer() {
		return inputLayer;
	}

	public void setInputLayer(InputLayer inputLayer) {
		this.inputLayer = inputLayer;
	}

	public OutputLayer getOutputLayer() {
		return outputLayer;
	}

	public void setOutputLayer(OutputLayer outputLayer) {
		this.outputLayer = outputLayer;
	}

	public HiddenLayer[] getHiddenLayers() {
		return hiddenLayers;
	}

	public void setHiddenLayers(HiddenLayer[] hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
	}
}

