public class Main {
    public static void main(String[] args) {
        TaskManagementService taskService = new TaskManagementService(System.in, "C:\\Химшиашвили\\Учеба\\Java\\+Курсы от CROC\\Занятие 7 (Исключения, Файлы)\\Task Management Service\\storage");
        System.out.println("Type help to see available commands");
        System.out.print("> ");
        taskService.run();
    }
}
