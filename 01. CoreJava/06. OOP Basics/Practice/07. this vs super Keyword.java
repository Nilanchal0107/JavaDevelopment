class Vehicle {
    int speed = 100;
}

class Car extends Vehicle {
    int speed = 200;

    void printSpeeds() {
        int speed = 300;
        System.out.println("Local speed: " + speed);
        System.out.println("Car speed: " + this.speed);
        System.out.println("Vehicle speed: " + super.speed);
    }
}

class Test {
    public static void main (String A[]) {
        Car obj = new Car();
        obj.printSpeeds();
    }
}