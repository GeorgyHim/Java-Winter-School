package TransportControlSystem.Vehicles;

import TransportControlSystem.Types.VehicleTypes;

/** Транспортное средство */
public abstract class Vehicle implements VehicleInfo {
    /** Тип транспорта */
    private VehicleTypes type;

    /**
     * Транспортное средство
     * @param type тип
     */
    public Vehicle(VehicleTypes type) {
        this.type = type;
    }

    /** Получение типа транспортного средства */
    public VehicleTypes getType() {
        return type;
    }

    /** Получение информации о транспортном средстве */
    public String displayInfo() {
        return this.toString();
    }

    // Явно укажем необходимость переопределить метод toString()
    public abstract String toString();
}
