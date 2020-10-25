package plot;

import java.util.Arrays;

public class Screenplay {
    private String title;
    private String linkToText;
    private Character[] characters;

    public Screenplay() {
        this("");
    }

    public Screenplay(String linkToText) {
        this("", linkToText, new Character[0]);
    }

    public Screenplay(String title, String linkToText, Character[] characters) {
        this.title = title;
        this.linkToText = linkToText;
        this.characters = characters;
    }

    public String getTitle() {
        return title;
    }

    public String getLinkToText() {
        return linkToText;
    }

    public Character[] getCharacters() {
        return characters;
    }

    public void addCharacter(Character character) {
        characters = Arrays.copyOf(characters,  characters.length + 1);
        characters[characters.length - 1] = character;
    }
}
