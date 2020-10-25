package zoo.tracking.event;


import zoo.tracking.incidents.Interaction;

/**
 * Событие взаимодействия сотрудника и животного.
 */
public abstract class InteractionEvent extends TrackingEvent {
    private final Interaction interaction;

    public InteractionEvent(Interaction interaction) {
        super(interaction.getStartTime());
        this.interaction = interaction;
    }

    public Interaction getInteraction() {
        return interaction;
    }
}
