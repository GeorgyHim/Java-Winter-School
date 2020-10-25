package zoo.tracking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zoo.Zoo;
import zoo.animal.Animal;
import zoo.employee.Employee;
import zoo.tracking.event.InteractionEvent;
import zoo.tracking.event.WorkShiftEvent;
import zoo.tracking.incidents.Interaction;
import zoo.tracking.incidents.WorkShift;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Проверка сервиса отслеживания объектов в зоопарке.
 */
public class TrackingServiceTest {
    private Zoo zoo;
    private Employee bob;
    private Employee alice;
    private Animal elephant;

    @BeforeEach
    public void init() {
        // Сотрудники
        bob = new Employee("Боб", LocalDate.of(1980, 3, 1));
        alice = new Employee("Алиса", LocalDate.of(1987, 7, 1));
        // Животные
        elephant = new Animal("Слон", LocalDate.now());
        final Animal monkey = new Animal("Обезьяна", LocalDate.now());

        final Zoo zoo = new Zoo("Африка рядом");
        zoo.add(bob, alice);
        zoo.add(elephant, bob);
        zoo.add(monkey, alice);

        this.zoo = zoo;
    }

    @DisplayName("Проверка журнала отслеживания животных")
    @Test
    public void testJournalOfAnimalTracking() throws InterruptedException {
        final TrackingService trackingService = zoo.getTrackingService();

        final Animal lion = new Animal("Лев", LocalDate.of(1990, 3, 8));
        final Set<Trackable> animalsAndEmployees = new HashSet<>();
        animalsAndEmployees.addAll(zoo.getAnimals());
        animalsAndEmployees.addAll(zoo.getEmployees());
        Assertions.assertEquals(animalsAndEmployees, trackingService.getTrackable());

        Assertions.assertFalse(trackingService.getTrackable().contains(lion));
        zoo.add(lion, bob);
        Assertions.assertTrue(trackingService.getTrackable().contains(lion));

        final LocalDateTime beforeTime = LocalDateTime.now();
        Thread.sleep(1);
        trackingService.update(lion.getId(), 0, 0);
        Thread.sleep(1);
        final LocalDateTime betweenTime = LocalDateTime.now();
        Thread.sleep(1);
        trackingService.update(lion.getId(), 10, 10);
        Thread.sleep(1);
        final LocalDateTime afterTime = LocalDateTime.now();

        Assertions.assertTrue(lion.getLocations().get(0).time.isAfter(beforeTime));
        Assertions.assertTrue(lion.getLocations().get(0).time.isBefore(betweenTime));

        Assertions.assertTrue(lion.getLocations().get(1).time.isAfter(betweenTime));
        Assertions.assertTrue(lion.getLocations().get(1).time.isBefore(afterTime));

    }


    @DisplayName("Проверка отслеживания событий взаимодействия сотрудника и животного")
    @Test
    public void testInteractionEmployeeAndAnimal() {
        final TrackingService trackingService = zoo.getTrackingService();

        Assertions.assertTrue(trackingService.getEvents().isEmpty());

        // начальные позиции
        trackingService.update(bob.getId(), 0, 0);
        trackingService.update(elephant.getId(), 10, 10);
        Assertions.assertEquals(1, trackingService.getEvents().size()); // Боб пришел на работу

        // Боб подошел к слону
        trackingService.update(bob.getId(), 10, 10);
        Assertions.assertEquals(2, trackingService.getEvents().size());
        final Interaction interaction = ((InteractionEvent) trackingService.getEvents().get(1))
                .getInteraction();
        Assertions.assertEquals(interaction.getA(), bob);
        Assertions.assertEquals(interaction.getB(), elephant);
        Assertions.assertNull(interaction.getFinishTime());

        // Боб продолжает стоять рядом со слоном
        trackingService.update(bob.getId(), 10.01, 9.99);
        trackingService.update(elephant.getId(), 9.98, 10.001);
        Assertions.assertEquals(2, trackingService.getEvents().size());
        Assertions.assertNull(interaction.getFinishTime());

        // Слон убежал от Боба
        trackingService.update(elephant.getId(), 5.01, 5.99);
        Assertions.assertEquals(2, trackingService.getEvents().size());
        Assertions.assertNotNull(interaction.getFinishTime());

        // Боб догнал слона
        trackingService.update(bob.getId(), 4.98, 6.02);
        Assertions.assertEquals(3, trackingService.getEvents().size());
    }


