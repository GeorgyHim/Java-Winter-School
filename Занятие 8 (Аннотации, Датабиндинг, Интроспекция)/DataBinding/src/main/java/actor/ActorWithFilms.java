package actor;

import film.FilmWithRole;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"name", "age", "films"})
public class ActorWithFilms {

    @XmlTransient
    private Actor actor;

    @XmlElementWrapper(name = "films")
    @XmlElement(name = "film")
    private List<FilmWithRole> filmsWithRoles = new ArrayList<>();

    @XmlElement(name = "name")
    public String getActorName() {
        return actor.getName();
    }

    @XmlElement(name = "age")
    public int getActorAge() {
        return actor.getAge();
    }

    public ActorWithFilms(Actor actor, List<FilmWithRole> filmsWithRoles) {
        this.actor = actor;
        this.filmsWithRoles = filmsWithRoles;
    }
}
