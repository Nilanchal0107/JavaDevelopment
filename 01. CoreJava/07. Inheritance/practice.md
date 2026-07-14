# 🧪 Practice Questions — Inheritance

> Topics covered: "is-a" vs "has-a", `extends`, single inheritance, multilevel inheritance, method reuse from parent, multiple inheritance (why not supported, Diamond Problem), method overriding, overriding vs overloading

---

## 🟢 Easy (5 Questions)

---

### Q1. Basic Inheritance — Animal
Create a class `Animal` with a method `breathe()` that prints `"Breathing..."`.  
Create a class `Dog extends Animal` with a method `bark()` that prints `"Woof!"`.  
In `main`, create a `Dog` object and call both `breathe()` and `bark()`.

**Expected Output:**
```
Breathing...
Woof!
```

**Concepts:** `extends`, method inheritance, "is-a" relationship

---

### Q2. Calculator Inheritance
Create a class `Calc` with methods `add(int n1, int n2)` and `sub(int n1, int n2)`.  
Create `AdvCalc extends Calc` with methods `multi(int n1, int n2)` and `div(int n1, int n2)`.  
In `main`, create an `AdvCalc` object and call all four methods.

**Expected Output:**
```
9
4
15
3
```

**Concepts:** Single inheritance, method reuse

---

### Q3. Multilevel Inheritance — Shape
Create a 3-level chain:
- `Shape` → method `draw()` prints `"Drawing shape"`
- `TwoD extends Shape` → method `area()` prints `"Calculating 2D area"`
- `Circle extends TwoD` → method `circumference()` prints `"Calculating circumference"`

In `main`, create a `Circle` object and call all three methods.

**Expected Output:**
```
Drawing shape
Calculating 2D area
Calculating circumference
```

**Concepts:** Multilevel inheritance, method chain

---

### Q4. Method Overriding — Greet
Create a class `Person` with a method `greet()` that prints `"Hello, I am a Person."`.  
Create a class `Student extends Person` that overrides `greet()` to print `"Hello, I am a Student."`.  
In `main`, create a `Student` object and call `greet()`.

**Expected Output:**
```
Hello, I am a Student.
```

**Concepts:** Method overriding, child's version takes priority

---

### Q5. Override with Extra Logic — `super` Method Call
Modify Q4: in `Student.greet()`, first call `super.greet()` (to print the parent's message), then print `"Hello, I am a Student."`.

**Expected Output:**
```
Hello, I am a Person.
Hello, I am a Student.
```

**Concepts:** `super.methodName()`, extending parent behaviour while overriding

---

## 🟡 Medium (3 Questions)

---

### Q6. Multilevel Calculator — power()
Extend the `Calc → AdvCalc → VeryAdvCalc` chain from the notes.  
`VeryAdvCalc` should add a `power(int n1, int n2)` method using `Math.pow()`.

In `main`, create a `VeryAdvCalc` object and call all 5 methods:
- `add(4, 5)`, `sub(7, 3)`, `multi(5, 3)`, `div(15, 4)`, `power(4, 2)`

**Expected Output:**
```
9 4 15 3 16.0
```

**Concepts:** Multilevel inheritance, `Math.pow()`, transitive method access

---

### Q7. Overriding vs Overloading — Side by Side
Create a class `Printer`:
- `print(int n)` → prints `"int: <n>"`
- `print(String s)` → prints `"String: <s>"` (overloading)

Create `FancyPrinter extends Printer`:
- Override `print(int n)` to print `"Fancy int: <n>"` (overriding)

In `main`, using a `FancyPrinter` object, call:
1. `print(42)` — which version runs?
2. `print("hello")` — inherited from `Printer`?

**Expected Output:**
```
Fancy int: 42
String: hello
```

Add a comment explaining the difference between what happened in call 1 vs call 2.

**Concepts:** Method overriding, method overloading, inheritance + overloading interaction

---

### Q8. Preventing Override with `final`
Create a class `Base` with a `final` method `show()` that prints `"Base show"`.  
Try to override it in a child class `Child extends Base`.  
Write the code that demonstrates the compile error, and in a comment explain why Java disallows this.

**Constraints:** The actual submitted code should compile — comment out the override and explain what happens if you uncomment it.

**Concepts:** `final` method, preventing overriding

---

## 🔴 Hard (2 Questions)

---

### Q9. Diamond Problem — Predict and Explain
Java does not support multiple class inheritance. Consider the following pseudocode:

```
class A { void show() { print "A" } }
class B extends A { void show() { print "B" } }
class C extends A { void show() { print "C" } }
// class D extends B, C { }   // If this were allowed...
```

Write a valid Java program that:
1. Implements `A`, `B`, `C` as described.
2. Creates objects of `B` and `C` and calls `show()` on each.
3. In comments, explain: If `D` could extend both `B` and `C`, and you called `new D().show()`, which `show()` would run and why this causes the Diamond Problem.
4. Explain how Java resolves this for interfaces (briefly — even if interfaces aren't covered yet).

**Expected Output:**
```
B
C
```

**Concepts:** Diamond Problem, multiple inheritance limitation, why Java chose this design

---

### Q10. Predict the Output — Overriding + Constructor Chain
**Before running**, trace through and predict the exact output:

```java
class Animal {
    String type;
    Animal() {
        type = "Unknown";
        System.out.println("Animal constructor: " + type);
    }
    void speak() {
        System.out.println("Some animal sound");
    }
}

class Dog extends Animal {
    String name;
    Dog(String name) {
        // super() is called implicitly here
        this.name = name;
        System.out.println("Dog constructor: " + name);
    }
    void speak() {          // overrides Animal.speak()
        System.out.println(name + " says: Woof!");
    }
}

class GoldenRetriever extends Dog {
    GoldenRetriever(String name) {
        super(name);
        System.out.println("GoldenRetriever constructor: " + name);
    }
    void speak() {          // overrides Dog.speak()
        super.speak();
        System.out.println(name + " wags tail!");
    }
}

public class Demo {
    public static void main(String[] args) {
        GoldenRetriever g = new GoldenRetriever("Buddy");
        g.speak();
    }
}
```

Answer in comments:
1. List every constructor call in order with arrows.
2. Why does `super.speak()` inside `GoldenRetriever.speak()` call `Dog.speak()` and not `Animal.speak()`?
3. What would happen if `Dog.speak()` was removed — which `speak()` would `super.speak()` call then?

**Concepts:** Multilevel inheritance, implicit `super()`, method overriding chain, `super.method()`

---

*Practice file for 01. CoreJava / 07. Inheritance — Telusko Java Tutorial*
