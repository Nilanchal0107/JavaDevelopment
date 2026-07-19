class Animal {
    protected String sound = "...";
}

class Dog extends Animal {
    void makeSound() {
        sound = "Woof";
        System.out.println(sound);
    }
}

class Test {
    public static void main (String A[]) {
        new Dog().makeSound();
    }
}