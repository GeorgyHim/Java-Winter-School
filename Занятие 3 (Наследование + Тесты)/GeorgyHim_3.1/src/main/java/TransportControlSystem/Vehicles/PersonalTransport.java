package TransportControlSystem.Vehicles;

import TransportControlSystem.Types.VehicleTypes;

/** Средство индивидуальной мобильности */
public class PersonalTransport extends Vehicle {
    /**
     * Средство индивидуальной мобильности
     * @param type тип
     */
    public PersonalTransport(VehicleTypes type) {
        super(type);
    }

    @Override
    public String toString() {
        return "PersonalTransport{" +
                "type=" + this.getType() +
                "}";
    }
}
