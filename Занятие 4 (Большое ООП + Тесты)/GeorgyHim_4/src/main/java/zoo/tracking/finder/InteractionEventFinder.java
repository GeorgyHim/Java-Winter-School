package zoo.tracking.finder;


import zoo.tracking.Trackable;
import zoo.tracking.incidents.Interaction;
import zoo.tracking.event.InteractionEvent;
import zoo.tracking.location.Position;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Анализатор события {@link InteractionEvent}.
 */
public abstract class InteractionEventFinder implements EventFinder {
    /** Список незавершенных взаимодействий для каждого объекта. (id -> список незавершенных взаимодействий) */
    private final Map<String, List<Interaction>> interactions = new HashMap<>();
    /** Максимальное расстояние, на котором происходит взаимодействие */
    private final double interactionDistance;

    /**
     * Анализатор события {@link InteractionEvent}.
     *
     * @param interactionDistance максимальное растояние, которое считается взаимодействием
     */
    public InteractionEventFinder(double interactionDistance) {
        this.interactionDistance = interactionDistance;
    }

    @Override
    /**
     * Ищет события для отслеживаемого объекта, у которого изменились координаты.
     *
     * @param updatedTrackable обновленый отслеживаемый объект
     * @param trackable все отслеживаемые объекты
     * @return список новых событий, если они обнаружены
     */
    public List<InteractionEvent> findNext(Trackable updatedTrackable, Map<String, Trackable> trackable) {
        final List<InteractionEvent> newEvents = new ArrayList<>();
        // Перебираем все отслеживаемые объекты
        for (Trackable trackableA : trackable.values()) {
            if (trackableA == updatedTrackable || trackableA.getCurrentLocation() == null) {
                continue;
            }

            // Проверяем, есть ли событие взаимодействия между объектами
            final InteractionEvent event = findInteractionEvent(updatedTrackable, trackableA);
            if (event != null) {
                // Если есть, добавляем его в список новых событий отслеживаемого объекта
                newEvents.add(event);
            }
        }
        return newEvents;
    }



    //------------------Новый код------------------------------------------------------------------------------

    /**
     * Поиск события взаимодействия
     *
     * @param trackableA первый объект
     * @param trackableB второй объект
     */
    private InteractionEvent findInteractionEvent(Trackable trackableA, Trackable trackableB) {
        if (!correctParticipants(trackableA, trackableB)) {
            return null;
        }
        // Проверяем, взаимодействуют ли сейчас объекты
        final boolean currentInteraction = isInteraction(trackableA.getCurrentLocation().position,
                trackableB.getCurrentLocation().position);

        if (currentInteraction) { // Если взаимодействие происходит
            if (isExistsNotCompletedInteractionBetween(trackableA, trackableB)) { // было зафиксировано ранее и не прекратилось
                return null; // значит оно продолжается, ничего не делаем
            }

            // Иначе генерируем новое взаимодействие
            final Interaction interaction = new Interaction(trackableA, trackableB, LocalDateTime.now());
            // Добавляем взаимодействие в список незавершенных для отслеживания завершения
            addInteraction(interaction);
            // Возвращаем событие появления взаимодействия
            return createEvent(interaction);
        } else { // Взаимодействие не происходит
            if (!isExistsNotCompletedInteractionBetween(trackableA, trackableB)) { // и его не было ранее
                return null; // значит взаимодействия не было и нет, ничего не делаем
            }

            // Иначе взаимодействие было, но прекратилость
            // Проставим время завершения в созданное ранее взаимодействие и удалим его с отслеживания
            final Interaction interaction = getNotCompletedInteractionBetween(trackableA, trackableB);
            interaction.setFinishTime(LocalDateTime.now());
            removeInteraction(interaction);
            return null;
        }
    }

    /**
     * Проверка на нужный состав участников для разных видов взаимодействий
     * @param trackableA первый объект
     * @param trackableB второй объект
     * @return True если нужный состав участников
     */
    public abstract boolean correctParticipants(Trackable trackableA, Trackable trackableB);


    /**
     * Создаёт событие соответствующего типа
     * @param interaction взаимодействие
     * @return событие нужного типа
     */
    public abstract InteractionEvent createEvent(Interaction interaction);

    //------------------Новый код закончен------------------------------------------------------------------------------



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
