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

    /** Название подпапки для задач*/
    private static String tasksFolder = "tasks";

    /** Название подпапки для исполнителей*/
    private static String executorsFolder = "executors";

    public StorageManagementService(String path) {
        this.path = Paths.get(path);
        String sep = this.path.getFileSystem().getSeparator();
        try {
            Files.createDirectories(Paths.get(this.path.toAbsolutePath().toString() + sep + tasksFolder + sep));
            Files.createDirectories(Paths.get(this.path.toAbsolutePath().toString() + sep + executorsFolder + sep));
        } catch (IOException ignored) {}
    }

    /** Получение файла объекта */
    private File getFile(String folder, String id) {
        String sep = this.path.getFileSystem().getSeparator();
        String path = this.path.toAbsolutePath().toString() + sep + folder + sep + id;
        return new File(path);
    }

    /** Созранение объекта */
    public void saveObject(Serializable object) throws IOException {
        FileOutputStream fileOutputStream = null;
        if (object instanceof Task)
            fileOutputStream = new FileOutputStream(getFile(tasksFolder, ((Task) object).getId()));
        if (object instanceof Executor)
            fileOutputStream = new FileOutputStream(getFile(executorsFolder, ((Executor) object).getId()));
        try (ObjectOutputStream serializer = new ObjectOutputStream(fileOutputStream)) {
            serializer.writeObject(object);
        }
    }

    Executor findExecutor(String executor_id) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(getFile(executorsFolder, executor_id));
        try (ObjectInputStream serializer = new ObjectInputStream(inputStream)) {
            return (Executor) serializer.readObject();
        }
    }

    public void setPath(String path) {
        this.path = Paths.get(path);
    }
}
