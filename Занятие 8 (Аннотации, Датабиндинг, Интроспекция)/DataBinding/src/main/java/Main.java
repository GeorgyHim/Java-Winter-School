import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Actor brad = new Actor("Brad", 55);
        Actor leo = new Actor("Leonardo", 44);
        Film hollywood = new Film("Once upon in a time in Hollywood", "Some trash", new HashMap<Actor, String>() {{
            put(brad, "bad guy");
            put(leo, "good guy");
        }});

        Actor tom = new Actor("Tom", 39);
        Film revenant  = new Film("Revenant", "Some trash", new ArrayList<>(Arrays.asList(new ActorRole(leo, "good guy"),
                new ActorRole(tom, "bad guy"))));


        DataBinder binder = new DataBinder(null);
        System.out.println(binder.toXml(revenant));

        Film film = binder.fromXml("<film><title>Revenant</title><description>Some trash</description><actors><actor name=\"Leonardo\" age=\"44\" role=\"good guy\"/><actor name=\"Tom\" age=\"39\" role=\"bad guy\"/></actors></film>",
                Film.class);
        System.out.println(film);
    }
}
