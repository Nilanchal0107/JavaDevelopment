@FunctionalInterface
interface Runnable2 {
    void execute();
}

class Demo {
    public static void main (String A[]) {
        Runnable2 r1 = new Runnable2() {
            public void execute() {
                System.out.println("Task executed");
            }
        };
        Runnable2 r2 = () -> System.out.println("Lambda task");

        r1.execute();
        r2.execute();
    }
}