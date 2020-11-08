package model;

import java.time.LocalDate;

/**
 * Фильм
 */
public class Movie {

    /** Идентификатор */
    private int id;

    /** Название */
    private String name;

    /** Дата премьеры */
    private LocalDate releaseDate;

    /** Длительность фильма в минутах */
    private int duration;

    /** Рейтинг */
    private double rating;

    /** Имеет ли награды */
    private boolean hasAwards;

    public Movie(String name, int duration) {
        this(name, LocalDate.now(), duration);
    }

    public Movie(String name, LocalDate releaseDate, int duration) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rating = 7.0;
        this.hasAwards = false;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public double getRating() {
        return rating;
    }

    public boolean isHasAwards() {
        return hasAwards;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setHasAwards(boolean hasAwards) {
        this.hasAwards = hasAwards;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", rating=" + rating +
                ", hasAwards=" + hasAwards +
                '}';
    }
}
