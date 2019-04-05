package application;

import network.NeuralNetwork;
import network.NeuralNetworkController;
import network.neuron.InputNeuron;
import network.neuron.Neuron;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class App {
	private static ArrayList<String> categoryList = new ArrayList<>() {{
		add("airplane");
		add("automobile");
		add("bird");
		add("cat");
		add("deer");
		add("dog");
		add("frog");
		add("horse");
		add("ship");
		add("truck");
	}};

	public static void main(String[] args) throws JAXBException, IOException {
//		NeuralNetwork nn = NeuralNetworkBuilder.build().inputLayer(2).hiddenLayer(50, new FastSigmoid()).hiddenLayer(50, new FastSigmoid()).hiddenLayer(50, new FastSigmoid()).outputLayer(2, new Sigmoid()).getNeuralNetwork();
		NeuralNetwork nn = NeuralNetworkController.loadNetwork("testPath");

		long time = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			((InputNeuron) nn.getInputLayer().getNeurons()[0]).setValue(1);
			((InputNeuron) nn.getInputLayer().getNeurons()[1]).setValue(1);
			NeuralNetworkController.train(nn, new double[] { 0.9, 0.1 }, 0.05);
			System.out.printf("Loss: %s\n",
					(Math.abs(nn.getOutputLayer().getNeurons()[0].fire() - 0.9) + Math.abs(nn.getOutputLayer().getNeurons()[1].fire() - 0.1)) / 2);
		}
		((InputNeuron) nn.getInputLayer().getNeurons()[0]).setValue(1);
		((InputNeuron) nn.getInputLayer().getNeurons()[1]).setValue(1);
		double fireValue = nn.getOutputLayer().getNeurons()[0].fire();
		double fireValue2 = nn.getOutputLayer().getNeurons()[1].fire();

		time = System.nanoTime() - time;
		System.out.printf("Expected Value: %s,%s\nReal Value: 0.9,0.1\nTrained: %sms\n", fireValue, fireValue2, time);
		NeuralNetworkController.saveNetwork(nn, "testPath");
	}

	private static void printStats(NeuralNetwork nn, int expected) {
		for (int i = 0; i < nn.getOutputLayer().getNeurons().length; i++) {
			Neuron neuron = nn.getOutputLayer().getNeurons()[i];
			System.out.printf("%s: %s\n", categoryList.get(i), neuron.fire());
		}
		System.out.printf("Real: %s\n", categoryList.get(expected));
	}

	private static CifarImage[] getCifarImages() throws IOException {
		byte[] bytes = readBinaryFile("C:\\Users\\npreen.CARGOSOFT\\Desktop\\NNTest\\cifar-10-batches-bin\\data_batch_1.bin");
		CifarImage[] images = new CifarImage[10000];

		for (int i = 0; i < images.length; i++) {
			int label = bytes[i * 3073];
			int[] pixels = new int[3072];
			for (int j = 1; j < 3073; j++) {
				pixels[j - 1] = bytes[i * j];
			}
			images[i] = new CifarImage(label, pixels);
		}

		return images;
	}

	private static byte[] readBinaryFile(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		return Files.readAllBytes(path);
	}

	private static class CifarImage {
		private int label;
		private int[] pixels;

		public CifarImage(int label, int[] pixels) {
			this.label = label;
			this.pixels = pixels;
		}

		public int getLabel() {
			return label;
		}

		public void setLabel(int label) {
			this.label = label;
		}

		public int[] getPixels() {
			return pixels;
		}

		public void setPixels(int[] pixels) {
			this.pixels = pixels;
		}
	}

	public static byte[] scale(byte[] fileData, int width, int height) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(fileData);
		BufferedImage img = ImageIO.read(in);
		if (height == 0) {
			height = (width * img.getHeight()) / img.getWidth();
		}
		if (width == 0) {
			width = (height * img.getWidth()) / img.getHeight();
		}
		Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		ImageIO.write(imageBuff, "jpg", buffer);

		return buffer.toByteArray();
	}
}
