# 📘 Static — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Static Variable](#1-static-variable)
2. [Static Method](#2-static-method)
3. [Static Block](#3-static-block)

---

## 1. Static Variable

A **static variable** (also called a class variable) belongs to the **class itself**, not to any individual object.  
All objects of the class **share the same copy** of a static variable.

### Problem Without Static

If every object of `Mobile` had its own `name` field, you'd have to set `"SmartPhone"` individually on each one — redundant and error-prone.

### Solution — static keyword

```java
class Mobile {
    String brand;      // instance variable — each object has its own copy
    int price;         // instance variable
    static String name; // static variable — shared by ALL Mobile objects
    
    public void show() {
        System.out.println(brand + " : " + price + " : " + name);
    }
}
```

### Accessing and using a static variable

```java
Mobile obj1 = new Mobile();
obj1.brand = "Apple";
obj1.price = 1500;
Mobile.name = "SmartPhone";   // access via CLASS name — best practice

Mobile obj2 = new Mobile();
obj2.brand = "Samsung";
obj2.price = 1700;
Mobile.name = "SmartPhone";

obj1.show();   // Output: Apple : 1500 : SmartPhone
obj2.show();   // Output: Samsung : 1700 : SmartPhone
```

> 💡 Always access static variables using the **class name** (`Mobile.name`), not through an object reference (`obj1.name`). Using the class name makes it clear that it is shared.

> ⚠️ If any object changes a static variable, the change is **visible to all other objects** immediately — because they all share the same copy.

---

## 2. Static Method

A **static method** belongs to the class, not to any instance. It can be called **without creating an object**.

### Rules for static methods
- Can access **static variables** directly.
- **Cannot** access instance variables directly (no `this` reference exists).
- To work with instance data, a static method must **receive an object as a parameter**.

### From the code — `show1` is a static method that takes an object

```java
class Mobile {
    String brand;
    int price;
    static String name;

    public void show() {                          // instance method
        System.out.println(brand + " : " + price + " : " + name);
    }

    public static void show1(Mobile obj) {        // static method — receives object as param
        System.out.println(obj.brand + " : " + obj.price + " : " + obj.name);
    }
}
```

### Calling a static method

```java
Mobile obj1 = new Mobile();
obj1.brand = "Apple";
obj1.price = 1500;
Mobile.name = "SmartPhone";

obj1.show();          // instance method — called on object
Mobile.show1(obj1);   // static method — called on class, object passed in
```

**Output:**
```
Apple : 1500 : SmartPhone
Apple : 1500 : SmartPhone
```

> 💡 `main` itself is a static method — that's why `System.out.println(...)` can be called inside it without creating an object first.

---

## 3. Static Block

A **static block** is a block of code that runs **once when the class is first loaded by the JVM** — before any object is created and before `main` runs.

### Use case
Initialize static variables with logic that can't be done in a simple assignment (e.g., computed values, loading configs).

### From the code

```java
class Mobile {
    String brand;
    int price;
    static String name;

    static {
        name = "Phone";                          // static variable initialized here
        System.out.println("in static block");   // runs once on class load
    }

    public Mobile() {
        brand = "";
        price = 200;
        System.out.println("in constructor");    // runs every time an object is created
    }

    public void show() {
        System.out.println(brand + " : " + price + " : " + name);
    }
}
```

### Triggering class load without creating an object — `Class.forName()`

```java
public class Demo {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("Mobile");   // loads the class → triggers the static block
        // No object is created, yet the static block runs
    }
}
```

**Output:**
```
in static block
```

> 💡 The static block runs **only once**, no matter how many objects you create. The constructor runs **every time** a new object is created.

> 💡 `Class.forName("ClassName")` is commonly used in JDBC to load database drivers — it forces the static block in the driver class to run, which registers the driver.

### Execution Order Summary

```
Class loaded → static block runs (once)
                        ↓
          new Object() → constructor runs (each time)
```

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 05. Static*
