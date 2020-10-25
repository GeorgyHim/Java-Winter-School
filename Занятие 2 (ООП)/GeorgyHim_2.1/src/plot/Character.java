package plot;

public class Character {
    private String name;
    private String linkToDescription;
    private boolean willDie;

    public Character() {
        this("");
    }

    public Character(String name) {
        this(name, "", false);
    }

    public Character(String name, String linkToDescription, boolean willDie) {
        this.name = name;
        this.linkToDescription = linkToDescription;
        this.willDie = willDie;
    }

    public String getName() {
        return name;
    }

    public String getLinkToDescription() {
        return linkToDescription;
    }

    public boolean isWillDie() {
        return willDie;
    }

    public void changeCharacterFate(boolean newWillDie) {
        willDie = newWillDie;
    }
}
