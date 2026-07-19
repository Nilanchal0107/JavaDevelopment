class Animal {
    void breathe() {
        System.out.println("Breathing...");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Woof!");
    }
}

class Test {
    public static void main (String A[]) {
        Dog a = new Dog();
        a.breathe();
        a.bark();
    }
}