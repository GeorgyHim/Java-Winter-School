package film;

import actor.Actor;
import actor.ActorWithRole;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@XmlType(propOrder = {"title", "description", "actors"})
public class FilmWithActors {
    @XmlTransient
    private Film film;

    @XmlElementWrapper(name = "actors")
    @XmlElement(name = "actor")
    private List<ActorWithRole> actorsWithRoles;

    @XmlElement(name = "title")
    public String getFilmTitle() {
        return film.getTitle();
    }

    @XmlElement(name = "title")
    public void setFilmTitle(String title) {
        if (film == null)
            film = new Film();
        film.setTitle(title);
    }

    @XmlElement(name = "description")
    public String getFilmDesctiption() {
        return film.getDescription();
    }

    @XmlElement(name = "description")
    public void setFilmDescription(String description) {
        if (film == null)
            film = new Film();
        film.setDescription(description);
    }

    public FilmWithActors() {
    }

    public FilmWithActors(String title, String description, List<ActorWithRole> actorsWithRoles) {
        this.film = new Film(title, description);
        this.actorsWithRoles = actorsWithRoles;
    }

    public FilmWithActors(String title, String description, Map<Actor, String> actorsWithRoles) {
        this.film = new Film(title, description);
        this.actorsWithRoles = actorsWithRoles.entrySet().stream()
                .map(actorStringEntry -> new ActorWithRole(actorStringEntry.getKey(), actorStringEntry.getValue()))
                .collect(Collectors.toList());
    }

    public Film getFilm() {
        return film;
    }

    public List<ActorWithRole> actorsWithRoles() {
        return actorsWithRoles;
    }
}
