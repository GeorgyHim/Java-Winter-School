import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "age", "role"})
public class ActorRole {

    @XmlTransient
    Actor actor;

    @XmlAttribute
    String role;

    @XmlAttribute(name = "name")
    public String getActorName() {
        return actor.getName();
    }

    @XmlAttribute(name = "age")
    public int getActorAge() {
        return actor.getAge();
    }

    public ActorRole(Actor actor, String role) {
        this.actor = actor;
        this.role = role;
    }

    public Actor getActor() {
        return actor;
    }

    public String getRole() {
        return role;
    }
}
