package utils;

import model.Flight;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "flightList")
public class FlightList {
    @XmlElement(name = "flight")
    private List<Flight> flights;

    public FlightList(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
