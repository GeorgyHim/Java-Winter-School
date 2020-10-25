package TransportControlSystem.ControlSystem;

import TransportControlSystem.Types.VehicleTypes;
import TransportControlSystem.Types.VehicleKinds;
import TransportControlSystem.Vehicles.Car;
import TransportControlSystem.Vehicles.PublicTransport;
import TransportControlSystem.Vehicles.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Система контроля транспорта */
public class TCS {
    /** Город, который обеспечивает система */
    private String cityName;
    /** Система учёта транспортных средств */
    private List<Vehicle> vehicles;

    /**
     * Система контроля транспорта
     * @param cityName город
     */
    public TCS(String cityName) {
        this.cityName = cityName;
        vehicles = new ArrayList<>();
    }

    /**
     * Регистрация транспортного средства в системе учёта
     * @param vehicle транспортное средство
     */
    public void register(Vehicle... vehicle) {
        vehicles.addAll(Arrays.asList(vehicle));
    }

    /**
     * Предоставление актуальной информации о заданном транспортном средстве
     * @param vehicle транспортное средство
     * @return актуальная информация
     */
    public String getActualInfo(Vehicle vehicle) {
        return vehicle.displayInfo();
    }

    /**
     * Предоставление актуальной информации обо всех транспортных средствах конкретного типа
     * @param type тип
     * @return актуальная информация
     */
    public String getActualTypeInfo(VehicleTypes type) {
        String info = "";
        for (Vehicle v: vehicles) {
            if (v.getType() == type) {
                info += v.displayInfo() + "\n";
            }
        }
        return info;
    }

    /**
     * Предоставление актуальной информации обо всех транспортных средствах конкретного вида по названию
     * @param kind вид
     * @return актуальная информация
     */
    public String getActualKindInfo(String kind) {
        String info = "";
        List <VehicleTypes> kindTypes = VehicleKinds.getTypesList(kind);
        for (Vehicle v: vehicles) {
            if (kindTypes.contains(v.getType())) {
                info += v.displayInfo() + "\n";
            }
        }
        return info;
    }

    /**
     * Подсчет максимального количества пассажиров, которое может перевозить общественный транспорт
     * @return Общая вместимость общественного транспорта
     */
    public int passengerCount() {
        int count = 0;
        for (Vehicle v: vehicles) {
            if (VehicleKinds.publicTransportTypes().contains(v.getType())) {
                count += ((PublicTransport) v).getCapacity();
            }
        }
        return count;
    }

    /**
     * Подсчет количества легковых автомобилей, выпущенных n лет назад
     * @param n возраст автомобиля
     * @return количество легковых автомобилей этого возраста
     */
    public int nYearsPassengerCars(int n) {
        int count = 0;
        for (Vehicle v: vehicles) {
            if (v.getType() == VehicleTypes.PASSENGERCAR &&
                    LocalDate.now().getYear() - ((Car)v).getProductionYear() == n) {
                count++;
            }
        }
        return count;
    }


    public String getCityName() {
        return cityName;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
