import executor.Executor;
import executor.NoExecutorException;
import storage_services.CountSaver;
import storage_services.StorageManagementService;
import task.NoTaskException;
import task.Task;
import task.TaskStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class TaskManagementService {
    /**
     * Сканнер для считывания команд
     */
    private Scanner in;

    /**
     * Служба хранения файлов
     */
    private StorageManagementService storageService;

    /** Слуджба сохранения и обновления поля count */
    private CountSaver countSaver;

    /**
     * Список id задач
     */
    private Set<String> tasksIds = new HashSet<>();

    /**
     * Список id исполнителей
     */
    private Set<String> executorsIds = new HashSet<>();

    public void run() {
        while (in.hasNext()) {
            String command = in.nextLine();
            System.out.println(manage(command));
            System.out.print("> ");
        }
    }

    public String manage(String command) {
        command = command.trim();
        if (command.equals("help")) {
            return processHelp();
        }
        if (command.equals("setstorage")) {
            return processSetStorage(getParams(command));
        }
        if (command.startsWith("list")) {
            return processList(getParams(command));
        }
        if (command.startsWith("add")) {
            return processAdd(getParams(command));
        }
        if (command.startsWith("changestatus")) {
            return processChangeStatus(getParams(command));
        }
        if (command.startsWith("changeexecutor")) {
            return processChangeExecutor(getParams(command));
        }
        return "Wrong Command!";
    }

    private String processHelp() {
        return  "help        — see all available commands\n" +
                "setstorage <path> — set storage path" +
                "list        — see all tasks and executors\n" +
                "list -t     — see all tasks\n" +
                "list -e     — see all executors\n" +
                "add  -t     — add a task, you will be able to set name, executor and description\n" +
                "add  -e     — add a executor, you will be able to set name\n" +
                "changestatus <taskId> <newStatus>\n" +
                "changeexecutor <taskId> <execId>";
    }

    private String processSetStorage(String path) {
        storageService.setPath(path);
        return "Storage path set";
    }

    private String getParams(String command) {
        int position = command.indexOf(' ');
        if (position == -1)
            return "";
        return command.substring(position + 1);
    }

    public boolean taskExists(String id) {
        return tasksIds.contains(id);
    }

    public boolean executorExists(String id) {
        return executorsIds.contains(id);
    }

    private Task findTask(String id) throws NoTaskException, IOException, ClassNotFoundException {
        if (!id.startsWith(Task.ID_PREFIX))
            id = Task.ID_PREFIX + Integer.parseInt(id);
        if (!taskExists(id))
            throw new NoTaskException();
        return storageService.findTask(id);
    }

    private Executor findExecutor(String id) throws NoExecutorException, IOException, ClassNotFoundException {
        if (!id.startsWith(Executor.ID_PREFIX))
            id = Executor.ID_PREFIX + Integer.parseInt(id);
        if (!executorExists(id))
            throw new NoExecutorException();
        return storageService.findExecutor(id);
    }

    private String processAdd(String flag) {
        if (flag.equals("-t")) {
            return addTask();
        }
        if (flag.equals("-e")) {
            return addExecutor();
        }
        return "Wrong Command!";
    }

    private String addExecutor() {
        System.out.print("Input name of executor: ");
        String name = in.nextLine();
        Executor newExecutor = new Executor(name);
        executorsIds.add(newExecutor.getId());
        countSaver.saveExecutorCount();
        try {
            storageService.saveObject(newExecutor);
        }
        catch (IOException e) {
            return "Some went wrong with file system. Maybe you set incorrect path to main storage.";
        }
        return "Executor added and saved";
    }

    private String addTask() {
        try {
            System.out.print("Input name of task: ");
            String name = in.nextLine();
            System.out.print("Input executor id: ");
            String executorId = in.nextLine();
            Executor executor = findExecutor(executorId);
            System.out.print("Input description of task: ");
            String description = in.nextLine();
            Task newTask = new Task(name, description, executor);
            tasksIds.add(newTask.getId());
            countSaver.saveTaskCount();
            storageService.saveObject(newTask);
        } catch (NoExecutorException | FileNotFoundException exc) {
            return "Executor doesn't exist";
        }
        catch (IOException e) {
            return "Some went wrong with file system. Maybe you set incorrect path to main storage.";
        }
        catch (Exception e) {
            return "Wrong Command!";
        }
        return "Task added and saved";
    }

    private String processList(String flag) {
        StringBuilder sb = new StringBuilder();
        try {
            if (flag.equals("-t")) {
                for (String id : tasksIds) {
                    sb.append(storageService.findTask(id).toString()).append("\n");
                }
                return sb.toString();
            }

            if (flag.equals("-e")) {
                for (String id : executorsIds) {
                    sb.append(storageService.findExecutor(id).toString()).append("\n");
                }
                return sb.toString();
            }
            if (flag.equals("")) {
                sb.append("\tTasks:\n");
                sb.append(processList("-t"));
                sb.append("\tExecutors:\n");
                sb.append(processList("-e"));
                return sb.toString();
            }
        }
        catch (Exception e) {
            return "Failed to list objects";
        }
        return "Wrong Command!";
    }

    private String processChangeExecutor(String params) {
        String[] args = params.split(" ");
        if (args.length != 2)
            return "Wrong Command!";
        String taskId = args[0];
        String newExecutorId = args[1];
        try {
            Task task = findTask(taskId);
            Executor newExecutor = findExecutor(newExecutorId);
            task.setExecutor(newExecutor);
            storageService.saveObject(task);
        }
        catch (NoTaskException exc) {
            return "Task doesn't exist";
        }
        catch (NoExecutorException e) {
            return "Executor doesn't exist";
        }
        catch (IOException e) {
            return "Some went wrong with file system. Maybe you set incorrect path to main storage.";
        }
        catch (Exception e) {
            return "Wrong Command!";
        }
        return "Executor changed, task saved";
    }

    private String processChangeStatus(String params) {
        String[] args = params.split(" ");
        if (args.length != 2)
            return "Wrong Command!";
        String taskId = args[0];
        String newStatus = args[1];
        try {
            Task task = findTask(taskId);
            task.setStatus(TaskStatus.valueOf(newStatus));
            storageService.saveObject(task);
        }
        catch (NoTaskException exc) {
            return "Task doesn't exist";
        }
        catch (IOException e) {
            return "Some went wrong with file system. Maybe you set incorrect path to main storage.";
        }
        catch (Exception e) {
            return "Wrong Command!";
        }
        return "Status changed, task saved";
    }

    public TaskManagementService(InputStream inputStream, String storagePath) {
        this.in = new Scanner(inputStream);
        this.storageService = new StorageManagementService(storagePath);
        this.countSaver = new CountSaver(this.storageService.getTasksFolder(), this.storageService.getExecutorsFolder());
        try {
            updateData();
        } catch (IOException e) {
            System.out.println("Data updating failed");
        }
    }

    private void updateData() throws IOException {
        Task.loadCount(countSaver);
        Executor.loadCount(countSaver);
        try (Stream<Path> paths = Files.walk(Paths.get(storageService.getTasksFolder()))) {
            paths.filter(Files::isRegularFile).filter(path -> path.getFileName().toString().contains("-"))
                    .forEach(path -> tasksIds.add(path.getFileName().toString()));
        }
        try (Stream<Path> paths = Files.walk(Paths.get(storageService.getExecutorsFolder()))) {
            paths.filter(Files::isRegularFile).filter(path -> path.getFileName().toString().contains("-"))
                    .forEach(path -> executorsIds.add(path.getFileName().toString()));
        }
    }

    public void setIn(InputStream inputStream) {
        this.in = new Scanner(inputStream);
    }
}
