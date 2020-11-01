package film;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"title", "role"})
public class FilmWithRole {
    @XmlTransient
    private Film film;

    @XmlAttribute
    String role;

    @XmlAttribute(name = "title")
    public String getFilmTitle() {
        return film.getTitle();
    }
}
