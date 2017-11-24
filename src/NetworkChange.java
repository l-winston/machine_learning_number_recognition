
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
		return null;
	}
}
