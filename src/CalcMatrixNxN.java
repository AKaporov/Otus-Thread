/*
Класс для расчета матрицы
*/

public class CalcMatrixNxN implements Runnable, SaveMatrix {

    private final int[][] matrixA;
    private final int[][] matrixB;
    
    volatile int aik;
    volatile int bkj;    

    public CalcMatrixNxN(int[][] matrixA, int[][] matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }


    @Override
    public void run() {
        // Рассчитаем новую матрицу
        int[][] c = calcMatrixC(matrixA, matrixB);

        // Сохраним полученную матрицу
        saveMatrix(c);
    }


    public int[][] calcMatrixC(int[][] matrixA, int[][] matrixB) {
        int n = matrixA.length;
        int newMatrixC[][] = new int[n][n];

        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Integer iSum = 0;
                    for (int k = 0; k < n; k++) {
                        aik = matrixA[i][k];  // у первой матрицы берем Строку
                        bkj = matrixB[k][j];  // у второй матрицы берем Столбец

                        synchronized (iSum) {
                            Thread.sleep(1000);  // Что бы поработал каждый поток

                            iSum += aik * bkj;

                            if (k == n - 1) {
                                newMatrixC[i][j] = iSum;
                                System.out.println("newMatrixC[" + i + ", " + j + "] = " + newMatrixC[i][j] + " / поток " + Thread.currentThread().getName());
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Поток " + Thread.currentThread().getName() + " прерван.");
        }

        return newMatrixC;
    }

    @Override
    public void saveMatrix(int[][] matrix) {
        SaveMatrixInConsolImpl consol = new SaveMatrixInConsolImpl();
        consol.saveMatrix(matrix);

        SaveMaxtrixInFileImpl inFile = new SaveMaxtrixInFileImpl("OtusFileName");
        inFile.saveMatrix(matrix);
    }
}
