package storage_services;

import executor.Executor;
import task.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Служба хранения файлов
 */
public class StorageManagementService {
    /** Путь до глобальной папки хранения объектов*/
    private Path path;

    public StorageManagementService(String path) {
        this.path = Paths.get(path);
        try {
            Files.createDirectories(Paths.get(getTasksFolder()));
            Files.createDirectories(Paths.get(getExecutorsFolder()));
        } catch (IOException ignored) {}
    }

    public String getTasksFolder() {
        String sep = this.path.getFileSystem().getSeparator();
        return this.path.toAbsolutePath().toString() + sep + "tasks"  + sep;
    }

    public String getExecutorsFolder() {
        String sep = this.path.getFileSystem().getSeparator();
        return this.path.toAbsolutePath().toString() + sep + "executors"  + sep;
    }

    /** Получение файла объекта */
    private File getFile(String folder, String id) {
        return new File(folder + id);
    }

    /** Сохранение объекта */
    public void saveObject(Serializable object) throws IOException {
        FileOutputStream fileOutputStream = null;
        if (object instanceof Task) {
            fileOutputStream = new FileOutputStream(getFile(getTasksFolder(), ((Task) object).getId()));
        }
        if (object instanceof Executor) {
            fileOutputStream = new FileOutputStream(getFile(getExecutorsFolder(), ((Executor) object).getId()));
        }
        try (ObjectOutputStream serializer = new ObjectOutputStream(fileOutputStream)) {
            serializer.writeObject(object);
        }
    }

    public Executor findExecutor(String executorId) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(getFile(getExecutorsFolder(), executorId));
        try (ObjectInputStream serializer = new ObjectInputStream(inputStream)) {
            return (Executor) serializer.readObject();
        }
    }

    public Task findTask(String taskId) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(getFile(getTasksFolder(), taskId));
        try (ObjectInputStream serializer = new ObjectInputStream(inputStream)) {
            return (Task) serializer.readObject();
        }
    }

    public void setPath(String path) {
        this.path = Paths.get(path);
    }
}
