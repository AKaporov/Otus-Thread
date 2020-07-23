import java.io.*;
import java.util.Arrays;

/*

Класс для сохранения матрицы в файл.
Если имя файла не передано, то сохраняется во временной директрории
указанной в TEMP/TMP с префиксом Otus_FileName
(напрмиер, C:\Windows\Temp\Otus_FileName17321303727237893112.tmp)

*/

public class SaveMaxtrixInFileImpl implements SaveMatrix {
    String fileName;

    public SaveMaxtrixInFileImpl(String fileName) {
        if (fileName.isEmpty()) {
            fileName = "Otus_File_Name";
        }
        this.fileName = fileName;
    }

    @Override
    public void saveMatrix(int[][] matrix) {
        try {
            File file = File.createTempFile(fileName, null);

            OutputStream out = new FileOutputStream(file);
            PrintWriter printWriter = new PrintWriter(out);

            printWriter.println(Arrays.deepToString(matrix));

            out.flush();
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
