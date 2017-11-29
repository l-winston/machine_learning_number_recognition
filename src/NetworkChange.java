
public class NetworkChange {
	public static double[][] layer1Weights = (new double[16][28*28]);
	public static double[][] layer1Biases = (new double[16][1]);
	public static double[][] layer2Weights = (new double[16][16]);
	public static double[][] layer2Biases = (new double[16][1]);
	public static double[][] outputWeights = (new double[10][16]);
	public static double[][] outputBiases = (new double[10][1]);
	
	public NetworkChange(double[][] layer1Weights, double[][] layer1Biases, double[][] layer2Weights, double[][] layer2Biases,
			double[][] outputWeights, double[][] outputBiases){
		this.layer1Weights = layer1Weights;
		this.layer1Biases = layer1Biases;
		this.layer2Weights = layer2Weights;
		this.layer2Biases = layer2Biases;
		this.outputWeights = outputWeights;
		this.outputBiases = outputBiases;
	}
	public NetworkChange (){
		
	}
	
	public NetworkChange (NetworkChange nc){
		this.layer1Weights = nc.layer1Weights;
		this.layer1Biases = nc.layer1Biases;
		this.layer2Weights = nc.layer2Weights;
		this.layer2Biases = nc.layer2Biases;
		this.outputWeights = nc.outputWeights;
		this.outputBiases = nc.outputBiases;
	}
	
	public static NetworkChange add(NetworkChange nc){

		return new NetworkChange(
				new Matrix(layer1Weights).add(new Matrix(nc.layer1Weights)).matrix,
				new Matrix(layer1Biases).add(new Matrix(nc.layer1Biases)).matrix,
				new Matrix(layer2Weights).add(new Matrix(nc.layer2Weights)).matrix,
				new Matrix(layer2Biases).add(new Matrix(nc.layer2Biases)).matrix,
				new Matrix(outputWeights).add(new Matrix(nc.outputWeights)).matrix,
				new Matrix(outputBiases).add(new Matrix(nc.outputBiases)).matrix
				);
	}
}
