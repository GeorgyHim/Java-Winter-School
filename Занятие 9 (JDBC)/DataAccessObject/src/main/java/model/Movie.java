package model;

import java.time.LocalDate;

/**
 * Фильм
 */
public class Movie {

    /** Счетчик фильмов */
    private static int count = 0;

    /** Идентификатор */
    private int id;

    /** Название */
    private String title;

    /** Дата премьеры */
    private LocalDate releaseDate;

    /** Длительность фильма в минутах */
    private int duration;

    /** Рейтинг */
    private double rating;

    /** Имеет ли награды */
    private boolean hasAwards;

    public Movie(String title, int duration) {
        this(title, LocalDate.now(), duration);
    }

    public Movie(String title, LocalDate releaseDate, int duration) {
        this.id = ++count;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rating = 7.0;
        this.hasAwards = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public boolean hasAwards() {
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
                "name='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", rating=" + rating +
                ", hasAwards=" + hasAwards +
                '}';
    }
}
