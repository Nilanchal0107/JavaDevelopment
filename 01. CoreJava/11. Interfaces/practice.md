# 🧪 Practice Questions — Interfaces

> Topics covered: `interface` declaration, `implements`, interface as a type (upcasting), interface constants (`public static final`), cannot reassign interface constants, multiple interface implementation, interface extending interface (`extends`), interface vs abstract class, loose coupling via interface, `class → class: extends`, `class → interface: implements`, `interface → interface: extends`

---

## 🟢 Easy (5 Questions)

---

### Q1. Basic Interface Implementation
Declare an interface `Printable` with a method `print()`.  
Create two classes: `PDFPrinter implements Printable` (prints `"Printing PDF"`) and `ConsolePrinter implements Printable` (prints `"Printing to Console"`).  
In `main`, create both using the interface type and call `print()`.

**Expected Output:**
```
Printing PDF
Printing to Console
```

**Concepts:** Interface, `implements`, upcasting to interface type

---

### Q2. Interface Constants are Final
Declare an interface `Config` with a constant `String APP_NAME = "MyApp"`.  
In `main`, print `Config.APP_NAME`.  
Then write (as a comment) the line that tries to reassign it and explain why it fails.

**Expected Output:**
```
MyApp
```

**Concepts:** Interface fields are `public static final`, cannot be reassigned

---

### Q3. Interface as Parameter Type (Loose Coupling)
Recreate the `Computer` interface with `void code()`.  
Create `Laptop implements Computer` (prints `"code, compile, run"`) and `Desktop implements Computer` (prints `"code, compile, faster"`).  
Create class `Developer` with method `devApp(Computer c)` that calls `c.code()`.

In `main`, pass both a `Laptop` and a `Desktop` to `devApp()`.

**Expected Output:**
```
code, compile, run
code, compile, faster
```

**Concepts:** Interface as method parameter type, loose coupling, polymorphism

---

### Q4. Implement Multiple Interfaces
Create:
- Interface `Flyable` with `void fly()`
- Interface `Swimmable` with `void swim()`
- Class `Duck implements Flyable, Swimmable` — implements both

In `main`, create a `Duck` object and call both methods.

**Expected Output:**
```
Duck is flying
Duck is swimming
```

**Concepts:** Multiple interface implementation, `class implements A, B`

---

### Q5. Interface Extending Interface
Create:
- Interface `X` with `void run()`
- Interface `Y extends X` (adds `void jump()`)
- Class `Athlete implements Y` — must implement both `run()` and `jump()`

In `main`, create an `Athlete` object and call both methods.

**Expected Output:**
```
Running...
Jumping!
```

**Concepts:** `interface extends interface`, implementing class must cover inherited methods

---

## 🟡 Medium (3 Questions)

---

### Q6. Polymorphic Interface Array
Create an interface `Shape` with method `double area()`.  
Create `Circle implements Shape` (area = `3.14 * r * r`) and `Rectangle implements Shape` (area = `l * w`).  

In `main`, create a `Shape[]` array of size 3 containing mixed objects, loop through it calling `area()`, and print each.

**Expected Output (example values):**
```
Circle area: 78.5
Rectangle area: 40.0
Circle area: 113.04
```

**Concepts:** Interface as array type, polymorphic loop, runtime dispatch through interface

---

### Q7. Interface vs Abstract Class Design Choice
Build two parallel hierarchies to solve the same problem — a notification system:

**Version A — Abstract Class:**
- `abstract class Notification` with concrete `log(String msg)` printing `"[LOG] <msg>"` and abstract `send(String msg)`
- `class EmailNotification extends Notification` implementing `send()` with `"Email: <msg>"`

**Version B — Interface:**
- `interface Notifiable` with `void send(String msg)`
- `class SMSNotification implements Notifiable` implementing `send()` with `"SMS: <msg>"`

In `main`, use both and call their `send()` methods.  
In comments, explain: which approach would you use if you also need a shared `log()` method that all notifications should reuse?

**Concepts:** Abstract class for shared implementation, interface for capability contract, design tradeoff

---

### Q8. Combining `extends` and `implements`
Create:
- Interface `Drawable` with `void draw()`
- Interface `Resizable` with `void resize(int factor)`
- Abstract class `Widget` with `String color` and concrete method `getColor()` returning the color
- Class `Button extends Widget implements Drawable, Resizable` — sets `color = "Blue"`, implements both interface methods

In `main`, create a `Button` and call all three methods.

**Expected Output:**
```
Drawing button
Resized by factor 3
Blue
```

**Concepts:** `extends` + `implements` together, abstract class + multiple interfaces, the full relationship syntax

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Interface Constants and Type Resolution
**Before running**, predict the exact output:

```java
interface A {
    int value = 10;
    void show();
}

interface B extends A {
    int value = 20;
}

class C implements B {
    public void show() {
        System.out.println("A.value = " + A.value);
        System.out.println("B.value = " + B.value);
    }
}

public class Demo {
    public static void main(String[] args) {
        A obj = new C();
        obj.show();
        System.out.println(obj instanceof B);
        System.out.println(A.value);
        System.out.println(B.value);
    }
}
```

Answer in comments:
1. `C` implements `B` which extends `A`. Does `C` also satisfy `instanceof A`?
2. Both `A` and `B` define `value`. Why is there no ambiguity when accessing them in `show()`?
3. If you tried `System.out.println(C.value)`, which `value` would you get and why?

**Concepts:** Interface constants per interface (no inheritance override), `instanceof` through interface chain, multiple interface values

---

### Q10. Plugin System Using Interfaces
Build a simple plugin system:

- Interface `Plugin` with methods: `String getName()` and `void execute()`
- Interface `Loggable` with method: `void log(String message)`
- Abstract class `BasePlugin implements Plugin, Loggable` — provides concrete `log(String message)` that prints `"[<getName()>] <message>"`
- Concrete class `DatabasePlugin extends BasePlugin` — `getName()` returns `"Database"`, `execute()` prints `"Connecting to DB..."` then calls `log("Connection established")`
- Concrete class `CachePlugin extends BasePlugin` — `getName()` returns `"Cache"`, `execute()` prints `"Starting cache..."` then calls `log("Cache warmed up")`

In `main`:
1. Create a `Plugin[]` array with both plugins.
2. Loop through and call `execute()` on each.
3. In a comment, explain why `log()` in `BasePlugin` can call `getName()` even though `getName()` is not yet implemented in `BasePlugin`.

**Concepts:** Interface + abstract class combination, shared implementation via abstract class, polymorphism through interface, `getName()` as a template method

---

*Practice file for 01. CoreJava / 11. Interfaces — Telusko Java Tutorial*
