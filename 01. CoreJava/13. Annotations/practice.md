# 🧪 Practice Questions — Annotations

> Topics covered: `@Override` (intent enforcement, catching typos), `@Deprecated` (marking outdated code, compiler warning), `@FunctionalInterface` (SAM enforcement), annotation purpose as metadata

---

## 🟢 Easy (5 Questions)

---

### Q1. `@Override` — Catches Typos
Create class `Animal` with method `makeSound()` printing `"Some animal sound"`.  
Create `Dog extends Animal` and add `@Override` before the overriding `makeSound()` that prints `"Woof!"`.  
In `main`, call `makeSound()` on a `Dog` object.

**Expected Output:**
```
Woof!
```

Then, in a comment, write what would happen if you misspelled the method as `makSound()` with `@Override` present.

**Concepts:** `@Override`, compile-time error on mismatch, intent documentation

---

### Q2. `@Deprecated` — Compiler Warning
Create a class `OldCalculator` with a `@Deprecated` method `add(int a, int b)` that returns `a + b`.  
Create a class `NewCalculator` with a normal `add(int a, int b)`.  
In `main`, use both. Add a comment explaining what compiler warning appears when you call the deprecated method.

**Expected Output:**
```
7
7
```

**Concepts:** `@Deprecated`, code still works, compiler warning for users

---

### Q3. `@FunctionalInterface` — Single Method Enforcement
Create a `@FunctionalInterface` interface `Greetable` with one abstract method `greet(String name)`.  
Implement it using an anonymous inner class in `main` and call `greet("Navin")`.

**Expected Output:**
```
Hello, Navin!
```

Then, in a comment, write the second abstract method you would add that would cause a compile error.

**Concepts:** `@FunctionalInterface`, SAM, compile error on second abstract method

---

### Q4. `@Override` in Multilevel Inheritance
Create `A` with `show()` printing `"A"`.  
Create `B extends A` with `@Override show()` printing `"B"`.  
Create `C extends B` with `@Override show()` printing `"C"`.  
In `main`, call `show()` on a `C` object.

**Expected Output:**
```
C
```

**Concepts:** `@Override` through multilevel chain

---

### Q5. Annotations as Metadata — No Logic Change
Create a class `LegacyCode` marked `@Deprecated` with a method `compute()` that prints `"Computing..."`.  
In `main`, use it normally and verify the output is unchanged by the annotation.

**Expected Output:**
```
Computing...
```

Add a comment: "Annotations do not change what the code does — they provide information to the compiler and tools."

**Concepts:** Annotations as metadata, no runtime behaviour change

---

## 🟡 Medium (3 Questions)

---

### Q6. `@Override` Bug Catcher — The Key Value
Recreate the scenario from the source code:

Create class `A` with a long method name:  
`showTheDataWhichBelongsToThisClass()` printing `"in show A"`.

Create `B extends A` and try overriding with a **one-letter typo** in the name:  
`showTheDataWhichBelongToThisClass()` (missing `s`).

**Without `@Override`**: Write a comment noting what happens — a new method is silently created, not an override.  
**With `@Override`**: Uncomment it and show the compile error is caught.

Fix the typo, add `@Override`, and call the correct method from `main`.

**Expected Output:**
```
in show B
```

**Concepts:** The practical value of `@Override`, silent bug without it, compile error with it

---

### Q7. Combining `@Deprecated` and `@Override`
Create:
- `class OldShape` (marked `@Deprecated`) with method `draw()` printing `"Old drawing"`
- `class NewShape extends OldShape` with `@Override draw()` printing `"New drawing"`

In `main`, use a `NewShape` object and call `draw()`.  
In a comment: "Even though `OldShape` is deprecated, its subclass `NewShape` can still extend it and override methods."

**Expected Output:**
```
New drawing
```

**Concepts:** `@Deprecated` class can still be extended, `@Override` still works

---

### Q8. `@FunctionalInterface` with Lambda Preview
Create `@FunctionalInterface Runnable2` with `void execute()`.  
In `main`, create a reference using an anonymous inner class that prints `"Task executed"`.  
Then create another reference using a lambda expression: `Runnable2 r2 = () -> System.out.println("Lambda task");`  
Call both.

**Expected Output:**
```
Task executed
Lambda task
```

**Concepts:** `@FunctionalInterface`, anonymous class vs lambda (both work), SAM contract

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — `@Override` Without the Annotation
**Predict what happens** (not a runtime output question — a design question):

```java
class Base {
    public void process() {
        System.out.println("Base process");
    }
}

class Child extends Base {
    // No @Override annotation
    public void proces() {   // typo: 'proces' not 'process'
        System.out.println("Child process");
    }
}

public class Demo {
    public static void main(String[] args) {
        Base obj = new Child();
        obj.process();
    }
}
```

Without `@Override`:
1. Does this compile? What is the output?
2. Is `Child.proces()` an override or a new method?
3. What does dynamic dispatch do here — which `process()` runs?

Now rewrite `Child.proces()` with `@Override` and explain what happens.

**Concepts:** Silent override failure, dynamic dispatch, `@Override` as safety net

---

### Q10. Design Question — When to Use Each Annotation
Write a Java program that demonstrates all three annotations (`@Deprecated`, `@Override`, `@FunctionalInterface`) in a **single coherent scenario**:

- A `@Deprecated` interface `OldProcessor` with method `run()`
- A `@FunctionalInterface` interface `NewProcessor` with method `execute()`
- A class `ModernTask implements NewProcessor` with `@Override execute()` printing `"Executing modern task"`
- In `main`, use `ModernTask` via a `NewProcessor` reference

In comments:
1. Why is `OldProcessor` deprecated?
2. Why is `@FunctionalInterface` useful for `NewProcessor`?
3. What does `@Override` guarantee in `ModernTask`?

**Expected Output:**
```
Executing modern task
```

**Concepts:** All three annotations together, their individual purpose, real-world design rationale

---

*Practice file for 01. CoreJava / 13. Annotations — Telusko Java Tutorial*
