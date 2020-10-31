import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Film {

    @XmlElement
    private String title;

    @XmlElement
    private String description;

    @XmlElementWrapper(name = "actors")
    @XmlElement
    private List<Actor> actors;
    // private Map<Actor, String> actorsWithRoles;

    public Film(String title, String description) {
        this(title, description, new ArrayList<>());
    }

    public Film(String title, String description, List<Actor> actors) {
        this.title = title;
        this.description = description;
        this.actors = actors;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Actor> getActors() {
        return actors;
    }
}
