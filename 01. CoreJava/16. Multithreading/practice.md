# 🧪 Practice Questions — Multithreading

> Topics covered: Thread definition, `extends Thread`, `run()` vs `start()`, output interleaving, `Thread.sleep(millis)`, `InterruptedException`, thread priority (`setPriority`, `getPriority`, MIN/NORM/MAX_PRIORITY), `implements Runnable`, lambda for Runnable, `Thread(Runnable)`, race condition, `synchronized`, `join()`, thread states (New, Runnable, Running, Waiting, Dead)

---

## 🟢 Easy (5 Questions)

---

### Q1. First Thread — `extends Thread`
Create class `PrintHi extends Thread` with `run()` that prints `"Hi"` 5 times.  
Create class `PrintHello extends Thread` with `run()` that prints `"Hello"` 5 times.  
In `main`, create objects and call `start()` on both.

**Expected Output (order varies):**
```
Hi
Hello
Hi
Hello
...
```

**Concepts:** `extends Thread`, `run()`, `start()` launches new thread

---

### Q2. `run()` vs `start()`
Take the same two thread classes from Q1.  
Call `.run()` instead of `.start()` on both.  
In comments, explain: why does the output always print all `"Hi"` first, then all `"Hello"` (sequential instead of concurrent)?

**Expected Output:**
```
Hi
Hi
Hi
Hi
Hi
Hello
Hello
Hello
Hello
Hello
```

**Concepts:** `run()` = sequential call on main thread; `start()` = actual new thread creation

---

### Q3. `Thread.sleep()` — Controlled Delay
Create a thread that prints numbers 1 to 5, sleeping 200ms between each print.  
In `main`, start the thread and let it complete.

**Expected Output (with 200ms gaps):**
```
1
2
3
4
5
```

**Concepts:** `Thread.sleep(millis)`, `InterruptedException` must be caught in `run()`

---

### Q4. `Runnable` with Lambda
Create two `Runnable` lambdas:
- First prints `"Hi"` 5 times with 100ms sleep between each
- Second prints `"Hello"` 5 times with 100ms sleep between each

Wrap each in a `Thread` and start both.

**Expected Output (interleaved, order varies):**
```
Hi
Hello
Hi
Hello
...
```

**Concepts:** `Runnable` as functional interface, lambda for thread task, `new Thread(runnable)`

---

### Q5. Thread Priority
Create two threads. Set one to `Thread.MAX_PRIORITY` and the other to `Thread.MIN_PRIORITY`.  
Print each thread's priority using `getPriority()` before starting.

**Expected Output:**
```
10
1
```

(followed by interleaved output from both threads)

**Concepts:** `setPriority()`, `getPriority()`, `Thread.MAX_PRIORITY`, `Thread.MIN_PRIORITY`

---

## 🟡 Medium (3 Questions)

---

### Q6. Race Condition — Before and After `synchronized`
Create class `Counter` with `int count` and `void increment()`.  
Create two `Runnable` lambdas that each call `c.increment()` 10,000 times.  
Use `join()` to wait for both threads, then print `c.count`.

**Part A — Without `synchronized`:**  
Observe that the result is NOT always 20000.

**Part B — With `synchronized`:**  
Add `synchronized` to `increment()` and observe that the result is always exactly `20000`.

**Expected Output (Part B):**
```
20000
```

**Concepts:** Race condition, `synchronized`, `join()`, shared mutable state

---

### Q7. `join()` — Wait for Completion
Create two threads — Thread A prints `"Thread A working"` 3 times (with 50ms sleep), Thread B prints `"Thread B working"` 3 times.  
In `main`:
1. Start both threads.
2. Call `t1.join()` and `t2.join()`.
3. Print `"Both threads done"` after join.

Verify `"Both threads done"` always appears LAST.

**Expected Output:**
```
Thread A/B working (interleaved)
...
Both threads done
```

**Concepts:** `join()`, main thread waits, ordering guarantee with join

---

### Q8. Runnable vs Thread — When to Use Which
Create a class `TaskWorker` that **extends `AnotherClass`** (to simulate real inheritance).  
Since it already extends something, implement threading via `implements Runnable`.  
The task prints `"Working..."` 3 times.

```java
class AnotherClass { }   // pretend parent class
class TaskWorker extends AnotherClass implements Runnable {
    public void run() { ... }
}
```

In `main`, create a `Thread` using `new Thread(new TaskWorker())` and start it.  
In comments, explain why `extends Thread` wouldn't work here.

**Expected Output:**
```
Working...
Working...
Working...
```

**Concepts:** `extends Thread` limitation (single inheritance), `implements Runnable` solution, separation of task and thread

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Thread Interleaving and join()
**Before running**, predict what is GUARANTEED vs what is NOT GUARANTEED in the output:

```java
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
```

Answer in comments:
1. Is the output order guaranteed here? Why?
2. What would change if you removed both `join()` calls?
3. Would `"Done"` always be the last line without `join()`?

**Concepts:** `join()` ordering guarantee, thread scheduling, guaranteed vs non-guaranteed output

---

### Q10. Thread-Safe Counter with Multiple Operations
Build a thread-safe `BankAccount` class with:
- `private int balance`
- `synchronized deposit(int amount)` — adds to balance
- `synchronized withdraw(int amount)` — subtracts if sufficient, prints `"Insufficient funds"` otherwise
- `getBalance()` getter

In `main`:
1. Create a `BankAccount` with balance 1000.
2. Create 3 threads — two depositing 500 each (10 times each), one withdrawing 200 (20 times).
3. Use `join()` on all three before printing final balance.
4. Print the final balance.

**Constraints:**
- Use lambda for `Runnable`.
- All threads must be properly `join()`ed.
- The final balance must always be deterministic given the operation counts.

**Expected Final Balance:**
```
(1000 + 500*10 + 500*10) - 200*20 = 1000 + 10000 - 4000 = 7000
7000
```

**Concepts:** `synchronized` methods, `join()`, thread-safe mutable state, lambda Runnable, deterministic output

---

*Practice file for 01. CoreJava / 16. Multithreading — Telusko Java Tutorial*
