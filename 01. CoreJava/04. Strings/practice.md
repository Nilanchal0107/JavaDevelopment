# 🧪 Practice Questions — Strings

> Topics covered: String creation (literal vs new), immutability, String Pool, `==` vs `.equals()`, `concat()`, `hashCode()`, StringBuffer (`append`, `insert`, `deleteCharAt`, `length`, `capacity`, `setLength`, `ensureCapacity`, `toString`), StringBuilder

---

## 🟢 Easy (5 Questions)

---

### Q1. String Creation and Methods
Declare a String using `new String("Java")` and a String literal `"Java"`.  
Print the following for each:
1. The string value
2. Its `hashCode()`

**Expected Output:**
```
Java
2301238
Java
2301238
```

**Concepts:** String creation (new vs literal), `hashCode()`

---

### Q2. String Concatenation
Declare `String first = "Hello"` and `String second = " World"`.  
Print the result of `first.concat(second)`.

**Expected Output:**
```
Hello World
```

**Concepts:** `concat()`, String immutability (original not changed)

---

### Q3. StringBuffer append
Create a `StringBuffer` with initial value `"Telusko"`.  
Append `" Java"` to it and print the result.

**Expected Output:**
```
Telusko Java
```

**Concepts:** `StringBuffer`, `append()`

---

### Q4. StringBuffer — insert and deleteCharAt
Create a `StringBuffer sb = new StringBuffer("HelloWorld")`.  
1. Insert `" "` (a space) at index 5.
2. Print the result.

**Expected Output:**
```
Hello World
```

**Concepts:** `StringBuffer.insert()`

---

### Q5. StringBuffer length and capacity
Create `StringBuffer sb = new StringBuffer("Navin")`.  
Print:
1. Its `length()`
2. Its `capacity()`

**Expected Output:**
```
5
21
```

**Concepts:** `StringBuffer.length()`, `StringBuffer.capacity()` (default = 16 + initial length)

---

## 🟡 Medium (3 Questions)

---

### Q6. == vs .equals() on Strings
Declare:
```java
String s1 = "Navin";
String s2 = "Navin";
String s3 = new String("Navin");
```
Print the result of:
1. `s1 == s2`
2. `s1 == s3`
3. `s1.equals(s3)`

**Expected Output:**
```
true
false
true
```

Add a **comment** explaining why `s1 == s2` is `true` but `s1 == s3` is `false`.

**Concepts:** String Pool, `==` vs `.equals()`, `new String()` vs literal

---

### Q7. String Immutability Demonstration
Write a program that:
1. Creates `String name = "navin"`.
2. Assigns `name = name + "reddy"`.
3. Prints `"hello" + name`.
4. In a comment, explain why the original `"navin"` object is not changed even though `name` now holds `"navinreddy" `.

**Expected Output:**
```
hellonavinreddy
```

**Concepts:** String immutability, reference update, String Pool

---

### Q8. StringBuffer — Full Operations
Create `StringBuffer sb = new StringBuffer("Java")`.  
Perform these operations in order:
1. Append `"Programming"` → print
2. Insert `" "` at index 4 → print
3. Delete the character at index 0 → print
4. Convert to String and store in a `String` variable → print

**Concepts:** `append()`, `insert()`, `deleteCharAt()`, `toString()`

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — String Pool Trap
**Before running**, predict the output of the following program, then verify:

```java
String a = "Hello";
String b = "Hello";
String c = new String("Hello");
String d = new String("Hello");

System.out.println(a == b);
System.out.println(a == c);
System.out.println(c == d);
System.out.println(a.equals(c));
System.out.println(c.equals(d));
System.out.println(a.hashCode() == c.hashCode());
```

Answer in comments:
1. Why does `a == b` return `true` but `a == c` return `false`?
2. Why do `a.hashCode()` and `c.hashCode()` return the same value even though they are different objects?
3. What does this tell you about when to use `==` vs `.equals()`?

**Concepts:** String Pool, heap allocation, `==` vs `.equals()`, `hashCode()` contract

---

### Q10. StringBuffer vs String — Performance Insight
Write a program that:
1. Uses a `String` variable and appends `"x"` to it **1000 times** in a loop.
2. Does the same using a `StringBuffer`.
3. Measures and prints the time taken for each (using `System.currentTimeMillis()`).

**Constraints:**
- Do not use `Scanner`.
- Calculate `endTime - startTime` in milliseconds for both.

Answer in comments:
1. Why is `StringBuffer` significantly faster for repeated concatenation?
2. How many new String objects are created in the `String` loop?

**Concepts:** String immutability cost, StringBuffer mutability, performance comparison

---

*Practice file for 01. CoreJava / 04. Strings — Telusko Java Tutorial*
