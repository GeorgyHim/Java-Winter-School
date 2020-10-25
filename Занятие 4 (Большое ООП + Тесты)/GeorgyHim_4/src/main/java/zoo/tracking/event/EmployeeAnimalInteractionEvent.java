package zoo.tracking.event;

import zoo.animal.Animal;
import zoo.employee.Employee;
import zoo.tracking.incidents.Interaction;

public class EmployeeAnimalInteractionEvent extends InteractionEvent {
    public EmployeeAnimalInteractionEvent(Interaction interaction) {
        super(interaction);
    }

    public Employee getEmployee() {
        final Interaction interaction = super.getInteraction();
        if (interaction.getA() instanceof Employee) {
            return (Employee) interaction.getA();
        }
        else {
            return (Employee) interaction.getB();
        }
    }

    public Animal getAnimal() {
        final Interaction interaction = super.getInteraction();
        if (interaction.getA() instanceof Animal) {
            return (Animal) interaction.getA();
        }
        else {
            return (Animal) interaction.getB();
        }
    }
}
