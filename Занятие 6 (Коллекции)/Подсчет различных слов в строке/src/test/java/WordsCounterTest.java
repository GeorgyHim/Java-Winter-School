import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class WordsCounterTest {

    @Test
    public void testSimpleText() {
        String text = "Гоша учит Java";
        Map<String, Integer> counts = WordsCounter.countWords(text);
        Assertions.assertEquals(new HashSet<>(Arrays.asList("гоша", "учит", "java")), counts.keySet());
        counts.values().forEach(value -> Assertions.assertEquals(value, 1));
    }

    @Test
    public void testNumbers() {
        String text = "Гоша учит Java 2 раз";
        Map<String, Integer> counts = WordsCounter.countWords(text);
        Assertions.assertEquals(new HashSet<>(Arrays.asList("гоша", "учит", "java", "2", "раз")), counts.keySet());
        counts.values().forEach(value -> Assertions.assertEquals(value, 1));
    }

    @Test
    public void testRepeatedWords() {
        String text = "Гоша учит Java. Java учит Гошу.";
        Map<String, Integer> counts = WordsCounter.countWords(text);
        Map<String, Integer> expected_counts = new HashMap<String, Integer>() {{
            put("гоша", 1);
            put("гошу", 1);
            put("учит", 2);
            put("java", 2);
        }};
        Assertions.assertEquals(expected_counts, counts);
    }

    @Test
    public void testSameWordsWithDifferentCase() {
        String text = "Гоша учит Java, гоша УчИт drf, учит C";
        Map<String, Integer> counts = WordsCounter.countWords(text);
        Assertions.assertEquals(new HashSet<>(Arrays.asList("гоша", "учит", "java", "c", "drf")), counts.keySet());
        Assertions.assertEquals(2, counts.get("гоша"));
        Assertions.assertEquals(3, counts.get("учит"));
    }

    @Test
    public void testNotLetters() {
        String text = " ; \n  Гоша учит гоша !учит. учит-Учит,   №;уч:ит \nучит  \t";
        Map<String, Integer> counts = WordsCounter.countWords(text);
        Assertions.assertEquals(new HashSet<>(Arrays.asList("гоша", "учит", "уч", "ит")), counts.keySet());
        Assertions.assertEquals(5, counts.get("учит"));
    }

    @Test
    public void testBlankStrings() {
        String[] blankStrings = new String[] {null, "", "   ", "\n\t  ", " \n "};
        for (String s : blankStrings) {
            Map<String, Integer> counts = WordsCounter.countWords(s);
            Assertions.assertEquals(Collections.emptySet(), counts.keySet());
        }
    }
}
