abstract class Car {
    abstract void drive();

    abstract void fly();

    void playMusic() {
        System.out.println("Music playing");
    }
}

abstract class WagnoR extends Car {
    void drive() {
        System.out.println("Driving WagonR");
    }
}

class FlyingWagnoR extends WagnoR {
    void fly() {
        System.out.println("Flying WagonR");
    }
}

class Demo {
    public static void main (String A[]) {
        Car obj = new FlyingWagnoR();
        obj.drive();
        obj.fly();
        obj.playMusic();
    }
}