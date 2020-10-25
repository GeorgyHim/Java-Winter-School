package reception;
import java.time.LocalDate;

public class Review {
    private String authorName;
    private LocalDate date;
    private String linkToText;
    private boolean isPositive;

    public Review() {
        this("");
    }

    public Review(String linkToText) {
        this("", LocalDate.now(), linkToText, false);
    }

    public Review(String authorName, LocalDate date, String linkToText, boolean isPositive) {
        this.authorName = authorName;
        this.date = date;
        this.linkToText = linkToText;
        this.isPositive = isPositive;
    }

    public String getAuthorName() {
        return authorName;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLinkToText() {
        return linkToText;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void changeMind(boolean newIsPositive) {
        isPositive = newIsPositive;
    }
}
