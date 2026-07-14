# 🧪 Practice Questions — Polymorphism

> Topics covered: compile-time polymorphism (overloading), runtime polymorphism (overriding), Dynamic Method Dispatch, `final` variable/method/class, `Object` class methods (`toString()`, `equals()`, `hashCode()`), upcasting (implicit), downcasting (explicit), `instanceof`, `ClassCastException`

---

## 🟢 Easy (5 Questions)

---

### Q1. Dynamic Method Dispatch — Three Shapes
Create:
- Class `Shape` with method `draw()` printing `"Drawing shape"`
- Class `Circle extends Shape` overriding `draw()` to print `"Drawing circle"`
- Class `Square extends Shape` overriding `draw()` to print `"Drawing square"`

In `main`, use a **single `Shape` reference** `obj` and assign it to a `Shape`, then a `Circle`, then a `Square` object — calling `draw()` after each assignment.

**Expected Output:**
```
Drawing shape
Drawing circle
Drawing square
```

**Concepts:** Dynamic method dispatch, runtime polymorphism, single reference — multiple behaviours

---

### Q2. Override `toString()`
Create a class `Student` with fields `name` (String) and `marks` (int).  
Override `toString()` to return `"<name> : <marks>"`.  
In `main`, print the object directly (not `obj.toString()`) — Java calls `toString()` automatically.

**Expected Output:**
```
Navin : 88
```

**Concepts:** `toString()` override, `Object` class, `println` calling `toString()` automatically

---

### Q3. Override `equals()` for Content Comparison
Create a class `Book` with fields `title` (String) and `isbn` (int).  
Override `equals(Book that)` to return `true` if both `title` and `isbn` match.  
In `main`, create two `Book` objects with the same values and print `book1.equals(book2)`.

**Expected Output:**
```
true
```

**Concepts:** `equals()` override, content comparison vs reference comparison

---

### Q4. `final` Variable
In `main`, declare `final int MAX = 100`.  
Print the value.  
Then write (as a comment) the line that would try to change it, and explain in a comment why it fails.

**Expected Output:**
```
100
```

**Concepts:** `final` variable, immutable after assignment

---

### Q5. Upcasting
Create class `Animal` with method `breathe()` printing `"Breathing..."`.  
Create class `Dog extends Animal` with method `bark()` printing `"Woof!"`.  
In `main`, upcast: `Animal obj = new Dog();` and call `breathe()`.  
Also explain in a comment why `obj.bark()` would be a compile error.

**Expected Output:**
```
Breathing...
```

**Concepts:** Upcasting, parent-type reference limits method access to parent methods

---

## 🟡 Medium (3 Questions)

---

### Q6. Dynamic Dispatch — Polymorphic Array
Create a class `Shape` with abstract-like overriding:
- `Shape`, `Circle`, `Rectangle`, `Triangle` — all with a `draw()` method printing their own name

In `main`, create a `Shape[]` array of size 3, assign a `Circle`, `Rectangle`, and `Triangle` to it.  
Loop through the array and call `draw()` on each element.

**Expected Output:**
```
Drawing circle
Drawing rectangle
Drawing triangle
```

**Concepts:** Polymorphic array, dynamic dispatch in a loop, one interface — many implementations

---

### Q7. `final` Method — Prevent Override
Create class `Vehicle` with a `final` method `fuelType()` that prints `"Petrol"`.  
Create class `Car extends Vehicle`.  
In `main`, create a `Car` and call `fuelType()`.  
In a comment, write what would happen (compile error) if you tried to override `fuelType()` in `Car`.

**Expected Output:**
```
Petrol
```

**Concepts:** `final` method, cannot be overridden

---

### Q8. Downcasting with `instanceof`
Create:
- Class `A` with method `show1()` printing `"A show"`
- Class `B extends A` with method `show2()` printing `"B show"`

In `main`:
1. Upcast: `A obj = new B();` — call `show1()`
2. Check `instanceof B` before downcasting, then call `show2()`
3. Create a plain `A obj2 = new A();`, check `obj2 instanceof B` — should be `false` — do NOT downcast

**Expected Output:**
```
A show
B show
false
```

**Concepts:** Upcasting, `instanceof`, safe downcasting

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Dynamic Dispatch Tracing
**Before running**, predict the exact output:

```java
class A {
    public void show() { System.out.println("A show"); }
    public void display() { System.out.println("A display"); }
}
class B extends A {
    public void show() { System.out.println("B show"); }
}
class C extends B {
    public void show() { System.out.println("C show"); }
    public void display() { System.out.println("C display"); }
}

public class Demo {
    public static void main(String[] args) {
        A obj1 = new A();
        A obj2 = new B();
        A obj3 = new C();

        obj1.show();
        obj2.show();
        obj3.show();
        obj1.display();
        obj2.display();
        obj3.display();
    }
}
```

Answer in comments:
1. For `obj2.display()` — `B` doesn't override `display()`. Which version runs?
2. For `obj3.display()` — `C` overrides `display()`. Does the reference type (`A`) affect which version runs?
3. What is the difference between what the compiler checks and what the JVM actually executes?

**Concepts:** Dynamic method dispatch, method resolution order, reference type vs object type

---

### Q10. Full Object Class Override + Downcast System
Create a class `Product` with:
- Fields: `id` (int), `name` (String), `price` (double)
- Override `toString()` to return `"[id] name - $price"`
- Override `equals(Product p)` to return `true` if `id` matches

Create a subclass `DiscountedProduct extends Product` with:
- Additional field: `discount` (double, as a percentage)
- Override `toString()` to call `super.toString()` and append `" (discount: <discount>%)"`

In `main`:
1. Create a `Product[]` array with 3 items (mix of `Product` and `DiscountedProduct`).
2. Loop and print each using `toString()` via the array reference.
3. Use `instanceof` to identify `DiscountedProduct` items and downcast to access the `discount` field specifically.
4. Check equality of two products with the same `id`.

**Concepts:** `toString()`, `equals()`, upcasting in array, `instanceof`, downcasting, polymorphic collection

---

*Practice file for 01. CoreJava / 09. Polymorphism — Telusko Java Tutorial*
