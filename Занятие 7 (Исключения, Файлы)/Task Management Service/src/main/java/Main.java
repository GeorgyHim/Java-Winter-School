public class Main {
    public static void main(String[] args) {
        TaskService service = new TaskService(System.in);
        System.out.println("Type help to see available commands");
        System.out.print("> ");
        service.process();
    }
}
