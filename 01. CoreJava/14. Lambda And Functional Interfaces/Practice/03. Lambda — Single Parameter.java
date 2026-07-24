@FunctionalInterface
interface Printer {
    void print(String msg);
}

class Demo {
    public static void main (String A[]) {
        Printer obj = msg -> System.out.println(msg);
        obj.print("Learning Java Lambdas");
    }
}