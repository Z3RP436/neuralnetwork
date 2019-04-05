package network;

import network.layers.AbstractLayer;
import network.layers.Connection;
import network.layers.HiddenLayer;
import network.layers.OutputLayer;
import network.neuron.InputNeuron;
import network.neuron.Neuron;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Random;

public class NeuralNetworkController {
	public static final Random random = new Random();

	public static double train(NeuralNetwork nn, double[] targets, double learningRate) {
		double[] newHToOWeights = oWeights(nn, targets, learningRate);

		for (HiddenLayer hiddenLayer : nn.getHiddenLayers()) {
			double[] newIToHWeights = hWeights(nn, targets, learningRate, hiddenLayer);
			int iterator = 0;
			for (Neuron hiddenNeuron : hiddenLayer.getNeurons()) {
				for (Connection connection : hiddenNeuron.getConnections()) {
					connection.setWeight(newIToHWeights[iterator]);
					iterator++;
				}
			}
		}
		int iterator = 0;
		for (Neuron outputNeuron : nn.getOutputLayer().getNeurons()) {
			for (Connection connection : outputNeuron.getConnections()) {
				connection.setWeight(newHToOWeights[iterator]);
				iterator++;
			}
		}

		return 0;
	}

	public static void saveNetwork(NeuralNetwork nn, String filePath) throws JAXBException, IOException {

		//Create JAXB Context
		JAXBContext jaxbContext = JAXBContext.newInstance(NeuralNetwork.class);

		//Create Marshaller
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		//Required formatting??
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		//Print XML String to Console
		StringWriter sw = new StringWriter();

		//Write XML to StringWriter
		jaxbMarshaller.marshal(nn, sw);

		//Verify XML Content
		FileWriter fw = new FileWriter(filePath);
		fw.write(sw.toString());
		fw.close();
	}

