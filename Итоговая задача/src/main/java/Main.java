import db.DataSourceProvider;
import repository.FlightRepository;
import service.FlightScheduleService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        DataSourceProvider dataSourceProvider = new DataSourceProvider();
        FlightRepository flightRepository = new FlightRepository(dataSourceProvider.getDataSource());
        FlightScheduleService scheduleService = new FlightScheduleService(flightRepository);

        Scanner in = new Scanner(System.in);
        System.out.println("Input city of departure:");
        String cityFrom = in.next();
        System.out.println("Input city of arrival:");
        String cityTo = in.next();
        System.out.println("Input date of departure in format YYYY-MM-DD:");
        LocalDate date = LocalDate.parse(in.next());

        scheduleService.getFlightSchedule(cityFrom, cityTo, date);
    }
}
