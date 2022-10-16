package learning.graph;

class AdjustableSquareMatrix<T> {
    private Object[][] matrix;

    public AdjustableSquareMatrix(int initialHeight) {
        this(initialHeight, null);
    }

    public AdjustableSquareMatrix(int initialHeight, T defaultValue) {
        matrix = new Object[initialHeight][initialHeight];
        fill(defaultValue);
    }

    public T get(int x, int y) {
        checkSize(x);
        checkSize(y);

        return (T) matrix[x][y];
    }

    public void set(int x, int y, T value) {
        adjustCapacity(Integer.max(x, y));
        matrix[x][y] = value;
    }

    public int width() {
        return matrix.length;
    }

    public void clear(T value) {
        fill(value);
    }

    private void fill(T defaultValue) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = defaultValue;
            }
        }
    }

    private void checkSize(int position) {
        if (position < 0 || position >= matrix.length) {
            throw new IllegalArgumentException("position " + position + " is not valid");
        }
    }

    private void adjustCapacity(int width) {
        if (width <= matrix.length) {
            return;
        }

        Object[][] newMatrix = new Object[width][width];

        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix.length);
        }

        matrix = newMatrix;
    }
}
