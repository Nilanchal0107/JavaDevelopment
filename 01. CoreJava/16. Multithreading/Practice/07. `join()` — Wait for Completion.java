
class Demo {
    public static void main (String A[]) throws InterruptedException {
        Runnable a = () -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("Thread A Working");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable b = () -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("Thread B Working");
            }
        };

        Thread t1 = new Thread(a);
        Thread t2 = new Thread(b);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Both threads done");
        
    }
}