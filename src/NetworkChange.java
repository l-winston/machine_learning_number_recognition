
public class NetworkChange {
	public static double[][] layer1Weights = new double[28*28][16];
	public static double[] layer1Biases = new double[16];
	public static double[][] layer2Weights = new double[16][16];
	public static double[] layer2Biases = new double[16];
	public static double[][] outputWeights = new double[16][10];
	public static double[] outputBiases = new double[10];
	public NetworkChange(double[][] layer1Weights, double[] layer1Biases, double[][] layer2Weights, double[] layer2Biases,
			double[][] outputWeights, double[] outputBiases){
		this.layer1Weights = layer1Weights;
		this.layer1Biases = layer1Biases;
		this.layer2Weights = layer2Weights;
		this.layer2Biases = layer2Biases;
		this.outputWeights = outputWeights;
		this.outputBiases = outputBiases;
	}
	public NetworkChange (){
		
	}
	

	
	public static NetworkChange add(NetworkChange nc){

		return new NetworkChange(
				new Matrix(layer1Weights).add(new Matrix(nc.layer1Weights)).get2DArray(),
				Matrix.toMatrix(layer1Biases).add(Matrix.toMatrix(nc.layer1Biases)).get2DArray()[0],
				new Matrix(layer2Weights).add(new Matrix(nc.layer2Weights)).get2DArray(),
				Matrix.toMatrix(layer2Biases).add(Matrix.toMatrix(nc.layer2Biases)).get2DArray()[0],
				new Matrix(outputWeights).add(new Matrix(nc.outputWeights)).get2DArray(),
				Matrix.toMatrix(outputBiases).add(Matrix.toMatrix(nc.outputBiases)).get2DArray()[0]
				);
	}
}
