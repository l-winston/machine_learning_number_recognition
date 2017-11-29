public class Matrix {
	double[][] matrix;

	public Matrix(double[][] matrix) {

		this.matrix = new double[matrix.length][matrix[0].length];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}
	}

	public Matrix add(Matrix m) {
		int rows = matrix.length;
		int columns = matrix[0].length;
		int mrows = m.matrix.length;
		int mcolumns = m.matrix[0].length;
		
		if (columns != mcolumns || rows != mrows) {
			throw new IllegalArgumentException("Dimenstions are different");
		}
		double[][] ret = new double[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				ret[i][j] = matrix[i][j] + m.matrix[i][j];
			}
		}
		return new Matrix(ret);
	}

	public Matrix multiply(Matrix m) {
		int rows = matrix.length;
		int columns = matrix[0].length;
		int mrows = m.matrix.length;
		int mcolumns = m.matrix[0].length;
		
		if (columns != mrows) {
			throw new IllegalArgumentException(
					"Matrix A.columns =/= Matrix B.rows : " + columns + " =/= " + mrows);
		}

		double[][] ret = new double[matrix.length][m.matrix[0].length];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < m.matrix[0].length; j++) {
				double x = 0;
				for (int col = 0; col < matrix[0].length; col++) {
						x += matrix[i][col] * m.matrix[col][j];
				}
				ret[i][j] = x;
			}
		}
		
		return new Matrix(ret);
	}

	public double[][] multiply(double scalar) {
		int rows = matrix.length;
		int columns = matrix[0].length;
		
		double[][] ret = new double[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				ret[i][j] = matrix[i][j] * scalar;
			}
		}
		return ret;
	}

	public static Matrix sigmoid(Matrix m) {
		int rows = m.matrix.length;
		int columns = m.matrix[0].length;
		
		double[][] ret = new double[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				ret[i][j] = NeuralNet.sigmoid(m.matrix[i][j]);
			}
		}
		return new Matrix (ret);
	}

	public double[][] divide(double scalar) {
		return this.multiply(1.0 / scalar);
	}

}
