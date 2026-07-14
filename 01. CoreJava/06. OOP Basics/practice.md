# 🧪 Practice Questions — OOP Basics

> Topics covered: Encapsulation, private fields, getters & setters, `this` keyword (disambiguation & object reference), default constructor, parameterized constructor, constructor overloading, `this()` constructor chaining, `super()` constructor chaining, `this` vs `super` keyword for field access, naming conventions, anonymous objects

---

## 🟢 Easy (5 Questions)

---

### Q1. Encapsulation — Bank Account
Create a class `BankAccount` with:
- `private double balance`
- `private String owner`
- Getter and setter for both fields
- Setter for `balance` should reject negative values and print `"Invalid amount"` instead

In `main`, create an object, set a valid balance, try setting a negative balance, and print the final balance.

**Expected Output:**
```
Invalid amount
Balance: 5000.0
```

**Concepts:** Encapsulation, private fields, getter/setter, validation in setter

---

### Q2. `this` Keyword — Disambiguate Field vs Parameter
Create a class `Rectangle` with fields `length` (int) and `width` (int).  
Write a setter `setDimensions(int length, int width)` that uses `this.length` and `this.width` to correctly assign values.  
Add a method `area()` that returns `length * width`.

In `main`, create a `Rectangle`, set dimensions to 8 and 5, and print the area.

**Expected Output:**
```
Area: 40
```

**Concepts:** `this` keyword, disambiguation, setter

---

### Q3. Default Constructor Initialization
Create a class `Student` with fields `name` (String) and `rollno` (int).  
Add a default constructor that sets `name = "Unknown"` and `rollno = 0`.  
In `main`, create a `Student` without setting any values and print its details.

**Expected Output:**
```
Unknown : 0
```

**Concepts:** Default constructor, auto-initialization

---

### Q4. Parameterized Constructor
Extend Q3's `Student` class with a parameterized constructor `Student(int rollno, String name)`.  
Create one object using the default constructor and one using the parameterized constructor.

**Expected Output:**
```
Unknown : 0
Navin : 1
```

**Concepts:** Constructor overloading, default vs parameterized constructor

---

### Q5. Anonymous Object
Create a class `Printer` with a method `print(String msg)` that prints the message.  
In `main`, call `print("Hello from anonymous object")` using an **anonymous object** (no variable assignment).

**Expected Output:**
```
Hello from anonymous object
```

**Concepts:** Anonymous object, one-time use

---

## 🟡 Medium (3 Questions)

---

### Q6. Constructor Chaining with `this()`
Create a class `Box` with three constructors:
- `Box()` → sets `length = 1`, `width = 1`, `height = 1`
- `Box(int side)` → uses `this()` then sets all three to `side`
- `Box(int length, int width, int height)` → uses `this()` then sets individual dimensions

Add a `volume()` method that returns `length * width * height`.

In `main`, create all three and print each volume.

**Expected Output:**
```
Volume: 1
Volume: 125
Volume: 60
```

**Constraints:** `Box(int side)` must call `this()` as its first statement.

**Concepts:** Constructor chaining with `this()`, constructor overloading

---

### Q7. `this` vs `super` Keyword
Create a class `Vehicle` with a field `speed = 100`.  
Create a class `Car extends Vehicle` with a field `speed = 200`.  
Add a method `printSpeeds()` in `Car` that prints:
1. The local variable `speed` (declare `int speed = 300` inside the method)
2. `this.speed` (Car's field)
3. `super.speed` (Vehicle's field)

**Expected Output:**
```
Local speed: 300
Car speed: 200
Vehicle speed: 100
```

**Concepts:** `this` vs `super` keyword, variable scoping across inheritance levels

---

### Q8. `super()` Constructor Chaining
Create:
- Class `Animal` with a constructor `Animal(String type)` that prints `"Animal: <type>"`
- Class `Dog extends Animal` with a constructor `Dog(String name)` that:
  - Calls `super("Mammal")` as first statement
  - Prints `"Dog: <name>"`

In `main`, create a `Dog` object.

**Expected Output:**
```
Animal: Mammal
Dog: Bruno
```

**Concepts:** `super()` constructor call, inheritance, constructor chaining

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Constructor Chain Trace
**Before running**, trace and predict the exact output:

```java
class A {
    public A() {
        super();
        System.out.println("in A");
    }
    public A(int n) {
        super();
        System.out.println("in A int");
    }
}
class B extends A {
    public B() {
        super();
        System.out.println("in B");
    }
    public B(int n) {
        this();
        System.out.println("in B int");
    }
}
public class Demo {
    public static void main(String[] args) {
        B obj = new B(5);
    }
}
```

Answer in comments:
1. List the exact sequence of constructor calls (with arrows showing the chain).
2. Why does `this()` in `B(int n)` cause `B()` to run, which then calls `A()`?
3. If you replaced `this()` with `super()` in `B(int n)`, what would the output be?

**Concepts:** `this()`, `super()`, constructor chaining, call sequence tracing

---

### Q10. Complete Encapsulated Employee System
Build a class `Employee` with:
- Private fields: `id` (int), `name` (String), `salary` (double), `department` (String)
- A default constructor: `id = 0`, `name = "N/A"`, `salary = 0.0`, `department = "Unassigned"`
- A parameterized constructor: `Employee(int id, String name, double salary, String department)` using `this` for all fields
- A copy constructor: `Employee(Employee other)` — creates a new Employee with the same values
- Getters for all fields; setters for `salary` (reject if < 0) and `department`
- A method `printInfo()` that prints all fields

In `main`:
1. Create one using default constructor — print info.
2. Create one using parameterized constructor — print info.
3. Create a copy of the second using the copy constructor — change salary on the original and show the copy is unaffected.

**Concepts:** Encapsulation, constructor overloading, `this` keyword, copy constructor, independent objects

---

*Practice file for 01. CoreJava / 06. OOP Basics — Telusko Java Tutorial*
