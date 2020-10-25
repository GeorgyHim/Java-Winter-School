import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WordsCounter {
    public static Map<String, Integer> countWords(String text) {
        if (text == null)
            return Collections.emptyMap();

        String s = text.replaceAll("[^\\p{L}\\p{Digit}]+", " ").trim();
        if (s.isEmpty())
            return Collections.emptyMap();

        String[] words = s.split("[\\s]+");
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            s = word.toLowerCase();
            counts.putIfAbsent(s, 0);
            counts.put(s, counts.get(s) + 1);
        }
        return counts;
    }

    public static void printCountResults(Map<String, Integer> counts) {
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
