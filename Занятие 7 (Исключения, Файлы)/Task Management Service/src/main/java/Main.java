public class Main {
    public static void main(String[] args) {
        TaskManagementService service = new TaskManagementService(System.in);
        System.out.println("Type help to see available commands");
        System.out.print("> ");
        service.process();
    }
}
