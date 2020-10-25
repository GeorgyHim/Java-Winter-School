import java.util.Arrays;

public class Employee {
    private String name;
    private Animal[] animalsInTow; // Животные на попечении

    public Employee(String name) {
        this.name = name;
        animalsInTow = new Animal[0];
    }

    public String getName() {
        return name;
    }

    public Animal[] getAnimalsInTow() {
        return animalsInTow;
    }

    public void addAnimal(Animal animal) {
        animalsInTow = Arrays.copyOf(animalsInTow, animalsInTow.length + 1);
        animalsInTow[animalsInTow.length - 1] = animal;
    }

    public void deleteAnimal(Animal animal) {
        Animal[] newAnimals = new Animal[animalsInTow.length-1];
        for (int i = 0, j = 0; i < animalsInTow.length; i++) {
            if (animalsInTow[i] != animal) {
                newAnimals[j++] = animalsInTow[i];
            }
        }
        animalsInTow = newAnimals;
    }
}
