class Printer {
    void print() {
        System.out.println("Default printing");
    }
}

class Demo {
    public static void main (String A[]) {
        Printer obj = new Printer() {
            void print() {
                System.out.println("Custom printing");
            }
        };
        obj.print();
    }
}