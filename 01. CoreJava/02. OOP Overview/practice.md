# 🧪 Practice Questions — Fundamentals of OOP

> Topics covered: Classes, Objects, JDK/JRE/JVM, Methods (void & return), Method Overloading, Stack & Heap Memory

---

## 🟢 Easy (5 Questions)

---

### Q1. Create Your First Class
Define a class called `Person` with two properties: `name` (String) and `age` (int).  
In the `main` method, create an object of `Person`, assign values to its properties, and print:
```
Name: Nilanchal
Age: 20
```

**Concepts:** Class definition, object creation, accessing instance variables

---

### Q2. Method That Returns a Value
Create a class `Rectangle` with a method `area(int length, int width)` that returns the area (`length * width`).  
In `main`, create a `Rectangle` object, call the method, and print the result.

**Expected Output (for length=8, width=5):**
```
Area of Rectangle: 40
```

**Concepts:** Methods with return type, object, method call

---

### Q3. void Method — Greet
Create a class `Greeter` with a `void` method `greet(String name)` that prints:
```
Hello, <name>! Welcome to OOP in Java.
```
Call this method from `main` with your own name.

**Concepts:** void methods, parameters, method call

---

### Q4. JDK vs JRE vs JVM Quiz
Write a Java program where you print (as `System.out.println` statements) a short explanation of:
1. What is JDK and who needs it?
2. What is JRE and who needs it?
3. What is JVM and what does it do?

**Example line:**
```
JDK - Java Development Kit. Used by developers to write and compile Java code.
```

**Concepts:** JDK, JRE, JVM understanding

---

### Q5. Overloaded `multiply` Method
Create a class `MathOps` with three overloaded versions of `multiply`:
- `multiply(int a, int b)` → returns product of two integers
- `multiply(int a, int b, int c)` → returns product of three integers
- `multiply(double a, double b)` → returns product of two doubles

Call all three from `main` and print the results.

**Concepts:** Method overloading

---

## 🟡 Medium (3 Questions)

---

### Q6. BankAccount Class
Create a class `BankAccount` with:
- Instance variable: `balance` (double), initialized to `0.0`
- Method `deposit(double amount)` — adds to balance, prints new balance
- Method `withdraw(double amount)` — subtracts from balance (only if funds are sufficient), else prints `"Insufficient funds"`
- Method `getBalance()` — returns the current balance

In `main`, create two **separate** `BankAccount` objects and perform different transactions on each.  
Verify that changing one account's balance does not affect the other.

**Concepts:** Instance variables, methods, independent objects (Stack & Heap behaviour)

---

### Q7. Overloaded `describe` Method
Create a class `Shape` with an overloaded method `describe`:
- `describe(int side)` → prints `"Square with side: <side>, Area: <side*side>"`
- `describe(int length, int width)` → prints `"Rectangle with length: <l>, width: <w>, Area: <l*w>"`
- `describe(double radius)` → prints `"Circle with radius: <r>, Area: <3.14 * r * r>"`

Call all three in `main`.

**Concepts:** Method overloading, different parameter types and counts

---

### Q8. Stack vs Heap Observation
Create a class `Counter` with an instance variable `count = 0` and a method `increment()` that increases `count` by 1.

In `main`:
1. Create **two separate** `Counter` objects: `c1` and `c2`.
2. Call `c1.increment()` three times.
3. Call `c2.increment()` once.
4. Print `c1.count` and `c2.count`.

Add a **comment** in your code explaining *why* `c1` and `c2` have different counts even though they are from the same class.

**Expected Output:**
```
c1 count: 3
c2 count: 1
```

**Concepts:** Instance variables in Heap, independent objects, Stack & Heap memory model

---

## 🔴 Hard (2 Questions)

---

### Q9. Student Report Card System
Build a complete mini-system with the following:

**Class `Student`:**
- Instance variables: `name` (String), `marks1` (int), `marks2` (int), `marks3` (int)
- Method `getTotal()` → returns sum of all three marks
- Method `getAverage()` → returns average of all three marks (as `double`)
- Method `getGrade()` → returns a `String` grade:
  - Average ≥ 90 → `"A"`
  - Average ≥ 75 → `"B"`
  - Average ≥ 60 → `"C"`
  - Average ≥ 45 → `"D"`
  - Below 45 → `"F"`
- Method `printReport()` → prints name, total, average, and grade

**In `main`:**  
Create at least 3 `Student` objects, set their marks, and call `printReport()` on each.

**Concepts:** Classes, multiple methods, instance variables, return types, method calling

---

### Q10. Method Overloading — Type Resolution Puzzle
Create a class `Resolver` with the following overloaded methods:
```java
public void show(int a)         { System.out.println("int: " + a);         }
public void show(double a)      { System.out.println("double: " + a);      }
public void show(int a, int b)  { System.out.println("int, int: " + a + ", " + b); }
public void show(double a, int b) { System.out.println("double, int: " + a + ", " + b); }
```

**Before running the code**, predict the output of each call below. Then write the full program, run it, and verify:
```java
Resolver r = new Resolver();
r.show(5);
r.show(5.0);
r.show(5, 10);
r.show(5.5, 3);
r.show('A');         // char gets promoted — which method is called?
r.show(5, 3.0);      // int and double — which method is called?
```

**Questions to answer in comments:**
1. Why does `'A'` not call a `show(char)` method?
2. Which method does `r.show(5, 3.0)` resolve to and why?
3. What is the term for this compile-time resolution of method calls?

**Concepts:** Method overloading, type promotion in overloading, compile-time (static) polymorphism

---

*Practice file for 01. CoreJava / 02. OOP Overview — Telusko Java Tutorial*
