class Animal {
    Animal(String type) {
        System.out.println("Animal: " + type);
    }
}

class Dog extends Animal {
    Dog(String name) {
        super("Mammal");
        System.out.println("Dog: " + name);
    }
}

class Test {
    public static void main (String A[]) {
        new Dog("Bruno");
    } 
}