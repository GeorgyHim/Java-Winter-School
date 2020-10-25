package cinematic;
import java.time.LocalDate;
import java.util.Arrays;

public class Cinematographer {
    private String name;
    private LocalDate dateOfBirth;
    private String country;
    private Award[] awards;

    public Cinematographer() {
        this("");
    }

    public Cinematographer(String name) {
        this(name, LocalDate.now(), "", new Award[0]);
    }

    public Cinematographer(String name, LocalDate dateOfBirth, String country, Award[] awards) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public Award[] getAwards() {
        return awards;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void winAward(Award award) {
        awards = Arrays.copyOf(awards,  awards.length + 1);
        awards[awards.length - 1] = award;
    }

}
