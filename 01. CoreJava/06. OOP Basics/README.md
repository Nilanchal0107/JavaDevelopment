# 📘 OOP Basics — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Encapsulation](#1-encapsulation)
2. [Getters and Setters](#2-getters-and-setters)
3. [The `this` Keyword](#3-the-this-keyword)
4. [Constructor](#4-constructor)
5. [Default vs Parameterized Constructor](#5-default-vs-parameterized-constructor)
6. [`this()` and `super()` — Constructor Chaining](#6-this-and-super--constructor-chaining)
7. [`this` vs `super` Keyword](#7-this-vs-super-keyword)
8. [Naming Conventions](#8-naming-conventions)
9. [Anonymous Object](#9-anonymous-object)

---

## 1. Encapsulation

**Encapsulation** means **hiding the internal data** of a class from outside access by making fields `private`, and providing controlled access through public methods.

```java
class Human {
    private int age;      // hidden from outside — cannot do obj.age directly
    private String name;  // hidden from outside

    public int getAge() {
        return age;       // controlled read access
    }
    public void SetAge(int a) {
        age = a;          // controlled write access
    }
    public String getName() {
        return name;
    }
    public void setName(String n) {
        name = n;
    }
}

public class Demo {
    public static void main(String[] args) {
        Human obj = new Human();
        obj.SetAge(30);
        obj.setName("Reddy");
        // obj.age = 11;    // ❌ compile error — private field
        System.out.println(obj.getName() + " : " + obj.getAge());  // Output: Reddy : 30
    }
}
```

> 💡 Encapsulation lets you **validate** data inside setters (e.g., reject negative age), add logging, or change internal representation — all without affecting the caller.

---

## 2. Getters and Setters

Getters and setters are the **standard way to expose and modify private fields**.

The key improvement over simple setters (where you use a different parameter name like `a`) is using the **same parameter name as the field** and resolving the ambiguity with `this`:

```java
class Human {
    private int age;
    private String name;

    public int getAge() {
        return age;
    }
    public void SetAge(int age) {     // parameter name same as field name
        this.age = age;               // 'this.age' = field, 'age' = parameter
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;             // 'this.name' = field, 'name' = parameter
    }
}
```

**Usage:**
```java
Human obj = new Human();
obj.SetAge(30);
obj.setName("Reddy");
System.out.println(obj.getName() + " : " + obj.getAge());  // Output: Reddy : 30
```

> 💡 Java naming convention for getters: `getFieldName()`. For setters: `setFieldName(value)`. For boolean getters: `isFieldName()`.

---

## 3. The `this` Keyword

`this` is a reference to the **current object** — the object whose method or constructor is currently being executed.

### Use Case 1 — Disambiguate field vs parameter name

```java
public void SetAge(int age) {
    this.age = age;   // 'this.age' refers to the field; 'age' refers to the parameter
}
```

### Use Case 2 — Pass current object as argument (from the code)

```java
class Human {
    private int age;

    public void SetAge(int age, Human obj) {
        Human obj1 = obj;   // obj1 is now the same reference as the passed-in obj
        obj1.age = age;     // equivalent to: this.age = age
        // this.age = age;  // cleaner alternative
    }
}
```

This file demonstrates the concept that `this` internally holds a reference to the calling object — showing it concretely by passing `obj` explicitly.

> 💡 `this` is implicitly passed to every instance method by the JVM. You rarely need to pass it manually — the demonstration here is conceptual.

---

## 4. Constructor

A **constructor** is a special method that runs **automatically when an object is created** using `new`. It is used to **initialize** instance variables.

### Rules
- Same name as the class.
- No return type (not even `void`).
- Called automatically when `new ClassName()` is used.

### From the code

```java
class Human {
    private int age;
    private String name;

    public Human() {           // constructor
        age = 12;
        name = "John";
        // System.out.println("in constructor");
    }
    // getters and setters ...
}

public class Demo {
    public static void main(String[] args) {
        Human obj = new Human();    // constructor runs automatically
        Human obj1 = new Human();   // constructor runs again for obj1
        System.out.println(obj.getName() + " : " + obj.getAge());  // Output: John : 12
    }
}
```

> 💡 If you do not define any constructor, Java provides a **default no-arg constructor** automatically. As soon as you define any constructor yourself, the automatic one is removed.

---

## 5. Default vs Parameterized Constructor

You can have **multiple constructors** (constructor overloading) to create objects in different ways.

| Type | Description |
|---|---|
| **Default constructor** | No parameters; sets default values |
| **Parameterized constructor** | Takes arguments; initializes with provided values |

```java
class Human {
    private int age;
    private String name;

    public Human() {                    // default constructor
        age = 12;
        name = "John";
    }
    public Human(String name) {         // parameterized — name only
        this.age = age;                 // age gets default 0 here (field not set)
        this.name = name;
    }
    public Human(int age, String name) { // parameterized — both fields
        this.age = age;
        this.name = name;
    }
}

public class Demo {
    public static void main(String[] args) {
        Human obj = new Human();          // uses default constructor
        // Human obj1 = new Human(18, "Navin"); // uses parameterized constructor
        System.out.println(obj.getName() + " : " + obj.getAge());  // Output: John : 12
    }
}
```

> ⚠️ In the `Human(String name)` constructor above, `this.age = age` actually assigns the default value of the `age` field (which is `0`) back to itself — the intended behavior here was probably `this.age = 0`. This is a subtle bug in the original code.

---

## 6. `this()` and `super()` — Constructor Chaining

### `super()` — Call a parent class constructor

```java
class A {
    public A() {
        super();                             // calls Object() — implicit
        System.out.println("in A");
    }
    public A(int n) {
        super();
        System.out.println("in A int");
    }
}
class B extends A {
    public B() {
        super();                             // calls A()
        System.out.println("in B");
    }
    public B(int n) {
        this();      // calls B() — the no-arg constructor of same class
        System.out.println("in B int");
    }
}

public class Demo {
    public static void main(String[] args) {
        B obj = new B(5);   // calls B(int n)
    }
}
```

**Output:**
```
in A
in B
in B int
```

### Trace — why this output?
1. `new B(5)` → calls `B(int n)`
2. `this()` inside `B(int n)` → calls `B()`
3. `super()` inside `B()` → calls `A()`
4. `super()` inside `A()` → calls `Object()`
5. Prints: `"in A"` → `"in B"` → `"in B int"`

### `this()` vs `super()`

| | `this()` | `super()` |
|---|---|---|
| Calls | Another constructor in the **same class** | A constructor in the **parent class** |
| Must be | First statement in the constructor | First statement in the constructor |
| Cannot use | Both `this()` and `super()` in the same constructor | (same restriction) |

> ⚠️ `this()` and `super()` must always be the **first statement** in a constructor. You cannot use both in the same constructor.

---

## 7. `this` vs `super` Keyword

When the same variable name exists at multiple levels (local, current class, parent class), `this` and `super` are used to disambiguate:

```java
class A extends Object {
    int num = 1;    // A's num
}
class B extends A {
    int num = 2;    // B's num

    public int getValue() {
        int num = 3;         // local variable
        // return this.num;  // returns B's num = 2
        return super.num;    // returns A's num = 1
    }
}

public class Demo {
    public static void main(String[] a) {
        B obj = new B();
        System.out.println(obj.getValue());  // Output: 1
    }
}
```

| Keyword | Refers to |
|---|---|
| `num` (no prefix) | Local variable (innermost scope) |
| `this.num` | Current class's instance field (`B.num = 2`) |
| `super.num` | Parent class's instance field (`A.num = 1`) |

---

## 8. Naming Conventions

Java follows **camelCase** naming conventions:

| Element | Convention | Example |
|---|---|---|
| **Class / Interface** | PascalCase (first letter of each word is capital) | `Human`, `MyData`, `Calculator` |
| **Variable / Method** | camelCase (starts lowercase) | `age`, `marks`, `show()`, `showMyMarks()` |
| **Constants** | ALL_CAPS with underscores | `PIE`, `BRAND`, `MAX_SIZE` |

**Method naming alternatives seen in code:**
```java
showMyMarks()    // camelCase — Java standard ✅
show_my_marks()  // snake_case — not Java convention ❌
```

> 💡 Follow the Java convention consistently. IDEs and frameworks (Spring, Hibernate) depend on naming conventions for auto-wiring and reflection.

---

## 9. Anonymous Object

An **anonymous object** is an object that is created but **not assigned to any variable**. It is created, used once, and then immediately eligible for garbage collection.

```java
class A {
    public A() {
        System.out.println("object created");
    }
    public void show() {
        System.out.println("in A show");
    }
}

public class Demo {
    public static void main(String[] a) {
        new A();          // anonymous object — constructor runs, no reference kept
        new A().show();   // anonymous object — method called, then discarded

        // Named object — reference kept for reuse
        A obj;
        obj = new A();
        obj.show();
    }
}
```

**Output:**
```
object created
object created
in A show
object created
in A show
```

> 💡 Use anonymous objects when you need to call a method on an object **exactly once** and don't need to reuse it. Common in event handlers and factory methods.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 06. OOP Basics*
