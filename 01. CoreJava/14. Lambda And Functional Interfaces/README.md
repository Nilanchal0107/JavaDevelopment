# 📘 Lambda And Functional Interfaces — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Functional Interface and `@FunctionalInterface`](#1-functional-interface-and-functionalinterface)
2. [Lambda Expression — Syntax and Evolution](#2-lambda-expression--syntax-and-evolution)
3. [Lambda with Return Statement](#3-lambda-with-return-statement)
4. [Types of Interfaces](#4-types-of-interfaces)

---

## 1. Functional Interface and `@FunctionalInterface`

A **functional interface** has **exactly one abstract method** (SAM — Single Abstract Method). It can be implemented using an **anonymous inner class** or, more concisely, a **lambda expression**.

```java
@FunctionalInterface
interface A {
    void show();
    // void run();   // ❌ second abstract method — compile error
}
```

### Implementing with Anonymous Inner Class (old way)

```java
A obj = new A() {
    public void show() {
        System.out.println("in Show");
    }
};
obj.show();   // Output: in Show
```

> 💡 `@FunctionalInterface` is optional but recommended — it makes the compiler enforce the single-method contract and documents intent.

---

## 2. Lambda Expression — Syntax and Evolution

A **lambda expression** is a concise way to implement a functional interface — it replaces the anonymous inner class boilerplate.

### Evolution of Implementation

**Step 1 — Anonymous inner class (verbose):**
```java
A obj = new A() {
    public void show(int i) {
        System.out.println("in show " + i);
    }
};
obj.show(5);
```

**Step 2 — Lambda with full type:**
```java
A obj = (int i) -> System.out.println("in show " + i);
obj.show(5);
```

**Step 3 — Lambda with inferred type (type can be omitted):**
```java
A obj = i -> System.out.println("in show " + i);   // single param: no parentheses needed
obj.show(5);
```

**Output (all three produce the same):**
```
in show 5
```

### Lambda Syntax Rules

| Case | Syntax |
|---|---|
| No parameters | `() -> expression` |
| One parameter | `param -> expression` (parens optional) |
| Multiple parameters | `(param1, param2) -> expression` |
| Multi-line body | `(params) -> { statements; }` |
| With return | `(params) -> expression` (return implicit) or `(params) -> { return value; }` |

> 💡 The lambda body is the implementation of the single abstract method. The compiler knows which method to implement because there is only one.

---

## 3. Lambda with Return Statement

When the functional interface's method has a return type, the lambda can return implicitly (single expression) or explicitly (with `return` in a block).

```java
@FunctionalInterface   // note: file has typo — missing @ in source, shown correctly here
interface A {
    int add(int i, int j);
}

public class Demo {
    public static void main(String[] args) {

        // Old way — anonymous inner class:
        // A obj = new A() {
        //     public int add(int i, int j) { return i + j; }
        // };

        // Lambda — implicit return (expression only, no braces needed):
        A obj = (i, j) -> i + j;

        int result = obj.add(5, 4);
        System.out.println(result);   // Output: 9
    }
}
```

> 💡 `(i, j) -> i + j` — no `return` keyword needed for a single-expression lambda. The expression IS the return value.

> 💡 For a multi-line lambda body you must use `{}` and an explicit `return`:
> ```java
> A obj = (i, j) -> {
>     int sum = i + j;
>     return sum;
> };
> ```

---

## 4. Types of Interfaces

From the code notes:

```
Types of Interface:
1. Normal Interface
   — An interface with two or more abstract methods

2. Functional Interface (SAM)
   — SAM = Single Abstract Method interface
   — Can be implemented with a lambda expression

3. Marker Interface
   — An interface that has NO methods (blank interface)
   — Examples: Serializable, Cloneable
   — Used to "tag" a class for special JVM/framework behaviour
```

| Type | Methods | Example | Lambda Usable? |
|---|---|---|---|
| Normal Interface | 2 or more | `Comparable` | ❌ |
| Functional Interface (SAM) | Exactly 1 abstract | `Runnable`, `Comparator` | ✅ |
| Marker Interface | 0 | `Serializable`, `Cloneable` | ❌ |

### `Runnable` — a built-in Functional Interface used with Lambda

The file also demonstrates `Runnable` being implemented with a lambda for multithreading:

```java
Runnable obj1 = () -> {
    for (int i = 1; i <= 5; i++) {
        System.out.println("Hi");
        try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
    }
};
Thread t1 = new Thread(obj1);
t1.start();
```

> 💡 `Runnable` is a `@FunctionalInterface` — its single method is `void run()`. Lambda replaces the entire anonymous class implementation.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 14. Lambda And Functional Interfaces*
