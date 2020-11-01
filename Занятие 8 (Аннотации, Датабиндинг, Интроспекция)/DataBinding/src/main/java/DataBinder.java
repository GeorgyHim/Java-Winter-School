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

public class DataBinder {
    SerializationFeature feature;
    private FilmList filmList;

    public DataBinder(SerializationFeature feature) {
        this.feature = feature;
    }

    private XmlMapper createXmlMapper() {
        final XmlMapper mapper = new XmlMapper();
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        mapper.registerModule(module);
        if (feature != null)
            mapper.enable(feature);
        return mapper;
    }

    public FilmList fromXml(String xml) throws IOException {
        String updatedXml = "<film.FilmList>\n" + xml + "\n</film.FilmList>";
        XmlMapper mapper = createXmlMapper();
        filmList = mapper.readValue(updatedXml, FilmList.class);
        return filmList;
    }

    private ActorList toActorList() {
        // TODO: Прописать equals и hashcode в Actor
        // TODO: For-ом пройтись по списку в filmList, и для каждого актера в мап положить список его фильмов
        ActorList actorList = new ActorList();

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
        for (Map.Entry<Actor, List<FilmWithRole>> entry : actorsWithFilmsMap.entrySet()) {
            ActorWithFilms actorWithFilms = new ActorWithFilms(entry.getKey(), entry.getValue());
            actorList.add(actorWithFilms);
        }
        return actorList;
    }

    public String toXml() throws JsonProcessingException {
        XmlMapper mapper = createXmlMapper();
        return mapper.writeValueAsString(toActorList());
    }
}
