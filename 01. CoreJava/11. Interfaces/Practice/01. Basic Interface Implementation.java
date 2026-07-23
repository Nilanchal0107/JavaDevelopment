interface Printable {
    void print();
}

class PDFPrinter implements Printable {
    public void print() {
        System.out.println("Printing PDF");
    }
}

class ConsolePrinter implements Printable {
    public void print() {
        System.out.println("Printing to Console");
    }
}

class Demo {
    public static void main (String A[]) {
        Printable obj1 = new PDFPrinter();
        obj1.print();

        Printable obj2 = new ConsolePrinter();
        obj2.print();
    }
}