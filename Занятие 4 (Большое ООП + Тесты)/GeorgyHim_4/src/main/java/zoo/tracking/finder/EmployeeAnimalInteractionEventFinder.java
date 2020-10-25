package zoo.tracking.finder;

import zoo.animal.Animal;
import zoo.employee.Employee;
import zoo.tracking.Trackable;
import zoo.tracking.event.EmployeeAnimalInteractionEvent;
import zoo.tracking.incidents.Interaction;

public class EmployeeAnimalInteractionEventFinder extends InteractionEventFinder {
    /**
     * Проверка на нужный состав участников для разных видов взаимодействий
     *
     * @param trackableA первый объект
     * @param trackableB второй объект
     * @return True если нужный состав участников
     */
    @Override
    public boolean correctParticipants(Trackable trackableA, Trackable trackableB) {
        return trackableA instanceof Animal && trackableB instanceof Employee
         || trackableA instanceof Employee && trackableB instanceof Animal;
    }

    /**
     * Создаёт событие соответствующего типа
     *
     * @param interaction взаимодействие
     * @return событие нужного типа
     */
    @Override
    public EmployeeAnimalInteractionEvent createEvent(Interaction interaction) {
        return new EmployeeAnimalInteractionEvent(interaction);
    }

    public EmployeeAnimalInteractionEventFinder(double interactionDistance) {
        super(interactionDistance);
    }
}
