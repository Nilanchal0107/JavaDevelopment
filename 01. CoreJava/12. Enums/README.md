# 📘 Enums — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [What is an Enum?](#1-what-is-an-enum)
2. [Enum with `if` and `switch`](#2-enum-with-if-and-switch)
3. [Enum as a Class](#3-enum-as-a-class)

---

## 1. What is an Enum?

An **enum** (enumeration) is a special data type that defines a **fixed set of named constants**. It prevents invalid values from being assigned to a variable that should only hold predefined options.

### Declaring an Enum

```java
enum Status {
    Running, Failed, Pending, Success;
}
```

### Using Enum Values

```java
// Status s = Status.Running;   // valid constant
// Status s = Status.NoIdea;    // ❌ compile error — not a defined constant
```

### `ordinal()` — Zero-based position of each constant

```java
Status s = Status.Running;
System.out.println(s);            // Output: Running
System.out.println(s.ordinal());  // Output: 0  (first declared = 0)
```

### `values()` — Iterate all enum constants

```java
Status[] ss = Status.values();   // returns all constants as an array
System.out.println(ss);          // prints array reference (e.g. [LStatus;@...)

for (Status s : ss) {
    System.out.println(s);                        // prints constant name
    System.out.println(s + " : " + s.ordinal());  // prints name and index
}
```

**Output:**
```
Running : 0
Failed : 1
Pending : 2
Success : 3
```

> 💡 Enums are **type-safe** — unlike using plain `int` constants (0, 1, 2...), you can't accidentally pass an invalid value. The compiler catches invalid constants immediately.

---

## 2. Enum with `if` and `switch`

Enums work naturally with both `if-else` and `switch` statements.

### `switch` with Enum

```java
Status s = Status.Pending;

switch (s) {
    case Running:
        System.out.println("All Good");
        break;
    case Failed:
        System.out.println("Try Again");
        break;
    case Pending:
        System.out.println("Please Wait");
        break;
    default:
        System.out.println("Done");
        break;
}
```

**Output (for `Status.Pending`):**
```
Please Wait
```

### `if-else` with Enum using `==`

```java
if (s == Status.Running)
    System.out.println("All Good");
else if (s == Status.Failed)
    System.out.println("Try Again");
else if (s == Status.Pending)
    System.out.println("Please Wait");
else
    System.out.println("Done");
```

> 💡 Comparing enums with `==` is safe and correct — enum constants are singletons (only one instance exists per constant). You do NOT need `.equals()` for enums.

> 💡 In `switch` on an enum, the `case` labels use just the constant name (e.g., `case Running:`) without the enum type prefix.

---

## 3. Enum as a Class

In Java, an enum is actually a **class** under the hood. It can have:
- **Fields** (instance variables)
- **Constructors** (always `private`)
- **Methods** (getters, setters, utility methods)

### From the code — `Laptop` enum with price field

```java
enum Laptop {
    Mackbook(2000), XPS(2200), Surface, ThinkPad(1800);
    //         ^value              ^no-arg constructor called for Surface

    private int price;

    private Laptop() {          // default constructor — used when no value given
        price = 500;
    }

    private Laptop(int price) { // parameterized constructor
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
        System.out.println("in Laptop" + this.name());  // name() returns the constant's name as String
    }
}

public class Demo {
    public static void main(String[] args) {
        for (Laptop lap : Laptop.values()) {
            System.out.println(lap + " : " + lap.getPrice());
        }
    }
}
```

**Output:**
```
Mackbook : 2000
XPS : 2200
Surface : 500
ThinkPad : 1800
```

> ⚠️ Enum constructors are **always private** — you cannot call them from outside. This is enforced by Java to preserve the fixed-set guarantee.

> 💡 `this.name()` inside an enum method returns the **name of the constant** as a String (e.g., `"Mackbook"`). This is a built-in method available on all enum constants.

### Key Built-in Enum Methods

| Method | Description | Example |
|---|---|---|
| `values()` | Returns array of all constants | `Status.values()` |
| `ordinal()` | Returns 0-based index of constant | `Status.Running.ordinal()` → `0` |
| `name()` | Returns constant name as String | `Status.Running.name()` → `"Running"` |

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 12. Enums*
