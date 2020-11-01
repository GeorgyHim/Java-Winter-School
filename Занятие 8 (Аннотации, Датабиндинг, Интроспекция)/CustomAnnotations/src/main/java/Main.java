import annotations.MapKeyFail;

public class Main {
    static ImprovedHashMap improvedHashMap = new ImprovedHashMap();

    @MapKeyFail
    static class Shit extends Object {String name = "fuck";}

    public static void main(String[] args) {
        Shit shit = new Shit();
        improvedHashMap.put("fuck", shit);
        System.out.println("Succes 1");
        improvedHashMap.put(shit, "fuck");
        System.out.println("Oh shi...");
    }
}
