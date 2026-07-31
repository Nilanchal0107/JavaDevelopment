# 🧪 Practice Questions — Stream API and Wrapper Class

> Topics covered: Stream API motivation, `forEach`, `Consumer` interface, `stream()` vs `parallelStream()`, `filter` + `Predicate`, `map` + `Function`, `reduce`, `sorted`, wrapper classes (`Integer`, `Character`, `Double`), autoboxing, unboxing, `Integer.parseInt()`

---

## 🟢 Easy (5 Questions)

---

### Q1. forEach with a Lambda
Given the list `[10, 20, 30, 40, 50]`, use `forEach` with a lambda to print each element on its own line.

**Constraints:**
- Do not use a traditional `for` loop
- Use `Arrays.asList(...)` to create the list

**Expected Output:**
```
10
20
30
40
50
```

**Concepts:** `forEach`, lambda, `Consumer<T>` (inline)

---

### Q2. Autoboxing and Unboxing
In `main`:
1. Declare `int num = 42;`
2. Autobox it into `Integer boxed = num;`
3. Unbox it manually using `.intValue()` into a new `int` variable.
4. Print both `boxed` and the unboxed value on separate lines.

**Expected Output:**
```
42
42
```

**Concepts:** Autoboxing, unboxing via `.intValue()`

---

### Q3. Parse and Compute
In `main`:
1. Declare `String s = "25";`
2. Parse it to an `int` using `Integer.parseInt()`.
3. Add `5` to the result and print it.
4. Also print `Integer.MAX_VALUE` on a new line.

**Expected Output:**
```
30
2147483647
```

**Concepts:** `Integer.parseInt()`, wrapper class utility methods, `MAX_VALUE`

---

### Q4. Filter Even Numbers with Stream
Given the list `[1, 2, 3, 4, 5, 6, 7, 8]`:
- Use `.stream().filter(...)` to keep only even numbers.
- Print each even number using `forEach`.

**Expected Output:**
```
2
4
6
8
```

**Concepts:** `stream()`, `filter`, `Predicate<T>` (lambda)

---

### Q5. Map to Double with Stream
Given the list `[3, 6, 9]`:
- Use `.stream().map(...)` to multiply each element by `3`.
- Print each result using `forEach`.

**Expected Output:**
```
9
18
27
```

**Concepts:** `stream()`, `map`, `Function<T, R>` (lambda)

---

## 🟡 Medium (3 Questions)

---

### Q6. filter + map + reduce Pipeline
Given the list `[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]`:
- Filter to keep only **odd** numbers.
- Map each to its **square** (`n * n`).
- Reduce by summing all values.
- Print the result.

**Expected Output:**
```
165
```
*(1² + 3² + 5² + 7² + 9² = 1 + 9 + 25 + 49 + 81 = 165)*

**Concepts:** `filter`, `map`, `reduce`, method chaining

---

### Q7. Sorted Stream + forEach
Given the list `[9, 1, 5, 3, 7]`:
1. Use `.stream().sorted()` to get elements in ascending order.
2. Print each using `forEach`.
3. Repeat using `.parallelStream().sorted()` and print again.

**Constraints:**
- Both blocks should produce the same output because `sorted()` enforces order

**Expected Output:**
```
1
3
5
7
9
1
3
5
7
9
```

**Concepts:** `sorted`, `stream()` vs `parallelStream()`, deterministic output with `sorted()`

---

### Q8. Predict the Output — Consumer Named Variable
Before running, predict the exact output of this code:

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Demo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(4, 5, 7, 3, 2, 6);

        Consumer<Integer> con = n -> System.out.println(n * n);

        nums.forEach(con);
        nums.forEach(n -> System.out.println(n + 10));
    }
}
```

**Expected Output:**
```
16
25
49
9
4
36
14
15
17
13
12
16
```

**Concepts:** Named `Consumer` variable, inline lambda in `forEach`, two separate `forEach` calls

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Stream Reuse Trap
**Before running**, predict what happens when you run this code:

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(2, 4, 6, 8);

        Stream<Integer> s = nums.stream().filter(n -> n > 3);

        System.out.println("First use:");
        s.forEach(n -> System.out.println(n));

        System.out.println("Second use:");
        s.forEach(n -> System.out.println(n));  // Line X
    }
}
```

Answer in comments:
1. What does `Line X` do — does it print correctly, print nothing, or throw an exception?
2. What is the exact exception name thrown (if any)?
3. Why does Java enforce this rule for streams? How does it differ from a `List` which can be iterated multiple times?

**Concepts:** Stream single-use guarantee, `IllegalStateException`, stream vs collection

---

### Q10. Full Pipeline — Filter, Map, Reduce, and Wrapper Utility
Write a program that:
1. Creates the list `["3", "7", "1", "9", "2", "8", "4", "6", "5", "10"]` (Strings, not ints).
2. Converts each element to `int` using `Integer.parseInt()` inside a `.map()` call.
3. Filters to keep only numbers **greater than 5**.
4. Multiplies each kept number by itself (`n * n`).
5. Reduces by summing all values.
6. Prints the result.

**Constraints:**
- Must be done in a single chained stream pipeline
- Do NOT manually convert the list to integers first — do it inside `.map()`
- No loops allowed

Answer in comments after your code:
- What is the exact result and why?
- What happens if one String in the list is `"abc"` — at which point does it fail and why?

**Concepts:** `stream()`, `map` with `Integer.parseInt`, `filter`, `reduce`, wrapper class parsing, `NumberFormatException`

---

*Practice file for 01. CoreJava / 18. Stream API and Wrapper class — Telusko Java Tutorial*
