package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import model.Flight;
import model.helper.FlightList;

import java.io.IOException;
import java.util.List;

public class XmlConverter {
    /** Преобразователь объектов в XML и обратно */
    private final XmlMapper mapper;

    public XmlConverter(boolean setIndentOutput) {
        this.mapper = createXmlMapper();
        if (setIndentOutput) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
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
        return mapper;
    }

    /**
     * Метод сериализации объекта {@link FlightList} в XML
     *
     * @param flightList                -   Объект со списком рейсов {@link FlightList}
     * @return                          -   XML-строка сериализованного объекта
     * @throws JsonProcessingException  -   Исключение при сериализации
     */
    public String toXml(FlightList flightList) throws JsonProcessingException {
        return mapper.writeValueAsString(flightList);
    }

    /**
     * Метод сериализации списка объектов {@link Flight}  в XML
     *
     * @param flights                   -   Список рейсов
     * @return                          -   XML-строка сериализованного объекта
     * @throws JsonProcessingException  -   Исключение при сериализации
     */
    public String toXml(List<Flight> flights) throws JsonProcessingException {
        return toXml(new FlightList(flights));
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
