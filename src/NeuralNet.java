
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class NeuralNet {

	public final static double[] input = new double[28 * 28];
	public final static double[] layer1 = new double[16];
	public final static double[] layer2 = new double[16];
	public final static double[] output = new double[10];

	public static double[][] layer1Weights = new double[input.length][layer1.length];
	public static double[] layer1Biases = new double[layer1.length];
	public static double[][] layer2Weights = new double[layer1.length][layer2.length];
	public static double[] layer2Biases = new double[layer2.length];
	public static double[][] outputWeights = new double[layer2.length][output.length];
	public static double[] outputBiases = new double[output.length];

	public NeuralNet(double[][] layer1Weights, double[] layer1Biases, double[][] layer2Weights, double[] layer2Biases,
			double[][] outputWeights, double[] outputBiases) {
		this.layer1Weights = layer1Weights;
		this.layer1Biases = layer1Biases;
		this.layer2Weights = layer2Weights;
		this.layer2Biases = layer2Biases;
		this.outputWeights = outputWeights;
		this.outputBiases = outputBiases;
	}

	public static double calculateCost(BufferedImage image, int label) {
		for (int i = 0; i < 28; i++) {
			for (int j = 0; j < 28; j++) {
				Color c = new Color(image.getRGB(j, i));
				input[j + 28 * i] = (255 - c.getRed())/255;
			}
		}

		// evaluate layer 1 neurons
		for (int i = 0; i < layer1.length; i++) {
			int x = 0;
			for (int j = 0; j < input.length; j++) {
				x += input[j] * layer1Weights[j][i];
			}
			layer1[i] = sigmoid(x + layer1Biases[i]);
		}

		for (int i = 0; i < layer2.length; i++) {
			int x = 0;
			for (int j = 0; j < layer1.length; j++) {
				x += layer1[j] * layer2Weights[j][i];
			}
			layer2[i] = sigmoid(x + layer2Biases[i]);
		}

		for (int i = 0; i < output.length; i++) {
			int x = 0;
			for (int j = 0; j < layer2.length; j++) {
				x += layer2[j] * outputWeights[j][i];
			}
			output[i] = sigmoid(x + outputBiases[i]);
		}
		//  1    2    3    4 
		//  1    0    1    0
		//  0    1    0    0
		
		// calculate cost based on outputs vs. actual answer
		double cost = 0;
		for (int i = 0; i < output.length; i++) {
			if (i == label)
				cost += Math.pow((output[i] - 1), 2);
			else
				cost += Math.pow((output[i] - 0), 2);
		}
		return cost;
	}

	public static double sigmoid(double d) {
		if (d > 5)
			d = 5;
		if (d < -5)
			d = -5;

		return (1) / (1 + Math.pow(Math.E, -1 * d));
	}
}
