package zoo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zoo.animal.Animal;
import zoo.animal.IllnessRecord;
import zoo.employee.Employee;
import zoo.locationControlSystem.records.ArrivalDepartureRecord;
import zoo.locationControlSystem.records.Pair;
import zoo.locationControlSystem.tracking.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

public class ZooTest {
    private Zoo zoo;
    // Сотрудники
    private final Employee bob = new Employee("Боб", LocalDate.of(1980, 3, 1));
    private final Employee alice = new Employee("Алиса", LocalDate.of(1987, 7, 1));
    // Животные
    private final Animal elephant = new Animal("Слон", LocalDate.now());
    private final Animal monkey = new Animal("Обезьяна", LocalDate.now());

    @BeforeEach
    public void init() {
        System.out.println("Init");
        zoo = new Zoo("Африка рядом");
        zoo.add(bob, alice);
        zoo.add(elephant, bob);
        zoo.add(monkey, alice);
    }

    @Test
    public void oldTest() {
        monkey.add(new IllnessRecord("Грипп", LocalDateTime.now(), "-"));
        Assertions.assertEquals(new HashSet<>(Arrays.asList(bob, alice)), new HashSet<>(zoo.getEmployees()));
        zoo.remove(bob);
        Assertions.assertEquals(new HashSet<>(Arrays.asList(bob, alice)), new HashSet<>(zoo.getEmployees()));
        zoo.remove(elephant);
        zoo.remove(bob);
        Assertions.assertEquals(new HashSet<>(Arrays.asList(alice)), new HashSet<>(zoo.getEmployees()));
    }

    @Test
    public void testProcess() {
        System.out.println("testProcess");
        zoo.move(bob, new Location(0, 0), LocalDateTime.now());
        zoo.move(monkey, new Location(0, 2), LocalDateTime.now());
        zoo.move(alice, new Location(0, -0.5), LocalDateTime.now());
        Assertions.assertTrue(zoo.isNowContacting(new Pair(monkey, bob)));
        Assertions.assertTrue(zoo.isNowContacting(new Pair(monkey, alice)));
        zoo.move(monkey, new Location(2, 3), LocalDateTime.now());
        Assertions.assertFalse(zoo.isNowContacting(new Pair(monkey, bob)));
        Assertions.assertFalse(zoo.isNowContacting(new Pair(monkey, alice)));
        zoo.move(elephant, new Location(1, 1), LocalDateTime.now());
        Assertions.assertTrue(zoo.isNowContacting(new Pair(elephant, bob)));
        Assertions.assertTrue(zoo.isNowContacting(new Pair(elephant, alice)));
        zoo.move(alice, new Location(-1, -1), LocalDateTime.now());
    }
}
