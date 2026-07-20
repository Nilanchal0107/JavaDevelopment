class Animal {
    public void breathe() {
        System.out.println("Breathing...");
    }
}

class Dog extends Animal {
    public void bark() {
        System.out.println("Woof!");
    }
}

public class Upcasting {
    public static void main (String A[]) {
        Animal obj = new Dog();
        obj.breathe();

        // obj.bark();
        // This will not work because Animal is a parent class and it doesn't knows about its child Dog.
        // A child can inherits parent's method but vice-versa is not true
    }
}