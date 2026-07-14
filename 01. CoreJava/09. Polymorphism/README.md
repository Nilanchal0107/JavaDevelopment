# 📘 Polymorphism — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [What is Polymorphism?](#1-what-is-polymorphism)
2. [Dynamic Method Dispatch (Runtime Polymorphism)](#2-dynamic-method-dispatch-runtime-polymorphism)
3. [The `final` Keyword](#3-the-final-keyword)
4. [Object Class — `equals()`, `toString()`, `hashCode()`](#4-object-class--equals-tostring-hashcode)
5. [Upcasting and Downcasting](#5-upcasting-and-downcasting)

---

## 1. What is Polymorphism?

**Polymorphism** means "many forms" — the same reference or method name behaves differently in different contexts.

From the code notes:
```
Polymorphism:
- Many behaviour (same object or reference has different behaviours)

1. Compile-time polymorphism → Method Overloading
   add(int, int)
   add(int, int, int)

2. Runtime polymorphism → Method Overriding
   Class A: add(int, int)
   Class B: add(int, int)   ← overrides A's add
```

| Type | Mechanism | Resolved At |
|---|---|---|
| Compile-time | Method Overloading | Compile time |
| Runtime | Method Overriding + Dynamic Dispatch | Runtime (JVM) |

---

## 2. Dynamic Method Dispatch (Runtime Polymorphism)

**Dynamic Method Dispatch** is the mechanism by which the JVM decides **at runtime** which overridden method to call, based on the **actual type** of the object — not the reference type.

### Key rule
- The **reference type** determines which methods you can *call*.
- The **object type** determines *which version* of the method runs.

### From the code

```java
class A {
    public void show() { System.out.println("in A show"); }
}
class B extends A {
    public void show() { System.out.println("in B show"); }
}
class C extends A {
    public void show() { System.out.println("in C show"); }
}

public class Demo {
    public static void main(String[] args) {
        A obj = new A();
        obj.show();           // Output: in A show

        obj = new B();        // same reference 'obj', now points to B object
        obj.show();           // Output: in B show  ← runtime decision

        obj = new C();        // now points to C object
        obj.show();           // Output: in C show  ← runtime decision

        // obj = new D();     // ❌ D does not extend A — compile error
    }
}
```

**Output:**
```
in A show
in B show
in C show
```

> 💡 This is the power of polymorphism — `obj` is declared as type `A`, but the actual method that runs depends on what object it currently holds. One reference, many behaviours.

> 💡 This enables writing code that works with a **parent type** but automatically uses the correct subclass behaviour — the foundation of pluggable and extensible designs.

---

## 3. The `final` Keyword

`final` can be applied to **variables**, **methods**, and **classes** — each with a different effect:

| Used on | Effect |
|---|---|
| `final` variable | Value cannot be changed after assignment (constant) |
| `final` method | Cannot be overridden by subclasses |
| `final` class | Cannot be extended (no subclassing allowed) |

### `final` Variable (from commented code)
```java
final int num = 8;
// num = 9;   // ❌ compile error — cannot reassign a final variable
System.out.println(num);  // Output: 8
```

### `final` Method (from the code)
```java
class Calc {
    public final void show() {       // final method — cannot be overridden
        System.out.println("By Navin");
    }
    public void add(int a, int b) {
        System.out.println(a + b);
    }
}

class AdvCalc extends Calc {
    public void show() {             // ❌ compile error — cannot override final method
        System.out.println("By John");
    }
}

public class Demo {
    public static void main(String[] args) {
        AdvCalc obj = new AdvCalc();
        obj.show();     // would call Calc's show() if override removed
        obj.add(4, 5);  // Output: 9
    }
}
```

### `final` Class (from commented code)
```java
// final class Calc { ... }
// class AdvCalc extends Calc { }   // ❌ compile error — cannot extend a final class
```

> ⚠️ The `final` method code in the source file has a compile error as written (the `AdvCalc.show()` override of a final method). This is intentional — it demonstrates what Java **does not allow**.

> 💡 `String` in Java is a `final` class — that's why you can never extend `String`.

---

## 4. Object Class — `equals()`, `toString()`, `hashCode()`

Every class in Java **implicitly extends `Object`**. The `Object` class provides default implementations of three key methods that you can override for meaningful behaviour.

### `toString()` — String representation of an object

By default, `System.out.println(obj)` prints something like `Laptop@1b6d3586` (class name + hex hash).  
Override `toString()` to print something meaningful:

```java
class Laptop {
    String model;
    int price;

    public String toString() {
        return model + " : " + price;   // custom representation
    }
}

Laptop obj = new Laptop();
obj.model = "Lenevo Yoga";
obj.price = 1000;

System.out.println(obj.toString());  // Output: Lenevo Yoga : 1000
// System.out.println(obj);          // same result — println calls toString() automatically
```

### `equals()` — Meaningful object comparison

By default, `==` and `equals()` on objects compare **references** (memory addresses).  
Override `equals()` to compare object **content**:

```java
class Laptop {
    String model;
    int price;

    public boolean equals(Laptop that) {
        return this.model.equals(that.model) && this.price == that.price;
    }
}

Laptop obj  = new Laptop(); obj.model  = "Lenevo Yoga"; obj.price  = 1000;
Laptop obj2 = new Laptop(); obj2.model = "Lenevo Yoga"; obj2.price = 1000;

boolean result = obj.equals(obj2);   // true — same model and price
System.out.println(obj.toString());  // Output: Lenevo Yoga : 1000
```

> ⚠️ In the code, `equals(Laptop that)` takes a `Laptop` parameter — this is **overloading**, not true overriding of `Object.equals(Object)`. To properly override, the signature must be `equals(Object obj)`.

> 💡 The contract of `equals()` and `hashCode()`: if two objects are `equals()`, they must have the same `hashCode()`. Always override both together.

---

## 5. Upcasting and Downcasting

### Upcasting — child object stored in parent reference (automatic)

```java
A obj = new B();   // upcasting — automatic, safe
// equivalent to: A obj = (A) new B();
obj.show1();       // can only call A's methods — show2() not accessible
```

- Upcasting is **implicit** — no cast needed.
- After upcasting, you can only call methods defined in the **parent class** (even if the child has extra methods).

### Downcasting — getting back to child type (manual)

```java
A obj = new B();        // upcasted
obj.show1();            // OK — A's method

B obj1 = (B) obj;       // downcasting — explicit cast needed
obj1.show2();           // OK — now can access B's method
```

**Output:**
```
in A show
in show B
```

### Full example from code

```java
class A {
    public void show1() { System.out.println("in A show"); }
}
class B extends A {
    public void show2() { System.out.println("in show B"); }
}

public class Demo {
    public static void main(String[] args) {
        A obj = new B();      // upcasting — implicit
        obj.show1();          // Output: in A show

        B obj1 = (B) obj;     // downcasting — explicit
        obj1.show2();         // Output: in show B
    }
}
```

> ⚠️ **Invalid downcast** causes a `ClassCastException` at runtime:
> ```java
> A obj = new A();
> B obj1 = (B) obj;   // ❌ runtime error — obj is actually an A, not a B
> ```

> 💡 Use `instanceof` before downcasting to check the actual type:
> ```java
> if (obj instanceof B) {
>     B obj1 = (B) obj;
> }
> ```

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 09. Polymorphism*
