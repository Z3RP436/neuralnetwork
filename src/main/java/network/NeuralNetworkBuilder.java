package network;

import network.activationfunction.ActivationFunction;
import network.layers.HiddenLayer;
import network.layers.InputLayer;
import network.layers.OutputLayer;
import network.neuron.InputNeuron;
import network.neuron.Neuron;

public class NeuralNetworkBuilder {
	private NeuralNetwork neuralNetwork;

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
		NeuralNetworkController.setNeuronLayer(neuralNetwork.getInputLayer());
		return this;
	}

	public NeuralNetworkBuilder hiddenLayer(int count, ActivationFunction activationFunction) {
		HiddenLayer hiddenLayer = new HiddenLayer(NeuralNetworkController.createNeurons(count));
		hiddenLayer.setLayerBefore(NeuralNetworkController.getLastLayer(neuralNetwork));
		hiddenLayer.setActivationFunction(activationFunction);
		NeuralNetworkController.linkToLastLayer(neuralNetwork, hiddenLayer);
		NeuralNetworkController.setNeuronLayer(hiddenLayer);
		NeuralNetworkController.addHiddenLayer(neuralNetwork, hiddenLayer);
		return this;
	}

	public NeuralNetworkBuilder outputLayer(int count, ActivationFunction activationFunction) {
		OutputLayer outputLayer = new OutputLayer(NeuralNetworkController.createNeurons(count));
		NeuralNetworkController.setNeuronLayer(outputLayer);
		NeuralNetworkController.linkToLastLayer(neuralNetwork, outputLayer);
		outputLayer.setBias(NeuralNetworkController.random.nextGaussian());
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


}
