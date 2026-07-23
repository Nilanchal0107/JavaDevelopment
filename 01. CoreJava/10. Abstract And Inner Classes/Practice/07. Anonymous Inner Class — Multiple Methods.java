abstract class Worker {
    abstract void doWork();
    abstract void getReport();
}

class Demo {
    public static void main (String A[]) {
        Worker obj = new Worker() {
            void doWork() {
                System.out.println("Working hard...");
            }
            void getReport() {
                System.out.println("Report: All done");
            }
        };
        obj.doWork();
        obj.getReport();
    }
}