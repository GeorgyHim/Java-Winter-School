import java.util.Arrays;

public class Animal {
    private String name;
    private Employee inCharge; // Ответственный за животное сотрудник
    boolean isIll;
    Disease[] diseases;

    public Animal(String name) {
        this.name = name;
        this.inCharge = null;
        this.isIll = false;
        diseases = new Disease[0];
    }

    public String getName() {
        return name;
    }

    public Employee getInCharge() {
        return inCharge;
    }

    public boolean isIll() {
        return isIll;
    }

    public Disease[] getDiseases() {
        return diseases;
    }

    public void setInCharge(Employee inCharge) {
        this.inCharge = inCharge;
    }

    public void becomeInfected(Disease disease) {
        isIll = true;
        diseases = Arrays.copyOf(diseases, diseases.length + 1);
        diseases[diseases.length - 1] = disease;
    }

    public void recover(Disease disease) {
        Disease[] newDiseases = new Disease[diseases.length-1];
        for (int i = 0, j = 0; i < diseases.length; i++) {
            if (diseases[i] != disease) {
                newDiseases[j++] = diseases[i];
            }
        }
        diseases = newDiseases;
        if (diseases.length == 0) {
            isIll = false;
        }
    }
}
