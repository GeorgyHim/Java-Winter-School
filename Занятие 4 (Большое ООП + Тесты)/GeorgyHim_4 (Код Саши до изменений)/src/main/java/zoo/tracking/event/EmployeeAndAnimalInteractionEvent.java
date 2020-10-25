package zoo.tracking.event;


import zoo.tracking.interaction.Interaction;

/**
 * Событие взаимодействия сотрудника и животного.
 */
public class EmployeeAndAnimalInteractionEvent extends TrackingEvent {
    private final Interaction interaction;

    public EmployeeAndAnimalInteractionEvent(Interaction interaction) {
        super(interaction.getStartTime());
        this.interaction = interaction;
    }

    public Interaction getInteraction() {
        return interaction;
    }
}
