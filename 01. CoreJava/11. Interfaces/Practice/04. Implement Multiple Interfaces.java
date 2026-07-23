interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

class Duck implements Flyable, Swimmable {
    public void fly() {
        System.out.println("Duck is flying");
    }
    public void swim() {
        System.out.println("Duck is swimming");
    }
}

class Demo {
    public static void main(String A[]) {
        Duck obj = new Duck();
        obj.fly();
        obj.swim();
    }
}