class MathOps {
    int multiply (int a, int b) {
        return a *  b;
    }

    int multiply (int a, int b, int c) {
        return a * b * c;
    }

    double multiply (double a, double b) {
        return a * b;
    }
}

class Overloaded {
    public static void main (String A[]) {
        MathOps op = new MathOps();

        System.out.println(op.multiply(5, 6));
        System.out.println(op.multiply(5, 6, 7));
        System.out.println(op.multiply(5.5, 6.5));
    }
}