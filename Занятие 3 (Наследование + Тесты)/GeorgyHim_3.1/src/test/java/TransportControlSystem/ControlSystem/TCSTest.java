package TransportControlSystem.ControlSystem;

import TransportControlSystem.Types.VehicleTypes;
import TransportControlSystem.Vehicles.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

public class TCSTest {
    private TCS tcs;
    private final Car oldCar = new Car(VehicleTypes.PASSENGERCAR, 1984);
    private final Car newCar = new Car(VehicleTypes.PASSENGERCAR, 2018);
    private final Car truck = new Car(VehicleTypes.TRUCK, 2018);
    private final PublicTransport tram = new PublicTransport(VehicleTypes.TRAM, 50);
    private final PublicTransport bus = new PublicTransport(VehicleTypes.BUS, 30);
    private final PersonalTransport bike = new PersonalTransport(VehicleTypes.BICYCLE);

    @BeforeEach
    public void init() {
        System.out.println("Init");
        tcs = new TCS("Краснодар");
    }

    @Test
    public void testRegister() {
        System.out.println("testRegister");
        tcs.register(oldCar);
        tcs.register(newCar, truck);
        tcs.register(bus);
        tcs.register(tram, bike);
        Assertions.assertEquals(new HashSet<>(Arrays.asList(oldCar, newCar, truck, bus, tram, bike)),
                new HashSet<>(tcs.getVehicles()));
    }

    @Test
    public void testGetActualInfo() {
        System.out.println("testGetActualInfo");
        tcs.register(newCar, truck, bus);
        Assertions.assertEquals(truck.displayInfo(), tcs.getActualInfo(truck));
    }

    @Test
    public void testGetCommonInfo() {
        System.out.println("testGetCommonInfo");
        tcs.register(oldCar, tram, bike);
        Assertions.assertEquals(bike.displayInfo() + "\n", tcs.getActualTypeInfo(VehicleTypes.BICYCLE));
        Assertions.assertEquals(oldCar.displayInfo() + "\n", tcs.getActualKindInfo("Car"));

        tcs.register(newCar, truck, bus);
        Assertions.assertEquals(oldCar.displayInfo() + "\n" + newCar.displayInfo() + "\n" + truck.displayInfo() + "\n",
                tcs.getActualKindInfo("Car"));
    }

    @Test
    public void testPassengerCount() {
        System.out.println("testPassengerCount");
        tcs.register(newCar, truck, bus, oldCar, tram, bike);
        Assertions.assertEquals(tram.getCapacity() + bus.getCapacity(), tcs.passengerCount());
    }

    @Test
    public void testNYearsPassengerCars() {
        System.out.println("testNYearsPassengerCars");
        tcs.register(newCar, truck, bus, oldCar, tram, bike);
        Assertions.assertEquals(1, tcs.nYearsPassengerCars(2));
    }

}
