package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import java.io.IOException;

public class XmlConverter {
    /** Преобразователь объектов в XML и обратно */
    private final XmlMapper mapper;

    public XmlConverter() {
        this.mapper = createXmlMapper();
    }

    /**
     * Метод создания XML-преобразователя
     *
     * @return  -   XML-преобразователь
     */
    private XmlMapper createXmlMapper() {
        final XmlMapper mapper = new XmlMapper();
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        mapper.registerModule(module);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    /**
     * Метод сериализации объекта в XML
     *
     * @param obj                       -   Объект
     * @return                          -   XML-строка сериализованного объекта
     * @throws JsonProcessingException  -   Исключение при сериализации
     */
    public String toXml(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    /**
     * Метод десериализации объекта из XML
     *
     * @param xml           -   Строка XML
     * @param cls           -   Класс объекта
     * @param <T>           -   Тип класса
     * @return              -   Объект нужного класса
     * @throws IOException  -   Исключение при десериализации объекта
     */
    public <T> T fromXml(String xml, Class<T> cls) throws IOException {
        return mapper.readValue(xml, cls);
    }
}
