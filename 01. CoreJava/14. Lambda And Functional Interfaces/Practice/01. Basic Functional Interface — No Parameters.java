@FunctionalInterface
interface Greeting {
    void greet();
}

class Demo {
    public static void main (String A[]) {
        Greeting obj = new Greeting() {
            public void greet() {
                System.out.println("Hello from anonymous class!");
            }
        };
        obj.greet();
    }
}