package TransportControlSystem.Vehicles;

import TransportControlSystem.Types.VehicleTypes;

public interface VehicleInfo {
    /** Отображение информации о транспортном средстве*/
    String displayInfo();
    /** Получение типа транспортного средства */
    public VehicleTypes getType();
}
