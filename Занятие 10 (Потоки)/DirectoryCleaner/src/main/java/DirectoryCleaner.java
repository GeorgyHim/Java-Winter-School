import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для очистки заданной директории
 */
public class DirectoryCleaner {
    /** Путь до директории, которую необходимо очистить */
    private final String directoryPath;

    /** Время таймаута очищения */
    private int timeout;

    /** Поток, совершающий очистку */
    private final Thread cleanerThread;

    private boolean isThreadAlive = false;

    public DirectoryCleaner(String directoryPath, int timeout) {
        this.directoryPath = directoryPath;
        this.timeout = timeout;
        cleanerThread = new Thread(new Cleaner());
        cleanerThread.setDaemon(true);
    }

    /**
     * Метод рекурсивной очистки директории
     *
     * @param dir - Путь до директории
     */
    private void cleanDirectory(String dir) throws IOException {
        File directory = new File(dir);
        File[] allContents = directory.listFiles();
        if (allContents != null)
            for (File file : allContents)
                cleanDirectory(file.getAbsolutePath());

        if (!dir.equals(directoryPath))
            Files.delete(directory.toPath());
    }

    /**
     * Метод запуска потока для очищения директории
     */
    public void startCleaning() {
        synchronized (cleanerThread) {
            isThreadAlive = true;
            cleanerThread.start();
        }
    }

    /**
     * Метод остановки очисти директории
     */
    public void stopCleaning() {
        synchronized (cleanerThread) {
            cleanerThread.interrupt();
            isThreadAlive = false;
        }
    }

    /**
     * Метод проверки потока очистки на непрерванность
     * @return - жив ли поток очистки директории
     */
    public boolean isThreadAlive() {
        synchronized (cleanerThread) {
            return isThreadAlive;
        }
    }

    /**
     * Метод проверки целевой директории на пустоту
     * @return - Пуста ли целевая директория
     * @throws IOException - Ошибка доступа к папке или создания потока для неё
     */
    public boolean isDirEmpty() throws IOException {
        synchronized (directoryPath) {
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(directoryPath))) {
                return !dirStream.iterator().hasNext();
            }
        }
    }

    /**
     * Класс для создания потока очистки директории
     */
    private class Cleaner implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(timeout);
                }
                catch (InterruptedException e) {
                    return;
                }

                synchronized (directoryPath) {
                    try {
                        cleanDirectory(directoryPath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public String getDirectoryPath() {
        return directoryPath;
    }
}
