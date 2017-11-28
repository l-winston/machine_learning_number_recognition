
public class NetworkChange {
	public static Matrix layer1Weights = new Matrix(new double[28*28][16]);
	public static Matrix layer1Biases = new Matrix(new double[16][1]);
	public static Matrix layer2Weights = new Matrix(new double[16][16]);
	public static Matrix layer2Biases = new Matrix(new double[16][1]);
	public static Matrix outputWeights = new Matrix(new double[16][10]);
	public static Matrix outputBiases = new Matrix(new double[10][1]);
	
	public NetworkChange(Matrix layer1Weights, Matrix layer1Biases, Matrix layer2Weights, Matrix layer2Biases,
			Matrix outputWeights, Matrix outputBiases){
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
				layer1Weights.add(nc.layer1Weights),
				layer1Biases.add(nc.layer1Biases),
				layer2Weights.add(nc.layer2Weights),
				layer2Biases.add(nc.layer2Biases),
				outputWeights.add(nc.outputWeights),
				outputBiases.add(nc.outputBiases)
				);
	}
}
