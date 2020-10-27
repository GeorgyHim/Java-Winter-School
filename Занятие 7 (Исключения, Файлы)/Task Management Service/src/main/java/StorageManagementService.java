import executor.Executor;
import task.Task;

import java.io.*;
import java.nio.file.Path;

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

    public StorageManagementService(Path path) {
        this.path = path;
    }

    void setPath(Path path) {
        this.path = path;
    }

    Executor findExecutor(String executor_id) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(getFile(executorsFolder, executor_id));
        try (ObjectInputStream serializer = new ObjectInputStream(inputStream)) {
            return (Executor) serializer.readObject();
        }
    }
}
