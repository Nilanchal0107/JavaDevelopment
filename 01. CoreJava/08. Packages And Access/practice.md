# 🧪 Practice Questions — Packages And Access Modifiers

> Topics covered: `package` declaration, sub-packages, `import` (single class vs wildcard), `java.lang` auto-import, `private`, default (package-private), `protected`, `public` access modifiers, access rules across classes and packages

---

## 🟢 Easy (5 Questions)

---

### Q1. Understand `public` vs `private`
Create a class `Person` with:
- `public String name`
- `private int age`
- A `public` getter `getAge()` and setter `setAge(int age)`

In `main`, try accessing `name` directly (works), and access `age` via getter/setter only.

**Expected Output:**
```
Navin
30
```

**Concepts:** `public` vs `private` access, getter/setter

---

### Q2. Default (Package-Private) Access
Create two classes **in the same file** (same package):
- `class Engine` with a default-access field `int horsepower = 150`
- `class Car` with a method `showPower()` that creates an `Engine` object and prints its `horsepower`

In `main`, call `showPower()`.

**Expected Output:**
```
150
```

**Concepts:** Default (package-private) access, same-package visibility

---

### Q3. `protected` in a Subclass
Create:
- `class Animal` with a `protected String sound = "..."` 
- `class Dog extends Animal` with a method `makeSound()` that prints `sound`

In `main`, create a `Dog` and call `makeSound()`.

**Expected Output:**
```
Woof
```

**Concepts:** `protected` access, subclass visibility

---

### Q4. `java.lang` is Auto-Imported
Write a program that uses `Math.sqrt()`, `System.out.println()`, and creates a `String` — all from `java.lang` — **without any import statement**.  
Print the square root of 144.

**Expected Output:**
```
12.0
```

**Concepts:** `java.lang` auto-import, built-in classes

---

### Q5. Wildcard vs Specific Import
Write a program with a comment block showing both forms of importing `java.util.ArrayList`:
1. Specific: `import java.util.ArrayList;`
2. Wildcard: `import java.util.*;`

Create an `ArrayList<String>`, add 3 names, and print each using a for-each loop.

**Expected Output:**
```
Navin
Reddy
Java
```

**Concepts:** Import syntax, wildcard vs specific import

---

## 🟡 Medium (3 Questions)

---

### Q6. Access Modifier Scope Table — Verify in Code
Create the following class setup **all in one package** (no sub-packages needed):

```java
class Base {
    private int a = 1;
    int b = 2;            // default
    protected int c = 3;
    public int d = 4;
}
class Child extends Base {
    void printValues() {
        // try accessing a, b, c, d here
    }
}
class Other {
    void printValues() {
        Base obj = new Base();
        // try accessing obj.a, obj.b, obj.c, obj.d here
    }
}
```

For each field (`a`, `b`, `c`, `d`), write a comment stating whether it's accessible in `Child.printValues()` and `Other.printValues()` and **why**.  
Only print the fields that actually compile.

**Expected Output (accessible fields from Child):**
```
b=2  c=3  d=4
```

**Concepts:** All four access modifiers side-by-side, `private` not inherited

---

### Q7. Package Declaration Practice
Imagine you are building a project called `com.myapp`. Create **two conceptual classes** (write them in a single file for simplicity, using comments to simulate package separation):

- `package com.myapp.utils;` → class `MathUtil` with `public static int square(int n)`
- `package com.myapp.main;` → class `Demo` that imports `com.myapp.utils.MathUtil` and calls `MathUtil.square(5)`

Write the code with the package and import statements as comments, then write valid single-file code demonstrating the same functionality.

**Expected Output:**
```
25
```

**Concepts:** Package structure, import statements, `public static` method

---

### Q8. `protected` Across Inheritance — Predict Access
Create:
- `class Vehicle` (in the same package) with `protected int speed = 120`
- `class Truck extends Vehicle` with a method `showSpeed()` accessing `speed` via `super.speed`
- `class Demo` (same package) that accesses `speed` directly via a `Vehicle` object

In `main`, demonstrate both accesses and print values.

**Expected Output:**
```
120
120
```

Add a comment: "If `Truck` were in a different package, could it still access `speed`?"

**Concepts:** `protected` access from subclass, `protected` access from same-package non-subclass

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Compile Errors
The following code has several access violations. **Without running it**, identify every line that will fail to compile and explain why:

```java
class Secret {
    private int pin = 1234;
    int code = 5678;           // default
    protected String key = "abc";
    public String name = "Secret";
}

class Spy extends Secret {
    void reveal() {
        System.out.println(pin);    // Line A
        System.out.println(code);   // Line B
        System.out.println(key);    // Line C
        System.out.println(name);   // Line D
    }
}

class Outside {
    void peek() {
        Secret s = new Secret();
        System.out.println(s.pin);  // Line E
        System.out.println(s.code); // Line F
        System.out.println(s.key);  // Line G
        System.out.println(s.name); // Line H
    }
}
```

List each line (A through H), whether it compiles, and the reason.

**Concepts:** All four access modifiers, inheritance access rules, object access vs inherited access

---

### Q10. Design a Proper Access Structure
Design a class `BankAccount` where:
- `private double balance` — no direct outside access
- `private String accountNumber` — no direct outside access
- `protected String ownerName` — accessible to subclasses
- `public String bankName` — accessible everywhere
- `public` getter for `balance` — returns the current balance
- `public` setter for `balance` — only accepts positive values, rejects negatives with a message
- A `protected` method `generateStatement()` that prints all four fields (accessible to subclasses)

Create a subclass `SavingsAccount extends BankAccount` that:
- Has an additional `private double interestRate`
- Overrides/extends `generateStatement()` by calling `super.generateStatement()` then printing interest rate

In `main`, create a `SavingsAccount`, set all values, and call `generateStatement()`.

**Constraints:** No Scanner. Every field access must go through the correct modifier.

**Concepts:** All four access modifiers in a realistic design, `protected` method inheritance, encapsulation

---

*Practice file for 01. CoreJava / 08. Packages And Access — Telusko Java Tutorial*
