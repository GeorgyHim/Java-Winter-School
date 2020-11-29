package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import db.DataSourceProvider;
import model.Flight;
import repository.FlightRepository;
import utils.XmlConverter;

import java.io.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FlightScheduleService {

    /** Репозиторий объектов {@link model.Flight}*/
    private final FlightRepository repository;

    /** Преобразователь в XML */
    private static XmlConverter converter = new XmlConverter(true);

    /** Шаблон имени файла для сохранения XML-данных о расписании рейсов */
    public static final String xmlFilenamePattern = "Flights from %s to %s on %s.xml";

    public FlightScheduleService(DataSourceProvider dataSourceProvider) throws IOException {
        this.repository = new FlightRepository(dataSourceProvider.getDataSource());
    }

    /**
     * Метод поиска рейсов на указанную дату и указанные города вылета/прилета и записи в файл этих данных в формате XML
     *
     * @param cityFrom                  -   Город вылета
     * @param cityTo                    -   Город прилета
     * @param date                      -   Дата вылета
     * @throws JsonProcessingException  -   Исключение во время сериализации и записи в файл списка рейсов
     */
    public void getFlightSchedule(String cityFrom, String cityTo, LocalDate date) throws IOException {
        List<Flight> fittingFlights = findFittingFlights(cityFrom, cityTo, date);
        String xml = converter.toXml(fittingFlights);
        writeXmlToFile(xml, String.format(xmlFilenamePattern, cityFrom, cityTo, date.toString()));
    }

    /**
     * Метод нахождения соответствующих входным параметрам рейсов
     *
     * @param cityFrom  -   Город вылета
     * @param cityTo    -   Город прилета
     * @param date      -   Дата вылета
     * @return          -   Список соответствующих рейсов
     */
    public List<Flight> findFittingFlights(String cityFrom, String cityTo, LocalDate date) {
        List<Flight> allFlights = repository.findAll();
        return allFlights.stream()
                .filter(flight ->
                                flight.getCityFrom().equals(cityFrom) &&
                                flight.getCityTo().equals(cityTo) &&
                                flight.getDepartureTime().toLocalDate().equals(date))
                .sorted(Comparator.comparing(Flight::getDepartureTime))
                .collect(Collectors.toList());
    }

    /**
     * Метод записи XML-строки в файл в соответствии с указанными параметрами поиска
     *
     * @param xml           -   Строка XML
     * @param filename      -   Имя файла
     * @throws IOException  -   Исключение при работе с файлом
     */
    public void writeXmlToFile(String xml, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(xml);
        }
    }
}
