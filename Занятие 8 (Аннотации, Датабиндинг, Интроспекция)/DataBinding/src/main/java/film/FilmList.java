package film;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilmList {

    @XmlElementWrapper(name = "films")
    @XmlElement(name = "film")
    private List<FilmWithActors> films;

    public FilmList() {
        this.films = new ArrayList<>();
    }

    public List<FilmWithActors> getFilms() {
        return films;
    }
}
