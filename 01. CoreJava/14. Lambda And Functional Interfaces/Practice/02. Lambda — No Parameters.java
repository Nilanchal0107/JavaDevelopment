@FunctionalInterface
interface Greeting {
    void greet();
}

class Demo {
    public static void main (String A[]) {
        Greeting obj =() -> System.out.println("Hello from lambda!");
        obj.greet();
    }
}