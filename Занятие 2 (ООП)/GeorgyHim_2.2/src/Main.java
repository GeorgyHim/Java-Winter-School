import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Zoo zoo = new Zoo("Safari");
        zoo.addAnimal(new Animal("cat")); // Нельзя добавить, выдаст сообщение
        for (int i = 0; i <= 5; i++) { // Нанимаем сотрудников
            zoo.hireEmployee(new Employee("Oleg" + i));
        }

        for (int i = 0; i <= 3; i++)
            zoo.addAnimal(new Animal("cat" + i)); // Добавляем животных, приписываем Oleg5

        Employee Oleg5 = zoo.getEmployees()[zoo.getEmployees().length - 1];
        zoo.fireEmployee(Oleg5); // Не можем уволить Oleg5, т.к. у него животные
        zoo.passAnimal(Oleg5); // Передаем животных от Oleg5
        zoo.fireEmployee(Oleg5); // Не можем уволить Oleg5, т.к. у него животные

        for (int i = 0; i <= 2; i++)
            zoo.addAnimal(new Animal("dragon" + i)); // Добавляем животных, приписываем Oleg4

        Employee georgy = new Employee("Georgy");
        zoo.hireEmployee(georgy);
        zoo.addAnimal(new Animal("bear"), georgy); // Добавили медведя конкретно к Георгию

        Animal dragon0 = zoo.getAnimals()[4];
        Animal cat0 = zoo.getAnimals()[0];
        zoo.Infect(dragon0, new Disease("lupus")); // Заразили дракона0 волчанкой
        zoo.Infect(cat0, new Disease("cancer", LocalDate.of(2005, 1, 1))); // cat0 болеет раком
        zoo.Infect(cat0, new Disease("COVID-19")); // cat0 заболел коронавирусом
        zoo.deleteAnimal(dragon0); // dragon0 умер
        for (Disease disease:cat0.getDiseases()) {
            cat0.recover(disease); // cat0 вылечивается от всего
        }
    }
}
