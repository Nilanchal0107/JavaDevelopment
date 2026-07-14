# 🧪 Practice Questions — Exception Handling

> Topics covered: compile-time / runtime / logical error types, `try-catch`, multiple `catch` blocks, exception order (specific before general), exception hierarchy (`Throwable` → `Error`/`Exception` → Unchecked/Checked), `throw` keyword, custom exception (extends `Exception`), `throws` declaration (ducking), `finally`, try-with-resources, `Scanner` for input, `BufferedReader`, `e.printStackTrace()`, `ClassNotFoundException`

---

## 🟢 Easy (5 Questions)

---

### Q1. Basic try-catch
Write a program that divides `18` by `0`.  
Wrap the division in a try-catch block catching `Exception`.  
Print `"Something went wrong"` in the catch, then print `"Bye"` after.

**Expected Output:**
```
Something went wrong
Bye
```

**Concepts:** `try-catch`, runtime exception, code continues after catch

---

### Q2. Multiple catch Blocks
Declare `int i = 0`, `int[] nums = new int[5]`, `String str = null`.  
In a try block, attempt:
1. `18 / i`
2. `str.length()`
3. `nums[5]`

Add three separate catches: `ArithmeticException`, `ArrayIndexOutOfBoundsException`, `Exception`.  
Run with `i = 0` — which exception is thrown first?

**Expected Output:**
```
Cannot divide by zero
0
Bye
```

**Concepts:** Multiple catch, first exception exits try block, specific-before-general order

---

### Q3. `finally` Always Runs
Write a try-catch-finally block where:
- `try` throws `ArithmeticException` (divide by zero)
- `catch` prints `"Caught exception"`
- `finally` prints `"Finally block ran"`

**Expected Output:**
```
Caught exception
Finally block ran
```

**Concepts:** `finally`, guaranteed execution, resource cleanup use case

---

### Q4. Custom Exception
Create `class AgeException extends Exception` with a constructor that passes a message to `super()`.  
In `main`, check if `age = -5` is negative — if so, `throw new AgeException("Age cannot be negative")`.  
Catch it with `catch (Exception e)` and print the message.

**Expected Output:**
```
Something went wrong.AgeException: Age cannot be negative
```

**Concepts:** Custom exception, `extends Exception`, `throw`, `super(message)`, caught by parent `Exception`

---

### Q5. `throws` — Ducking a Checked Exception
Create class `FileLoader` with method `load() throws ClassNotFoundException` that calls `Class.forName("SomeClass")`.  
In `main`, call `loader.load()` inside a try-catch for `ClassNotFoundException`.  
Print a stack trace using `e.printStackTrace()`.

**Expected Output:**
```
java.lang.ClassNotFoundException: SomeClass
    at ...
```

**Concepts:** `throws` declaration, checked exception, caller handles it, `printStackTrace()`

---

## 🟡 Medium (3 Questions)

---

### Q6. Exception Order Matters — Spot the Error
The following code has a problem. Identify it, explain why, and fix it:

```java
try {
    int[] arr = new int[3];
    arr[5] = 10;
}
catch (Exception e) {
    System.out.println("General: " + e);
}
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Array error: " + e);
}
```

Write the corrected version and explain in a comment why the original order is wrong.

**Expected Output (after fix):**
```
Array error: java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 3
```

**Concepts:** Specific catch must come before general `Exception`, unreachable catch is a compile error

---

### Q7. `throw` for Business Logic
Create method `static void validateAge(int age)` that:
- Throws `IllegalArgumentException("Age must be between 0 and 150")` if age < 0 or age > 150
- Prints `"Valid age: " + age` otherwise

In `main`, test with values `25`, `-1`, `200`.  
Wrap each call in a try-catch.

**Expected Output:**
```
Valid age: 25
Invalid: Age must be between 0 and 150
Invalid: Age must be between 0 and 150
```

**Concepts:** `throw` for domain validation, `IllegalArgumentException` (unchecked), `getMessage()`

---

### Q8. try-with-resources
Write a program using try-with-resources to read an integer from `System.in` using `BufferedReader`:

```java
try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
    int num = Integer.parseInt(br.readLine());
    System.out.println("You entered: " + num);
}
```

Add `catch (IOException e)` and `catch (NumberFormatException e)` blocks.  
In comments, explain why `br.close()` does not need to be called explicitly.

**Concepts:** Try-with-resources, `AutoCloseable`, `BufferedReader`, `IOException`, `NumberFormatException`

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Exception Flow Tracing
**Before running**, predict the exact output:

```java
public class Demo {
    static void method1() throws Exception {
        try {
            System.out.println("method1 try");
            method2();
            System.out.println("method1 after method2");
        } catch (ArithmeticException e) {
            System.out.println("method1 catch: " + e.getMessage());
        } finally {
            System.out.println("method1 finally");
        }
    }

    static void method2() throws Exception {
        System.out.println("method2 start");
        throw new ArithmeticException("bad math");
    }

    public static void main(String[] args) {
        try {
            method1();
            System.out.println("main after method1");
        } catch (Exception e) {
            System.out.println("main catch: " + e.getMessage());
        }
        System.out.println("main end");
    }
}
```

Answer in comments:
1. Does `"method1 after method2"` print? Why?
2. Which catch block handles the `ArithmeticException` — `method1`'s or `main`'s?
3. Does `"main after method1"` print?
4. Does `"main end"` print?

**Concepts:** Exception propagation through call stack, `finally` execution, catch specificity

---

### Q10. Full Exception Handling System — Bank Transfer
Build a banking system with:
- Custom exceptions:
  - `InsufficientFundsException extends Exception` — message: `"Insufficient funds: balance is <balance>"`
  - `InvalidAmountException extends RuntimeException` — message: `"Amount must be positive"`
- Class `BankAccount` with `private double balance`:
  - Constructor sets initial balance
  - `deposit(double amount)` — throws `InvalidAmountException` if amount ≤ 0
  - `withdraw(double amount)` — throws `InvalidAmountException` if amount ≤ 0, throws `InsufficientFundsException` if amount > balance
  - `getBalance()` getter

In `main`:
1. Create account with balance 500.
2. Try depositing -100 (catch `InvalidAmountException`).
3. Try withdrawing 200 (success).
4. Try withdrawing 400 (catch `InsufficientFundsException`).
5. Print final balance.

Wrap all operations in try-catch with `finally` that prints `"Operation complete"` after each.

**Concepts:** Checked vs unchecked custom exceptions, `throw`, multiple catch, `finally`, chained operations

---

*Practice file for 01. CoreJava / 15. Exception Handling — Telusko Java Tutorial*
