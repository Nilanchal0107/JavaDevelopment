class Printer {
    void print(int n) {
        System.out.println("int: " + n);
    }

    void print(String s) {
        System.out.println("String: " + s);
    }
}

class FancyPrinter extends Printer {
    void print(int n) {
        System.out.println("Fancy int: " + n);
    }
}

class Test {
    public static void main (String A[]) {
        FancyPrinter obj = new FancyPrinter();
        obj.print(42);
        obj.print("hello");
    }
}

/* 

the difference between what happened in call 1 vs call 2.

in obj.print(42) is the object of FancyPrinter.
it checks if FancyPrinter has print(int) method or not.
If it has then it runs and if it doesn't have then it will check for parent class.

in obj.print("hello") is the object of FancyPrinter.
FancyPrinter doesn't have method print(String).
It runs its parent method i.e from Printer

*/