# 🧪 Practice Questions — Abstract And Inner Classes

> Topics covered: `abstract` class, `abstract` method, concrete methods in abstract class, partial implementation (abstract subclass), cannot instantiate abstract class, static nested class, non-static inner class, anonymous inner class (concrete class + abstract class), implementing abstract methods inline

---

## 🟢 Easy (5 Questions)

---

### Q1. Basic Abstract Class
Create an abstract class `Shape` with:
- Abstract method `area()` that returns `double`
- Concrete method `describe()` that prints `"I am a shape"`

Create a concrete class `Circle extends Shape` with field `radius` (double) that implements `area()` as `3.14 * radius * radius`.

In `main`, create a `Circle` with radius 5.0 and:
1. Call `describe()`
2. Print `area()`

**Expected Output:**
```
I am a shape
78.5
```

**Concepts:** Abstract class, abstract method, concrete method inheritance, cannot instantiate abstract class

---

### Q2. Cannot Instantiate Abstract Class
Write a program with an abstract class `Vehicle` that has an abstract method `move()`.  
In `main`, write (as a comment) the line `new Vehicle()` and explain why it causes a compile error.  
Create a concrete subclass `Car extends Vehicle` implementing `move()` with `"Car is moving"`.  
Instantiate `Car` via an upcast: `Vehicle v = new Car();` and call `move()`.

**Expected Output:**
```
Car is moving
```

**Concepts:** Cannot instantiate abstract class, upcasting to abstract type

---

### Q3. Static Nested Class
Create an outer class `Computer` with a method `start()` printing `"Computer started"`.  
Inside it, create a `static class GPU` with a method `render()` printing `"GPU rendering"`.

In `main`, access both without creating a `Computer` instance for `GPU`:
```java
Computer c = new Computer();
c.start();
Computer.GPU gpu = new Computer.GPU();
gpu.render();
```

**Expected Output:**
```
Computer started
GPU rendering
```

**Concepts:** Static nested class, `Outer.Inner` instantiation, no outer instance needed

---

### Q4. Anonymous Inner Class — Concrete Class
Create a class `Printer` with a method `print()` that prints `"Default printing"`.  
In `main`, create an anonymous inner class that overrides `print()` to print `"Custom printing"` and call it.

**Expected Output:**
```
Custom printing
```

**Concepts:** Anonymous inner class overriding a concrete class method

---

### Q5. Abstract Class with Anonymous Inner Class
Create an abstract class `Animal` with an abstract method `speak()`.  
In `main`, instantiate it using an anonymous inner class that implements `speak()` to print `"Moo!"`.

**Expected Output:**
```
Moo!
```

**Concepts:** Anonymous inner class implementing an abstract class, inline instantiation

---

## 🟡 Medium (3 Questions)

---

### Q6. Partial Abstract Implementation
Create the following hierarchy:
- `abstract class Car` — abstract methods: `drive()`, `fly()`; concrete method `playMusic()` prints `"Music playing"`
- `abstract class WagnoR extends Car` — implements `drive()` printing `"Driving WagnoR"`, but leaves `fly()` abstract
- `class FlyingWagnoR extends WagnoR` — implements `fly()` printing `"Flying WagnoR"`

In `main`, use `Car obj = new FlyingWagnoR()` and call all three methods.

**Expected Output:**
```
Driving WagnoR
Flying WagnoR
Music playing
```

**Concepts:** Partial implementation in intermediate abstract class, final concrete class, upcasting to abstract type

---

### Q7. Anonymous Inner Class — Multiple Methods
Create an abstract class `Worker` with two abstract methods: `doWork()` and `getReport()`.  
In `main`, instantiate it with an anonymous inner class that:
- Implements `doWork()` to print `"Working hard..."`
- Implements `getReport()` to print `"Report: All done"`

Call both methods on the object.

**Expected Output:**
```
Working hard...
Report: All done
```

**Concepts:** Anonymous inner class implementing multiple abstract methods, all must be implemented

---

### Q8. Non-static Inner Class vs Static Nested Class
Create an outer class `Library`:
- A non-static inner class `Book` with a field `title` and a method `read()` printing `"Reading: <title>"`
- A static nested class `Catalogue` with a method `list()` printing `"Listing all books"`

In `main`, demonstrate:
1. Creating `Book` using an outer object: `Library.Book b = lib.new Book();`
2. Creating `Catalogue` directly: `Library.Catalogue cat = new Library.Catalogue();`

**Expected Output:**
```
Reading: Clean Code
Listing all books
```

**Concepts:** Non-static inner class (needs outer instance), static nested class (no outer instance needed)

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Abstract + Polymorphism
**Before running**, predict the exact output:

```java
abstract class Appliance {
    String brand = "Generic";

    Appliance() {
        System.out.println("Appliance created: " + brand);
    }

    public abstract void operate();

    public void powerOn() {
        System.out.println("Powering on: " + brand);
        operate();
    }
}

class WashingMachine extends Appliance {
    WashingMachine() {
        brand = "Samsung";
        System.out.println("WashingMachine created: " + brand);
    }

    public void operate() {
        System.out.println("Washing clothes with " + brand);
    }
}

public class Demo {
    public static void main(String[] args) {
        Appliance a = new WashingMachine();
        a.powerOn();
    }
}
```

Answer in comments:
1. Why does `Appliance created: Generic` print even though we only called `new WashingMachine()`?
2. In `powerOn()`, which version of `operate()` is called — `Appliance`'s or `WashingMachine`'s? Why?
3. What happens at the line `brand = "Samsung"` — is this a new variable or the inherited one?

**Concepts:** Abstract class constructor, super constructor call chain, dynamic dispatch inside a concrete method, inherited fields

---

### Q10. Strategy Pattern Using Anonymous Classes
Create an abstract class `SortStrategy` with abstract method `sort(int[] arr)`.

In `main`, write three anonymous inner class implementations for:
1. **Bubble Sort strategy** — implements a simple bubble sort
2. **Reverse Sort strategy** — sorts in reverse (descending) using bubble sort
3. **No-op strategy** — does nothing, just prints `"No sort applied"`

Create a method (static or inline) `applySorting(SortStrategy s, int[] arr)` that calls `s.sort(arr)` and then prints the array.

Call all three strategies on the same array `{5, 2, 8, 1, 9}`.

**Constraints:** No Java built-in sort. Use only concepts from this folder and previous ones.

**Concepts:** Abstract class as a strategy type, anonymous inner class as pluggable implementations, polymorphism through abstract class reference

---

*Practice file for 01. CoreJava / 10. Abstract And Inner Classes — Telusko Java Tutorial*
