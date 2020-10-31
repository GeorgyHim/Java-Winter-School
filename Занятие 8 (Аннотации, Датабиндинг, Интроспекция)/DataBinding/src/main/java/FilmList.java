import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class FilmList {

    @XmlElementWrapper(name = "films")
    @XmlElement(name = "film")
    private List<Film> films;

    public FilmList() {
    }

    public FilmList(Film... films) {
        this.films = Arrays.asList(films);
    }

    public List<Film> getFilms() {
        return films;
    }
}
