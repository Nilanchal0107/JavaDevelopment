abstract class Vehicle {
    abstract void move();
}

class Car extends Vehicle {
    void move() {
        System.out.println("Car is moving");
    }
}

class Demo {
    public static void main (String A[]) {
        // new Vehicle()  It will cause compile error because we can't create object of abstract class;

        Vehicle v = new Car();
        v.move();
    }
}
