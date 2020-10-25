package cinematic;

public class Award {
    private String title;
    private int year;
    private String category;

    public Award() {
        this("");
    }

    public Award(String title) {
        this(title, 0);
    }

    public Award(String title, int year) {
        this(title,  year, "");
    }

    public Award(String title, int year, String category) {
        this.title = title;
        this.year = year;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }
}
