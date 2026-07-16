class Greeter {
    void greet (String name) {
        System.out.println("Hello, " + name + "! Welcome to OOP in Java.");
    }
}

class Greet {
    public static void main (String A[]) {
        Greeter hello = new Greeter();
        hello.greet("Nilanchal");
    }
}
