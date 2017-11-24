import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class createStartingNet {
	//run this program to create a new random neural net
	public static void main(String[] args) throws IOException {
		String outFileName = "startingNet.in";
		PrintWriter out = new PrintWriter(outFileName);
		
		Random rand = new Random();
		
		for(int i = 0; i < 13002; i++){
			out.print(rand.nextDouble()*50 - 25 + " ");
		}
		
		out.close();
	}
}
