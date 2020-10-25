package main;

import cinematic.Award;
import cinematic.Cinematographer;
import cinematic.Film;
import plot.Character;
import plot.Screenplay;
import reception.Response;
import reception.Review;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Cinematographer Ari_Aster = new Cinematographer("Ari Aster", LocalDate.of(1986, 7, 15), "USA", new Award[0]);
        Cinematographer Florence_Pugh = new Cinematographer("Florence Pugh", LocalDate.of(1996, 1, 3), "Great Britain",
                new Award[] {new Award("Oscar", 2020, "Best Supporting Actress")});
        Cinematographer Jack_Reynor = new Cinematographer("Jack Reynor", LocalDate.of(1992, 1, 23), "USA", new Award[0]);
        Character Dani_Ardor = new Character("Dani Ardor", "no link", false);
        Character Christian_Hughes = new Character("Christian Hughes", "no link", true);

        // Описываем фильм Солнцестояние
        Film Midsommar = new Film("Midsommar", Ari_Aster, new Cinematographer[] {Ari_Aster},
                new Cinematographer[] {Florence_Pugh, Jack_Reynor},
                new Screenplay("Midsommar", "no link", new Character[]{Dani_Ardor, Christian_Hughes}), 10, 43.8,
                new Response(7.1, 7.6, 228800, new Review[] {new Review("Anonymous", LocalDate.of(2020, 1, 20), "no link", false),
                        new Review("Roger Ebert", LocalDate.of(2020, 1, 12), "empty link", true)}), 2019, 148, new Award[0]);


        Cinematographer Guy_Ritchie = new Cinematographer("Guy Ritchie", LocalDate.of(1968, 9, 10), "Great Britain", new Award[0]);
        Cinematographer Matthew_McConaughey = new Cinematographer("Matthew McConaughey", LocalDate.of(1969, 10, 4), "USA",
                new Award[] {new Award("Oscar", 2014, "Best Actor")});
        Cinematographer Hugh_Grant = new Cinematographer("Hugh Grant", LocalDate.of(1960, 9, 9), "Great Britain",
                new Award[] {new Award("Golden Globe", 1995, "Best Actor")});
        Character Mickey_Pearson = new Character("Mickey Pearson", "no link", false);
        Character Raymond_Smith = new Character("Raymond Smith", "no link", false);

        // Описываем фильм Джентльмены
        Film The_Gentlemen = new Film("The Gentlemen", Guy_Ritchie, new Cinematographer[] {Guy_Ritchie},
                new Cinematographer[] {Matthew_McConaughey, Hugh_Grant},
                new Screenplay("The Gentlemen", "no link", new Character[]{Mickey_Pearson, Raymond_Smith}), 22, 115,
                new Response(8.1, 6.5, 3820000, new Review[] {new Review("SomeBody", LocalDate.of(2020, 2, 20), "no link", false),
                        new Review("Some Critic", LocalDate.of(2020, 2, 12), "empty link", true)}), 2019, 113, new Award[0]);

        Cinematographer Todd_Phillips = new Cinematographer("Todd Phillips", LocalDate.of(1970, 12, 20), "USA", new Award[0]);
        Todd_Phillips.winAward(new Award("La Biennale di Venezia", 2019, "Golden Lion"));
        Cinematographer Joaquin_Phoenix = new Cinematographer("Joaquin Phoenix", LocalDate.of(1974, 10, 28), "USA",
                new Award[] {new Award("Oscar", 2020, "Best Actor")});
        Cinematographer Robert_De_Niro = new Cinematographer("Robert De Niro", LocalDate.of(1943, 9, 17), "USA",
                new Award[] {new Award("Oscar", 1975, "Best Supporting Actor"), new Award("Oscar", 1981, "Best  Actor")});
        Character Arthur_Fleck = new Character("Arthur Fleck", "no link", false);
        Character Murray_Franklin = new Character("Murray Franklin", "no link", true);

        Film Joker = new Film("Joker", Todd_Phillips, new Cinematographer[] {Todd_Phillips},
                new Cinematographer[] {Joaquin_Phoenix, Robert_De_Niro},
                new Screenplay("Joker", "no link", new Character[] {Arthur_Fleck, Murray_Franklin}), 70, 1074,
                new Response(8.5, 7.2, 7120000, new Review[] {new Review("Anonymous", LocalDate.of(2020, 2, 20), "some link", true),
                        new Review("SomeBody", LocalDate.of(2020, 3, 14), "no link", true)}), 2019, 122, new Award[0]);
        Joker.winAward(new Award("La Biennale di Venezia", 2019, "Golden Lion"));
        Joker.winAward(new Award("Oscar", 2020, "Best Actor"));
    }
}
