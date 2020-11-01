import actor.ActorList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import film.FilmList;

import java.io.IOException;

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
    }

    public String toXml() throws JsonProcessingException {
        XmlMapper mapper = createXmlMapper();
        return mapper.writeValueAsString(toActorList());
    }
}
