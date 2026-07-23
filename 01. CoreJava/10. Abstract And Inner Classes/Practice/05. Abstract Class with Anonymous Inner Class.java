abstract class Animal {
    abstract void speak();
}

class Demo {
    public static void main (String A[]) {
        Animal cow = new Animal() {
            void speak() {
                System.out.println("Moo!");
            }
        };
        cow.speak();
    }
}