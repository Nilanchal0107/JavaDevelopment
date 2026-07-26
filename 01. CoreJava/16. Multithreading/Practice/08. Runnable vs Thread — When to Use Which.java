class AnotherClass { }   // pretend parent class
class TaskWorker extends AnotherClass implements Runnable {
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("Working...");
        }
    }
}

class Demo {
    public static void main (String A[]) {
        Thread t1 = new Thread(new TaskWorker());
        t1.start();
    }
}

// java doesn't support multiple inheritance.
// That's we cant' extends Thread to a child class.