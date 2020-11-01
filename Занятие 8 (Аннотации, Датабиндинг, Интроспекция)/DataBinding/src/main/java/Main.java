import actor.Actor;
import actor.ActorWithRole;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        Actor brad = new Actor("Brad", 55);
        Actor leo = new Actor("Leonardo", 44);
        Film hollywood = new Film("Once upon in a time in Hollywood", "Some trash", new HashMap<Actor, String>() {{
            put(brad, "Stunt double");
            put(leo, "Great actor");
        }});

        Actor tom = new Actor("Tom", 39);
        Film revenant  = new Film("Revenant", "Some trash", new ArrayList<>(Arrays.asList(new ActorWithRole(leo, "good guy"),
                new ActorWithRole(tom, "bad guy"))));

        FilmList filmList = new FilmList(revenant, hollywood);
        DataBinder binder = new DataBinder(SerializationFeature.INDENT_OUTPUT);
        System.out.println(binder.toXml(filmList));

        FilmList filmList1 = binder.fromXml("<films>\n" +
                "   <film>\n" +
                "       <title>Фильм 1</title>\n" +
                "       <description>Описание 1</description>\n" +
                "       <actors>\n" +
                "           <actor name=\"Актер 1\" age=\"30\" role=\"Роль 1\"/>\n" +
                "           <actor name=\"Актер 2\" age=\"23\" role=\"Роль 2\"/>\n" +
                "           <actor name=\"Актер 3\" age=\"40\" role=\"Роль 3\"/>\n" +
                "       </actors>\n" +
                "   </film>\n" +
                "   <film>\n" +
                "       <title>Фильм 2</title>\n" +
                "       <description>Описание 2</description>\n" +
                "       <actors>\n" +
                "           <actor name=\"Актер 4\" age=\"70\" role=\"Роль 4\"/>\n" +
                "           <actor name=\"Актер 2\" age=\"23\" role=\"Роль 5\"/>\n" +
                "           <actor name=\"Актер 3\" age=\"40\" role=\"Роль 6\"/>\n" +
                "       </actors>\n" +
                "   </film>\n" +
                "</films>\n");
        System.out.println(filmList1);
        System.out.println(filmList1.getFilms().get(0).getTitle());
        System.out.println(filmList1.getFilms().get(1).getTitle());
    }
}
