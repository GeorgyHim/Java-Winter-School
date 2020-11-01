package film;

import actor.Actor;
import actor.ActorWithRole;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@XmlRootElement(name = "film")
public class Film {

    @XmlElement
    private String title;

    @XmlElement
    private String description;

    @XmlElementWrapper(name = "actors")
    @XmlElement(name = "actor")
    private List<ActorWithRole> actorsWithRoles;

    public Film() {
    }

    public Film(String title, String description) {
        this(title, description, new HashMap<>());
    }

    public Film(String title, String description, List<ActorWithRole> actorsWithRoles) {
        this.title = title;
        this.description = description;
        this.actorsWithRoles = actorsWithRoles;
    }

    public Film(String title, String description, Map<Actor, String> actorsWithRoles) {
        this.title = title;
        this.description = description;
        this.actorsWithRoles = actorsWithRoles.entrySet().stream()
                .map(actorStringEntry -> new ActorWithRole(actorStringEntry.getKey(), actorStringEntry.getValue()))
                .collect(Collectors.toList());
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<ActorWithRole> actorsWithRoles() {
        return actorsWithRoles;
    }
}
