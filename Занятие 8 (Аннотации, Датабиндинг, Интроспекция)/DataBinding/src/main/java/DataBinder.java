import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

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

    public <T> T fromXml(String xml,  Class<T> type) throws IOException {
        XmlMapper mapper = createXmlMapper();
        return mapper.readValue(xml, type);
    }
}
