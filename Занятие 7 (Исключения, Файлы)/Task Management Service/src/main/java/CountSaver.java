import executor.Executor;
import task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CountSaver {
    private File tasksFile;
    private File executorsFile;

    public CountSaver(String tasksFolder, String executorsFolder) {
        this.tasksFile = new File(tasksFolder + "TasksCount");
        this.executorsFile = new File(executorsFolder + "ExecutorsCount");
    }

    public void saveTaskCount() {
        try (FileOutputStream outputStream = new FileOutputStream(tasksFile)) {
            outputStream.write(Task.getCount());
        }
        catch (IOException ignored) {}
    }

    public void saveExecutorCount() {
        try (FileOutputStream outputStream = new FileOutputStream(executorsFile)) {
            outputStream.write(Executor.getCount());
        }
        catch (IOException ignored) {}
    }
}
