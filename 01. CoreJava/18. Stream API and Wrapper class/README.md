# 📘 Stream API and Wrapper Class — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Why Stream API? — The Problem with Loops](#1-why-stream-api--the-problem-with-loops)
2. [forEach and the Consumer Interface](#2-foreach-and-the-consumer-interface)
3. [Stream API — Chaining Operations](#3-stream-api--chaining-operations)
4. [map, filter, reduce, sorted](#4-map-filter-reduce-sorted)
5. [parallelStream](#5-parallelstream)
6. [Wrapper Classes](#6-wrapper-classes)
7. [Autoboxing and Unboxing](#7-autoboxing-and-unboxing)

---

## 1. Why Stream API? — The Problem with Loops

Traditional loops for filtering and transforming data are **verbose and imperative** — you write *how* to do something step by step. Stream API lets you express *what* you want in a clean, readable pipeline.

### The old way (for loop)
```java
List<Integer> nums = Arrays.asList(4, 5, 7, 3, 2, 6);

int sum = 0;
for (int n : nums) {
    if (n % 2 == 0) {   // filter: only even numbers
        n = n * 2;       // map: double each
        sum = sum + n;   // reduce: accumulate sum
    }
}
System.out.println(sum);  // Output: 24
```

The Stream API does the same thing in one clean chain — no manual loop, no mutable accumulator variable.

> 💡 The loop approach mixes filtering, transformation, and accumulation into one messy block. Stream API separates these concerns into distinct, named operations.

---

## 2. forEach and the Consumer Interface

`forEach` is an **internal iteration** method available on any `Iterable` (like `List`). It takes a `Consumer<T>` — a functional interface with a single `accept(T)` method.

### Consumer as anonymous class (verbose)
```java
Consumer<Integer> con = new Consumer<Integer>() {
    public void accept(Integer n) {
        System.out.println(n);
    }
};
```

### Consumer as lambda (concise)
```java
Consumer<Integer> con = n -> System.out.println(n);
```

### Using forEach directly with a lambda
```java
List<Integer> nums = Arrays.asList(4, 5, 7, 3, 2, 6);
nums.forEach(n -> System.out.println(n));
```

**Output:**
```
4
5
7
3
2
6
```

> ⚠️ Passing `null` to `forEach` throws a `NullPointerException` at runtime — `nums.forEach(null)` is a runtime error, not a compile error.

> 💡 `forEach` uses the `Consumer` functional interface from `java.util.function`. You don't need to name the lambda variable; you can pass it directly inline.

---

## 3. Stream API — Chaining Operations

A `Stream<T>` is a **sequence of elements supporting sequential and parallel aggregate operations**. You get one from a collection with `.stream()`. Streams are not data structures — they carry elements from a source through a pipeline of operations.

### Step-by-step (verbose, for understanding)
```java
List<Integer> nums = Arrays.asList(4, 5, 7, 3, 2, 6);

Stream<Integer> s1 = nums.stream();                    // source
Stream<Integer> s2 = s1.filter(n -> n % 2 == 0);      // intermediate: keep evens
Stream<Integer> s3 = s2.map(n -> n * 2);               // intermediate: double each
int result = s3.reduce(0, (c, e) -> c + e);            // terminal: sum all
```

### Chained (production style — from the code)
```java
int result = nums.stream()
                 .filter(n -> n % 2 == 0)    // keep: 4, 2, 6
                 .map(n -> n * 2)             // double: 8, 4, 12
                 .reduce(0, (c, e) -> c + e); // sum: 0+8+4+12 = 24

System.out.println(result);  // Output: 24
```

> ⚠️ A stream can only be **consumed once**. After a terminal operation (like `reduce` or `forEach`) is called, the stream is closed. Calling another terminal operation on the same stream reference throws `IllegalStateException`.

> 💡 **Intermediate operations** (like `filter`, `map`, `sorted`) are **lazy** — they don't execute until a terminal operation is called. This makes chaining efficient.

---

## 4. map, filter, reduce, sorted

These are the four core Stream operations shown in the code. Each maps to a well-known functional interface.

| Operation | Type | Functional Interface | What it does |
|---|---|---|---|
| `filter` | Intermediate | `Predicate<T>` | Keeps elements where the condition is `true` |
| `map` | Intermediate | `Function<T, R>` | Transforms each element into a new value |
| `reduce` | Terminal | `BinaryOperator<T>` | Folds all elements into one value |
| `sorted` | Intermediate | (natural order) | Sorts elements in ascending natural order |

### Predicate (for filter) — both forms
```java
// Anonymous class
Predicate<Integer> p = new Predicate<Integer>() {
    public boolean test(Integer n) {
        return n % 2 == 0;
    }
};

// Lambda (concise)
Predicate<Integer> p = n -> n % 2 == 0;
```

### Function (for map) — both forms
```java
// Anonymous class
Function<Integer, Integer> fun = new Function<Integer, Integer>() {
    public Integer apply(Integer n) {
        return n * 2;
    }
};

// Lambda (concise)
Function<Integer, Integer> fun = n -> n * 2;
```

### sorted in a stream chain
```java
List<Integer> nums = Arrays.asList(4, 5, 7, 3, 2, 6);

Stream<Integer> sortedValues = nums.stream()
        .filter(n -> n % 2 == 0)  // keep: 4, 2, 6
        .sorted();                  // sort: 2, 4, 6

sortedValues.forEach(n -> System.out.println(n));
```

**Output:**
```
2
4
6
```

> 💡 `reduce(0, (c, e) -> c + e)` — the first argument `0` is the **identity** (starting value). `c` is the accumulated result so far, `e` is the current element.

---

## 5. parallelStream

`parallelStream()` is a drop-in replacement for `stream()` that splits the work across **multiple CPU threads automatically**.

### From the code
```java
List<Integer> nums = Arrays.asList(4, 5, 7, 3, 2, 6);

Stream<Integer> sortedValues = nums.parallelStream()  // uses multiple threads
        .filter(n -> n % 2 == 0)
        .sorted();

sortedValues.forEach(n -> System.out.println(n));
```

**Output:**
```
2
4
6
```

> ⚠️ `parallelStream()` output order may vary for operations that don't enforce ordering (like `forEach`). In this example `sorted()` enforces the order, so output is deterministic. Without `sorted()`, parallel `forEach` can print in any order.

> 💡 Use `parallelStream()` for CPU-intensive operations on large datasets. For small collections or I/O-heavy tasks, the thread management overhead can make it **slower** than a regular stream.

---

## 6. Wrapper Classes

Every Java primitive has a corresponding **Wrapper class** — an object version of the primitive that lives in `java.lang`.

| Primitive | Wrapper Class |
|---|---|
| `int` | `Integer` |
| `char` | `Character` |
| `double` | `Double` |
| `float` | `Float` |
| `long` | `Long` |
| `short` | `Short` |
| `byte` | `Byte` |
| `boolean` | `Boolean` |

### Why wrapper classes exist
- **Collections** only work with objects — `List<int>` is invalid; `List<Integer>` is required.
- Wrapper classes provide **utility methods**: `Integer.parseInt()`, `Integer.MAX_VALUE`, `Double.parseDouble()`, etc.
- Enable `null` values — a primitive can never be `null`, but an `Integer` can.

### Parsing a String to int (key utility)
```java
String str = "12";
int num3 = Integer.parseInt(str);  // converts String "12" to int 12

System.out.println(num3 + 2);  // Output: 14
```

> ⚠️ `Integer.parseInt("12abc")` throws a `NumberFormatException` at runtime. Always use try-catch when parsing user input.

---

## 7. Autoboxing and Unboxing

**Boxing** is the manual conversion of a primitive to its wrapper object. **Autoboxing** is when Java does it automatically. The reverse is **unboxing** (manual) and **autounboxing** (automatic).

### From the code

```java
int num = 7;

// Old manual boxing (deprecated constructor)
// Integer num1 = new Integer(num);  // boxing — manual, now deprecated

Integer num1 = num;         // autoboxing — Java automatically wraps int to Integer

int num2 = num1.intValue(); // unboxing — explicitly calling intValue()
// int num2 = num1;         // autounboxing — Java does it automatically too

System.out.println(num2);   // Output: 7
```

| Term | Direction | How |
|---|---|---|
| Boxing | `int` → `Integer` | `new Integer(n)` or `Integer.valueOf(n)` |
| Autoboxing | `int` → `Integer` | Just assign: `Integer x = primitiveInt;` |
| Unboxing | `Integer` → `int` | Call `.intValue()` |
| Autounboxing | `Integer` → `int` | Just assign: `int x = integerObject;` |

> ⚠️ The constructor `new Integer(num)` is **deprecated** since Java 9. Prefer `Integer.valueOf(num)` for manual boxing, or just rely on autoboxing.

> ⚠️ Autounboxing a `null` wrapper object causes a `NullPointerException`:
> ```java
> Integer x = null;
> int y = x;  // NullPointerException at runtime
> ```

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 18. Stream API and Wrapper class*
