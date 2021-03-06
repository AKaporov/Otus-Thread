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

Онлайн UML диаграмма
https://www.diagrameditor.com/

 */

public class CalcMatrixApplication {

    public static void main(String[] args) throws InterruptedException {
        // Запуск перемножения двух квадратных матриц в Потоках
        CalcTwoSquareMatrixInThread calcTwoSquareMatrixInThread = new CalcTwoSquareMatrixInThread(2, true);
        calcTwoSquareMatrixInThread.calcTwoMatrixInThread();
    }
}
