class PrintHi extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++)
            System.out.println("Hi");
    }
}

class PrintHello extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++)
            System.out.println("Hello");
    }
}

class Demo {
    public static void main (String A[]) {
        PrintHi hi = new PrintHi();
        PrintHello hello = new PrintHello();

        hi.setPriority(10);
        hello.setPriority(1);

        System.out.println(hi.getPriority());
        System.out.println(hello.getPriority());

        hi.start();
        hello.start();
    }
}