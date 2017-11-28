
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class NeuralNet {

	public static double[][] input = new double[28*28][1];
	public static double[][] layer1 = new double[16][1];
	public static double[][] layer2 = new double[16][1];
	public static double[][] output = new double[10][1];

	public static double[][] layer1Weights = new double[16][28*28];
	public static double[][] layer1Biases = new double[16][1];
	public static double[][] layer2Weights = new double[16][16];
	public static double[][] layer2Biases = new double[16][1];
	public static double[][] outputWeights = new double[10][16];
	public static double[][] outputBiases = new double[10][1];

	public NeuralNet(double[][] layer1Weights, double[][] layer1Biases, double[][] layer2Weights, double[][] layer2Biases,
			double[][] outputWeights, double[][] outputBiases) {

		this.layer1Weights = layer1Weights;
		this.layer1Biases = layer1Biases;
		this.layer2Weights = layer2Weights;
		this.layer2Biases = layer2Biases;
		this.outputWeights = outputWeights;
		this.outputBiases = outputBiases;

	}

	public static NeuralNet generate() throws IOException {
		Scanner scan = new Scanner(new File("startingNet.in"));

		double[][] l1w = new double[16][28*28];
		double[][] l1b = new double[16][1];
		double[][] l2w = new double[16][16];
		double[][] l2b = new double[16][1];
		double[][] ow = new double[10][16];
		double[][] ob = new double[10][1];

		for (int j = 0; j < 16; j++) {
			for (int k = 0; k < 28*28; k++) {
				l1w[j][k] = scan.nextDouble();
			}
		}
		for (int j = 0; j < 16; j++) {
			l1b[j][0] = scan.nextDouble();
		}
		for (int j = 0; j < 16; j++) {
			for (int k = 0; k < 16; k++) {
				l2w[j][k] = scan.nextDouble();
			}
		}
		for (int j = 0; j < 16; j++) {
			l2b[j][0] = scan.nextDouble();
		}
		for (int j = 0; j < 10; j++) {
			for (int k = 0; k < 16; k++) {
				ow[j][k] = scan.nextDouble();
			}
		}
		for (int j = 0; j < 10; j++) {
			ob[j][0] = scan.nextDouble();
		}
		scan.close();
		
		return new NeuralNet(l1w, l1b, l2w, l2b, ow, ob);
	}

	public static double calculateCost(BufferedImage image, int label) {
		//feed 28x28 greyscale image into 784x1 input matrix
		double[][] temp = new double[28 * 28][1];
		for (int i = 0; i < 28; i++) {
			for (int j = 0; j < 28; j++) {
				Color c = new Color(image.getRGB(j, i));
				temp[j + 28 * i][0] = (255 - c.getRed()) / 255.0;
			}
		}
		
		input = temp;

//		matrix multiplication to solve for layers, makes sense but for some reason is MUCH slower and provides different results
		
		//evaluate layer 1 activations
		layer1 = Matrix.sigmoid(new Matrix(layer1Weights).multiply(new Matrix (input))).matrix;
		//evaluate layer 2 activations
		layer2 = Matrix.sigmoid(new Matrix(layer2Weights).multiply(new Matrix (layer1))).matrix;
		//evaluate layer 3 activations
		output = Matrix.sigmoid(new Matrix(outputWeights).multiply(new Matrix (layer2))).matrix;
		
		// calculate cost based on outputs vs. actual answer
		double cost = 0;
		for (int i = 0; i < 10; i++) {
			if (i == label)
				cost += Math.pow((output[i][0] - 1), 2);
			else
				cost += Math.pow((output[i][0] - 0), 2);
			//System.out.println(output[i][0]);
		}
		//System.out.println();
		return cost;
	}

	public static NetworkChange findChange(double cost) {
		//TODO: put backpropagation here
		return null;
	}

	public static void applyStep(NetworkChange nc) {
		layer1Weights = new Matrix(layer1Weights).add(new Matrix(nc.layer1Weights)).matrix;
		layer1Biases = new Matrix(layer1Biases).add(new Matrix(nc.layer1Biases)).matrix;
		layer2Weights = new Matrix(layer2Weights).add(new Matrix(nc.layer2Weights)).matrix;
		layer2Biases = new Matrix(layer2Biases).add(new Matrix(nc.layer2Biases)).matrix;
		outputWeights = new Matrix(outputWeights).add(new Matrix(nc.outputWeights)).matrix;
		outputBiases = new Matrix(outputBiases).add(new Matrix(nc.outputBiases)).matrix;

	}
	
	//if d > 5 || d 
	public static double sigmoid(double d) {
		if (d > 5)
			d = 5;
		if (d < -5)
			d = -5;
		double ret = (1.0) / (1 + Math.pow(Math.E, (-1 * d)));
		//System.out.println(d + "\t" + ret);
		return ret;
	}
}
