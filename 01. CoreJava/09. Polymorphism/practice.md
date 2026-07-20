# 🧪 Practice Questions — Polymorphism

> Topics covered: compile-time polymorphism (overloading), runtime polymorphism (overriding), Dynamic Method Dispatch, `final` variable/method/class, `Object` class methods (`toString()`, `equals()`, `hashCode()`), upcasting (implicit), downcasting (explicit), `instanceof`, `ClassCastException`, autoboxing, autounboxing, wrapper classes

---

## 🟢 Easy (5 Questions)

---

### Q1. Basic Autoboxing and Autounboxing
In `main`:
1. Declare `int a = 42;`
2. Assign it to `Integer boxed = a;` (autoboxing)
3. Declare `int unboxed = boxed;` (autounboxing)
4. Print both `boxed` and `unboxed`.

**Expected Output:**
```
42
42
```

**Concepts:** Autoboxing (primitive → wrapper), autounboxing (wrapper → primitive)

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

### Q7. Downcasting with `instanceof`
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

### Q8. Parse and Add — Wrapper Utilities
In `main`:
1. Create two Strings: `"25"` and `"75"`.
2. Use `Integer.parseInt()` to convert both to `int`.
3. Add them and print the result.
4. Also print `Integer.MAX_VALUE` and `Integer.MIN_VALUE`.

**Expected Output:**
```
100
2147483647
-2147483648
```

**Concepts:** `Integer.parseInt()`, wrapper class utility methods, `MAX_VALUE`, `MIN_VALUE`

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Autoboxing Equality Trap
**Before running**, predict the exact output:

```java
public class Demo {
    public static void main(String[] args) {
        Integer a = 127;
        Integer b = 127;
        Integer c = 128;
        Integer d = 128;

        System.out.println(a == b);       // Line 1
        System.out.println(c == d);       // Line 2
        System.out.println(c.equals(d));  // Line 3
    }
}
```

Answer in comments:
1. Why does Line 1 print `true` but Line 2 prints `false`?
2. What is the **Integer cache** in Java, and what range does it cover?
3. Why should you always use `.equals()` instead of `==` when comparing wrapper objects?

**Concepts:** Integer cache (-128 to 127), reference comparison vs content comparison, `.equals()` vs `==` on wrapper types

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
