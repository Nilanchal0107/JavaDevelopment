class Animal {
    public void makeSound() {
        System.out.println("Some animal sound");
    }
}

class Dog extends Animal {
    @Override // I will get compile-time error if I misspell makeSound()
    public void makeSound() {
        System.out.println("Woof!");
    }
}

class Demo {
    public static void main (String A[]) {
        Dog a = new Dog();
        a.makeSound();
    }
}