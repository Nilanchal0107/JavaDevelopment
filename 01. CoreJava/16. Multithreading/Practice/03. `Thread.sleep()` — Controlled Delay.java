class Num extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i + 1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                 e.printStackTrace();
            }
        }
    }
}

class Demo {
    public static void main (String A[]) {
        Num num = new Num();
        num.start();
    }
}