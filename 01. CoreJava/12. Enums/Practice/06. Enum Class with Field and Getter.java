enum Planet {
    Mercury(3.7), Earth(9.8), Mars(3.7);

    private double gravity;

    private Planet(double gravity) {
        this.gravity = gravity;
    }

    public double getGravity() {
        return gravity;
    }
}

class Demo {
    public static void main (String A[]) {
        Planet[] ss = Planet.values();

        for (Planet s : ss) {
            System.out.println(s.name() + " : " + s.getGravity());
        }
    }
}