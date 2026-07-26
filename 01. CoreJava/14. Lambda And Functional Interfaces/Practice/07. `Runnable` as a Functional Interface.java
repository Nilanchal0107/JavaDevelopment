class Demo {
    public static void main (String A[]) {
        Runnable a = () -> {
            for (int i = 0; i < 5; i++) 
                System.out.println(i + 1);
        };

        Thread t1 = new Thread(a);
        t1.start();

        Runnable b = () -> {
            for (int i = 0; i < 5; i++) 
                System.out.println("Hello");
        };

        Thread t2 = new Thread(b);
        t2.start();

    }
}