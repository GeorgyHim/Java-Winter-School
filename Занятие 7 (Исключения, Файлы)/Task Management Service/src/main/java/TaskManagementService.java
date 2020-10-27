import executor.Executor;
import executor.NoExecutorException;
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

    /**
     * Список id задач
     */
    private Set<String> tasks_ids = new HashSet<>();

    /**
     * Список id исполнителей
     */
    private Set<String> executors_ids = new HashSet<>();

    public void process() {
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
                "?changestatus <task_id> <new_status 0-3>" +
                "?changeexecutor <task_id> <exec_id>";
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


    private Task findTask(String id) throws NoTaskException, IOException, ClassNotFoundException {
        if (!id.startsWith(Task.ID_PREFIX)) {
            id = Task.ID_PREFIX + Integer.parseInt(id);;
        }
        if (!tasks_ids.contains(id))
            throw new NoTaskException();
        return storageService.findTask(id);
    }

    private String processAdd(String flag) {
        if (flag.equals("-t")) {
            return addTask();
        }
        if (flag.equals("-e")) {
            return addExecutor();
        }
        return "Bad flag param";
    }

    private String addExecutor() {
        System.out.print("Input name of executor: ");
        String name = in.nextLine();
        Executor new_executor = new Executor(name);
        executors_ids.add(new_executor.getId());
        try {
            storageService.saveObject(new_executor);
        }
        catch (IOException e) {
            return "Wrong path to main storage. Set new correct path.";
        }
        return "Executor added and saved";
    }

    private String addTask() {
        try {
            System.out.print("Input name of task: ");
            String name = in.nextLine();
            System.out.print("Input executor id: ");
            String executor_id = in.nextLine();
            Executor executor = findExecutor(executor_id);
            System.out.print("Input description of task: ");
            String description = in.nextLine();
            Task new_task = new Task(name, description, executor);
            tasks_ids.add(new_task.getId());
            storageService.saveObject(new_task);
        } catch (NoExecutorException | FileNotFoundException exc) {
            return "Executor doesn't exist";
        }
        catch (IOException e) {
            return "Some went wrong with file system. Maybe you set incorrect path to main storage.";
        }
        catch (Exception e) {
            return "Bad input";
        }
        return "Task added and saved";
    }

    private Executor findExecutor(String executor_id) throws NoExecutorException, IOException, ClassNotFoundException {
        if (!executor_id.startsWith(Executor.ID_PREFIX)) {
            int id = Integer.parseInt(executor_id);
            executor_id = Executor.ID_PREFIX + id;
        }
        if (!executors_ids.contains(executor_id))
            throw new NoExecutorException();
        return storageService.findExecutor(executor_id);
    }

    private String processList(String flag) {
        StringBuilder sb = new StringBuilder();
        try {
            if (flag.equals("-t")) {
                for (String id : tasks_ids) {
                    sb.append(storageService.findTask(id).toString()).append("\n");
                }
                return sb.toString();
            }

            if (flag.equals("-e")) {
                for (String id : executors_ids) {
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
        return "Bad flag param";
    }

    private String processChangeExecutor(String params) {
        String[] args = params.split(" ");
        if (args.length != 2)
            return "Bad params";
        String task_id = args[0];
        String new_executor_id = args[1];
        try {
            Task task = findTask(task_id);
            Executor new_executor = findExecutor(new_executor_id);
            task.setExecutor(new_executor);
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
            return "Bad input";
        }
        return "Executor changed, task saved";
    }

    private String processChangeStatus(String params) {
        String[] args = params.split(" ");
        if (args.length != 2)
            return "Bad params";
        String task_id = args[0];
        String new_status = args[1];
        try {
            Task task = findTask(task_id);
            task.setStatus(TaskStatus.valueOf(new_status));
            storageService.saveObject(task);
        }
        catch (NoTaskException exc) {
            return "Task doesn't exist";
        }
        catch (IOException e) {
            return "Some went wrong with file system. Maybe you set incorrect path to main storage.";
        }
        catch (Exception e) {
            return "Bad input";
        }
        return "Status changed, task saved";
    }

    public TaskManagementService(InputStream inputStream, String path) {
        this.in = new Scanner(inputStream);
        this.storageService = new StorageManagementService(path);
        try {
            updateData();
        } catch (IOException e) {
            System.out.println("Data updating failed");
        }
    }

    private void updateData() throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(storageService.getTasksFolder()))) {
            paths.filter(Files::isRegularFile).forEach(path -> tasks_ids.add(path.getFileName().toString()));
        }
        try (Stream<Path> paths = Files.walk(Paths.get(storageService.getExecutorsFolder()))) {
            paths.filter(Files::isRegularFile).forEach(path -> executors_ids.add(path.getFileName().toString()));
        }
    }
}
