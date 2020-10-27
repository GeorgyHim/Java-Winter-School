import executor.Executor;
import task.Task;

import java.io.*;
import java.nio.file.Path;

class StorageManagementService {
    private static String tasksFolder = "tasks";
    private static String executorsFolder = "executors";

    private Path path;

    public FileOutputStream getTaskFile(String task_code) throws FileNotFoundException {
        String sep = this.path.getFileSystem().getSeparator();
        String path = this.path.toAbsolutePath().toString() + sep + tasksFolder + sep + task_code;
        return new FileOutputStream(path);
    }

    public FileOutputStream getExecutorFile(String executor_id) throws FileNotFoundException {
        String sep = this.path.getFileSystem().getSeparator();
        String path = this.path.toAbsolutePath().toString() + sep + executorsFolder + sep + executor_id;
        return new FileOutputStream(path);
    }

    public void saveObject(Serializable object) throws IOException {
        FileOutputStream fileOutputStream = null;
        if (object instanceof Task)
            fileOutputStream = getTaskFile(((Task) object).getCode());
        if (object instanceof Executor)
            fileOutputStream = getExecutorFile(((Executor) object).getId());
        try (ObjectOutputStream serializer = new ObjectOutputStream(fileOutputStream)) {
            serializer.writeObject(object);
        }
    }

    public StorageManagementService(Path path) {
        this.path = path;
    }

    // TODO: Вынести сюда все операции сохранения и загрузки
    // TODO: Вынести сюда потоки, но не в виде потока, а в виде Path. Потом мы к этому Path будем добавлять папки для Task / executor.Executor
}
