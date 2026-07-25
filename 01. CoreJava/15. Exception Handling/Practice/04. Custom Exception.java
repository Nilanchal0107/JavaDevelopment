class AgeException extends Exception {
    AgeException (String msg) {
        super(msg);
    }
}

class Demo {
    public static void main (String A[]) {
        int age = -5;

        try {
            if (age < 0) {
                throw new AgeException("Age cannot be negative");
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong." + e);
        }
    }
}