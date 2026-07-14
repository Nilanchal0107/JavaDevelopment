# 📘 Multithreading — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [What is a Thread?](#1-what-is-a-thread)
2. [Creating Threads — Extending `Thread`](#2-creating-threads--extending-thread)
3. [Thread Priority and `sleep()`](#3-thread-priority-and-sleep)
4. [Runnable Interface — Preferred Approach](#4-runnable-interface--preferred-approach)
5. [Race Condition and `synchronized`](#5-race-condition-and-synchronized)
6. [Thread States](#6-thread-states)

---

## 1. What is a Thread?

From the code notes:
```
Threads: Multiple threads run at the same time in a code. This is known as Multithreading.
- A thread is the smallest unit of work (individual task)
- They can run in parallel
- Multiple threads can share resources
```

> 💡 Java is inherently **multi-threaded** — the JVM itself uses multiple threads (e.g., garbage collector runs in a background thread alongside your code).

---

## 2. Creating Threads — Extending `Thread`

The simplest way to create a thread is to **extend `Thread`** and override the `run()` method.

```java
class A extends Thread {
    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("Hi");
        }
    }
}

class B extends Thread {
    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("Hello");
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        A obj1 = new A();
        B obj2 = new B();

        obj1.start();   // starts Thread A — runs run() concurrently
        obj2.start();   // starts Thread B — runs run() concurrently
    }
}
```

**Output (interleaved, order varies each run):**
```
Hi
Hello
Hi
Hi
Hello
...
```

> ⚠️ Call `.start()`, NOT `.run()`. Calling `.run()` directly executes it on the main thread sequentially — it does not create a new thread.

> ⚠️ The downside of `extends Thread`: your class cannot extend anything else (Java has single inheritance). Prefer `implements Runnable` when inheritance is needed.

---

## 3. Thread Priority and `sleep()`

### `Thread.sleep(millis)` — Pause a Thread

Pauses the current thread for the given milliseconds. Used to simulate delay or control scheduling.

```java
class A extends Thread {
    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("Hi");
            try {
                Thread.sleep(10);   // pause 10ms between each print
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

> ⚠️ `Thread.sleep()` throws `InterruptedException` (checked) — must be caught inside `run()`.

### Thread Priority

Priority hints to the scheduler which thread to prefer (1 = MIN, 5 = NORM, 10 = MAX). Not guaranteed.

```java
obj2.setPriority(Thread.MAX_PRIORITY);   // give obj2 higher priority (10)
System.out.println(obj1.getPriority());  // prints default priority (5)

obj1.start();
Thread.sleep(2);   // small delay before starting obj2
obj2.start();
```

| Constant | Value |
|---|---|
| `Thread.MIN_PRIORITY` | 1 |
| `Thread.NORM_PRIORITY` | 5 (default) |
| `Thread.MAX_PRIORITY` | 10 |

> ⚠️ Priority is a **hint** to the JVM scheduler — it is not guaranteed. The actual execution order still depends on the OS thread scheduler.

---

## 4. Runnable Interface — Preferred Approach

`Runnable` is a functional interface with a single method `void run()`. Preferred over `extends Thread` because:
- Your class can still extend another class.
- The task (what to do) is separated from the thread (how to run it).

### Three ways to implement `Runnable`

**1. Named class:**
```java
class A implements Runnable {
    public void run() { ... }
}
Runnable obj1 = new A();
Thread t1 = new Thread(obj1);
t1.start();
```

**2. Anonymous inner class:**
```java
Runnable obj1 = new Runnable() {
    public void run() { ... }
};
```

**3. Lambda (cleanest — from the final code):**
```java
Runnable obj1 = () -> {
    for (int i = 1; i <= 5; i++) {
        System.out.println("Hi");
        try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
    }
};

Runnable obj2 = () -> {
    for (int i = 1; i <= 5; i++) {
        System.out.println("Hello");
        try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
    }
};

Thread t1 = new Thread(obj1);
Thread t2 = new Thread(obj2);
t1.start();
t2.start();
```

---

## 5. Race Condition and `synchronized`

A **race condition** occurs when two or more threads modify shared data concurrently, producing unpredictable results.

### The Problem — Without `synchronized`

```java
class Counter {
    int count;
    public void increment() {   // NOT synchronized
        count++;
    }
}
```

Two threads each calling `increment()` 10,000 times should give `20000` — but without synchronization, the result is typically **less** (threads overwrite each other's updates).

### The Fix — `synchronized`

```java
class Counter {
    int count;
    public synchronized void increment() {   // only one thread at a time
        count++;
    }
}

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();

        Runnable obj1 = () -> { for (int i = 1; i <= 10000; i++) c.increment(); };
        Runnable obj2 = () -> { for (int i = 1; i <= 10000; i++) c.increment(); };

        Thread t1 = new Thread(obj1);
        Thread t2 = new Thread(obj2);

        t1.start();
        t2.start();

        t1.join();   // main thread waits for t1 to finish
        t2.join();   // main thread waits for t2 to finish

        System.out.println(c.count);   // Output: 20000 (always correct with synchronized)
    }
}
```

> 💡 `synchronized` acquires a **lock** on the object before entering the method. Other threads calling `synchronized` methods on the same object must wait until the lock is released.

> 💡 `join()` makes the calling thread wait until the target thread finishes. Without it, `main` might print `c.count` before both threads are done.

---

## 6. Thread States

From the code notes:

```
Thread States:
1. New State      → thread object created, not yet started
2. Runnable State → after start() is called — ready to run, waiting for CPU
3. Running State  → thread's run() is actively executing
4. Waiting State  → after sleep() or wait() — temporarily inactive
5. Dead State     → run() completes — thread is done
```

### State Transitions

```
New ──start()──► Runnable ──CPU assigned──► Running
                                │                │
                          sleep()/wait()         │ stop() / run() ends
                                │                ▼
                           Waiting          Dead State
                                │
                         notify() / sleep expires
                                │
                           Runnable
```

> 💡 `notify()` moves a thread from **Waiting → Runnable**. `stop()` is deprecated — use flags or `interrupt()` instead for graceful shutdown.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 16. Multithreading*
