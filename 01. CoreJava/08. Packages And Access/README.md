# 📘 Packages And Access Modifiers — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Packages](#1-packages)
2. [Access Modifiers](#2-access-modifiers)

---

## 1. Packages

A **package** is a namespace that organises related classes and interfaces into a folder-like structure. It prevents naming conflicts and controls visibility.

### Declaring a Package

The `package` statement must be the **very first line** of a Java source file:

```java
package other.tools;   // declares this file belongs to the 'other.tools' package

public class Calc {
    public int add(int n1, int n2) { return n1 + n2; }
    public int sub(int n1, int n2) { return n1 - n2; }
}
```

Sub-packages use dot notation — `other.tools` means a folder `other` containing a subfolder `tools`.

### Importing Packages

To use a class from another package, `import` it before the class declaration:

```java
package com.google.Calculation;

import other.tools.*;       // imports all classes from other.tools package
// import other.tools.Calc; // import a single class

import java.lang.*;         // java.lang is auto-imported — you don't actually need this line

public class Demo {
    public static void main(String[] args) {
        Calc obj    = new Calc();    // from other.tools
        AdvCalc obj1 = new AdvCalc(); // from tools (must also be imported)
        A obj2 = new A();            // from other
    }
}
```

### Package Naming Convention

| Level | Example |
|---|---|
| Single level | `tools` |
| Multi-level | `other.tools` |
| Company-style (reverse domain) | `com.google.Calculation` |

> 💡 `java.lang` (contains `String`, `System`, `Math`, `Object`, etc.) is **automatically imported** by Java into every program — you never need to import it manually.

### Types of Import

```java
import tools.Calc;      // specific class import — preferred
import tools.*;         // wildcard import — imports all classes in the package
```

> ⚠️ Wildcard import (`*`) does NOT import sub-packages. `import other.*` imports classes in `other`, but NOT classes in `other.tools`.

---

## 2. Access Modifiers

Access modifiers control **who can see and use** a class, field, or method.

### The Four Access Modifiers

| Modifier | Same Class | Same Package | Subclass (other package) | Anywhere |
|---|---|---|---|---|
| `private` | ✅ | ❌ | ❌ | ❌ |
| (default / package) | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

### From the code — default (package-private) access

```java
package other;

public class A {
    int marks = 6;       // default access — visible inside 'other' package only
    // protected int marks = 6;  // would also allow subclasses in other packages
    public void show() { }
}

public class B {
    int marks;           // default access
}

class C extends A {
    public void abc() {
        System.out.println(marks);   // OK — C is in same package as A
    }
}

public class Demo {
    public static void main(String[] args) {
        A obj = new A();
        System.out.println(obj.marks);   // OK — Demo is in same package (other)
        obj.show();

        B obj1 = new B();
        System.out.println(obj.marks);   // OK — same package
    }
}
```

> 💡 When no modifier is written, the field/method has **default (package-private)** access — accessible only within the same package.

> ⚠️ `private` fields of a parent class are **not directly accessible** in a subclass, even within the same package. Use getters/setters instead.

### Access Modifier Decision Guide

```
Is it internal implementation detail?  → private
Should subclasses in other packages access it? → protected
Should any class anywhere access it? → public
Should only classes in this package access it? → (default)
```

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 08. Packages And Access*
