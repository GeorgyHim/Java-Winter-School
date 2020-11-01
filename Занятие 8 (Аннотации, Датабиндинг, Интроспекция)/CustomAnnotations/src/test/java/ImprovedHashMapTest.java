import annotations.MapKeyFail;
import annotations.MapValueFail;
import exceptions.PutMapKeyFailException;
import exceptions.PutMapValueFailException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MapKeyFail
class BadKey extends Object {}

@MapValueFail
class BadValue extends Object {}

public class ImprovedHashMapTest {
    ImprovedHashMap improvedHashMap;
    BadKey badKey = new BadKey();
    BadValue badValue = new BadValue();

    @BeforeEach
    public void initMap() {
        improvedHashMap = new ImprovedHashMap();
    }

    @Test
    public void testGoodData() {
        Assertions.assertDoesNotThrow(() -> improvedHashMap.put("Some", 5));
        Assertions.assertEquals(5, improvedHashMap.get("Some"));
    }

    @Test
    public void testBadKey() {
        Assertions.assertThrows(PutMapKeyFailException.class, () -> improvedHashMap.put(badKey, "Bad key"));
    }

    @Test
    public void testBadValue() {
        Assertions.assertThrows(PutMapValueFailException.class, () -> improvedHashMap.put("Bad value", badValue));
    }

    @Test
    public void testBadBoth() {
        Assertions.assertThrows(PutMapKeyFailException.class, () -> improvedHashMap.put(badKey, badValue));
    }

    @Test
    public void testKeyValueOrder() {
        Assertions.assertDoesNotThrow(() -> improvedHashMap.put(badValue, badKey));
        Assertions.assertEquals(badKey, improvedHashMap.get(badValue));
    }

}
