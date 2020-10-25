package TransportControlSystem.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Виды транспортных средств */
public abstract class VehicleKinds {
    /** Список типов автомобилей */
    public static List<VehicleTypes> carTypes() {
        return Arrays.asList(new VehicleTypes[]{VehicleTypes.PASSENGERCAR, VehicleTypes.TRUCK});
    }

    /** Список типов общественного транспорта */
    public static List<VehicleTypes> publicTransportTypes() {
        return Arrays.asList(new VehicleTypes[]{VehicleTypes.BUS, VehicleTypes.TROLLEYBUS,
                VehicleTypes.TRAM, VehicleTypes.SUBWAY});
    }

    /** Список типов средств индивидуальной мобильности */
    public static List<VehicleTypes> personalTransportTypes() {
        return Arrays.asList(new VehicleTypes[]{VehicleTypes.KICKSCOOTER, VehicleTypes.BICYCLE});
    }

    /** Список типов транспортных средств по названию */
    public static List<VehicleTypes> getTypesList(String kind) {
        switch (kind) {
            case "Car": return carTypes();
            case "Public": return publicTransportTypes();
            case "Personal": return personalTransportTypes();
            default: return new ArrayList<VehicleTypes>();
        }
    }
}
