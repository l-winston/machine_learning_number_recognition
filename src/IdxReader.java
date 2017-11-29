import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class IdxReader {

	public final static int BATCH_SIZE = 500;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileInputStream inImage = null;
		FileInputStream inLabel = null;

		String inputImagePath = "train-images-idx3-ubyte";
		String inputLabelPath = "train-labels-idx1-ubyte";

		JFrame frame = new JFrame("frame");
		frame.pack();// ???
		frame.setVisible(true);
		frame.setSize(new java.awt.Dimension(200, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// exit when frame

		int[] hashMap = new int[10];

		try {
			inImage = new FileInputStream(inputImagePath);
			inLabel = new FileInputStream(inputLabelPath);

			int magicNumberImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8)
					| (inImage.read());
			int numberOfImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8)
					| (inImage.read());
			int numberOfRows = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8)
					| (inImage.read());
			int numberOfColumns = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8)
					| (inImage.read());

			int magicNumberLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8)
					| (inLabel.read());
			int numberOfLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8)
					| (inLabel.read());

			BufferedImage image = new BufferedImage(numberOfColumns, numberOfRows, BufferedImage.TYPE_INT_ARGB);
			int numberOfPixels = numberOfRows * numberOfColumns;
			int[] imgPixels = new int[numberOfPixels];

			double avgCost = 0;

			int batchInterval = BATCH_SIZE;

			NeuralNet net = NeuralNet.generate();
			NetworkChange batchStep = new NetworkChange(); //start with an empty batchStep

			for (int i = 0; i < numberOfImages; i++) {

				//after a batch is completed, find average, calculate where the next batch ends, and reset average back to 0
				if (i % batchInterval == 0 && i != 0) {
					avgCost /= BATCH_SIZE;
					System.out.println("--------------------------------------------------");
					System.out.println(avgCost);
					System.out.println("--------------------------------------------------");
					avgCost = 0;
					net.applyStep(batchStep);
					batchStep = new NetworkChange();
				}

				if (i % 100 == 0) {
					System.out.println("Number of images extracted: " + i);
				}

				for (int p = 0; p < numberOfPixels; p++) {
					int gray = 255 - inImage.read();
					imgPixels[p] = 0xFF000000 | (gray << 16) | (gray << 8) | gray;
				}

				image.setRGB(0, 0, numberOfColumns, numberOfRows, imgPixels, 0, numberOfColumns);
				int label = inLabel.read();
				hashMap[label]++;
				
				//reset frame and show the image
				frame.invalidate();
				frame.validate();
				frame.getContentPane().add(new JLabel(new ImageIcon(image)));
				
				avgCost += net.calculateCost(image, label);

				batchStep = batchStep.add(net.findChange(label));



			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inImage != null) {
				try {
					inImage.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (inLabel != null) {
				try {
					inLabel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}