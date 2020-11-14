import java.io.File;

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
    private void cleanDirectory(String dir) {
        File directory = new File(dir);
        File[] allContents = directory.listFiles();
        if (allContents != null)
            for (File file : allContents)
                cleanDirectory(file.getAbsolutePath());

        if (!dir.equals(directoryPath))
            directory.delete();
    }

    /**
     * Метод запуска потока для очищения директории
     */
    public void startCleaning() {
        synchronized (directoryPath) {
            isThreadAlive = true;
            cleanerThread.start();
        }
    }

    public void stopCleaning() {
        synchronized (directoryPath) {
            cleanerThread.interrupt();
            isThreadAlive = false;
        }
    }

    public boolean isThreadAlive() {
        return isThreadAlive;
    }

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

                synchronized (this) {
                    cleanDirectory(directoryPath);
                }
            }
        }
    }

    public String getDirectoryPath() {
        return directoryPath;
    }
}
