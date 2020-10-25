package TransportControlSystem.Vehicles;

import TransportControlSystem.Types.VehicleTypes;

/** Автомобиль */
public class Car extends Vehicle {
    /** Год производства */
    private int productionYear;

    /**
     * Автомобиль
     * @param type тип
     * @param productionYear год производства
     */
    public Car(VehicleTypes type, int productionYear) {
        super(type);
        this.productionYear = productionYear;
    }

    /** Получение года производства */
    public int getProductionYear() {
        return productionYear;
    }

    @Override
    public String toString() {
        return "Car{" +
                "type=" + this.getType() +
                ", productionYear=" + this.getProductionYear() +
                '}';
    }
}
