public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 3; i++)
                System.out.println("T1: " + i);
        });
        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 3; i++)
                System.out.println("T2: " + i);
        });

        t1.start();
        t1.join();     // main waits for t1 to finish

        t2.start();
        t2.join();     // main waits for t2 to finish

        System.out.println("Done");
    }
}

/*

T1: 1
T1: 2
T1: 3
T2: 1
T2: 2
T2: 3
Done

1. Is the output order guaranteed here? Why?
join() here does guarantee order: t1.start() followed by t1.join() means main waits for t1 to completely finish before even calling t2.start(). 
So t1's three prints (T1: 1, T1: 2, T1: 3) will always print in order and always complete before t2 even starts. 
Then t2 runs to completion before "Done" prints.

2. What would change if you removed both `join()` calls?
If we remove join() then Done will be print randomly that is at start, or at middle or at end.

3. Would `"Done"` always be the last line without `join()`?
No, the prinitng of "Done" is unpredictable or random.

*/