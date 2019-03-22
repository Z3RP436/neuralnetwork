package network;

import network.layers.Connection;
import network.layers.HiddenLayer;
import network.layers.InputLayer;
import network.layers.OutputLayer;
import network.neuron.InputNeuron;
import network.neuron.Neuron;

import java.util.LinkedList;

public class NeuralNetwork {
	private InputLayer inputLayer;
	private OutputLayer outputLayer;
	private HiddenLayer[] hiddenLayers;

	public double trainSuccess(double target) {
		//		Neuron outputNeuron = outputLayer.getNeurons().get(0);
		//		LinkedList<Neuron> hiddenLayerNeurons = hiddenLayers.get(0).getNeurons();
		//
		//		double calculated = outputLayer.getNeurons().get(0).fire();
		//		double errorOutput = target - calculated;
		//		double deltaOutput = outputLayer.getActivationFunction().derivativeActivate(outputLayer.getNeurons().get(0).backFire()) * errorOutput;
		//
		//
		//		double[] hiddenLayerResults = new double[hiddenLayerNeurons.size()];
		//		for(int i = 0 ; i < hiddenLayerResults.length;i++){
		//			hiddenLayerResults[i] = hiddenLayerNeurons.get(i).fire();
		//		}
		//
		//		double[] hiddenToOuterWeights = new double[outputNeuron.getConnections().size()];
		//		for(int i = 0 ; i < hiddenToOuterWeights.length;i++){
		//			hiddenToOuterWeights[i] = outputNeuron.getConnections().get(i).getWeight();
		//		}
		//
		//		double[] hiddenSum = new double[outputNeuron.getConnections().size()];
		//		for(int i = 0 ; i < hiddenSum.length;i++){
		//			hiddenSum[i] = outputNeuron.getConnections().get(i).getNeuron().backFire();
		//		}
		//		double[] inputData = new double[inputLayer.getNeurons().size()];
		//		for(int i = 0 ; i < inputData.length;i++){
		//			inputData[i] = ((InputNeuron)inputLayer.getNeurons().get(i)).getValue();
		//		}
		//		double[] hiddenWeights = new double[inputLayer.getNeurons().size() * hiddenLayerNeurons.size()];
		//		for(int i = 0 ; i < hiddenLayerNeurons.size();i++){
		//			for(int j = 0 ; j < inputLayer.getNeurons().size();j++) {
		//				hiddenWeights[i+j+1] = hiddenLayerNeurons.get(i).getConnections().get(j).getWeight();
		//			}
		//		}
		//
		//
		//		double[] deltaOutputWeights = divide(hiddenLayerResults,deltaOutput);
		//		double[] newHiddenToOuterWeights = add(hiddenToOuterWeights,deltaOutputWeights);
		//		double[] deltaHiddenSum = mult(divide(hiddenToOuterWeights,deltaOutput),hiddenSum);
		//		double[] deltaWeights = divide(deltaHiddenSum,inputData);
		//		double[] newHiddenWeights = add(hiddenWeights,deltaWeights);
		//
		//		for(int i = 0; i< newHiddenToOuterWeights.length;i++){
		//			outputNeuron.getConnections().get(i).setWeight(newHiddenToOuterWeights[i]);
		//		}
		//
		//		for(int i = 0 ; i < hiddenLayerNeurons.size();i++){
		//			for(int j = 0 ; j < inputLayer.getNeurons().size();j++) {
		//				hiddenLayerNeurons.get(i).getConnections().get(j).setWeight(newHiddenWeights[i+j+1]);
		//			}
		//		}
		//
		//		return calculated;
		return 0;
	}

	public double train(double[] targets, double learningRate) {
		double[] newIToHWeights = hWeights(targets,learningRate);
		double[] newHToOWeights = oWeights(targets,learningRate);

		int iterator = 0;
		for(Neuron hiddenNeuron: hiddenLayers[0].getNeurons()){
			for(Connection connection: hiddenNeuron.getConnections()){
				connection.setWeight(newIToHWeights[iterator]);
				iterator++;
			}
		}
		iterator = 0;
		for(Neuron outputNeuron: outputLayer.getNeurons()){
			for(Connection connection: outputNeuron.getConnections()){
				connection.setWeight(newHToOWeights[iterator]);
				iterator++;
			}
		}

		return 0;
	}


	public double[] oWeights(double[] targets, double learningRate){
		int iterator = 0;
		double[] newWeights = new double[hiddenLayers[0].getNeurons().length * outputLayer.getNeurons().length];
		double[] outNetO1 = new double[outputLayer.getNeurons().length];
		double[] eOutputs = new double[outputLayer.getNeurons().length];
		for(int i = 0; i < outputLayer.getNeurons().length;i++){
			Neuron outputNeuron = outputLayer.getNeurons()[i];
			outNetO1[i] = outputNeuron.fire() * (1 - outputNeuron.fire());
			eOutputs[i] = -(targets[i] - outputNeuron.fire());
		}


		for(int j = 0; j< outputLayer.getNeurons().length;j++){
			Neuron outputNeuron = outputLayer.getNeurons()[j];
			for(int i = 0;i< outputNeuron.getConnections().length;i++){
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

	public double[] hWeights(double[] targets, double learningRate){
		int iterator = 0;
		double[] newWeights = new double[hiddenLayers[0].getNeurons().length * inputLayer.getNeurons().length];
		double[] outNetO1 = new double[outputLayer.getNeurons().length];
		double[] eOutputs = new double[outputLayer.getNeurons().length];
		for(int i = 0; i < outputLayer.getNeurons().length;i++){
			Neuron outputNeuron = outputLayer.getNeurons()[i];
			outNetO1[i] = outputNeuron.fire() * (1 - outputNeuron.fire());
			eOutputs[i] = -(targets[i] - outputNeuron.fire());
		}

		HiddenLayer hiddenLayer = hiddenLayers[0];
		for(int j = 0; j< hiddenLayer.getNeurons().length;j++){
			Neuron hNeuron = hiddenLayer.getNeurons()[j];
			double eTotalOutHNeuron = 0;
			double outH = hNeuron.fire();

			for(int i = 0; i< outputLayer.getNeurons().length;i++){
				Neuron outputNeuron = outputLayer.getNeurons()[i];

				double weight = outputNeuron.getConnections()[j].getWeight();
				double eNet = eOutputs[i] * outNetO1[i];
				double eOut = eNet * weight;
				eTotalOutHNeuron += eOut;
			}

			for(int i = 0; i< hNeuron.getConnections().length;i++){
				Connection connection = hNeuron.getConnections()[i];

				double weight = connection.getWeight();
				double input = ((InputNeuron)connection.getNeuron()).getValue();
				double outHNetH = outH * (1 - outH);
				double eTotal = eTotalOutHNeuron * outHNetH * input;
				double newW = weight - learningRate * eTotal;
				newWeights[iterator] = newW;
				iterator++;
			}
		}
		return newWeights;
	}

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

