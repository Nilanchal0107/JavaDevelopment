class Demo {
    public static void main (String A[]) {
        int j = 0;

        try {
            j = 18 / 0;
        }
        catch (ArithmeticException e) {
            System.out.println("Caught exception");
        }
        finally {
            System.out.println("Finally block ran");
        }
    }
}