package film;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

public class FilmList {

    @XmlElementWrapper(name = "films")
    @XmlElement(name = "film")
    private List<FilmWithActors> films;

    public FilmList() {
    }

    public FilmList(FilmWithActors... films) {
        this.films = Arrays.asList(films);
    }

    public List<FilmWithActors> getFilms() {
        return films;
    }
}
