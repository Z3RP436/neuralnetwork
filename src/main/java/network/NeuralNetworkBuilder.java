package network;

import network.activationfunction.ActivationFunction;
import network.layers.*;
import network.neuron.InputNeuron;
import network.neuron.Neuron;

import java.util.Random;

public class NeuralNetworkBuilder {
	private NeuralNetwork neuralNetwork;
	private Random random = new Random();

	public static NeuralNetworkBuilder build() {
		NeuralNetworkBuilder neuralNetworkBuilder = new NeuralNetworkBuilder();
		neuralNetworkBuilder.setNeuralNetwork(new NeuralNetwork());
		return neuralNetworkBuilder;
	}

	public NeuralNetworkBuilder inputLayer(int count) {
		Neuron[] neurons = new Neuron[count];
		for (int i = 0; i < count; i++) {
			neurons[i] = new InputNeuron();
		}

		neuralNetwork.setInputLayer(new InputLayer(neurons));
		setNeuronLayer(neuralNetwork.getInputLayer());
		return this;
	}

	public NeuralNetworkBuilder hiddenLayer(int count, ActivationFunction activationFunction) {
		HiddenLayer hiddenLayer = new HiddenLayer(createNeurons(count));
		hiddenLayer.setLayerBefore(getLastLayer());
		hiddenLayer.setActivationFunction(activationFunction);
		linkToLastLayer(hiddenLayer);
		setNeuronLayer(hiddenLayer);
		addHiddenLayer(hiddenLayer);
		return this;
	}

	public NeuralNetworkBuilder outputLayer(int count, ActivationFunction activationFunction) {
		OutputLayer outputLayer = new OutputLayer(createNeurons(count));
		setNeuronLayer(outputLayer);
		linkToLastLayer(outputLayer);
		outputLayer.setBias(random.nextGaussian());
		outputLayer.setLayerBefore(neuralNetwork.getHiddenLayers()[neuralNetwork.getHiddenLayers().length - 1]);
		outputLayer.setActivationFunction(activationFunction);
		neuralNetwork.setOutputLayer(outputLayer);
		return this;
	}

	public NeuralNetwork getNeuralNetwork() {
		return neuralNetwork;
	}

	public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}

	private void addHiddenLayer(HiddenLayer layer) {
		HiddenLayer[] layers = neuralNetwork.getHiddenLayers();
		HiddenLayer[] _layers;
		if (layers == null) {
			_layers = new HiddenLayer[1];
		} else {
			_layers = new HiddenLayer[layers.length + 1];
			for (int i = 0; i < layers.length; i++) {
				_layers[i] = layers[i];
			}
		}

		_layers[_layers.length - 1] = layer;
		neuralNetwork.setHiddenLayers(_layers);
	}

	private AbstractLayer getLastLayer() {
		HiddenLayer[] hiddenLayers = neuralNetwork.getHiddenLayers();
		if (hiddenLayers == null || hiddenLayers.length <= 0) {
			return neuralNetwork.getInputLayer();
		} else {
			return hiddenLayers[hiddenLayers.length - 1];
		}
	}

	private void linkToLastLayer(AbstractLayer layer) {
		AbstractLayer lastLayer = getLastLayer();
		layer.setBias(random.nextGaussian());
		linkNeurons(layer, lastLayer);
	}

	private void setNeuronLayer(AbstractLayer layer) {
		for (Neuron neuron : layer.getNeurons()) {
			neuron.setLayer(layer);
		}
	}

	private void linkNeurons(AbstractLayer layer, AbstractLayer preLayer) {
		for (int i = 0; i < layer.getNeurons().length; i++) {
			layer.getNeurons()[i].setConnections(new Connection[preLayer.getNeurons().length]);
			for (int j = 0; j < preLayer.getNeurons().length; j++) {
				layer.getNeurons()[i].getConnections()[j] = new Connection(preLayer.getNeurons()[j], random.nextGaussian());
			}
		}
	}

	private Neuron[] createNeurons(int n) {
		Neuron[] neurons = new Neuron[n];
		for (int i = 0; i < n; i++) {
			neurons[i] = new Neuron();
		}
		return neurons;
	}
}
