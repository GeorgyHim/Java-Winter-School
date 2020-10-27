import executor.Executor;
import task.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Служба хранения файлов
 */
class StorageManagementService {
    /** Путь до глобальной папки хранения объектов*/
    private Path path;
    private CountSaver countSaver;

    public StorageManagementService(String path) {
        this.path = Paths.get(path);
        try {
            Files.createDirectories(Paths.get(getTasksFolder()));
            Files.createDirectories(Paths.get(getExecutorsFolder()));
            this.countSaver = new CountSaver(getTasksFolder(), getExecutorsFolder());
        } catch (IOException ignored) {}
    }

    String getTasksFolder() {
        String sep = this.path.getFileSystem().getSeparator();
        return this.path.toAbsolutePath().toString() + sep + "tasks"  + sep;
    }

    String getExecutorsFolder() {
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
        if (object instanceof Task)
            fileOutputStream = new FileOutputStream(getFile(getTasksFolder(), ((Task) object).getId()));
        if (object instanceof Executor)
            fileOutputStream = new FileOutputStream(getFile(getExecutorsFolder(), ((Executor) object).getId()));
        try (ObjectOutputStream serializer = new ObjectOutputStream(fileOutputStream)) {
            serializer.writeObject(object);
        }
    }

    Executor findExecutor(String executor_id) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(getFile(getExecutorsFolder(), executor_id));
        try (ObjectInputStream serializer = new ObjectInputStream(inputStream)) {
            return (Executor) serializer.readObject();
        }
    }

    public Task findTask(String task_id) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(getFile(getTasksFolder(), task_id));
        try (ObjectInputStream serializer = new ObjectInputStream(inputStream)) {
            return (Task) serializer.readObject();
        }
    }

    void setPath(String path) {
        this.path = Paths.get(path);
    }
}
