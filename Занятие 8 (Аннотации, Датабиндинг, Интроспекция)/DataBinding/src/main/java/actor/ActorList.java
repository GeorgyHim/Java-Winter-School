package actor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Arrays;
import java.util.List;

public class ActorList {

    @XmlElementWrapper(name = "actors")
    @XmlElement(name = "actor")
    private List<ActorWithFilms> actors;

    public ActorList(ActorWithFilms... actors) {
        this.actors = Arrays.asList(actors);
    }

    public ActorList(List<ActorWithFilms> actors) {
        this.actors = actors;
    }
}
