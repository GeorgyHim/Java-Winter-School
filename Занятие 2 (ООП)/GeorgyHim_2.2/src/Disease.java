import java.time.LocalDate;

public class Disease {
    public String name;
    LocalDate dateOfInfection;

    public Disease(String name) {
        this(name, LocalDate.now());
    }

    public Disease(String name, LocalDate dateOfInfection) {
        this.name = name;
        this.dateOfInfection = dateOfInfection;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfInfection() {
        return dateOfInfection;
    }
}
