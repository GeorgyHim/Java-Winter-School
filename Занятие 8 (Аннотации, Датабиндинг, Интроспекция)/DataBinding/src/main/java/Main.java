import actor.Actor;
import actor.ActorWithRole;
import com.fasterxml.jackson.databind.SerializationFeature;
import film.FilmList;
import film.FilmWithActors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        Actor brad = new Actor("Brad", 55);
        Actor leo = new Actor("Leonardo", 44);
        FilmWithActors onceInHollywood = new FilmWithActors(
                "Once upon in a time in Hollywood", "Some description",
                new HashMap<Actor, String>() {{
                    put(brad, "Stunt double");
                    put(leo, "Great actor");
        }});

        Actor tom = new Actor("Tom", 39);
        FilmWithActors revenant  = new FilmWithActors(
                "Revenant", "Some trash",
                new ArrayList<>(Arrays.asList(new ActorWithRole(leo, "good guy"), new ActorWithRole(tom, "bad guy")))
        );

        DataBinder binder = new DataBinder();

        FilmList filmList = binder.fromXml("<films>\n" +
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
        System.out.println("Первый " + filmList);

        filmList = binder.fromXml("<films></films>\n");
        System.out.println(filmList);

        System.out.println(binder.toXml());
    }
}
