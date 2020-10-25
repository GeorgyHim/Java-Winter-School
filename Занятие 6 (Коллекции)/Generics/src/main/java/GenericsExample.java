public class GenericsExample {

}

class Optional <T> {
    private T value;

    private Optional(T value) {
        this.value = value;
    }

    public static <T> Optional<T> create(T value) {
        return new Optional<>(value);
    }
}

class A {}

class B extends A {}

class C extends B {}