    @DisplayName("Проверка отслеживания нескольких сотрудников и животных")
    @Test
    public void testInteractionManyEmployeesAndAnimals() {
        final TrackingService trackingService = zoo.getTrackingService();
        final Animal tiger = new Animal("Тигр", LocalDate.of(2010, 7, 7));
        zoo.add(tiger, bob);

        Assertions.assertTrue(trackingService.getEvents().isEmpty());

        // начальные позиции
        trackingService.update(bob.getId(), 0, 0);
        trackingService.update(elephant.getId(), 0, 10);
        trackingService.update(alice.getId(), 0, 6);
        trackingService.update(tiger.getId(), 40, 40);
        Assertions.assertEquals(2, trackingService.getEvents().size()); // Боб и Алиса пришли на работу

        // Боб подошел к слону
        trackingService.update(bob.getId(), 0, 11);
        Assertions.assertEquals(3, trackingService.getEvents().size());
        final Interaction bobElephantInteraction = ((InteractionEvent) trackingService.getEvents().get(2))
                .getInteraction();

        // Алиса подошла к слону, но не к Бобу
        trackingService.update(alice.getId(), 0, 8);
        Assertions.assertEquals(4, trackingService.getEvents().size());
        final Interaction aliceElephantInteraction = ((InteractionEvent) trackingService.getEvents().get(3))
                .getInteraction();
        Assertions.assertEquals(aliceElephantInteraction.getA(), alice);
        Assertions.assertNull(aliceElephantInteraction.getFinishTime());

        // Алиса подошла к Бобу, оставаясь рядом со слоном
        trackingService.update(alice.getId(), 0, 10);
        Assertions.assertEquals(5, trackingService.getEvents().size());
        final Interaction aliceBobInteraction = ((InteractionEvent) trackingService.getEvents().get(4))
                .getInteraction();
        Assertions.assertTrue(aliceBobInteraction.getA() == alice || aliceBobInteraction.getB() == alice);
        Assertions.assertNull(aliceBobInteraction.getFinishTime());

        // Алиса отошла от Боба, но осталась рядом со слоном
        trackingService.update(alice.getId(), 0, 8);
        Assertions.assertEquals(5, trackingService.getEvents().size());
        Assertions.assertNotNull(aliceBobInteraction.getFinishTime());

        // Боб отошел от слона и подошел к тигру
        trackingService.update(bob.getId(), 40, 40);
        Assertions.assertEquals(6, trackingService.getEvents().size());
        Assertions.assertNotNull(bobElephantInteraction.getFinishTime());
        final Interaction bobTigerInteraction = ((InteractionEvent) trackingService.getEvents().get(5))
                .getInteraction();
        Assertions.assertEquals(bobTigerInteraction.getA(), bob);
        Assertions.assertEquals(bobTigerInteraction.getB(), tiger);
        Assertions.assertNull(bobTigerInteraction.getFinishTime());
        Assertions.assertNotNull(bobElephantInteraction.getFinishTime());
    }

    @DisplayName("Проверка отслеживания прихода и ухода сотрудников")
    @Test
    public void testWorkShift() {
        final TrackingService trackingService = zoo.getTrackingService();
        // Никто еще не пришел и не ушел
        Assertions.assertTrue(trackingService.getEvents().isEmpty());

        // Боб пришел на работу
        trackingService.update(bob.getId(), 0, 0);
        Assertions.assertEquals(1, trackingService.getEvents().size());
        final WorkShift bobWorkShift = ((WorkShiftEvent) trackingService.getEvents().get(0)).getWorkShift();
        Assertions.assertNull(bobWorkShift.getFinishTime());

        // Слон походил по зоопарку вдалеке, ничего не должно измениться
        trackingService.update(elephant.getId(), 10, 10);
        trackingService.update(elephant.getId(), 12, 12);
        Assertions.assertEquals(1, trackingService.getEvents().size());

        // Боб ходит по территории зоопарка
        trackingService.update(bob.getId(), 1, 1);
        Assertions.assertEquals(1, trackingService.getEvents().size());
        Assertions.assertNull(bobWorkShift.getFinishTime());

        // Боб ушел из зоопарка
        trackingService.update(bob.getId(), -200, -200);
        Assertions.assertEquals(1, trackingService.getEvents().size());
        Assertions.assertNotNull(bobWorkShift.getFinishTime());

        // Боб снова пришел на работу
        trackingService.update(bob.getId(), 0, 0);
        Assertions.assertEquals(2, trackingService.getEvents().size());
        final WorkShift bobSecondWorkShift = ((WorkShiftEvent) trackingService.getEvents().get(1)).getWorkShift();
        Assertions.assertNull(bobSecondWorkShift.getFinishTime());

        // Алиса пришла на работу вдалеке
        trackingService.update(alice.getId(), 50, 50);
        Assertions.assertEquals(3, trackingService.getEvents().size());
        final WorkShift aliceWorkShift = ((WorkShiftEvent) trackingService.getEvents().get(2)).getWorkShift();
        Assertions.assertNull(aliceWorkShift.getFinishTime());

        // Алиса ушла с работы
        trackingService.update(alice.getId(), 50, 200);
        Assertions.assertEquals(3, trackingService.getEvents().size());
        Assertions.assertNotNull(aliceWorkShift.getFinishTime());

        // Боб снова ушел из зоопарка
        trackingService.update(bob.getId(), -200, 10);
        Assertions.assertEquals(3, trackingService.getEvents().size());
        Assertions.assertNotNull(bobSecondWorkShift.getFinishTime());
    }

