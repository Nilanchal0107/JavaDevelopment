enum Laptop {
    XPS;
}

class Demo {
    public static void main (String A[]) {
        Laptop lap = Laptop.XPS;

        System.out.println(lap.name());
        System.out.println(lap.ordinal());
    }
}