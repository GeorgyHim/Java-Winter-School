import executor.Executor;
import executor.NoExecutorException;
import task.NoTaskException;
import task.Task;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

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
        if (command.startsWith("list")) {
            return processList(getParam(command));
        }
        if (command.startsWith("add")) {
            return processAdd(getParam(command));
        }
        return "Wrong Command!";
    }

    private String getParam(String command) {
        int position = command.indexOf(' ');
        if (position == -1)
            return "";
        return command.substring(position + 1);
    }

//    private String processSaveTask(String id) {
//        try {
//            Task task = findTask(id);
//            storageService.saveObject(task);
//        }
//        catch (NoTaskException e) {
//            return "Task doesn't exist";
//        }
//        catch (IOException e) {
//            return "Wrong OutputStream. Set new correct OutputStream";
//        }
//        catch (Exception e) {
//            return "Bad input";
//        }
//        return "Task saved";
//    }

//    private Task findTask(String id) throws NoTaskException {
//        if (!id.startsWith(Task.ID_PREFIX)) {
//            id = Task.ID_PREFIX + Integer.parseInt(id);;
//        }
//        if (!tasks_ids.contains(id))
//
//        for (Task task : tasks) {
//            if (task.getId().equals(id)) {
//                return task;
//            }
//        }
//        throw new NoTaskException();
//    }

    private String processAdd(String flag) {
        if (flag.equals("-t")) {
            return addTask();
        }
        if (flag.equals("-e")) {
            return addExecutor();
        }
        return "Bad command";
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
        } catch (NoExecutorException exc) {
            return "Executor doesn't exist";
        }
        catch (IOException e) {
            return "Wrong path to main storage. Set new correct path.";
        }
        catch (Exception e) {
            return "Bad input";
        }
        return "Task added";
    }

    private Executor findExecutor(String executor_id) throws NoExecutorException {
        if (!executor_id.startsWith(Executor.ID_PREFIX)) {
            int id = Integer.parseInt(executor_id);
            executor_id = Executor.ID_PREFIX + id;
        }
        for (Executor executor : executors) {
            if (executor.getId().equals(executor_id)) {
                return executor;
            }
        }
        throw new NoExecutorException();
    }

    private String processList(String flag) {
        StringBuilder sb = new StringBuilder();
        if (flag.equals("-t")) {
            tasks.forEach(task -> sb.append(task.toString()).append("\n"));
            return sb.toString();
        }
        if (flag.equals("-e")) {
            executors.forEach(task -> sb.append(task.toString()).append("\n"));
            return sb.toString();
        }
        if (flag.equals("")) {
            sb.append("Tasks:\n");
            tasks.forEach(task -> sb.append("\t").append(task.toString()).append("\n"));
            sb.append("Executors:\n");
            executors.forEach(task -> sb.append("\t").append(task.toString()).append("\n"));
            return sb.toString();
        }
        return "Bad command";
    }

    private String processHelp() {
        return  "help        — see all available commands\n" +
                "list        — see all tasks and executors\n" +
                "list -t     — see all tasks\n" +
                "list -e     — see all executors\n" +
                "add  -t     — add a task, you will be able to set name, executor and description\n" +
                "add  -e     — add a executor, you will be able to set name\n" +
                "changestatus <task_id> <new status 0-3>" +
                "changeexecutor <task_id> <exec_id>";
    }

    public TaskManagementService(InputStream inputStream, Path path) {
        this.in = new Scanner(inputStream);
        this.storageService = new StorageManagementService(path);
    }
}

    /*
    TODO: Переписать то что есть, чтение объектов из файла, изменение полей
    */
