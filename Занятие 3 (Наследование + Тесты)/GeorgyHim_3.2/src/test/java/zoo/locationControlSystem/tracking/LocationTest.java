package zoo.locationControlSystem.tracking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocationTest {
    private Location location;
    private Location same = new Location(1, 0);
    private Location near = new Location(0, 0);
    private Location far = new Location(1000, 1000);

    @BeforeEach
    public void init() {
        System.out.println("Init");
        location = new Location(1, 0);
    }

    @Test
    public void testDistance() {
        System.out.println("testDistance");
        Assertions.assertEquals(0, location.distance(same));
        Assertions.assertEquals(1, location.distance(near));
    }

    @Test
    public void testEquals() {
        System.out.println("testEquals");
        Assertions.assertTrue(location.equals(location));
        Assertions.assertTrue(location.equals(same));
        Assertions.assertFalse(location.equals(near));
        Assertions.assertFalse(location.equals(null));
        Assertions.assertFalse(location.equals("sdg"));
    }
}
