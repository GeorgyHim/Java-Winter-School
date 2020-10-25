package cinematic;
import plot.*;
import reception.*;

import java.util.Arrays;

public class Film {
    private String title;
    private Cinematographer director;
    private Cinematographer[] writers;
    private Cinematographer[] actors;
    private Screenplay screenplay;
    private double budget; // Бюджет, $ млн
    private double boxOffice; // Кассовые сборы, $ млн
    private Response response; // Реакция на фильм
    private int year;
    private int runningTime; // Длительность
    private Award[] awards;

    public Film() {
        this("");
    }

    public Film(String title) {
        this(title, new Cinematographer(), 0);
    }

    public Film(String title, Cinematographer Director, int year) {
        this(title, Director, new Cinematographer[0], new Cinematographer[0],
                new Screenplay(), 0, 0, new Response(), year, 0, new Award[0]);
    }

    public Film(String title, Cinematographer director, Cinematographer[] writers, Cinematographer[] actors,
                Screenplay screenplay, double budget, double boxOffice, Response response, int year,
                int runningTime, Award[] awards) {
        this.title = title;
        this.director = director;
        this.writers = writers;
        this.actors = actors;
        this.screenplay = screenplay;
        this.budget = budget;
        this.boxOffice = boxOffice;
        this.response = response;
        this.year = year;
        this.runningTime = runningTime;
        this.awards = awards;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public String getTitle() {
        return title;
    }

    public Cinematographer getDirector() {
        return director;
    }

    public Cinematographer[] getWriters() {
        return writers;
    }

    public Cinematographer[] getActors() {
        return actors;
    }

    public Screenplay getScreenplay() {
        return screenplay;
    }

    public double getBudget() {
        return budget;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public Response getResponse() {
        return response;
    }

    public int getYear() {
        return year;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public Award[] getAwards() {
        return awards;
    }

    public double getProfit() {
        return boxOffice - budget;
    }

    public void winAward(Award award) {
        awards = Arrays.copyOf(awards,  awards.length + 1);
        awards[awards.length - 1] = award;
    }
}
