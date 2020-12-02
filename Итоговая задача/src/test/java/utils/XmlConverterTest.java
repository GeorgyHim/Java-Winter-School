package utils;

import model.Flight;
import model.helper.FlightList;
import model.helper.FlightStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

public class XmlConverterTest {
    static XmlConverter converter = new XmlConverter(false);
    static Flight flight1 = new Flight(
            "US-228", "Краснодар", "Санкт-Петербург", "SomebodyAirlines",
            LocalDateTime.of(2020, 11, 28, 10, 0), LocalDateTime.of(2020, 11, 28, 12, 30), FlightStatus.EN_ROUTE
    );
    static Flight flight2 = new Flight(
            "TT-322", "Сочи", "Москва", "AwesomeFly",
            LocalDateTime.of(2020, 11, 30, 15, 0), LocalDateTime.of(2020, 11, 30, 17, 0), FlightStatus.SCHEDULED
    );

    @Test
    public void testSerializeFlightList() throws IOException {
        FlightList flightList = new FlightList(Arrays.asList(flight1, flight2));
        String xml = converter.toXml(flightList);
        Assertions.assertEquals(
                xml,
                "<flightList><flight><cityFrom>Краснодар</cityFrom><cityTo>Санкт-Петербург</cityTo><departureDateTime>2020-11-28 10:00</departureDateTime><arrivalDateTime>2020-11-28 12:30</arrivalDateTime><airline>SomebodyAirlines</airline><number>US-228</number><status>EN_ROUTE</status></flight><flight><cityFrom>Сочи</cityFrom><cityTo>Москва</cityTo><departureDateTime>2020-11-30 15:00</departureDateTime><arrivalDateTime>2020-11-30 17:00</arrivalDateTime><airline>AwesomeFly</airline><number>TT-322</number><status>SCHEDULED</status></flight></flightList>"
        );
    }

    @Test
    public void testSerializeList() throws IOException {
        String xml = converter.toXml(Arrays.asList(flight1, flight2));
        Assertions.assertEquals(
                xml,
                "<flightList><flight><cityFrom>Краснодар</cityFrom><cityTo>Санкт-Петербург</cityTo><departureDateTime>2020-11-28 10:00</departureDateTime><arrivalDateTime>2020-11-28 12:30</arrivalDateTime><airline>SomebodyAirlines</airline><number>US-228</number><status>EN_ROUTE</status></flight><flight><cityFrom>Сочи</cityFrom><cityTo>Москва</cityTo><departureDateTime>2020-11-30 15:00</departureDateTime><arrivalDateTime>2020-11-30 17:00</arrivalDateTime><airline>AwesomeFly</airline><number>TT-322</number><status>SCHEDULED</status></flight></flightList>"
        );
    }

    @Test
    public void testDeserialize() throws IOException {
        FlightList flightList = new FlightList(Arrays.asList(flight1, flight2));
        String xml = converter.toXml(flightList);
        FlightList flightList2 = converter.fromXml(xml, FlightList.class);
        Assertions.assertEquals(flightList, flightList2);
    }
}
