package utils;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter {

    /**
     * Метод записи строки в файл
     *
     * @param string        -   Строка
     * @param filename      -   Имя файла
     * @throws IOException  -   Исключение при работе с файлом
     */
    public static void writeStringToFile(String string, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filename))) {
            writer.write(string);
        }
    }
}
