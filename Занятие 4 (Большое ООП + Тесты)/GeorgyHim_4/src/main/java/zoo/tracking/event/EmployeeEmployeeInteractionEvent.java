package zoo.tracking.event;

import zoo.tracking.incidents.Interaction;

public class EmployeeEmployeeInteractionEvent extends InteractionEvent {
    public EmployeeEmployeeInteractionEvent(Interaction interaction) {
        super(interaction);
    }
}