	public static NeuralNetwork loadNetwork(String filePath) throws JAXBException {
		NeuralNetwork neuralNetwork;
		File file = new File(filePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(NeuralNetwork.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		neuralNetwork = (NeuralNetwork) jaxbUnmarshaller.unmarshal(file);

		for(int i = 0; i< neuralNetwork.getInputLayer().getNeurons().length;i++){
			InputNeuron inputNeuron = new InputNeuron();
			inputNeuron.setLayer(neuralNetwork.getInputLayer());
			neuralNetwork.getInputLayer().getNeurons()[i] = inputNeuron;
		}

		for(Neuron neuron : neuralNetwork.getInputLayer().getNeurons()){
			neuron.setLayer(neuralNetwork.getInputLayer());
		}
		for(int i = 0; i <neuralNetwork.getHiddenLayers().length ; i++){
			HiddenLayer layer = neuralNetwork.getHiddenLayers()[i];
			if(i == 0){
				layer.setLayerBefore(neuralNetwork.getInputLayer());
			}else{
				layer.setLayerBefore(neuralNetwork.getHiddenLayers()[i-1]);
			}

			linkOldConnections(layer);
		}
		OutputLayer outputLayer = neuralNetwork.getOutputLayer();
		if(neuralNetwork.getHiddenLayers().length > 0){
			outputLayer.setLayerBefore(neuralNetwork.getHiddenLayers()[neuralNetwork.getHiddenLayers().length-1]);
		}else{
			outputLayer.setLayerBefore(neuralNetwork.getInputLayer());
		}
		linkOldConnections(outputLayer);
		return neuralNetwork;
	}

	private static void linkOldConnections(AbstractLayer layer){
		for(int j =0 ; j< layer.getNeurons().length;j++){
			Neuron neuron = layer.getNeurons()[j];
			if(neuron.getLayer() == null){
				neuron.setLayer(layer);
			}
			for(int o = 0 ; o < neuron.getConnections().length;o++){
				Connection connection = neuron.getConnections()[o];
				connection.setNeuron(layer.getLayerBefore().getNeurons()[o]);
			}
		}
	}

	private static double[] oWeights(NeuralNetwork nn, double[] targets, double learningRate) {
		int iterator = 0;
		double[] newWeights = new double[nn.getOutputLayer().getLayerBefore().getNeurons().length * nn.getOutputLayer().getNeurons().length];
		double[][] vals = generateOutputVals(nn, targets);
		double[] outNetO1 = vals[0];
		double[] eOutputs = vals[1];

		for (int j = 0; j < nn.getOutputLayer().getNeurons().length; j++) {
			Neuron outputNeuron = nn.getOutputLayer().getNeurons()[j];
			for (int i = 0; i < outputNeuron.getConnections().length; i++) {
				Connection connection = outputNeuron.getConnections()[i];

				double weight = connection.getWeight();
				double eTotal = eOutputs[j] * outNetO1[j] * connection.getNeuron().fire();
				double newWeight = weight - learningRate * eTotal;
				newWeights[iterator] = newWeight;
				iterator++;
			}
		}

		return newWeights;
	}

	private static double[] hWeights(NeuralNetwork nn, double[] targets, double learningRate, HiddenLayer hiddenLayer) {
		double[] newWeights = new double[hiddenLayer.getNeurons().length * hiddenLayer.getLayerBefore().getNeurons().length];
		double[][] vals = generateOutputVals(nn, targets);
		double[] outNetO1 = vals[0];
		double[] eOutputs = vals[1];

		int iterator = 0;
		for (int j = 0; j < hiddenLayer.getNeurons().length; j++) {
			Neuron hNeuron = hiddenLayer.getNeurons()[j];
			double eTotalOutHNeuron = 0;
			double outH = hNeuron.fire();

			for (int i = 0; i < nn.getOutputLayer().getNeurons().length; i++) {
				Neuron outputNeuron = nn.getOutputLayer().getNeurons()[i];

				double weight = outputNeuron.getConnections()[j].getWeight();
				double eNet = eOutputs[i] * outNetO1[i];
				double eOut = eNet * weight;
				eTotalOutHNeuron += eOut;
			}

			for (int i = 0; i < hNeuron.getConnections().length; i++) {
				Connection connection = hNeuron.getConnections()[i];

				double weight = connection.getWeight();
				double input;
				if (connection.getNeuron() instanceof InputNeuron) {
					input = ((InputNeuron) connection.getNeuron()).getValue();
				} else {
					input = connection.getNeuron().fire();
				}
				double outHNetH = outH * (1 - outH);
				double eTotal = eTotalOutHNeuron * outHNetH * input;
				double newW = weight - learningRate * eTotal;
				newWeights[iterator] = newW;
				iterator++;
			}
		}
		return newWeights;
	}

	private static double[][] generateOutputVals(NeuralNetwork nn, double[] targets) {
		double[] outNetO1 = new double[nn.getOutputLayer().getNeurons().length];
		double[] eOutputs = new double[nn.getOutputLayer().getNeurons().length];
		for (int i = 0; i < nn.getOutputLayer().getNeurons().length; i++) {
			Neuron outputNeuron = nn.getOutputLayer().getNeurons()[i];
			outNetO1[i] = outputNeuron.fire() * (1 - outputNeuron.fire());
			eOutputs[i] = -(targets[i] - outputNeuron.fire());
		}
		return new double[][] { outNetO1, eOutputs };
	}

	public static void addHiddenLayer(NeuralNetwork nn, HiddenLayer layer) {
		HiddenLayer[] layers = nn.getHiddenLayers();
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
		nn.setHiddenLayers(_layers);
	}

	public static AbstractLayer getLastLayer(NeuralNetwork nn) {
		HiddenLayer[] hiddenLayers = nn.getHiddenLayers();
		if (hiddenLayers == null || hiddenLayers.length <= 0) {
			return nn.getInputLayer();
		} else {
			return hiddenLayers[hiddenLayers.length - 1];
		}
	}

	public static void linkToLastLayer(NeuralNetwork nn, AbstractLayer layer) {
		AbstractLayer lastLayer = getLastLayer(nn);
		layer.setBias(random.nextGaussian());
		linkNeurons(layer, lastLayer);
	}

	public static void setNeuronLayer(AbstractLayer layer) {
		for (Neuron neuron : layer.getNeurons()) {
			neuron.setLayer(layer);
		}
	}

	public static void linkNeurons(AbstractLayer layer, AbstractLayer preLayer) {
		for (int i = 0; i < layer.getNeurons().length; i++) {
			layer.getNeurons()[i].setConnections(new Connection[preLayer.getNeurons().length]);
			for (int j = 0; j < preLayer.getNeurons().length; j++) {
				layer.getNeurons()[i].getConnections()[j] = new Connection(preLayer.getNeurons()[j], random.nextGaussian());
			}
		}
	}

	public static Neuron[] createNeurons(int n) {
		Neuron[] neurons = new Neuron[n];
		for (int i = 0; i < n; i++) {
			neurons[i] = new Neuron();
		}
		return neurons;
	}
}
