package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

public class XmlConverterTest {
    static XmlConverter converter = new XmlConverter();
    static Flight flight1 = new Flight(
            "US-228", "Краснодар", "Санкт-Петербург", "SomebodyAirlines",
            LocalDateTime.of(2020, 11, 28, 10, 0), LocalDateTime.of(2020, 11, 28, 12, 30), FlightStatus.EN_ROUTE
    );
    static Flight flight2 = new Flight(
            "TT-322", "Сочи", "Москва", "AwesomeFly",
            LocalDateTime.of(2020, 11, 30, 15, 0), LocalDateTime.of(2020, 11, 30, 17, 0), FlightStatus.SCHEDULED
    );

    @Test
    public void testSerializing() throws JsonProcessingException {
        FlightList flightList = new FlightList(Arrays.asList(flight1, flight2));
        String xml = converter.toXml(flightList);
        // TODO:
        Assertions.assertTrue(true);
    }
}
