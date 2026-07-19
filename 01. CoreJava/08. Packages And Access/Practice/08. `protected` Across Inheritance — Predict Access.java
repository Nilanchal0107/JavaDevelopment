class Vehicle {
    protected int speed = 120;
}

class Truck extends Vehicle {
    void showSpeed() {
        System.out.println(super.speed);
    }
}

class Demo {
    public static void main (String A[]) {
        new Truck().showSpeed();
        System.out.println(new Vehicle().speed);
    }
}

/* 

"If `Truck` were in a different package, could it still access `speed`
Yes since protected variables are accessible as sub class even though they are from different package

*/