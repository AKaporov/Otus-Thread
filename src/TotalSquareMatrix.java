import java.util.Arrays;

/*
  Класс для результирующей матрицы (после расчета)
*/
public class TotalSquareMatrix implements SaveMatrix {
    private String currentThreadNameCalcMatrix = "";  //Имя текущего потока, который сейчас производит расчет элемента матрицы

    private volatile int[][] totalMatrix;  //Итоговая матрица
    private int iIndexRowCalc;     // Номер строки для расчета
    private int iIndexColumnCalc;  // Номер колонки для расчета
    private static volatile TotalSquareMatrix totalSquareMatrix;

    private TotalSquareMatrix(int matrixLength) {
        this.totalMatrix = new int[matrixLength][matrixLength];
    }

    public String getCurrentThreadNameCalcMatrix() {
        return currentThreadNameCalcMatrix;
    }

    /*
    Проверка занятости индекса матрицы для расчетаы
    */
    public Boolean isEmptyIndexMatrix() {
        Boolean bIsEmpty = Boolean.FALSE;
        iIndexColumnCalc = Integer.MIN_VALUE;
        iIndexRowCalc = Integer.MIN_VALUE;

        for (int i = 0; i < this.totalMatrix.length; i++) {
            for (int j = 0; j < this.totalMatrix.length; j++) {
                Integer index = this.totalMatrix[i][j];
                if (index.equals(Integer.MIN_VALUE)) {
                    bIsEmpty = Boolean.TRUE;
                    iIndexRowCalc = i;
                    iIndexColumnCalc = j;
                    break;
                }
            }
        }

        return bIsEmpty;
    }

    /*
    Расчет индекса матрицы переданным потоком
    */
    synchronized void calcIndexMatrix(String threadNameCalcMatrix) {

        try {
            Thread.sleep(1000);

            if ((iIndexRowCalc != Integer.MIN_VALUE) & (iIndexColumnCalc != Integer.MIN_VALUE)) {

                // Запомним текущий поток
                currentThreadNameCalcMatrix = threadNameCalcMatrix;

                totalMatrix[iIndexRowCalc][iIndexColumnCalc] = 0;
                int iLength = CalcTwoSquareMatrixInThread.getSquareMatrixA().matrix.length;
                for (int k = 0; k < iLength; k++) {
                    int a = CalcTwoSquareMatrixInThread.getSquareMatrixA().matrix[iIndexRowCalc][k];
                    int b = CalcTwoSquareMatrixInThread.getSquareMatrixB().matrix[k][iIndexColumnCalc];

                    totalMatrix[iIndexRowCalc][iIndexColumnCalc] += a * b;
                }

                System.out.println("Поток (" + currentThreadNameCalcMatrix + ") расчитал элемент марицы [" + iIndexRowCalc + "][" + iIndexColumnCalc + "] = " + totalMatrix[iIndexRowCalc][iIndexColumnCalc]);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    Создание итоговой матрицы
    */
    public static TotalSquareMatrix getTotalSquareMatrix() {
        if (totalSquareMatrix == null) {
            synchronized (TotalSquareMatrix.class) {
                if (totalSquareMatrix == null) {
                    totalSquareMatrix = new TotalSquareMatrix(CalcTwoSquareMatrixInThread.getSquareMatrixA().matrix.length);
                    totalSquareMatrix.fillMatrixMinValue();

                    System.out.println("Поток (" + Thread.currentThread().getName() + ") создал итоговую матрицы для расчета: " + totalSquareMatrix);
                }
            }
        }

        return totalSquareMatrix;
    }

    /*
    Проинициализируем итоговую матрицу минимальными значениями
    */
    private void fillMatrixMinValue() {
        for (int i = 0; i < this.totalMatrix.length; i++) {
            for (int j = 0; j < this.totalMatrix.length; j++) {
                this.totalMatrix[i][j] = Integer.MIN_VALUE;
            }
        }
    }

    @Override
    public String toString() {
        return "TotalSquareMatrix{" +
                "totalMatrix=" + Arrays.deepToString(this.totalMatrix) +
                '}';
    }

    @Override
    public void saveMatrix(int[][] matrix) {
        int[][] matrixToSave = getTotalSquareMatrix().totalMatrix;

        if (matrix != null) {
            matrixToSave = matrix.clone();
        }

        SaveMatrixInConsolImpl consol = new SaveMatrixInConsolImpl();
        consol.saveMatrix(matrixToSave);

        SaveMaxtrixInFileImpl inFile = new SaveMaxtrixInFileImpl("OtusFileName");
        inFile.saveMatrix(matrixToSave);
    }
}
