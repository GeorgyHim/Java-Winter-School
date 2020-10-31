import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
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
    private List<ActorRole> actorsWithRoles;

    public Film() {
    }

    public Film(String title, String description) {
        this(title, description, new HashMap<>());
    }

    public Film(String title, String description, List<ActorRole> actorsWithRoles) {
        this.title = title;
        this.description = description;
        this.actorsWithRoles = actorsWithRoles;
    }

    public Film(String title, String description, Map<Actor, String> actorsWithRoles) {
        this.title = title;
        this.description = description;
        this.actorsWithRoles = actorsWithRoles.entrySet().stream()
                .map(actorStringEntry -> new ActorRole(actorStringEntry.getKey(), actorStringEntry.getValue()))
                .collect(Collectors.toList());
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<ActorRole> actorsWithRoles() {
        return actorsWithRoles;
    }
}
