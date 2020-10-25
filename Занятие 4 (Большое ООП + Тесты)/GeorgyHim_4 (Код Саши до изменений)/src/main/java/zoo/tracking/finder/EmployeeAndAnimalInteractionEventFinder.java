package zoo.tracking.finder;


import zoo.animal.Animal;
import zoo.employee.Employee;
import zoo.tracking.Trackable;
import zoo.tracking.interaction.Interaction;
import zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import zoo.tracking.location.Position;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Анализатор события {@link EmployeeAndAnimalInteractionEvent}.
 */
public class EmployeeAndAnimalInteractionEventFinder implements EventFinder {
    /** Список незавершенных взаимодействий для каждого объекта. (id -> список незавершенных взаимодействий) */
    private final Map<String, List<Interaction>> interactions = new HashMap<>();
    /** Максимальное расстояние, на котором происходит взаимодействие */
    private final double interactionDistance;

    /**
     * Анализатор события {@link EmployeeAndAnimalInteractionEvent}.
     *
     * @param interactionDistance максимальное растояние, которое считается взаимодействием
     */
    public EmployeeAndAnimalInteractionEventFinder(double interactionDistance) {
        this.interactionDistance = interactionDistance;
    }

    @Override
    /**
     * Ищет события для отслеживаемого объекта, у которого изменились координаты.
     *
     * @param updatedTracked обновленый отслеживаемый объект
     * @param trackable все отслеживаемые объекты
     * @return список новых событий, если они обнаружены
     */
    public List<EmployeeAndAnimalInteractionEvent> findNext(Trackable updatedTracked, Map<String, Trackable> trackable) {
        final List<EmployeeAndAnimalInteractionEvent> newEvents = new ArrayList<>();
        // Перебираем все отслеживаемые объекты
        for (Trackable trackedA : trackable.values()) {
            if (trackedA == updatedTracked || trackedA.getCurrentLocation() == null) {
                continue;
            }

            // Проверяем, есть ли событие взаимодействия между объектами
            final EmployeeAndAnimalInteractionEvent event = findInteractionEvent(updatedTracked, trackedA);
            if (event != null) {
                // Если есть, добавляем его в список новых событий отслеживаемого объекта
                newEvents.add(event);
            }
        }
        return newEvents;
    }

    /**
     * Поиск события взаимодействия сотрудника и животного.
     *
     * @param trackedA первый объект
     * @param trackedB второй объект
     */
    private EmployeeAndAnimalInteractionEvent findInteractionEvent(Trackable trackedA, Trackable trackedB) {
        final boolean animalAndEmployee = trackedA instanceof Animal && trackedB instanceof Employee
                || trackedA instanceof Employee && trackedB instanceof Animal;
        if (!animalAndEmployee) {
            return null;
        }
        // Проверяем, взаимодействуют ли сейчас объекты
        final boolean currentInteraction = isInteraction(
                trackedA.getCurrentLocation().position,
                trackedB.getCurrentLocation().position
        );

        if (currentInteraction) { // Если взаимодействие происходит
            if (isExistsNotCompletedInteractionBetween(trackedA, trackedB)) { // было зафиксировано ранее и не прекратилось
                return null; // значит оно продолжается, ничего не делаем
            }

            // Иначе генерируем новое взаимодействие
            final Interaction interaction = new Interaction(trackedA, trackedB, LocalDateTime.now());
            // Добавляем взаимодействие в список незавершенных для отслеживания завершения
            addInteraction(interaction);
            // Возвращаем событие появления взаимодействия
            return new EmployeeAndAnimalInteractionEvent(interaction);
        } else { // Взаимодействие не происходит
            if (!isExistsNotCompletedInteractionBetween(trackedA, trackedB)) { // и его не было ранее
                return null; // значит взаимодействия не было и нет, ничего не делаем
            }

            // Иначе взаимодействие было, но прекратилость
            // Проставим время завершения в созданное ранее взаимодействие и удалим его с отслеживания
            final Interaction interaction = getNotCompletedInteractionBetween(trackedA, trackedB);
            interaction.setFinishTime(LocalDateTime.now());
            removeInteraction(interaction);
            return null;
        }
    }

    /**
     * Проверяет находятся ли два обекта в зоне взаимодействия.
     *
     * @param a а
     * @param b b
     * @return true, если взаимодействие есть в текущий момент
     */
    private boolean isInteraction(Position a, Position b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)) <= interactionDistance;
    }

    /**
     * Проверяет существует ли незаконченное взаимодействие между двуми объектами.
     *
     * @param a объект A
     * @param b объекта B
     * @return true, если да
     */
    private boolean isExistsNotCompletedInteractionBetween(Trackable a, Trackable b) {
        return getNotCompletedInteractionBetween(a, b) != null;
    }

    /**
     * Находит незавершенное взаимодействие между двуми объектами.
     *
     * @param a объект A
     * @param b объекта B
     * @return взаимодействие, null иначе
     */
    private Interaction getNotCompletedInteractionBetween(Trackable a, Trackable b) {
        // Получаем список незавершершенных взаимодействий для А - либо существующий, либо пустой список
        final List<Interaction> interactionsForA = interactions.getOrDefault(a.getId(), Collections.emptyList());
        for (Interaction interaction : interactionsForA) {
            // Если в каком-то взаимодействии для А участвует В, то существует незавершенное взаимодействие А-В
            if (interaction.getA() == b || interaction.getB() == b) {
                return interaction;
            }
        }
        return null;
    }

    /**
     * Добавляет взаимодействие для отслеживнаия завершения.
     *
     * @param interaction взаимодействие
     */
    private void addInteraction(Interaction interaction) {
        if (!interactions.containsKey(interaction.getA().getId())) {
            interactions.put(interaction.getA().getId(), new ArrayList<>());
        }
        if (!interactions.containsKey(interaction.getB().getId())) {
            interactions.put(interaction.getB().getId(), new ArrayList<>());
        }
        // Добавляем незавершенное взаимодействие в список обоих участников
        interactions.get(interaction.getA().getId()).add(interaction);
        interactions.get(interaction.getB().getId()).add(interaction);
    }

    /**
     * Удаляет взаимодействие для прекращения отслеживнаия завершения.
     *
     * @param interaction взаимодействие
     */
    private void removeInteraction(Interaction interaction) {
        interactions.get(interaction.getA().getId()).remove(interaction);
        interactions.get(interaction.getB().getId()).remove(interaction);
    }

}
