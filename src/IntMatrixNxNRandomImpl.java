import java.util.Random;

public class IntMatrixNxNRandomImpl implements FillMatrixRandom {

    protected volatile int matrix[][];

    public IntMatrixNxNRandomImpl(int matrixLength) {
        matrix = new int[matrixLength][matrixLength];
    }

    @Override
    public void fillMatrixRandom() {
        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {

                this.matrix[i][j] = 1 + random.nextInt(10);
            }
        }
    }
}
