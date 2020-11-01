package actor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActorList {

    @XmlElementWrapper(name = "actors")
    @XmlElement(name = "actor")
    private List<ActorWithFilms> actors;

    public ActorList() {
        this.actors  = new ArrayList<>();
    }

    public void add(ActorWithFilms actorWithFilms) {
        actors.add(actorWithFilms);
    }

    public List<ActorWithFilms> getActors() {
        return actors;
    }
}
