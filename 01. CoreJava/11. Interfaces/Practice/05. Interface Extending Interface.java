interface X {
    void run();
}

interface Y extends X {
    void jump();
}

class Athlete implements Y {
    public void run() {
        System.out.println("Running...");
    }
    public void jump() {
        System.out.println("Jumping!");
    }
}

class Demo {
    public static void main (String A[]) {
        Athlete navin = new Athlete();
        navin.run();
        navin.jump();
    }
}