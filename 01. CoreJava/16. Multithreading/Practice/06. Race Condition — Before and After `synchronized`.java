class Counter {
    int count;

    // Without synchronized: ran multiple times, got inconsistent results like 16569, 16569, 18995
    // (race condition — two threads incrementing count at the same time can overwrite each other's update)
    // With synchronized: always exactly 20000
    public synchronized void increment() {
        count++;
    }
}

class Demo {
    public static void main (String A[]) throws InterruptedException {
        Counter c = new Counter();

        Runnable a = () -> {
            for (int i = 0; i < 10000; i++)
                c.increment();
        };

        Runnable b = () -> {
            for (int i = 0; i < 10000; i++)
                c.increment();
        };

        Thread t1 = new Thread(a);
        Thread t2 = new Thread(b);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(c.count);
        
    }
}
