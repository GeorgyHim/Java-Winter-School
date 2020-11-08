package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Фильм
 */
public class Movie {

    /** Счетчик фильмов */
    public static int count = 0;

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

    /** Конструктор для создания объектов в коде */
    public Movie(String title, int duration) {
        this(++count, title, LocalDate.now(), duration, 7.0, false);
    }

    /** Конструктор для создания объектов при загрузке из базы  */
    public Movie(int id, String title, LocalDate releaseDate, int duration, double rating, boolean hasAwards) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rating = rating;
        this.hasAwards = hasAwards;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                duration == movie.duration &&
                Double.compare(movie.rating, rating) == 0 &&
                hasAwards == movie.hasAwards &&
                Objects.equals(title, movie.title) &&
                Objects.equals(releaseDate, movie.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseDate, duration, rating, hasAwards);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", rating=" + rating +
                ", hasAwards=" + hasAwards +
                '}';
    }
}
