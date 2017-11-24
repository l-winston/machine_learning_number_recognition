public class Matrix {
	int[][] matrix;
	int columns;
	int rows;

	public Matrix(int[][] matrix) {
		this.matrix = matrix;
		this.rows = matrix.length;
		this.columns = matrix[0].length;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public int getWidth() {
		return columns;
	}

	public int getHeight() {
		return rows;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				str.append(matrix[i][j] + " ");
			}
			str.append('\n');
		}
		String ret = str.toString();
		return ret;
	}

	public Matrix add(Matrix m) {
		if (columns != m.columns || rows != m.rows) {
			throw new IllegalArgumentException("Dimenstions are different");
		}
		int[][] ret = new int[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				ret[i][j] = matrix[i][j] + m.getMatrix()[i][j];
			}
		}
		return new Matrix(ret);
	}

	public Matrix multiply(Matrix m) {

		return null;
	}

}
