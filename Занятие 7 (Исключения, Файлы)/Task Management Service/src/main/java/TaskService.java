import task.Task;

import java.util.*;

public class TaskService {
    private static List<String> commands = Arrays.asList("help", "list", "add", "save <code>");

    /** Список текущих задач */
    private Set<Task> tasks = new HashSet<>();

    public void process() {
        Scanner in = new Scanner(System.in);
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