    @DisplayName("Проверка отслеживания общего времени с подопечными животными")
    @Test
    public void testGeneralTimeWithWards() throws InterruptedException {
        // Слон привязан к Бобу,  обезьяна - к Алисе
        final Animal monkey = new Animal("Обезьяна", LocalDate.now());
        zoo.add(monkey, alice);

        final TrackingService trackingService = zoo.getTrackingService();
        // начальные позиции
        trackingService.update(bob.getId(), 0, 0);
        trackingService.update(elephant.getId(), 10, 10);
        trackingService.update(monkey.getId(), 20, 20);

        Assertions.assertEquals(0, trackingService.generalTimeWithWards(bob));
        Assertions.assertEquals(0, trackingService.generalTimeWithWards(alice));

        // Боб подошел к слону
        trackingService.update(bob.getId(), 10, 10);
        Thread.sleep(2000);
        Assertions.assertNotEquals(0, trackingService.generalTimeWithWards(bob));
        Assertions.assertEquals(0, trackingService.generalTimeWithWards(alice));

        // Алиса пришла на работу
        trackingService.update(alice.getId(), 0, 0);
        Thread.sleep(2000);
        Assertions.assertEquals(0, trackingService.generalTimeWithWards(alice));

        // Боб подошел к обезьяне
        trackingService.update(bob.getId(), 20, 20);
        long bobWorkTime = trackingService.generalTimeWithWards(bob);
        Thread.sleep(2000);
        Assertions.assertNotEquals(0, bobWorkTime);
        Assertions.assertEquals(0, trackingService.generalTimeWithWards(alice));

        // Алиса подошла к обезьяне
        trackingService.update(alice.getId(), 20, 20);
        Thread.sleep(2000);
        Assertions.assertNotEquals(0, trackingService.generalTimeWithWards(alice));
        Assertions.assertEquals(bobWorkTime, trackingService.generalTimeWithWards(bob));
    }


    @DisplayName("Проверка отслеживания вывода животных")
    @Test
    public void testCountBringingOut() throws InterruptedException {
        final TrackingService trackingService = zoo.getTrackingService();
        final Animal monkey = new Animal("Обезьяна", LocalDate.now());
        zoo.add(monkey, alice);

        // начальные позиции
        trackingService.update(bob.getId(), 0, 0);
        trackingService.update(elephant.getId(), 195, 0);
        trackingService.update(monkey.getId(), 20, 20);
        Thread.sleep(300);
        Assertions.assertEquals(0, trackingService.countBringingOut(bob));

        // Боб подошел к слону
        trackingService.update(bob.getId(), 195, 0);
        Thread.sleep(300);
        Assertions.assertEquals(0, trackingService.countBringingOut(bob));

        // Боб отошел от слона
        trackingService.update(bob.getId(), 190, 0);
        Thread.sleep(300);
        Assertions.assertEquals(0, trackingService.countBringingOut(bob));

        // Боб выводит слона из зоопарка
        trackingService.update(bob.getId(), 199, 0);
        trackingService.update(elephant.getId(), 199, 0);
        Thread.sleep(300);
        trackingService.update(bob.getId(), 201, 0);
        trackingService.update(elephant.getId(), 201, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Боб возвращает слона в зоопарк
        trackingService.update(bob.getId(), 100, 0);
        trackingService.update(elephant.getId(), 100, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Слон сам уходит из зоопарка
        trackingService.update(elephant.getId(), 220, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Слон возвращается в зоопарк
        trackingService.update(elephant.getId(), 20, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Боб уходит с работы
        trackingService.update(bob.getId(), 300, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Боб возвращается на работу
        trackingService.update(bob.getId(), 0, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Боб подходит к обезьяне
        trackingService.update(bob.getId(), 20, 20);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Боб подводит обезьяну к границе зоопарка
        trackingService.update(bob.getId(), 199, 0);
        trackingService.update(monkey.getId(), 199.5, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Обезьяна сама выбегает из зоопарка, а Боб внутри, контакт продолжается
        trackingService.update(monkey.getId(), 201, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        //Обезьяна отходит дальше, контакт прекращается
        trackingService.update(monkey.getId(), 204, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Боб выходит за обезьяной
        trackingService.update(bob.getId(), 204, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Боб возвращается с обезьяной в зоопарк
        trackingService.update(bob.getId(), 201.5, 0);
        trackingService.update(monkey.getId(), 201.5, 0);
        Thread.sleep(300);
        trackingService.update(bob.getId(), 199, 0);
        trackingService.update(monkey.getId(), 199, 0);
        Thread.sleep(300);
        Assertions.assertEquals(1, trackingService.countBringingOut(bob));

        // Боб выводит обезьяну из зоопарка
        trackingService.update(bob.getId(), 201, 0);
        trackingService.update(monkey.getId(), 201, 0);
        Thread.sleep(300);
        Assertions.assertEquals(2, trackingService.countBringingOut(bob));

        // Боб возвращается с обезьяной в зоопарк
        trackingService.update(bob.getId(), 199, 0);
        trackingService.update(monkey.getId(), 199, 0);
        Thread.sleep(300);
        Assertions.assertEquals(2, trackingService.countBringingOut(bob));

        // Боб уходит с работы
        trackingService.update(bob.getId(), 300, 0);
        Assertions.assertEquals(2, trackingService.countBringingOut(bob));
    }
}
