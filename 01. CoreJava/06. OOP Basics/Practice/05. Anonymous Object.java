class Printer {
    void print(String msg) {
        System.out.println(msg);
    }
}

class Test {
    public static void main (String A[]) {
        new Printer().print("Hello from anonymous object");
    }
}