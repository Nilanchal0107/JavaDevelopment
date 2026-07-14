# 📘 Java Basics — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [First Code in JShell](#1-first-code-in-jshell)
2. [How Java Works](#2-how-java-works)
3. [Variables](#3-variables)
4. [Data Types](#4-data-types)
5. [Literals](#5-literals)
6. [Type Conversion & Casting](#6-type-conversion--casting)
7. [Assignment & Arithmetic Operators](#7-assignment--arithmetic-operators)
8. [Relational Operators](#8-relational-operators)
9. [Logical Operators](#9-logical-operators)
10. [If-Else Statement](#10-if-else-statement)
11. [If-Else If (Ladder)](#11-if-else-if-ladder)
12. [Ternary Operator](#12-ternary-operator)
13. [Switch Statement](#13-switch-statement)
14. [Need for Loops](#14-need-for-loops)
15. [While Loop](#15-while-loop)
16. [Do-While Loop](#16-do-while-loop)
17. [For Loop](#17-for-loop)
18. [Which Loop to Use?](#18-which-loop-to-use)

---

## 1. First Code in JShell

**JShell** is an interactive REPL (Read-Evaluate-Print-Loop) tool introduced in Java 9.  
It lets you run Java statements without creating a full class.

**How to start JShell:**
```
jshell
```

**Examples:**
```java
System.out.println("Hello World")   // prints: Hello World
2 + 4                               // $3 ==> 6
9 - 6                               // $4 ==> 3
```

> 💡 JShell is great for quick experiments. No need for `public static void main`.

---

## 2. How Java Works

Java follows a **Write Once, Run Anywhere (WORA)** approach.

**Compilation Flow:**
```
Source Code (.java)  →  Compiler (javac)  →  Bytecode (.class)  →  JVM  →  Output
```

- **JDK** (Java Development Kit): Used to write and compile Java programs.
- **JRE** (Java Runtime Environment): Used to run Java programs.
- **JVM** (Java Virtual Machine): Interprets bytecode and runs it on the OS.

**Basic Structure of a Java Program:**
```java
public class hello {
    public static void main(String[] args) {
        System.out.print("Hello World");
    }
}
```

- Every Java file must have a class.
- Execution starts from the `main` method.
- `System.out.println()` prints with a newline; `System.out.print()` prints without.

---

## 3. Variables

A **variable** is a named container that stores a value in memory.

**Syntax:**
```java
dataType variableName = value;
```

**Example:**
```java
int num1 = 3;
int num2 = 5;
int result = num1 + num2;
System.out.println(result);   // Output: 8
```

> 💡 Without variables, you'd have to hardcode every value. Variables make your code reusable and readable.

---

## 4. Data Types

Java is **statically typed** — every variable must have a declared type.

### Primitive Data Types

| Type      | Size    | Range / Description              | Example            |
|-----------|---------|----------------------------------|--------------------|
| `byte`    | 1 byte  | -128 to 127                      | `byte b = 127;`    |
| `short`   | 2 bytes | -32,768 to 32,767                | `short s = 558;`   |
| `int`     | 4 bytes | ~-2 billion to 2 billion         | `int n = 9;`       |
| `long`    | 8 bytes | Very large numbers               | `long l = 5854l;`  |
| `float`   | 4 bytes | Decimal (7 digits precision)     | `float f = 5.8f;`  |
| `double`  | 8 bytes | Decimal (15 digits precision)    | `double d = 5.8;`  |
| `char`    | 2 bytes | Single Unicode character         | `char c = 'k';`    |
| `boolean` | 1 bit   | `true` or `false`                | `boolean b = true;`|

> ⚠️ Suffix `l` or `L` for `long`, `f` or `F` for `float`.

---

## 5. Literals

A **literal** is a fixed value written directly in the code.

### Integer Literals
```java
int binary = 0b101;          // Binary → 5
int hex    = 0x7E;           // Hexadecimal → 126
int big    = 10_00_00_000;   // Underscore for readability → 100000000
```

### Floating-Point Literals
```java
float  f = 56;       // auto-converts to 56.0
double d = 56;       // auto-converts to 56.0
double sci = 12e10;  // Scientific notation → 1.2E11
```

### Character Literals
```java
char c = 'a';
c++;                  // c becomes 'b' (char arithmetic works!)
```

### Boolean Literals
```java
boolean flag = true;
boolean flag2 = false;
// Note: In Java, boolean cannot be assigned integer (1 or 0) directly
```

---

## 6. Type Conversion & Casting

### Implicit (Widening) Conversion — Automatic
Smaller type → Larger type. Done **automatically** by Java.

```java
byte b = 127;
int a = b;    // byte automatically widens to int
```

**Widening order:**
```
byte → short → int → long → float → double
```

### Explicit (Narrowing) Casting — Manual
Larger type → Smaller type. You **must** cast manually, and **data loss may occur**.

```java
int aa = 257;
byte k = (byte) aa;   // k = 1 (data loss due to overflow)

float f = 5.6f;
int t = (int) f;      // t = 5 (decimal part truncated)
```

### Arithmetic Type Promotion
When arithmetic is done on `byte` or `short`, Java **automatically promotes** them to `int`:

```java
byte a = 10;
byte b = 20;
int result = a * b;   // Must store in int, not byte
```

---

## 7. Assignment & Arithmetic Operators

### Arithmetic Operators
| Operator | Description  | Example        |
|----------|--------------|----------------|
| `+`      | Addition     | `7 + 5 = 12`   |
| `-`      | Subtraction  | `7 - 5 = 2`    |
| `*`      | Multiplication | `7 * 5 = 35` |
| `/`      | Division     | `7 / 5 = 1`    |
| `%`      | Modulus      | `7 % 5 = 2`    |

### Assignment Operators
```java
num += 2;   // num = num + 2
num -= 2;   // num = num - 2
num *= 2;   // num = num * 2
num /= 2;   // num = num / 2
num %= 2;   // num = num % 2
```

### Increment & Decrement
```java
num++;   // Post-increment: use value, then increment
++num;   // Pre-increment:  increment first, then use
num--;   // Post-decrement
--num;   // Pre-decrement
```

**Key difference:**
```java
int num = 7;
int result = num++;   // result = 7, then num becomes 8
```

---

## 8. Relational Operators

Used to **compare** two values. Always returns a `boolean`.

| Operator | Meaning               | Example (`x=6, y=5`)  | Result  |
|----------|-----------------------|-----------------------|---------|
| `<`      | Less than             | `x < y`               | `false` |
| `>`      | Greater than          | `x > y`               | `true`  |
| `<=`     | Less than or equal    | `x <= y`              | `false` |
| `>=`     | Greater than or equal | `x >= y`              | `true`  |
| `==`     | Equal to              | `x == y`              | `false` |
| `!=`     | Not equal to          | `x != y`              | `true`  |

```java
int x = 6, y = 5;
boolean result = x == y;   // false
```

---

## 9. Logical Operators

Used to **combine** multiple conditions.

| Operator | Name | Description                              | Example                |
|----------|------|------------------------------------------|------------------------|
| `&&`     | AND  | True only if **both** conditions are true | `x>y && a<b`          |
| `\|\|`   | OR   | True if **at least one** is true         | `x>y \|\| a<b`        |
| `!`      | NOT  | Reverses the boolean value               | `!result`              |

**Short-circuit evaluation:**
- `&&` — if left side is `false`, right side is **not evaluated**.
- `||` — if left side is `true`, right side is **not evaluated**.

```java
boolean result = a > b;
System.out.println(!result);   // Negates the result
```

---

## 10. If-Else Statement

Executes a block of code **conditionally**.

**Syntax:**
```java
if (condition) {
    // runs if condition is true
} else {
    // runs if condition is false
}
```

**Example:**
```java
int x = 8, y = 7;
if (x > y) {
    System.out.println(x);
    System.out.println("Thankyou");
} else {
    System.out.println(y);
}
// Output: 8, Thankyou
```

> 💡 If only one statement in the block, curly braces `{}` are optional (but always recommended).

---

## 11. If-Else If (Ladder)

Used when you need to check **multiple conditions** in sequence.

**Syntax:**
```java
if (condition1) {
    // block 1
} else if (condition2) {
    // block 2
} else {
    // default block
}
```

**Example — Find greatest of three numbers:**
```java
int x = 8, y = 7, z = 9;
if (x > y && x > z)
    System.out.println(x);   // x is greatest
else if (y > z)
    System.out.println(y);   // y is greatest
else
    System.out.println(z);   // z is greatest → Output: 9
```

---

## 12. Ternary Operator

A **one-liner** alternative to simple if-else.

**Syntax:**
```java
variable = (condition) ? valueIfTrue : valueIfFalse;
```

**Example:**
```java
int n = 5;
int result = n % 2 == 0 ? 10 : 20;
System.out.println(result);   // Output: 20 (since 5 is odd)
```

> 💡 Best used for simple assignments. Avoid nesting ternary for readability.

---

## 13. Switch Statement

An efficient alternative to long `if-else if` chains, especially for **equality checks**.

**Syntax:**
```java
switch (expression) {
    case value1:
        // code
        break;
    case value2:
        // code
        break;
    default:
        // code if no case matches
}
```

**Example — Day of week:**
```java
int n = 1;
switch (n) {
    case 1: System.out.println("Monday");    break;
    case 2: System.out.println("Tuesday");   break;
    case 3: System.out.println("Wednesday"); break;
    // ...
    default: System.out.println("Enter a valid number");
}
```

> ⚠️ Without `break`, execution **falls through** to the next case.

---

## 14. Need for Loops

When you need to **repeat** a block of code multiple times, you use a loop.

**Types of loops in Java:**
- `while` — condition-based, unknown iterations
- `do-while` — runs at least once
- `for` — fixed number of iterations

---

## 15. While Loop

Checks the condition **before** executing the body.

**Syntax:**
```java
while (condition) {
    // code
}
```

**Example — Nested while:**
```java
int i = 1;
while (i <= 4) {
    System.out.println("Hi" + i);
    int j = 1;
    while (j <= 3) {
        System.out.println("Hello" + j);
        j++;
    }
    i++;
}
```

> 💡 Use `while` when the **number of iterations is unknown** (e.g., reading a file until end).

---

## 16. Do-While Loop

Executes the body **at least once**, then checks the condition.

**Syntax:**
```java
do {
    // code
} while (condition);
```

**Example:**
```java
int i = 1;
do {
    System.out.println("Hi" + i);
    i++;
} while (i <= 4);
// Even if i started at 5, it would print once
```

> 💡 Use `do-while` when the loop body **must execute at least once** (e.g., menu-driven programs).

---

## 17. For Loop

Best when the **number of iterations is known** in advance.

**Syntax:**
```java
for (initialization; condition; update) {
    // code
}
```

**Example:**
```java
for (int i = 0; i <= 4; i++) {
    System.out.println("Hi" + i);
}
```

**Nested For Loop:**
```java
for (int i = 1; i <= 7; i++) {
    System.out.println("Day" + i);
    for (int j = 1; j <= 9; j++) {
        System.out.println(" " + (j + 8) + "-" + (j + 9));
    }
}
```

**Flexible Syntax** (parts can be omitted):
```java
int i = 1;
for (; i <= 5;) {        // initialization & update can be moved outside
    System.out.println("DAY" + i);
    i++;
}
```

---

## 18. Which Loop to Use?

| Loop       | When to Use                                                        |
|------------|--------------------------------------------------------------------|
| `for`      | When you **know** the number of iterations in advance              |
| `while`    | When the number of iterations is **unknown** (condition-driven)    |
| `do-while` | When the body must **execute at least once** regardless of condition |

> 💡 A `for` loop can always be rewritten as a `while` loop and vice versa. The choice is about **readability**.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 01. Basics*
