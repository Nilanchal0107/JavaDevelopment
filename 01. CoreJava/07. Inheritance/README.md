# 📘 Inheritance — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Need of Inheritance](#1-need-of-inheritance)
2. [What is Inheritance](#2-what-is-inheritance)
3. [Single and Multilevel Inheritance](#3-single-and-multilevel-inheritance)
4. [Multiple Inheritance — Why Java Doesn't Support It](#4-multiple-inheritance--why-java-doesnt-support-it)
5. [Method Overriding](#5-method-overriding)

---

## 1. Need of Inheritance

### The "is-a" vs "has-a" relationship

Inheritance models a real-world **"is-a"** relationship:
- A `Dog` **is-a** `Animal`
- An `AdvCalc` **is-a** `Calc`

> 💡 "has-a" is composition (an object holds another object as a field). "is-a" is inheritance (`extends`). Choose the right relationship for your design.

### The Problem — Duplication Without Inheritance

From the code's conceptual notes:

```
Class Calc {           // Parent class — Super / Base
    add()
    sub()
    multi()
    div()
}

Class AdvCalc {        // Without inheritance: would need to redefine add(), sub() etc.
    ...
}
```

Without inheritance, `AdvCalc` would duplicate all methods from `Calc`. Inheritance eliminates this duplication.

**Terminology:**
| Term | Alias |
|---|---|
| Parent class | Super class, Base class |
| Child class | Sub class, Derived class |

---

## 2. What is Inheritance

**Inheritance** allows a child class to **inherit (reuse) all public and protected members** of a parent class using the `extends` keyword.

```java
class Calc {
    public int add(int n1, int n2) {
        return n1 + n2;
    }
    public int sub(int n1, int n2) {
        return n1 - n2;
    }
}

public class AdvCalc extends Calc {   // AdvCalc inherits add() and sub() from Calc
    public int multi(int n1, int n2) {
        return n1 * n2;
    }
    public int div(int n1, int n2) {
        return n1 / n2;
    }
}

public class Demo {
    public static void main(String[] a) {
        AdvCalc obj = new AdvCalc();
        int r1 = obj.add(4, 5);    // inherited from Calc — Output: 9
        int r2 = obj.sub(7, 3);    // inherited from Calc — Output: 4
        int r3 = obj.multi(5, 3);  // own method — Output: 15
        int r4 = obj.div(15, 4);   // own method — Output: 3

        System.out.println(r1 + " " + r2);  // Output: 9 4
    }
}
```

> 💡 `AdvCalc` didn't rewrite `add()` or `sub()` — it just called them directly because it inherited them from `Calc`.

---

## 3. Single and Multilevel Inheritance

### Single Inheritance
One class inherits from one parent:
```
Calc → AdvCalc
```

### Multilevel Inheritance
A chain of inheritance — child inherits from a class which is itself a child:
```
Calc → AdvCalc → VeryAdvCalc
```

```java
class Calc {
    public int add(int n1, int n2) { return n1 + n2; }
    public int sub(int n1, int n2) { return n1 - n2; }
}

class AdvCalc extends Calc {
    public int multi(int n1, int n2) { return n1 * n2; }
    public int div(int n1, int n2)   { return n1 / n2; }
}

class VeryAdvCalc extends AdvCalc {   // inherits from AdvCalc AND transitively from Calc
    public double power(int n1, int n2) {
        return Math.pow(n1, n2);
    }
}

public class Demo {
    public static void main(String[] a) {
        VeryAdvCalc obj = new VeryAdvCalc();

        int r1 = obj.add(4, 5);       // from Calc — 9
        int r2 = obj.sub(7, 3);       // from Calc — 4
        int r3 = obj.multi(5, 3);     // from AdvCalc — 15
        int r4 = obj.div(15, 4);      // from AdvCalc — 3
        double r5 = obj.power(4, 2);  // own method — 16.0

        System.out.println(r1 + " " + r2 + " " + r3 + " " + r4 + " " + r5);
        // Output: 9 4 15 3 16.0
    }
}
```

> 💡 `VeryAdvCalc` can call `add()` from `Calc` even though `Calc` is two levels up. Java searches up the inheritance chain until it finds the method.

---

## 4. Multiple Inheritance — Why Java Doesn't Support It

**Multiple inheritance** means one class inheriting from **two or more parent classes** simultaneously.

Java does **NOT** support multiple class inheritance due to the **Diamond Problem** (ambiguity):

```java
class A { }
class B extends A { }
// class C extends A, B   // ❌ Not allowed in Java — compile error
class C extends B { }     // ✅ This is fine (multilevel)

public class Demo {
    public static void main(String[] args) { }
}
```

### Why the ambiguity problem?

Imagine both `A` and `B` define a method `show()`. If `C extends A, B`, which `show()` does `C` inherit? Java cannot resolve this ambiguity for classes.

> 💡 Java solves the multiple inheritance need using **Interfaces** — a class can implement multiple interfaces. This is covered in the Interfaces topic.

---

## 5. Method Overriding

**Method Overriding** means a child class provides its **own implementation** of a method that is already defined in the parent class.

### Rules for Overriding
- Same method name, same parameter list, same return type.
- The child class's version takes priority when called on a child object.

### From the code

```java
class Calc {
    public int add(int n1, int n2) {
        return n1 + n2;      // parent's add: simple sum
    }
}
class AdvCalc extends Calc {
    public int add(int n1, int n2) {   // overrides Calc's add
        return n1 + n2 + 1;           // child's add: adds an extra 1
    }
}

public class Demo {
    public static void main(String[] args) {
        AdvCalc obj = new AdvCalc();
        int r1 = obj.add(3, 4);        // calls AdvCalc's version, not Calc's
        System.out.println(r1);        // Output: 8
    }
}
```

> 💡 Overriding is **runtime polymorphism** — the JVM decides which method to call based on the **actual type** of the object at runtime, not the reference type.

### Overriding vs Overloading

| Feature | Overloading | Overriding |
|---|---|---|
| Where | Same class | Child class overrides parent |
| Parameters | Must differ | Must be identical |
| Return type | Can differ | Must be same (or covariant) |
| Resolved at | Compile time | Runtime |

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 07. Inheritance*
