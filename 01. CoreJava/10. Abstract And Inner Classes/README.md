# 📘 Abstract And Inner Classes — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Abstract Class and `abstract` Keyword](#1-abstract-class-and-abstract-keyword)
2. [Inner Class (Static Nested Class)](#2-inner-class-static-nested-class)
3. [Anonymous Inner Class](#3-anonymous-inner-class)
4. [Abstract Class with Anonymous Inner Class](#4-abstract-class-with-anonymous-inner-class)

---

## 1. Abstract Class and `abstract` Keyword

An **abstract class** is a class that:
- Cannot be instantiated directly (you cannot do `new AbstractClass()`).
- May contain **abstract methods** (declared but not implemented) that subclasses **must** implement.
- May also contain **concrete methods** (with a full implementation) that subclasses can use as-is.

### Syntax

```java
abstract class ClassName {
    public abstract void methodName();  // no body — must be implemented by subclass
    public void concreteMethod() { }    // has body — inherited as-is
}
```

### From the code — `Car`, `WagnoR`, `UpdateWagnoR`

```java
abstract class Car {
    public abstract void drive();      // abstract — no body
    public abstract void fly();        // abstract — no body

    public void playMusic() {          // concrete — has body
        System.out.println("play music");
    }
}

abstract class WagnoR extends Car {
    // implements drive() but NOT fly() — so WagnoR is still abstract
    public void drive() {
        System.out.println("Driving...");
    }
}

class UpdateWagnoR extends WagnoR {   // concrete class — implements ALL remaining abstract methods
    public void fly() {
        System.out.println("flying...");
    }
}

public class Demo {
    public static void main(String[] args) {
        // Car obj = new Car();           // ❌ cannot instantiate abstract class
        // Car obj = new WagnoR();        // ❌ WagnoR is still abstract (fly() not implemented)
        Car obj = new UpdateWagnoR();     // ✅ upcasting to abstract type

        obj.drive();       // Output: Driving...      (from WagnoR)
        obj.playMusic();   // Output: play music      (from Car — concrete method)
    }
}
```

**Output:**
```
Driving...
play music
```

### Key Rules

| Rule | Detail |
|---|---|
| Cannot instantiate | `new AbstractClass()` is a compile error |
| Abstract methods | Must be implemented by the first **concrete** subclass |
| Partial implementation | A subclass can implement *some* abstract methods and remain abstract itself |
| Can have constructors | Abstract classes can have constructors (called via `super()`) |
| Can have fields | Abstract classes can have instance variables |

> 💡 Use abstract classes when you want to **enforce a contract** on subclasses while also providing shared implementation (concrete methods). Unlike interfaces, abstract classes support state (instance variables).

---

## 2. Inner Class (Static Nested Class)

An **inner class** is a class defined **inside another class**. It is used to logically group classes that are only used in one place.

### Static Inner Class (Nested Class)

A **static nested class** belongs to the outer class, not to any specific instance. It can be accessed without creating an outer class object.

```java
class A {
    int age;

    public void show() {
        System.out.println("in show");
    }

    static class B {                // static inner class
        public void config() {
            System.out.println("in config");
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        A obj = new A();
        obj.show();           // Output: in show

        // For non-static inner class (commented out in code):
        // A.B obj1 = obj.new B();   // needs an outer object instance

        A.B obj1 = new A.B();  // static nested class — no outer object needed
        obj1.config();         // Output: in config
    }
}
```

**Output:**
```
in show
in config
```

### Non-static vs Static Inner Class

| Feature | Non-static inner class | Static nested class |
|---|---|---|
| Instantiation | `outerObj.new Inner()` | `new Outer.Inner()` |
| Access to outer instance | ✅ Can access outer class members | ❌ Cannot access outer instance members |
| Use when | Inner class needs outer class data | Inner class is logically related but independent |

---

## 3. Anonymous Inner Class

An **anonymous inner class** is a class that is declared and instantiated **at the same time**, without giving it a name.

### Use case
When you need to **override a method of a class just once** — for a single specific use — and creating a named subclass would be unnecessary overhead.

### From the code — overriding a concrete class anonymously

```java
class A {
    public void show() {
        System.out.println("in A show");
    }
}

// Without anonymous class, you'd need:
// class B extends A { public void show() { ... } }
// Then: A obj = new B();

public class Demo {
    public static void main(String[] args) {

        A obj = new A() {              // anonymous inner class — extends A on the fly
            public void show() {
                System.out.println("in new show");   // overrides A's show()
            }
        };                             // semicolon ends the statement

        obj.show();   // Output: in new show
    }
}
```

**Output:**
```
in new show
```

> 💡 The anonymous class is created inline, overrides `show()`, and the reference `obj` points to it. `A`'s original `show()` is completely replaced for this specific object.

> 💡 Anonymous classes are widely used in event handling (e.g., button click listeners in GUI) and in callbacks — anywhere you need a quick one-time implementation.

---

## 4. Abstract Class with Anonymous Inner Class

The most powerful combination: use an **anonymous inner class to instantiate an abstract class** inline, providing implementations for abstract methods on the spot.

```java
abstract class A {
    public abstract void show();
    public abstract void config();
}

// Without anonymous class, you'd need a concrete named class extending A

public class Demo {
    public static void main(String[] args) {

        A obj = new A() {                   // cannot do 'new A()' normally — abstract!
            public void show() {            // must implement ALL abstract methods
                System.out.println("in new show");
            }
            // config() NOT implemented    // ❌ compile error if omitted
        };

        obj.show();   // Output: in new show
    }
}
```

> ⚠️ The anonymous inner class in the source code only implements `show()` but not `config()` — as written, this would be a **compile error**. Both abstract methods must be implemented. The code demonstrates the concept while highlighting this requirement.

> 💡 This pattern is extremely common in Java — for example, implementing `Comparator` or `Runnable` inline without needing to create a separate class file.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 10. Abstract And Inner Classes*
