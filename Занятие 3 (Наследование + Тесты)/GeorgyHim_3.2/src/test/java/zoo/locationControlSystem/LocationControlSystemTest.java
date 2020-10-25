package zoo.locationControlSystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zoo.animal.Animal;
import zoo.employee.Employee;
import zoo.locationControlSystem.records.ArrivalDepartureRecord;
import zoo.locationControlSystem.records.MoveRecord;
import zoo.locationControlSystem.records.Pair;
import zoo.locationControlSystem.tracking.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class LocationControlSystemTest {
    private LocationControlSystem lcs;
    // Сотрудники
    private final Employee bob = new Employee("Боб", LocalDate.of(1980, 3, 1));
    private final Employee alice = new Employee("Алиса", LocalDate.of(1987, 7, 1));
    // Животные
    private final Animal elephant = new Animal("Слон", LocalDate.now());
    private final Animal monkey = new Animal("Обезьяна", LocalDate.now());

    @BeforeEach
    public void init() {
        System.out.println("Init");
        lcs = new LocationControlSystem(new Location(-1, -1));
    }

    @Test
    public void testAddADRecord() {
        System.out.println("testAddADRecord");
        lcs.addADRecord(bob, LocalDateTime.now(), true);
        lcs.addADRecord(alice, LocalDateTime.now(), true);

        ArrayList<Employee> emp = new ArrayList<Employee>();
        for (ArrivalDepartureRecord record : lcs.getArrivalDepartureJournal()) {
            emp.add(record.getEmployee());
        }
        Assertions.assertEquals(new HashSet<>(Arrays.asList(bob, alice)), new HashSet<>(emp));
    }

    @Test
    public void testMove() {
        System.out.println("testMove");
        LocalDateTime dateTime = LocalDateTime.now();
        lcs.move(bob, new Location(0, 0), dateTime);
        dateTime = LocalDateTime.now();
        lcs.move(monkey, new Location(0, 2), dateTime);
        Assertions.assertTrue(lcs.isContact(monkey, bob));
        dateTime = LocalDateTime.now();
        lcs.move(alice, new Location(-1, -1), dateTime);
        Assertions.assertFalse(lcs.isContact(monkey, alice));

        boolean aliceDepart = false;
        for (ArrivalDepartureRecord record : lcs.getArrivalDepartureJournal()) {
            if (record.getEmployee() == alice && record.getDateTime() == dateTime && !record.isArrived()) {
                aliceDepart = true;
            }
        }
        Assertions.assertTrue(aliceDepart);
    }

    @Test
    public void testContact() {
        System.out.println("testContact");
        LocalDateTime dateTime = LocalDateTime.now();
        lcs.move(bob, new Location(0, 0), dateTime);
        dateTime = LocalDateTime.now();
        lcs.move(monkey, new Location(0, 2), dateTime);
        lcs.move(alice, new Location(0, -0.5), dateTime);
        lcs.addContact(monkey, bob, dateTime);
        lcs.addContact(monkey, alice, dateTime);
        Assertions.assertTrue(lcs.isContact(monkey, alice));
        Assertions.assertTrue(lcs.isNowContacting(new Pair(monkey, bob)));
        Assertions.assertTrue(lcs.isNowContacting(new Pair(monkey, alice)));
        dateTime = LocalDateTime.now();
        lcs.move(monkey, new Location(2, 3), dateTime);
        Assertions.assertFalse(lcs.isContact(monkey, bob));
        lcs.updateNowContacting(dateTime);
        Assertions.assertFalse(lcs.isNowContacting(new Pair(monkey, bob)));
        Assertions.assertFalse(lcs.isNowContacting(new Pair(monkey, alice)));
    }

}
