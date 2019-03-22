package network;

import network.layers.*;
import network.neuron.InputNeuron;
import network.neuron.Neuron;

import java.util.LinkedList;
import java.util.Random;

public class NeuralNetworkBuilder {
	private NeuralNetwork neuralNetwork;
	private Random random = new Random();

	public static NeuralNetworkBuilder build(){
		NeuralNetworkBuilder neuralNetworkBuilder = new NeuralNetworkBuilder();
		neuralNetworkBuilder.setNeuralNetwork(new NeuralNetwork());
		neuralNetworkBuilder.getNeuralNetwork().setHiddenLayers(new HiddenLayer[1]);
		return neuralNetworkBuilder;
	}

	public NeuralNetworkBuilder inputLayer(int count){
		Neuron[] neurons = new Neuron[count];
		for(int i = 0;i< count;i++){
			neurons[i] = new InputNeuron();
		}

		neuralNetwork.setInputLayer(new InputLayer(neurons));
		setNeuronLayer(neuralNetwork.getInputLayer());
		return this;
	}

	public NeuralNetworkBuilder hiddenLayer(int count){
		HiddenLayer hiddenLayer = new HiddenLayer(createNeurons(count));
		linkToLastHiddenLayer(hiddenLayer);
		setNeuronLayer(hiddenLayer);
		neuralNetwork.getHiddenLayers()[0] = hiddenLayer;
		return this;
	}

	public NeuralNetworkBuilder outputLayer(int count){
		OutputLayer outputLayer = new OutputLayer(createNeurons(count));
		setNeuronLayer(outputLayer);
		linkToLastHiddenLayer(outputLayer);
		outputLayer.setBias(random.nextGaussian());
		neuralNetwork.setOutputLayer(outputLayer);
		return this;
	}

	public NeuralNetwork getNeuralNetwork() {
		return neuralNetwork;
	}

	public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}

	private void linkToLastHiddenLayer (AbstractLayer layer){
		HiddenLayer[] layers = neuralNetwork.getHiddenLayers();
		layer.setBias(random.nextGaussian());
		if(layers.length < 0 || layers[0] == null){
			linkNeurons(layer,neuralNetwork.getInputLayer());
		}else{
			linkNeurons(layer,layers[layers.length -1 ]);
		}
	}

	private void setNeuronLayer(AbstractLayer layer){
		for(Neuron neuron: layer.getNeurons()){
			neuron.setLayer(layer);
		}
	}

	private void linkNeurons (AbstractLayer layer, AbstractLayer preLayer){
		for(int i = 0; i< layer.getNeurons().length;i++){
			layer.getNeurons()[i].setConnections(new Connection[preLayer.getNeurons().length]);
			for(int j = 0; j < preLayer.getNeurons().length;j++){
				layer.getNeurons()[i].getConnections()[j] = new Connection(preLayer.getNeurons()[j],random.nextGaussian());
			}
		}
	}

	private Neuron[] createNeurons(int n){
		Neuron[] neurons = new Neuron[n];
		for(int i = 0;i<n;i++){
			neurons[i] = new Neuron();
		}
		return neurons;
	}
}
