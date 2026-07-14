# 🧪 Practice Questions — Lambda And Functional Interfaces

> Topics covered: `@FunctionalInterface`, SAM (Single Abstract Method), anonymous inner class implementation, lambda expression (no-param, single-param, multi-param), type inference in lambdas, lambda with return value (implicit and explicit), `Runnable` as functional interface, types of interfaces (Normal, Functional/SAM, Marker)

---

## 🟢 Easy (5 Questions)

---

### Q1. Basic Functional Interface — No Parameters
Declare a `@FunctionalInterface` called `Greeting` with method `void greet()`.  
Implement it using an **anonymous inner class** that prints `"Hello from anonymous class!"`.  
Call `greet()`.

**Expected Output:**
```
Hello from anonymous class!
```

**Concepts:** `@FunctionalInterface`, anonymous inner class implementation

---

### Q2. Lambda — No Parameters
Using the same `Greeting` interface from Q1, implement it using a **lambda expression** instead of an anonymous class.  
Print `"Hello from lambda!"`.

**Expected Output:**
```
Hello from lambda!
```

**Concepts:** Lambda with no parameters `() -> expression`

---

### Q3. Lambda — Single Parameter
Declare `@FunctionalInterface Printer` with `void print(String msg)`.  
Implement with a lambda and call `print("Learning Java Lambdas")`.

**Expected Output:**
```
Learning Java Lambdas
```

**Concepts:** Lambda with one parameter (parentheses optional)

---

### Q4. Lambda — Two Parameters
Declare `@FunctionalInterface MathOp` with `int operate(int a, int b)`.  
Implement three lambdas — one for addition, one for subtraction, one for multiplication.  
Call each with values `10` and `3`.

**Expected Output:**
```
13
7
30
```

**Concepts:** Lambda with multiple parameters, lambda with return value (implicit)

---

### Q5. Lambda Evolution — Three Styles
Declare `@FunctionalInterface Showable` with `void show(int i)`.  
Implement the same behaviour (`"in show " + i`) in three ways:
1. Anonymous inner class
2. Lambda with type: `(int i) -> ...`
3. Lambda without type: `i -> ...`

Call `show(5)` on each.

**Expected Output:**
```
in show 5
in show 5
in show 5
```

**Concepts:** Evolution from anonymous class to lambda, type inference

---

## 🟡 Medium (3 Questions)

---

### Q6. Lambda with Explicit Return
Declare `@FunctionalInterface Calculator` with `int add(int i, int j)`.  
Implement using:
1. A lambda with **implicit return**: `(i, j) -> i + j`
2. A lambda with **explicit return** using a block body: `(i, j) -> { return i + j; }`

Call `add(5, 4)` on each.

**Expected Output:**
```
9
9
```

**Concepts:** Implicit return (expression), explicit return (block body with `return`)

---

### Q7. `Runnable` as a Functional Interface
Implement `Runnable` using a lambda expression that prints numbers 1 to 5.  
Wrap it in a `Thread` and start it.  
Do the same with a second lambda that prints `"Hello"` 5 times and start both threads.

**Expected Output (order may vary):**
```
1
Hello
2
Hello
...
```

**Constraints:** Use lambdas — do not extend `Thread` or create a named `Runnable` class.

**Concepts:** `Runnable` as functional interface, lambda for thread task, `Thread(Runnable)` constructor

---

### Q8. Identify Interface Type
For each interface below, identify whether it is **Normal**, **Functional (SAM)**, or **Marker**, and explain why:

Write a Java program with these three interfaces and appropriate usage:
- `interface Saveable { }` — marker
- `@FunctionalInterface interface Transformable { String transform(String s); }` — functional
- `interface Shape { double area(); String color(); }` — normal

Implement `Transformable` with a lambda that converts a string to uppercase, and call it.

**Expected Output:**
```
HELLO JAVA
```

**Concepts:** Three types of interfaces, `@FunctionalInterface` enforcement, lambda on SAM

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Lambda Scope and Return
**Before running**, predict the exact output:

```java
@FunctionalInterface
interface Op {
    int compute(int x);
}

public class Demo {
    static int apply(Op op, int value) {
        return op.compute(value);
    }

    public static void main(String[] args) {
        Op doubler = x -> x * 2;
        Op squarer = x -> x * x;
        Op chain   = x -> doubler.compute(squarer.compute(x));

        System.out.println(apply(doubler, 5));
        System.out.println(apply(squarer, 5));
        System.out.println(apply(chain, 3));
        System.out.println(doubler.compute(3) + squarer.compute(3));
    }
}
```

Answer in comments:
1. What does `chain.compute(3)` do step by step?
2. What is `doubler.compute(3) + squarer.compute(3)`?
3. Is `Op` a valid `@FunctionalInterface`? Why?

**Concepts:** Lambda as object, composing lambdas, functional interface as method parameter

---

### Q10. Strategy Pattern with Lambdas
Create a `@FunctionalInterface SortStrategy` with `void sort(int[] arr)`.

Write a method `static void applySorting(SortStrategy s, int[] arr)` that calls `s.sort(arr)` then prints the array.

In `main`, define three lambda strategies for the same array `{5, 2, 8, 1, 9}`:
1. **Ascending bubble sort** (manually implemented)
2. **Descending sort** (reverse of ascending)
3. **No-op** (prints `"No sort applied"`, does not modify array)

Call `applySorting` with each and print results.

**Constraints:** No `Arrays.sort()` or `Collections.sort()`. Implement sorting manually in the lambda body.

**Concepts:** Lambda as a strategy/behaviour, passing lambdas to methods, multi-line lambda bodies, functional interface replacing anonymous class

---

*Practice file for 01. CoreJava / 14. Lambda And Functional Interfaces — Telusko Java Tutorial*
