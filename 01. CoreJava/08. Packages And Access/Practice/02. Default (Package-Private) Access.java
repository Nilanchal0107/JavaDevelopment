class Engine {
    int horsepower = 150;
}

class Car {
    void showPower() {
        System.out.println(new Engine().horsepower);
    }
}

class Test {
    public static void main (String A[]) {
        new Car().showPower();
    }
}