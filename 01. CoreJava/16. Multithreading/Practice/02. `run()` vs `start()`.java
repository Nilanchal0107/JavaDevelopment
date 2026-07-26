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

        hi.run();
        hello.run();
    }
}

// start() starts both the thread at the same time. That is start() makes runnable threads into running state at the same time.
// run() acts like function call. It makes threads run sequential.