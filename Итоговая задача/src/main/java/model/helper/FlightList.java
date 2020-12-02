package model.helper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import model.Flight;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "flightList")
public class FlightList {
    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "flight")
    private List<Flight> flights;

    public FlightList(List<Flight> flights) {
        this.flights = flights;
    }

    public FlightList() {
    }

    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightList that = (FlightList) o;

        return flights.equals(that.getFlights());
    }

    @Override
    public int hashCode() {
        return Objects.hash(flights);
    }
}
