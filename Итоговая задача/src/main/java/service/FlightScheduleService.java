package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import db.DataSourceProvider;
import model.Flight;
import org.apache.derby.jdbc.EmbeddedDataSource;
import repository.FlightRepository;
import utils.XmlConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FlightScheduleService {

    /** Репозиторий объектов {@link model.Flight}*/
    private final FlightRepository repository;

    /** Преобразователь в XML */
    private static XmlConverter converter = new XmlConverter(true);

    public FlightScheduleService() throws IOException {
        DataSourceProvider dataSourceProvider = new DataSourceProvider();
        this.repository = new FlightRepository(dataSourceProvider.getDataSource());
    }

    /**
     * Метод поиска рейсов на указанную дату и указанные города вылета/прилета и записи в файл этих данных в формате XML
     *
     * @param cityFrom                  -   Город вылета
     * @param cityTo                    -   Город прилета
     * @param date                      -   Дата вылета
     * @throws JsonProcessingException  -   Исключение во время сериализации списка рейсов
     */
    public void getFlightSchedule(String cityFrom, String cityTo, LocalDate date) throws JsonProcessingException {
        List<Flight> fittingFlights = findFittingFlights(cityFrom, cityTo, date);
        String xml = converter.toXml(fittingFlights);

    }

    /**
     * Метод нахождения соответствующих входным параметрам рейсов
     *
     * @param cityFrom  -   Город вылета
     * @param cityTo    -   Город прилета
     * @param date      -   Дата вылета
     * @return          -   Список соответствующих рейсов
     */
    private List<Flight> findFittingFlights(String cityFrom, String cityTo, LocalDate date) {
        List<Flight> allFlights = repository.findAll();
        return allFlights.stream()
                .filter(flight ->
                        flight.getCityFrom().equals(cityFrom) &&
                                flight.getCityTo().equals(cityTo) &&
                                flight.getDepartureTime().toLocalDate() == date)
                .sorted(Comparator.comparing(Flight::getDepartureTime))
                .collect(Collectors.toList());
    }
}
