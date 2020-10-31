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

    @XmlAttribute(name = "name")
    public void setActorName(String name) {
        if (actor == null)
            actor = new Actor();
         actor.setName(name);
    }

    @XmlAttribute(name = "age")
    public int getActorAge() {
        return actor.getAge();
    }

    @XmlAttribute(name = "age")
    public void setActorAge(int age) {
        if (actor == null)
            actor = new Actor();
        actor.setAge(age);
    }

    public ActorRole() {
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
