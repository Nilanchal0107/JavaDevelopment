# 📘 Fundamentals of OOP — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Class and Object — Theory](#1-class-and-object--theory)
2. [Class and Object — Practical](#2-class-and-object--practical)
3. [JDK, JRE, JVM](#3-jdk-jre-jvm)
4. [Methods](#4-methods)
5. [Method Overloading](#5-method-overloading)
6. [Stack and Heap Memory](#6-stack-and-heap-memory)

---

## 1. Class and Object — Theory

### What is Object-Oriented Programming (OOP)?
OOP is a programming paradigm that organizes code around **objects** rather than logic and functions.

### Object
An object is a real-world entity that has:
- **Properties** (data / attributes / instance variables) — what it *has*
- **Behaviors** (methods / functions) — what it *does*

**Examples:**
| Object  | Properties               | Behaviors                    |
|---------|--------------------------|------------------------------|
| Car     | color, speed, brand      | drive(), brake(), honk()     |
| Student | name, rollno, marks      | study(), giveExam()          |
| Phone   | model, battery, storage  | call(), takephoto()          |

### Class
A **class** is a **blueprint or template** for creating objects.  
It defines what properties and behaviors an object of that type will have.

```
Class  →  Blueprint
Object →  Instance (real thing created from the blueprint)
```

> 💡 A class is like a cookie cutter; objects are the cookies made from it. You can create many objects from one class.

---

## 2. Class and Object — Practical

### Defining a Class
```java
class Calculator {
    public int add(int n1, int n2) {
        int r = n1 + n2;
        return r;
    }
}
```

### Creating an Object
```java
Calculator calc = new Calculator();
```
- `Calculator` → the class (type)
- `calc` → the reference variable (name)
- `new Calculator()` → creates a new object in memory

### Calling a Method on an Object
```java
int result = calc.add(4, 5);
System.out.println(result);   // Output: 9
```

### Full Example
```java
class Calculator {
    public int add(int n1, int n2) {
        return n1 + n2;
    }
}

class Demo {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        int result = calc.add(4, 5);
        System.out.println(result);   // 9
    }
}
```

> 💡 A Java file can have multiple classes but only **one `public` class**, and it must match the filename.

---

## 3. JDK, JRE, JVM

These three make up the Java ecosystem:

| Term | Full Form                  | Role                                                                 |
|------|----------------------------|----------------------------------------------------------------------|
| **JDK** | Java Development Kit    | Used to **write and compile** Java programs. Includes compiler (`javac`), JRE, and developer tools. |
| **JRE** | Java Runtime Environment | Used to **run** Java programs. Contains JVM + standard libraries.  |
| **JVM** | Java Virtual Machine    | **Executes** the bytecode (`.class` file). Makes Java platform-independent. |

### Relationship:
```
JDK  ⊇  JRE  ⊇  JVM
```

- **Developers** need JDK (to write and compile code).
- **End-users** only need JRE (to run the app).
- JVM is what actually *runs* the bytecode on any OS.

> 💡 This is why Java is **"Write Once, Run Anywhere"** — the JVM translates bytecode for each specific OS.

---

## 4. Methods

A **method** is a named block of code inside a class that performs a specific task.

### Syntax
```java
accessModifier returnType methodName(parameters) {
    // method body
    return value;   // only if returnType is not void
}
```

### Types of Methods

#### 1. `void` method — returns nothing
```java
public void playMusic() {
    System.out.println("Music Playing...");
}
```

#### 2. Method with return value
```java
public String getMeAPen(int cost) {
    if (cost >= 10)
        return "Pen";
    else
        return "Nothing";
}
```

### Calling Methods
```java
Computer obj = new Computer();

obj.playMusic();                      // void — just call it
String result = obj.getMeAPen(10);    // has return value — store it
System.out.println(result);           // Output: Pen
```

### Key Points
- A method with a non-void return type **must** have a `return` statement.
- Methods promote **code reusability** — write once, call many times.
- Parameters are **local** to the method; they only exist inside it.

---

## 5. Method Overloading

**Method Overloading** means having **multiple methods with the same name** but **different parameter lists** in the same class.

Java decides which method to call at **compile time** based on the arguments passed (**static/compile-time polymorphism**).

### Rules for Overloading
A method can be overloaded by changing:
1. **Number of parameters** — `add(int a, int b)` vs `add(int a, int b, int c)`
2. **Type of parameters** — `add(int a, int b)` vs `add(double a, int b)`
3. **Order of parameters** — `add(int a, double b)` vs `add(double a, int b)`

> ⚠️ Changing **only the return type** does NOT count as overloading.

### Example
```java
class Calculator {
    public int add(int n1, int n2) {
        return n1 + n2;
    }

    public int add(int n1, int n2, int n3) {
        return n1 + n2 + n3;
    }

    public double add(double n1, int n2) {
        return n1 + n2;
    }
}

public class Demo {
    public static void main(String[] args) {
        Calculator obj = new Calculator();
        int r1 = obj.add(3, 4);          // calls add(int, int)    → 7
        // obj.add(3, 4, 5)              // calls add(int, int, int)
        // obj.add(3.5, 4)               // calls add(double, int)
        System.out.println(r1);          // Output: 7
    }
}
```

---

## 6. Stack and Heap Memory

Java uses two main memory areas at runtime:

### Stack Memory
- Stores **local variables** and **method call frames**.
- Each method call creates a new **stack frame**.
- Memory is automatically freed when the method returns (**LIFO** — Last In, First Out).
- Primitive variables (like `int data = 10`) live here.

### Heap Memory
- Stores all **objects** created with `new`.
- Objects persist in heap until garbage collected.
- **Instance variables** (fields of a class) live inside the object on the heap.
- **Each object gets its own copy** of instance variables.

### Key Demonstration
```java
class Calculator {
    int num = 5;   // instance variable — lives on Heap, inside each object
}

public class Demo {
    public static void main(String[] args) {
        int data = 10;                   // local variable → Stack
        Calculator obj  = new Calculator(); // obj  → Stack (reference)
        Calculator obj1 = new Calculator(); // obj1 → Stack (reference)
        // Both objects live on Heap with their own 'num'

        obj.num = 8;   // changes only obj's copy

        System.out.println(obj.num);    // Output: 8
        System.out.println(obj1.num);   // Output: 5 (unaffected!)
    }
}
```

### Memory Diagram
```
     Stack                   Heap
  ┌─────────┐           ┌──────────────┐
  │  data=10│           │ Calculator   │ ← obj points here
  │  obj ───┼──────────►│   num = 8    │
  │  obj1 ──┼──────┐    └──────────────┘
  └─────────┘      │    ┌──────────────┐
                   └───►│ Calculator   │ ← obj1 points here
                        │   num = 5    │
                        └──────────────┘
```

> 💡 Two objects from the same class are **completely independent** in memory. Changing one doesn't affect the other.

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 02. Fundamentals of OOP*
