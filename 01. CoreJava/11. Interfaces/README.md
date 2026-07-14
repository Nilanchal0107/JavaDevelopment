# 📘 Interfaces — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Need of Interface](#1-need-of-interface)
2. [What is an Interface](#2-what-is-an-interface)
3. [More on Interfaces — Multiple Implementation and Interface Inheritance](#3-more-on-interfaces--multiple-implementation-and-interface-inheritance)

---

## 1. Need of Interface

### The Problem — Tight Coupling Without Abstraction

From the code, the evolution from concrete type to abstract type to interface is demonstrated:

**Stage 1 — Tightly coupled (bad):** The `Developer` only works with `Laptop`:
```java
class Developer {
    public void devApp(Laptop lap) {   // only accepts Laptop — tightly coupled
        lap.code();
    }
}
```

**Stage 2 — Better with abstract class:**
```java
abstract class Computer {
    public abstract void code();
}
class Laptop extends Computer { public void code() { ... } }
class Desktop extends Computer { public void code() { ... } }

class Developer {
    public void devApp(Computer lap) {  // now accepts any Computer subtype
        lap.code();
    }
}
```

**Stage 3 — Best with interface (the active code):**

```java
interface Computer {
    void code();                    // implicitly public abstract
}

class Laptop implements Computer {
    public void code() {
        System.out.println("code, compile, run");
    }
}

class Desktop implements Computer {
    public void code() {
        System.out.println("code, compile, faster");
    }
}

class Developer {
    public void devApp(Computer lap) {  // accepts ANY Computer implementor
        lap.code();
    }
}

public class Demo {
    public static void main(String[] args) {
        Computer lap  = new Laptop();
        Computer desk = new Desktop();

        Developer navin = new Developer();
        navin.devApp(lap);   // Output: code, compile, run
    }
}
```

> 💡 By accepting an **interface type** instead of a concrete class, `Developer.devApp()` works with any class that implements `Computer` — `Laptop`, `Desktop`, or any future device — without any code change to `Developer`.

---

## 2. What is an Interface

An **interface** is a pure contract — it specifies **what** a class must do, but not **how** it does it. It contains method signatures (and optionally constants) that implementing classes must provide.

### Interface Definition

```java
interface A {
    int age = 44;          // implicitly: public static final int age = 44;
    String area = "Mumbai"; // implicitly: public static final String area = "Mumbai";

    void show();           // implicitly: public abstract void show();
    void config();         // implicitly: public abstract void config();
}
```

### Implementing an Interface

```java
class B implements A {
    public void show() {           // must be public — implementing a public abstract method
        System.out.println("in show");
    }
    public void config() {
        System.out.println("in cofing");  // note: typo from original code
    }
}
```

### Using an Interface as a Type

```java
public class Demo {
    public static void main(String[] args) {
        A obj;
        obj = new B();     // upcasting — B is stored as type A

        obj.show();        // Output: in show
        obj.config();      // Output: in cofing

        // A.area = "Hyderabad";  // ❌ compile error — area is final (constant)

        System.out.println(A.area);  // Output: Mumbai
    }
}
```

### Interface Rules Summary

| Feature | Interface Behaviour |
|---|---|
| Methods | Implicitly `public abstract` (before Java 8) |
| Fields | Implicitly `public static final` (constants) |
| Cannot instantiate | `new A()` is a compile error |
| Constructor | No constructor in an interface |
| Inheritance | A class uses `implements`; an interface uses `extends` |

> ⚠️ All variables in an interface are **constants** — they are `public static final` by default. You **cannot** reassign them (e.g., `A.area = "Hyderabad"` is a compile error).

---

## 3. More on Interfaces — Multiple Implementation and Interface Inheritance

### Relationship Rules (from the code comments)

```java
// class  → class      : extends (single only)
// class  → interface  : implements (multiple allowed)
// interface → interface : extends (can extend multiple)
```

### A Class Can Implement Multiple Interfaces

```java
interface A {
    int age = 44;
    String area = "Mumbai";
    void show();
    void config();
}

interface X {
    void run();
}

interface Y extends X {    // interface extending interface — inherits run()
    // Y inherits run() from X
}

class B implements A, Y {  // B must implement: show(), config() from A, and run() from X (via Y)
    public void show()   { System.out.println("in show"); }
    public void config() { System.out.println("in cofing"); }
    public void run()    { System.out.println("running..."); }
}
```

### Using Multiple Interface Types

```java
public class Demo {
    public static void main(String[] args) {
        A obj = new B();
        obj.show();     // Output: in show
        obj.config();   // Output: in cofing

        X obj1 = new B();
        obj1.run();     // Output: running...

        // A.area = "Hyderabad";  // ❌ constants cannot be reassigned
        System.out.println(A.area);  // Output: Mumbai
    }
}
```

**Output:**
```
in show
in cofing
running...
Mumbai
```

### Interface vs Abstract Class

| Feature | Interface | Abstract Class |
|---|---|---|
| Methods | Only abstract (Java 7); can have `default`/`static` (Java 8+) | Both abstract and concrete |
| Fields | Only `public static final` constants | Any type of fields |
| Constructors | ❌ None | ✅ Can have constructors |
| Multiple inheritance | ✅ A class can implement many | ❌ A class can extend only one |
| Use when | Defining a **capability/contract** across unrelated classes | Sharing **implementation** among related classes |

> 💡 **Interface** solves Java's multiple inheritance limitation for classes. A class can only `extend` one class, but can `implement` many interfaces.

> 💡 This is how Java enables designs like `class Dog extends Animal implements Runnable, Serializable`.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 11. Interfaces*
