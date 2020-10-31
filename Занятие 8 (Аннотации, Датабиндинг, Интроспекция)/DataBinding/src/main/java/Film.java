import java.util.ArrayList;
import java.util.List;

public class Film {

    private String title;

    private String description;

    private List<Actor> actors;

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
