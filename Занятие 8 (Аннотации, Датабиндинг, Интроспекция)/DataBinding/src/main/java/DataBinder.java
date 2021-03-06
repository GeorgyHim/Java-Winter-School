import actor.Actor;
import actor.ActorList;
import actor.ActorWithFilms;
import actor.ActorWithRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import film.FilmList;
import film.FilmWithActors;
import film.FilmWithRole;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DataBinder {
    private FilmList filmList;

    private XmlMapper createXmlMapper() {
        final XmlMapper mapper = new XmlMapper();
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        mapper.registerModule(module);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    public FilmList fromXml(String xml) throws IOException {
        String updatedXml = "<FilmList>\n" + xml + "\n</FilmList>";
        XmlMapper mapper = createXmlMapper();
        filmList = mapper.readValue(updatedXml, FilmList.class);
        if (filmList == null || filmList.getFilms() == null)
            filmList = new FilmList();
        return filmList;
    }

    private ActorList toActorList() {
        // Реорганизуем входные данные в структуру вида (актер - список фильмов с ролями)
        Map<Actor, List<FilmWithRole>> actorsWithFilmsMap = new HashMap<>();
        for (FilmWithActors filmWithActors : filmList.getFilms()) {
            for (ActorWithRole actorWithRole : filmWithActors.getActorsWithRoles()) {
                FilmWithRole filmWithRole = new FilmWithRole();
                filmWithRole.setFilm(filmWithActors.getFilm());
                filmWithRole.setRole(actorWithRole.getRole());

                actorsWithFilmsMap.putIfAbsent(actorWithRole.getActor(), new ArrayList<>());
                actorsWithFilmsMap.get(actorWithRole.getActor()).add(filmWithRole);
            }
        }

        // Преобразуем мап в объект ActorList
        ActorList actorList = new ActorList();
        for (Map.Entry<Actor, List<FilmWithRole>> entry :
                actorsWithFilmsMap.entrySet().stream()
                        .sorted(Comparator.comparing(actorListEntry -> actorListEntry.getKey().getName()))
                        .collect(Collectors.toList())) {
            ActorWithFilms actorWithFilms = new ActorWithFilms(entry.getKey(), entry.getValue());
            actorList.add(actorWithFilms);
        }
        return actorList;
    }

    public String toXml() throws JsonProcessingException {
        XmlMapper mapper = createXmlMapper();
        String xml = mapper.writeValueAsString(toActorList());
        return xml.replace("<ActorList>\r\n  ", "").replace("\r\n</ActorList>\r\n", "").replace("\r\n  ", "\r\n");
    }
}
