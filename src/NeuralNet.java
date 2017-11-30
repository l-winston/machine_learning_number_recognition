
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class NeuralNet {
	public static final double ε = Math.pow(10, 2); //Hyperparamater called ε (epsilon) see>Bayesian statistics
	
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

	public static NetworkChange findChange(int label) {
		double[] y = new double[output.length]; //desired output
		y[label] = 1;
		
		double[][] ow_c = new double[outputWeights.length][outputWeights[0].length]; //Output Weight Change (ow_c)
		
		//start with outputs (Backpropagation)
		for(int j = 0; j < outputWeights.length; j++){
			for(int k = 0; k < outputWeights[0].length; k++){
				double activiationK = layer2[k][0];
				
				double [] temp = new double[outputWeights[0].length];
				temp = outputWeights[j];
				double temp1 [][] = new double[1][outputWeights[0].length];
				temp1[0] = temp;
				double Z = new Matrix(temp1).multiply(new Matrix(layer2)).matrix[0][0];
								
				double sigmoidZderivative = sigmoid(Z) * (1 - sigmoid(Z));
				
				double dCOSTdOutput = 2*(layer2[j][0] - y[j]);
				
				double dCOSTdWeight = activiationK * sigmoidZderivative * dCOSTdOutput;
				ow_c[j][k] = -1 * dCOSTdWeight * ε;
			}
		}
		return new NetworkChange(new double[16][28*28], new double[16][1], new double[16][16], new double[16][1], ow_c, new double[10][1]);
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
	public double success(int label){
		int confident = -1;
		for(int i = 0; i < output.length; i++){
			if(output[i][0] > confident) confident = i;
			System.out.print(output[i][0] + " ");
		}
		System.out.println();
		if(confident == label){
			return 1;
		}
		return 0;
	}
	public void print (PrintWriter out){
		for(int i = 0; i < layer1Weights.length; i++){
			for(int j = 0; j < layer1Weights[0].length; j++){
				out.print(layer1Weights[i][j] + " ");
			}
		}
		for(int i = 0; i < layer1Biases.length; i++){
			for(int j = 0; j < layer1Biases[0].length; j++){
				out.print(layer1Biases[i][j] + " ");
			}
		}
		for(int i = 0; i < layer2Weights.length; i++){
			for(int j = 0; j < layer2Weights[0].length; j++){
				out.print(layer2Weights[i][j] + " ");
			}
		}
		for(int i = 0; i < layer2Biases.length; i++){
			for(int j = 0; j < layer2Biases[0].length; j++){
				out.print(layer2Biases[i][j] + " ");
			}
		}
		for(int i = 0; i < outputWeights.length; i++){
			for(int j = 0; j < outputWeights[0].length; j++){
				out.print(outputWeights[i][j] + " ");
			}
		}
		for(int i = 0; i < outputBiases.length; i++){
			for(int j = 0; j < outputBiases[0].length; j++){
				out.print(outputBiases[i][j] + " ");
			}
		}
		out.close();
	}
}
