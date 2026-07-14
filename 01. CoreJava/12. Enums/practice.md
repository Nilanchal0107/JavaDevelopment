# 🧪 Practice Questions — Enums

> Topics covered: enum declaration, enum constants, `ordinal()`, `values()`, `name()`, enum with `switch`, enum with `if-else`, enum as a class (fields, private constructors, getters/setters)

---

## 🟢 Easy (5 Questions)

---

### Q1. Basic Enum Declaration
Declare an enum `Direction` with constants: `North`, `South`, `East`, `West`.  
In `main`, assign `Direction.East` to a variable and print it along with its `ordinal()`.

**Expected Output:**
```
East
2
```

**Concepts:** Enum declaration, constant access, `ordinal()`

---

### Q2. Iterate All Enum Values
Using the `Status` enum (`Running`, `Failed`, `Pending`, `Success`), print each constant and its ordinal using `values()` and a for-each loop.

**Expected Output:**
```
Running : 0
Failed : 1
Pending : 2
Success : 3
```

**Concepts:** `values()`, for-each loop on enum, `ordinal()`

---

### Q3. Enum in `switch`
Using `Status s = Status.Failed`, write a `switch` statement that prints:
- `Running` → `"All Good"`
- `Failed` → `"Try Again"`
- `Pending` → `"Please Wait"`
- default → `"Done"`

**Expected Output:**
```
Try Again
```

**Concepts:** Enum with `switch`, case labels without type prefix

---

### Q4. Enum in `if-else`
Repeat Q3 using an `if-else if` chain instead of `switch` with `Status s = Status.Success`.

**Expected Output:**
```
Done
```

**Concepts:** Enum with `if-else`, `==` comparison on enums

---

### Q5. `name()` Method
Declare `Laptop lap = Laptop.XPS`.  
Print `lap.name()` and `lap.ordinal()`.

**Expected Output:**
```
XPS
1
```

**Concepts:** `name()` built-in method, ordinal position

---

## 🟡 Medium (3 Questions)

---

### Q6. Enum Class with Field and Getter
Create an enum `Planet` with constants: `Mercury(3.7)`, `Earth(9.8)`, `Mars(3.7)`.  
Each constant holds a `gravity` (double) field.  
Add a `private` double field, a `private` parameterized constructor, and a `getGravity()` method.  
In `main`, print each planet's name and gravity using `values()`.

**Expected Output:**
```
Mercury : 3.7
Earth : 9.8
Mars : 3.7
```

**Concepts:** Enum as class, private constructor, parameterized constants, getter

---

### Q7. Enum with Default and Parameterized Constructor
Recreate the `Laptop` enum from the notes:  
`Mackbook(2000)`, `XPS(2200)`, `Surface` (no price — defaults to 500), `ThinkPad(1800)`.  
Loop through `values()` and print each laptop name and price.

**Expected Output:**
```
Mackbook : 2000
XPS : 2200
Surface : 500
ThinkPad : 1800
```

**Concepts:** Enum with multiple constructors, default constructor for constants without values

---

### Q8. Enum-Based State Machine
Create an enum `TrafficLight` with constants `Red`, `Yellow`, `Green`.  
Write a method `nextLight(TrafficLight current)` that returns the next light in order (Green → Red cycles back).  
In `main`, start from `Red`, print the sequence 4 times.

**Expected Output:**
```
Red
Yellow
Green
Red
```

**Constraints:** Use `switch` or `ordinal()` inside `nextLight()`. No Scanner.

**Concepts:** Enum in logic, cycling through enum constants, switch on enum

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Enum Ordinals and Comparison
**Before running**, predict the exact output:

```java
enum Season { Spring, Summer, Autumn, Winter; }

public class Demo {
    public static void main(String[] args) {
        Season s1 = Season.Summer;
        Season s2 = Season.Summer;
        Season s3 = Season.Winter;

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1.equals(s2));
        System.out.println(s3.ordinal());
        System.out.println(Season.values().length);

        for (Season s : Season.values()) {
            if (s.ordinal() % 2 == 0)
                System.out.println(s.name());
        }
    }
}
```

Answer in comments:
1. Why is `s1 == s2` true even though they are objects?
2. What does `s3.ordinal()` return?
3. Which constants are printed by the loop and why?

**Concepts:** Enum singleton guarantee, `==` on enums, `ordinal()`, `values()`, loop with condition

---

### Q10. Full Enum Class — Product Catalogue
Create an enum `Product` with at least 4 constants, each holding `name` (String) and `price` (double):
- `LAPTOP("Laptop", 1200.0)`
- `PHONE("Phone", 800.0)`
- `TABLET("Tablet", 500.0)`
- `WATCH("Watch", 250.0)`

Add:
- A `getPrice()` and `getName()` method
- A `setPrice(double price)` method that updates the price and prints `"Price updated for <name>"`
- A `static` method `cheaperThan(double budget)` that prints all products below the given price

In `main`:
1. Print all products using `values()`.
2. Call `cheaperThan(600.0)`.
3. Update `WATCH` price to 300.0 using `setPrice()`.
4. Print `WATCH`'s updated price.

**Concepts:** Enum as full class, static method in enum, mutable field via setter, `name()` inside enum method

---

*Practice file for 01. CoreJava / 12. Enums — Telusko Java Tutorial*
