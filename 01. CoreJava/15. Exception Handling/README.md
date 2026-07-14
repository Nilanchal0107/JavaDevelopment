# 📘 Exception Handling — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Types of Errors](#1-types-of-errors)
2. [try-catch — Basic Exception Handling](#2-try-catch--basic-exception-handling)
3. [Multiple catch Blocks](#3-multiple-catch-blocks)
4. [Exception Hierarchy](#4-exception-hierarchy)
5. [The `throw` Keyword](#5-the-throw-keyword)
6. [Custom Exception](#6-custom-exception)
7. [Ducking with `throws`](#7-ducking-with-throws)
8. [User Input — `BufferedReader` and `Scanner`](#8-user-input--bufferedreader-and-scanner)
9. [`finally` and try-with-resources](#9-finally-and-try-with-resources)

---

## 1. Types of Errors

```
Types of error:
1. Compile-time error   → Caught by compiler (syntax, type mismatch)
2. Runtime error        → Exception Handling deals with these
3. Logical error        → Wrong result; no crash, hardest to detect
```

---

## 2. try-catch — Basic Exception Handling

Wraps risky code in a `try` block. If an exception occurs, control jumps to the `catch` block — the rest of `try` is skipped but execution continues after `catch`.

```java
int i = 0;
int j = 0;

try {
    j = 18 / i;              // ArithmeticException: / by zero
}
catch (Exception e) {
    System.out.println("Something went wrong");
}

System.out.println(j);       // j = 0 (was never assigned)
System.out.println("Bye");
```

**Output:**
```
Something went wrong
0
Bye
```

> 💡 Code **after** the try-catch block still runs normally. Exception handling prevents a crash and allows graceful continuation.

---

## 3. Multiple catch Blocks

You can have multiple `catch` blocks to handle different exception types specifically. Java matches the first catch that fits the thrown exception type.

```java
int i = 2;
int[] nums = new int[5];
String str = null;

try {
    j = 18 / i;                      // OK with i=2
    System.out.println(str.length()); // NullPointerException — str is null
    System.out.println(nums[1]);      // would print 0
    System.out.println(nums[5]);      // ArrayIndexOutOfBoundsException — index 5 on size-5 array
}
catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
}
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Stay in your limit.");
}
catch (Exception e) {                 // catches anything else — most general last
    System.out.println("Something went wrong." + e);
}
System.out.println(j);
System.out.println("Bye");
```

**Output (with i=2, str=null):**
```
Something went wrong.java.lang.NullPointerException
9
Bye
```

> ⚠️ **Order matters**: always put **specific exceptions first** and `Exception` last. Java catches the **first matching** block — if `Exception` is first, specific catches are never reached (compiler error).

---

## 4. Exception Hierarchy

```
Object
  └── Throwable
        ├── Error                    ← serious JVM problems, not meant to catch
        │     ├── ThreadDeath
        │     ├── VirtualMachineError (OutOfMemoryError)
        │     └── IOError
        └── Exception
              ├── RuntimeException   ← Unchecked (optional to handle)
              │     ├── ArithmeticException
              │     ├── ArrayIndexOutOfBoundsException
              │     └── NullPointerException
              ├── IOException         ← Checked (must handle or declare)
              └── SQLException        ← Checked (must handle or declare)
```

| Type | Handle Required? | Examples |
|---|---|---|
| **Unchecked** (RuntimeException) | Optional | `ArithmeticException`, `NullPointerException` |
| **Checked** | Mandatory | `IOException`, `SQLException`, `ClassNotFoundException` |

> 💡 The compiler enforces handling of **checked exceptions** — you must either `try-catch` them or declare them with `throws`.

---

## 5. The `throw` Keyword

`throw` manually triggers an exception inside your code — useful to enforce business rules.

```java
int i = 0;
int j = 0;

try {
    j = 18 / i;                   // ArithmeticException thrown here
    if (j == 0)
        throw new ArithmeticException("I don't want to print zero");
}
catch (ArithmeticException e) {
    j = 18 / i;                   // still divides by zero — creates infinite issue
    System.out.println("that is default output" + e);
}
catch (Exception e) {
    System.out.println("Something went wrong." + e);
}
System.out.println(j);
System.out.println("Bye");
```

> 💡 `throw new ExceptionType("message")` creates and throws an exception object immediately. Execution of the current block stops and the nearest matching `catch` is triggered.

---

## 6. Custom Exception

You can create your own exception class by extending `Exception` (checked) or `RuntimeException` (unchecked).

```java
class NavinException extends Exception {
    public NavinException(String string) {
        super(string);   // passes message to Exception's constructor
    }
}

public class Demo {
    public static void main(String[] args) {
        int i = 20;
        int j = 0;

        try {
            j = 18 / i;          // j = 0 (18/20 = 0 in integer division)
            if (j == 0)
                throw new NavinException("I don't want to print zero");
        }
        catch (ArithmeticException e) {
            System.out.println("that is default output" + e);
        }
        catch (Exception e) {
            System.out.println("Something went wrong." + e);  // NavinException caught here
        }
        System.out.println(j);
        System.out.println("Bye");
    }
}
```

**Output:**
```
Something went wrong.NavinException: I don't want to print zero
0
Bye
```

> 💡 Custom exceptions make error handling more **readable and domain-specific** (e.g., `InsufficientFundsException`, `InvalidAgeException`).

---

## 7. Ducking with `throws`

Instead of handling a checked exception inside a method, you can **declare** that the method might throw it using `throws` — pushing the responsibility to the caller.

```java
class A {
    public void show() throws ClassNotFoundException {
        Class.forName("Calc");   // checked exception — must handle or declare
    }
}

public class Demo {
    static {
        System.out.println("Class Loader");
    }

    public static void main(String[] args) {
        A obj = new A();
        try {
            obj.show();                         // caller handles the exception
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

**Output:**
```
Class Loader
java.lang.ClassNotFoundException: Calc
    at ...
```

> ⚠️ `throws` in the method signature = "I might throw this, handle it yourself."  
> `throw` in the method body = "I am throwing this exception right now."

---

## 8. User Input — `BufferedReader` and `Scanner`

This file covers two ways to read user input — both are treated as I/O resources.

### `Scanner` (simpler, preferred for beginners)

```java
import java.util.Scanner;

Scanner sc = new Scanner(System.in);
int num = sc.nextInt();
System.out.println(num);
```

### `BufferedReader` (from commented code — more verbose)

```java
import java.io.*;

InputStreamReader in = new InputStreamReader(System.in);
BufferedReader bf = new BufferedReader(in);
int num = Integer.parseInt(bf.readLine());
System.out.println(num);
```

> 💡 `BufferedReader.readLine()` throws `IOException` (checked) — that's why the main method declares `throws IOException`. `Scanner` hides this complexity.

---

## 9. `finally` and try-with-resources

### `finally` block

Runs **always** — whether an exception occurred or not. Used to close resources (files, connections).

```java
int i = 0;
int j = 0;

try {
    j = 18 / i;                      // ArithmeticException
} catch (Exception e) {
    System.out.println("Someting went wrong.");
    System.out.println("Bye");
} finally {
    System.out.println("Bye");        // always runs
}
```

**Output:**
```
Someting went wrong.
Bye
Bye
```

### try-with-resources (Java 7+)

Automatically closes resources declared in the `try(...)` parentheses — no need for `finally { resource.close(); }`.

```java
import java.io.*;

try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
    int num = Integer.parseInt(br.readLine());
    System.out.println(num);
}
// br is automatically closed when the try block exits
```

> 💡 Resources used in try-with-resources must implement `AutoCloseable` or `Closeable`. `BufferedReader` does, so it closes automatically — even if an exception occurs.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 15. Exception Handling*
