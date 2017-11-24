import javax.print.attribute.standard.PrinterLocation;

public class Matrix {
	int[][] matrix;
	int columns;
	int rows;

	public static void main(String[] args) {
		Matrix a = new Matrix(new int[][] { { 1, 2 }, { 3, 4 } });
		Matrix b = new Matrix(new int[][] { { 2, 0 }, { 1, 2 } });
		System.out.println(a.multiply(b));
	}

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
		if (columns != m.rows) {
			throw new IllegalArgumentException("Matrix A.columns =/= Matrix B.rows");
		}
		int[][] ret = new int[this.rows][m.columns];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < m.columns; j++) {
				int x = 0;
				for (int col = 0; col < this.columns; col++) {
					for (int row = 0; row < m.rows; row++) {
						x += matrix[i][col] * m.matrix[row][j];
					}
				}
				ret[i][j] = x;
			}
		}

		return new Matrix(ret);
	}

	public Matrix multiply(int scalar) {
		int[][] ret = new int[this.rows][this.columns];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				ret[i][j] *= scalar;
			}
		}
		return new Matrix(ret);
	}
	
	public Matrix subtract(Matrix m){
		return this.add(m.multiply(-1));
	}

}
