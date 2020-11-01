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

    @BeforeEach
    public void initMap() {
        improvedHashMap = new ImprovedHashMap();
    }

    @Test
    public void testGoodData() {
        Assertions.assertDoesNotThrow(() -> improvedHashMap.put("Some", 5));
    }

    @Test
    public void testBadKey() {
        BadKey badKey = new BadKey();
        Assertions.assertThrows(PutMapKeyFailException.class, () -> improvedHashMap.put(badKey, "Bad key"));
    }

    @Test
    public void testBadValue() {
        BadValue badValue = new BadValue();
        Assertions.assertThrows(PutMapValueFailException.class, () -> improvedHashMap.put("Bad value", badValue));
    }

}
