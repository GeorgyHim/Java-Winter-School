import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        Actor brad = new Actor("Brad", 55);
        Actor leo = new Actor("Leonardo", 44);

        Film hollywood = new Film("Once upon in a time in Hollywood", "Some trash", new ArrayList<>(Arrays.asList(brad, leo)));

        DataBinder binder = new DataBinder(SerializationFeature.INDENT_OUTPUT);
        System.out.println(binder.toXml(hollywood));
    }
}
