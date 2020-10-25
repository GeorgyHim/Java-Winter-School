import java.util.Arrays;

public class Zoo {
    private String title;
    private Animal[] animals;
    private Employee[] employees;

    public Zoo(String title) {
        this(title, new Employee[0]);
    }

    public Zoo(String title, Employee[] employees) {
        this.title = title;
        this.employees = employees;
        this.animals = new Animal[0];
    }

    public String getTitle() {
        return title;
    }

    public Animal[] getAnimals() {
        return animals;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public void passAnimal(Employee empfrom, Employee empto, Animal animal) {
        empto.addAnimal(animal);
        empfrom.deleteAnimal(animal);
    }

    public void passAnimal(Employee empfrom) {
        if (employees.length <= 1) {
            System.out.println("Невозможно осуществить передачу! " +
                    "Это единственный сотрудник!\n");
            return;
        }
        for (Employee employee:employees) {
            if (employee != empfrom) {
                for (Animal animal:empfrom.getAnimalsInTow()) {
                    passAnimal(empfrom, employee, animal);
                }
                break;
            }
        }
    }

    public void fireEmployee(Employee employee) {
        if (employee.getAnimalsInTow().length > 0) {
            System.out.println("Невозможно уволить сотрудника, " +
                    "так как на его попечении находятся животные\n" +
                    "Передайте этих животных другому сотруднику\n");
            return;
        }
        if (employees.length == 0) {
            System.out.println("В зоопарке нет сотрудников!\n");
            return;
        }
        Employee[] newEmployees = new Employee[employees.length-1];
        for (int i = 0, j = 0; i < employees.length; i++) {
            if (employees[i] != employee) {
                newEmployees[j++] = employees[i];
            }
        }
        employees = newEmployees;
    }

    public void hireEmployee(Employee employee) {
        employees = Arrays.copyOf(employees, employees.length + 1);
        employees[employees.length - 1] = employee;
    }

    public void addAnimal(Animal animal, Employee employee) {
        animal.setInCharge(employee);
        employee.addAnimal(animal);
        animals = Arrays.copyOf(animals, animals.length + 1);
        animals[animals.length - 1] = animal;
    }

    public void addAnimal(Animal animal) {
        if (employees.length == 0) {
            System.out.println("Нельзя добавить животное, так как за ним некому ухаживать!\n");
            return;
        }
        animal.setInCharge(employees[employees.length - 1]);
        employees[employees.length - 1].addAnimal(animal);
        animals = Arrays.copyOf(animals, animals.length + 1);
        animals[animals.length - 1] = animal;
    }

    public void deleteAnimal(Animal animal) {
        animal.getInCharge().deleteAnimal(animal);
        Animal[] newAnimals = new Animal[animals.length - 1];
        for (int i = 0, j = 0; i < animals.length; i++) {
            if (animals[i] != animal) {
                newAnimals[j++] = animals[i];
            }
        }
        animals = newAnimals;
    }

    public void Infect(Animal animal, Disease disease) {
        animal.becomeInfected(disease);
    }

    public void Recover(Animal animal, Disease disease) {
        animal.recover(disease);
    }
}
