# 🧪 Practice Questions — Static

> Topics covered: static variable (class variable vs instance variable), accessing static via class name, static method, passing objects to static methods, static block, `Class.forName()`, execution order (static block → constructor)

---

## 🟢 Easy (5 Questions)

---

### Q1. Shared Static Variable
Create a class `Company` with:
- Instance variables: `empName` (String), `empId` (int)
- A static variable: `companyName` (String)
- A method `show()` that prints all three

In `main`, create 2 `Company` objects, set their individual fields, set `Company.companyName = "Telusko"` once, and call `show()` on both.

**Expected Output:**
```
Navin : 101 : Telusko
Reddy : 102 : Telusko
```

**Concepts:** Static variable, shared across objects, class name access

---

### Q2. Count Objects Using Static Variable
Create a class `Counter` with:
- A static variable `count = 0`
- A constructor that increments `count` by 1 each time an object is created
- A static method `getCount()` that returns `count`

In `main`, create 3 objects and print the count after each creation.

**Expected Output:**
```
1
2
3
```

**Concepts:** Static variable, constructor, static method

---

### Q3. Static Method — No Object Needed
Create a class `MathUtil` with a static method `square(int n)` that returns `n * n`.  
Call it from `main` **without creating an object** of `MathUtil`.

**Expected Output (for n = 7):**
```
49
```

**Concepts:** Static method, calling via class name

---

### Q4. Static Block Runs First
Create a class `Config` with:
- A static variable `appName`
- A static block that sets `appName = "MyApp"` and prints `"Config loaded"`
- A constructor that prints `"Object created"`

In `main`, create 2 `Config` objects and observe the output order.

**Expected Output:**
```
Config loaded
Object created
Object created
```

**Concepts:** Static block, execution order (static block before constructor)

---

### Q5. Class.forName() — Load Without Object
Using `Class.forName("Config")` (from Q4 or a new class with a static block), trigger the static block **without** creating any object.

**Expected Output:**
```
Config loaded
```

**Concepts:** `Class.forName()`, class loading, static block

---

## 🟡 Medium (3 Questions)

---

### Q6. Static vs Instance Variable Behaviour
Create a class `Mobile` with:
- Instance variable: `brand` (String)
- Static variable: `type` (String)
- Method `show()` printing both

In `main`:
1. Create 2 `Mobile` objects.
2. Set `type = "Smartphone"` via the class name.
3. Then change `type = "Feature Phone"` via the class name.
4. Call `show()` on both objects.

**Expected Output:**
```
Apple : Feature Phone
Samsung : Feature Phone
```

Add a comment explaining why **both** objects reflect the changed value.

**Concepts:** Static variable shared memory, mutation visible to all objects

---

### Q7. Static Method with Object Parameter
Create a class `Student` with:
- Instance variables: `name` (String), `marks` (int)
- A static method `printReport(Student s)` that prints the student's name and marks

In `main`, create 2 `Student` objects and call `Student.printReport()` for each.

**Expected Output:**
```
Navin : 88
Kiran : 95
```

**Concepts:** Static method, object as parameter, why static methods need object params to access instance data

---

### Q8. Singleton Pattern — Static Variable + Static Method
Create a class `Database` that allows **only one instance** to be created:
- Private static variable `instance` of type `Database`
- Private constructor (so no one can do `new Database()` outside)
- Static method `getInstance()` that creates the object only if `instance == null`, then returns it

In `main`, call `Database.getInstance()` twice and verify both calls return the same object (use `==`).

**Expected Output:**
```
true
```

**Concepts:** Static variable, static method, private constructor, singleton pattern

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Static Block Order
**Before running**, predict the exact output of the following program:

```java
class A {
    static int x = 10;
    static {
        x = 20;
        System.out.println("Static block A, x = " + x);
    }
    A() {
        System.out.println("Constructor A");
    }
}

class B extends A {
    static {
        System.out.println("Static block B");
    }
    B() {
        System.out.println("Constructor B");
    }
}

public class Demo {
    public static void main(String[] args) {
        System.out.println("main started");
        B obj1 = new B();
        B obj2 = new B();
    }
}
```

Answer in comments:
1. In what order do static blocks run when inheritance is involved?
2. How many times does each static block run?
3. What is the value of `A.x` after the static block runs?

**Concepts:** Static block order with inheritance, single execution of static block, execution order

---

### Q10. Static Counter with Reset
Create a class `Session` with:
- A static variable `sessionCount` tracking how many sessions were ever created
- An instance variable `sessionId` assigned from `sessionCount` at creation
- A static method `resetCount()` that sets `sessionCount = 0`
- A method `info()` that prints the session ID

In `main`:
1. Create 3 sessions — print each session's info.
2. Call `Session.resetCount()`.
3. Create 2 more sessions — print their info.

**Constraints:** No Scanner. Use only concepts from this folder and previous ones.

Answer in comments: Why do the new sessions after reset start from 1 again, even though the earlier objects still exist?

**Concepts:** Static variable, instance variable, static method, object independence vs shared state

---

*Practice file for 01. CoreJava / 05. Static — Telusko Java Tutorial*
