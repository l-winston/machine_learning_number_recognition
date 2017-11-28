public class Matrix {
	double[][] matrix;
	public static int columns;
	public static int rows;

	public Matrix(double[][] matrix) {
		Matrix.rows = matrix.length;
		Matrix.columns = matrix[0].length;

		this.matrix = new double[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}
	}

	public double[][] get2DArray() {
		return matrix;
	}

	public void print() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println(rows + " Matrix.print() " + columns);
	}

	public Matrix add(Matrix m) {
		if (columns != m.columns || rows != m.rows) {
			throw new IllegalArgumentException("Dimenstions are different");
		}
		double[][] ret = new double[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				ret[i][j] = matrix[i][j] + m.get2DArray()[i][j];
			}
		}
		return new Matrix(ret);
	}

	public Matrix multiply(Matrix m) {
		if (matrix[0].length != m.matrix.length) {
			throw new IllegalArgumentException(
					"Matrix A.columns =/= Matrix B.rows : " + this.columns + " =/= " + m.rows);
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

	public Matrix multiply(double scalar) {
		double[][] ret = new double[this.rows][this.columns];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				ret[i][j] *= scalar;
			}
		}
		return new Matrix(ret);
	}

	public static Matrix sigmoid(Matrix m) {
		double[][] ret = new double[m.rows][m.columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				ret[i][j] = NeuralNet.sigmoid(ret[i][j]);
			}
		}
		return new Matrix(ret);
	}

	public Matrix subtract(Matrix m) {
		return this.add(m.multiply(-1));
	}

	public Matrix divide(double scalar) {
		return this.multiply(1 / scalar);
	}

}
