package zoo.tracking.finder;

import zoo.employee.Employee;
import zoo.tracking.Trackable;
import zoo.tracking.event.EmployeeEmployeeInteractionEvent;
import zoo.tracking.incidents.Interaction;

public class EmployeeEmployeeInteractionEventFinder extends InteractionEventFinder {

    /**
     * Проверка на нужный состав участников для разных видов взаимодействий
     *
     * @param trackableA первый объект
     * @param trackableB второй объект
     * @return True если нужный состав участников
     */
    @Override
    public boolean correctParticipants(Trackable trackableA, Trackable trackableB) {
        return trackableA instanceof Employee && trackableB instanceof Employee;
    }

    /**
     * Создаёт событие соответствующего типа
     *
     * @param interaction взаимодействие
     * @return событие нужного типа
     */
    @Override
    public EmployeeEmployeeInteractionEvent createEvent(Interaction interaction) {
        return new EmployeeEmployeeInteractionEvent(interaction);
    }

    public EmployeeEmployeeInteractionEventFinder(double interactionDistance) {
        super(interactionDistance);
    }
}
