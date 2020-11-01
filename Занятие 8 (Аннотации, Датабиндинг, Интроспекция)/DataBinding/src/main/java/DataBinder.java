import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import film.Film;

import java.io.IOException;
import java.util.List;

public class DataBinder {
    SerializationFeature feature;
    private List<Film> films;

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

    public String toXml(Object obj) throws JsonProcessingException {
        XmlMapper mapper = createXmlMapper();
        return mapper.writeValueAsString(obj);
    }

    public FilmList fromXml(String xml) throws IOException {
        String updatedXml = "<FilmList>\n" + xml + "\n</FilmList>";
        XmlMapper mapper = createXmlMapper();
        return mapper.readValue(updatedXml, FilmList.class);
    }
}
