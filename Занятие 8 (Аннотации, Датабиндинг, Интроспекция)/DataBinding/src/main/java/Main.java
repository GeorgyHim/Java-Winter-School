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
            put(brad, "Stunt double");
            put(leo, "Great actor");
        }});

        Actor tom = new Actor("Tom", 39);
        Film revenant  = new Film("Revenant", "Some trash", new ArrayList<>(Arrays.asList(new ActorRole(leo, "good guy"),
                new ActorRole(tom, "bad guy"))));

        FilmList filmList = new FilmList(revenant, hollywood);
        DataBinder binder = new DataBinder(SerializationFeature.INDENT_OUTPUT);
        System.out.println(binder.toXml(filmList));

        FilmList filmList1 = binder.fromXml("Смотри Film.xml");
        System.out.println(filmList1);
        System.out.println(filmList1.getFilms().get(0).getTitle());
        System.out.println(filmList1.getFilms().get(1).getTitle());
    }
}
