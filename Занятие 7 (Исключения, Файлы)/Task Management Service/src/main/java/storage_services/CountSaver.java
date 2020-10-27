package storage_services;

import executor.Executor;
import task.Task;

import java.io.*;

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

    public int getTaskCount() {
        try (FileInputStream inputStream = new FileInputStream(tasksFile)) {
            return inputStream.read();
        }
        catch (IOException ignored) {
            return 0;
        }
    }

    public void saveExecutorCount() {
        try (FileOutputStream outputStream = new FileOutputStream(executorsFile)) {
            outputStream.write(Executor.getCount());
        }
        catch (IOException ignored) {}
    }

    public int getExecutorCount() {
        try (FileInputStream inputStream = new FileInputStream(executorsFile)) {
            return inputStream.read();
        }
        catch (IOException ignored) {
            return 0;
        }
    }
}
