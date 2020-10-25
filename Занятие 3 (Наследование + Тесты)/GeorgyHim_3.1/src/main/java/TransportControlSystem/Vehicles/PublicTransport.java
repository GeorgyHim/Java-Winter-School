package TransportControlSystem.Vehicles;

import TransportControlSystem.Types.VehicleTypes;

/** Общественный транспорт */
public class PublicTransport extends Vehicle {
    /** Вместимость транспортного средства */
    private int capacity;

    /**
     * Общественный транспорт
     * @param type тип
     * @param capacity - вместимость
     */
    public PublicTransport(VehicleTypes type, int capacity) {
        super(type);
        this.capacity = capacity;
    }

    /** Получение вместимости транспортного средства */
    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "PublicTransport{" +
                "type=" + this.getType() +
                ", capacity=" + capacity +
                '}';
    }
}
