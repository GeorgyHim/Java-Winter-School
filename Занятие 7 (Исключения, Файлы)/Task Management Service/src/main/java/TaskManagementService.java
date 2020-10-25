import executor.Executor;
import executor.NoExecutorException;
import task.Task;

import java.io.InputStream;
import java.util.*;

public class TaskManagementService {
    private Scanner in;

    /** Список задач */
    private Set<Task> tasks = new HashSet<>();

    /** Список исполнителей */
    private List<Executor> executors = new ArrayList<>();

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
        if (command.startsWith("save")) {
            try {
                return processSave(getParam(command));
            }
            // TODO: Написать обработку исключения
            catch (Exception e) {}
        }

        return "Wrong Command!";
    }

    private String getParam(String command) {
        int position = command.indexOf(' ');
        if (position == -1)
            return "";
        return command.substring(position+1);
    }

    // TODO: Написать сохранение объекта в файл
    private String processSave(String code) {
        if (code.equals("2"))
            return "Shit";
        return "fuck";
    }

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
        executors.add(new Executor(name));
        return "Executor added";
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
            tasks.add(new Task(name, description, executor));
        }
        catch (NoExecutorException exc) {
            return "Executor doesn't exist";
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
        Executor finded = null;
        for (Executor executor : executors) {
            if (executor.getId().equals(executor_id)) {
                finded = executor;
                break;
            }
        }
        if (finded == null) {
            throw new NoExecutorException();
        }
        return finded;
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
                "save <code> — save task with specified code\n";
    }

    public TaskManagementService(InputStream stream) {
        this.in = new Scanner(stream);
    }

    /*
    TODO:
    Функционал:
        Вывести список доступных команд (отдельный enum?)
        Прочитать и обработать команду (если такой команды нет, выбросить исключение)
        В Мэйне выводить "Type HELP to see available commands"

    Команды:
        Добавить задачу (все варианты в одном методе):
                (название)
                (название + исполнитель)
                (название + исполнитель + описание)
        Сохранить задачу в память
     */
}
