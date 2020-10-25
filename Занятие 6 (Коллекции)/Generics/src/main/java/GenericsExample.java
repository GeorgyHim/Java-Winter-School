public class GenericsExample {
    public void some() {
        Optional<? extends B> v1 = Optional.create(new A());    // CE - A not extends B
        Optional<? extends B> v2 = Optional.create(new B());
        Optional<? extends B> v3 = Optional.create(new C());

        Object o = v2.getValue();
        A a = v2.getValue();
        B b = v2.getValue();
        C c = v2.getValue();    // CE - тип = что-то (?) расширяющее В, мб это не С
        C c2 = v3.getValue();   // То же самое - тип Optionala указывается в <> и не зависит от того, объект какого типа мы передаем в качестве параметра
    }

    public void Other() {
        Optional<? super B> v1 = Optional.create(new A());
        Optional<? super B> v2 = Optional.create(new B());
        Optional<? super B> v3 = Optional.create(new C());
        Object o = v2.getValue();
        A a = v2.getValue();    // Там мб какой-то другой супер-класс для В, мб не А. В целом это считается Object
        B b = v2.getValue();
        C c = v2.getValue();
        C c2 = v3.getValue();

    }

}

class Optional <T> {
    private T value;

    private Optional(T value) {
        this.value = value;
    }

    public static <T> Optional<T> create(T value) {
        return new Optional<>(value);
    }

    public T getValue() {
        return value;
    }
}

class A {}

class B extends A {}

class C extends B {}