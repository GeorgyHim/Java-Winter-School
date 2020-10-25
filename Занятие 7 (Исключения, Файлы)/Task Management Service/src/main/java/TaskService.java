import executor.Executor;
import executor.NoExecutorException;
import task.Task;

import java.io.InputStream;
import java.util.*;

public class TaskService {
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
        if (command.equals("help")) {
            return processHelp();
        }
        if (command.startsWith("list")) {
            return processList();
        }
        if (command.equals("add")) {
            return processAdd();
        }
        if (command.startsWith("save")) {
            try {
                int code = Integer.parseInt(command.split(" ")[1]);
                return processSave();
            }
            catch (Exception e) {}
        }

        return "Wrong Command!";
    }

    // TODO
    private String processSave() {
        return "Shit";
    }

    private String processAdd() {
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

    private String processList() {
        StringBuilder sb = new StringBuilder();
        tasks.forEach(task -> sb.append(task.toString()));
        return sb.toString();
    }

    private String processHelp() {
        return  "help        — see all available commands\n" +
                "list        — see all tasks and executors\n" +
                "list -t     — see all tasks\n" +
                "list -e     — see all executors\n" +
                "add         — add a task, you will be able to set name, executor and description\n" +
                "save <code> — save task with specified code\n";
    }

    public TaskService(InputStream stream) {
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
