# 🧪 Practice Questions — Java Basics

> Topics covered: JShell, Java Program Structure, Variables, Data Types, Literals, Type Conversion, Operators (Arithmetic, Assignment, Relational, Logical), If-Else, Ternary, Switch, Loops (while, do-while, for)

---

## 🟢 Easy (5 Questions)

---

### Q1. Hello User
Write a Java program that stores your name in a `String` variable and prints:
```
Hello, <your name>! Welcome to Java.
```

**Expected Output (for name = "Nilanchal"):**
```
Hello, Nilanchal! Welcome to Java.
```

**Concepts:** Variables, `System.out.println()`

---

### Q2. Simple Calculator
Declare two `int` variables `a = 15` and `b = 4`.  
Print the result of all five arithmetic operations: addition, subtraction, multiplication, division, and modulus.

**Expected Output:**
```
Addition: 19
Subtraction: 11
Multiplication: 60
Division: 3
Modulus: 3
```

**Concepts:** Arithmetic operators, variables

---

### Q3. Even or Odd
Take an integer `n = 27`.  
Using an `if-else` statement, print whether the number is **Even** or **Odd**.

**Expected Output:**
```
27 is Odd
```

**Concepts:** If-else, modulus operator

---

### Q4. Multiplication Table
Using a `for` loop, print the multiplication table of `5` from 1 to 10.

**Expected Output:**
```
5 x 1 = 5
5 x 2 = 10
...
5 x 10 = 50
```

**Concepts:** For loop

---

### Q5. Day Name with Switch
Take an integer `day` (e.g., `day = 3`).  
Use a `switch` statement to print the name of the day.  
- 1 → Monday, 2 → Tuesday, ... 7 → Sunday  
- For any other value, print `"Invalid day"`

**Expected Output (for day = 3):**
```
Wednesday
```

**Concepts:** Switch statement, default case

---

## 🟡 Medium (3 Questions)

---

### Q6. Count Down with Do-While
Using a `do-while` loop, print all numbers from 10 down to 1, then print `"Blast Off!"`.

**Expected Output:**
```
10
9
8
...
1
Blast Off!
```

**Concepts:** Do-while loop, decrement operator

---

### Q7. Data Type Size Demo
Declare one variable of each primitive type (`byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`).
- Assign the **maximum possible value** to `byte` and `short`.
- For `int`, use a number with underscore separator (e.g., `1_00_00_000`).
- For `long`, use the `l` suffix.
- For `float`, use the `f` suffix.
- For `char`, use a character literal, then increment it and print the next character.
- Print all values.

**Concepts:** Data types, literals, char arithmetic

---

### Q8. Grade Calculator
Take a student's marks (0–100) stored in an `int` variable.  
Use an `if-else if` ladder to print the grade:
- 90–100 → `A`
- 75–89  → `B`
- 60–74  → `C`
- 45–59  → `D`
- Below 45 → `F`

Also use a ternary operator to print whether the student **Passed** or **Failed** (pass mark = 45).

**Expected Output (for marks = 78):**
```
Grade: B
Result: Passed
```

**Concepts:** If-else if, ternary operator, relational operators

---

## 🔴 Hard (2 Questions)

---

### Q9. Number Pattern — Right-Angled Triangle
Using **nested for loops**, print the following right-angled number pattern for `n = 5` rows:

```
1
1 2
1 2 3
1 2 3 4
1 2 3 4 5
```

**Constraints:**
- Use only `for` loops (no arrays allowed).
- The inner loop must depend on the outer loop's counter.

**Concepts:** Nested for loops, loop control

---

### Q10. Type Narrowing Trap
Predict and verify the output of the following code snippet **without running it first**, then write it as a complete Java program and confirm your prediction by running it:

```java
int a = 300;
byte b = (byte) a;

float f = 9.99f;
int i = (int) f;

byte x = 10;
byte y = 20;
int z = x * y;

System.out.println("byte cast of 300: " + b);
System.out.println("float to int 9.99f: " + i);
System.out.println("byte * byte stored in int: " + z);
```

**Questions to answer in comments:**
1. Why is `b` not 300?
2. Why does `9.99f` become `9` and not `10`?
3. Why can't `x * y` be stored in a `byte` variable directly?

**Concepts:** Type casting, narrowing conversion, overflow, arithmetic type promotion

---

*Practice file for 01. CoreJava / 01. Basics — Telusko Java Tutorial*
