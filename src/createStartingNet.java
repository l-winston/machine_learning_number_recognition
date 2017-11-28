import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class createStartingNet {
	//run this program to create a new random neural net
	public static void main(String[] args) throws IOException {
		double bounds = 0.25;
		String outFileName = "startingNet.in";
		PrintWriter out = new PrintWriter(outFileName);
		
		Random rand = new Random();
		
		for(int i = 0; i < 13002; i++){
			double randomDouble = rand.nextDouble()*bounds*2 - bounds;
			int j = (int) (randomDouble*1000);
			randomDouble = j/1000.0;
			out.print(randomDouble + " ");
		}
		
		out.close();
	}
}
