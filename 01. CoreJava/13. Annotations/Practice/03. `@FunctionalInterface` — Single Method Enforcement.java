@FunctionalInterface
interface Greetable {
    void greet(String name);
}

class Demo {
    public static void main (String A[])  {
        Greetable obj = new Greetable() {
            public void greet(String name) {
                System.out.println("Hello, " + name + "!");
            }
        };
        obj.greet("Navin");
    }
}

// Functional interface is also known as SAM that is Single Abstract Method Interface, it will give compile error if you add another abstract method