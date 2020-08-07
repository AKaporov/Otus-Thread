import java.util.Arrays;

/*
 Даны две матрицы A и B размерности NxN. Необходимо вычислить их произведение: матрицу С.
C[i][j] = сумма по k от 1 до N A[i][k]*B[k][j].
Разработайте многопоточное приложение, выполняющее вычисление произведения матриц.
Элементы cij матрицы произведения С = AхB вычисляются параллельно p однотипными потоками.
Если некоторый поток уже вычисляет элемент cij матрицы C, то следующий приступающий к вычислению поток выбирает для
расчета элемент ci,j+1, если j<k, и ci+1,k, если j=k.
Выполнив вычисление элемента матрицы-произведения, поток проверяет, нет ли элемента, который еще не рассчитывается.
Если такой элемент есть, то приступает к его расчету.
В противном случае отправляет (пользовательское) сообщение о завершении своей работы и приостанавливает своё выполнение.
Главный поток, получив сообщения о завершении вычислений от всех потоков, выводит результат на экран и запускает поток,
записывающий результат в конец файла-протокола.
В каждом потоке должна быть задержка в выполнении вычислений (чтобы дать возможность поработать всем потокам).

Синхронизацию потоков между собой организуйте через критическую секцию или мьютекс.

Сделать диаграмму классов.
ДЗ сдается в виде ссылки на GitHub репозиторий с проектом.
Срок сдачи- 2 недели со дня занятия.
По вопросам обращаться в Slack к студентам, преподавателям и наставникам в канал группы

Критерии оценки:
1. Решение прислано в срок 1 балл
2. программа работает - 1 балл.
3. Разработан тест - 1 балл

Минимальный балл для принятия - 2

 */
public class MatrixApplication {

    public static void main(String[] args) {
        // Работа с Матрицей
        int iMatrixLength = 2;

        // 1. Создание матрицы размером iMatrixLength на iMatrixLength
        IntMatrixNxNRandomImpl matrixA = new IntMatrixNxNRandomImpl(iMatrixLength);
        IntMatrixNxNRandomImpl matrixB = new IntMatrixNxNRandomImpl(iMatrixLength);

        // 2. Заполнение матрицы случайными числами
        matrixA.fillMatrixRandom();
        matrixB.fillMatrixRandom();

        System.out.println("Матрица А = " + Arrays.deepToString(matrixA.matrix));
        System.out.println("Матрица В = " + Arrays.deepToString(matrixB.matrix));
        System.out.println("==============================");

        // Работа с Потоками
        CalcMatrixNxN matrixNxN = new CalcMatrixNxN(matrixA.matrix, matrixB.matrix);
        // 1. Создание потоков
        Thread tOne = new Thread(matrixNxN);
        Thread tTwo = new Thread(matrixNxN);

        System.out.println("Первый шаг - Первый поток выполняется: " + tOne.isAlive());
        System.out.println("Первый шаг - Второй поток выполняется: " + tTwo.isAlive());

        // 2. Запуск потоков
        tOne.start();
        tTwo.start();

        System.out.println("Вторй шаг - Первый поток выполняется: " + tOne.isAlive());
        System.out.println("Вторй шаг - Второй поток выполняется: " + tTwo.isAlive());

        try {
            System.out.println("Ждим завершения потоков");
            Thread.sleep(10000);
            // 3. Завершение работы потоков
            tOne.join();
            tTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Третий шаг - Первый поток выполняется: " + tOne.isAlive());
        System.out.println("Третий шаг - Второй поток выполняется: " + tTwo.isAlive());


        System.out.println("Завершение основного потока");
    }
}
