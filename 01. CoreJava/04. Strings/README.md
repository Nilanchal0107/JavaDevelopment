# 📘 Strings — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [What is a String?](#1-what-is-a-string)
2. [Mutable vs Immutable Strings](#2-mutable-vs-immutable-strings)
3. [StringBuffer and StringBuilder](#3-stringbuffer-and-stringbuilder)

---

## 1. What is a String?

A **String** is a sequence of characters. In Java, `String` is **not a primitive type** — it is a class (an object) in the `java.lang` package.

### Two ways to create a String

```java
// Method 1: Using the new keyword (creates a fresh object on the Heap)
String name = new String();         // empty string object
String name = new String("Navin"); // string object with value

// Method 2: String literal (uses String Pool — preferred)
String name = "Navin";
```

### From the code — exploring an empty String object

```java
String name = new String();               // creates an empty String object
System.out.println(name);                // Output: (blank — empty string)
System.out.println(name.hashCode());     // Output: 0 (hash of empty string is always 0)
System.out.println("hello " + name);    // Output: hello  (concatenation with empty string)
System.out.println(name.concat("reddy")); // Output: reddy
```

> 💡 `String` in Java is **immutable** — once a String object is created, its content cannot be changed. Methods like `concat()` return a **new** String; the original is unchanged.

> 💡 `new String()` creates an object directly on the **heap**. String literals like `"Navin"` use the **String Pool** (a special area of heap) for memory efficiency.

---

## 2. Mutable vs Immutable Strings

### Immutability of String

When you "modify" a String, Java actually creates a **new String object** and the reference is updated to point to the new one. The old object remains until garbage collected.

```java
String name = "navin";
name = name + "reddy";                  // a NEW string "navinreddy" is created
System.out.println("hello" + name);    // Output: hellonavinreddy
```

> ⚠️ `name + "reddy"` does NOT modify the original `"navin"` object. It creates a new object `"navinreddy"` and `name` is updated to reference it. The old object `"navin"` is discarded.

### String Pool and the `==` operator

```java
String s1 = "Navin";
String s2 = "Navin";

System.out.println(s1 == s2);   // Output: true
```

> 💡 When you create a String literal, Java checks the **String Pool** first. If `"Navin"` already exists there, both `s1` and `s2` point to the **same object** — so `==` returns `true`.

> ⚠️ `==` compares **references** (memory addresses), not content. To compare actual String content, always use `.equals()`. If you had used `new String("Navin")` for both, `==` would return `false` even though the values are the same.

### Summary — String vs StringBuffer / StringBuilder

| Feature | `String` | `StringBuffer` / `StringBuilder` |
|---|---|---|
| Mutability | ❌ Immutable | ✅ Mutable |
| Thread-safe | ✅ (by nature) | `StringBuffer` ✅, `StringBuilder` ❌ |
| Performance on modification | Slow (new object each time) | Fast (modifies in place) |

---

## 3. StringBuffer and StringBuilder

When you need to **frequently modify** a String (append, insert, delete), use `StringBuffer` or `StringBuilder`. They are **mutable** — they modify the same object in memory.

### StringBuffer — methods demonstrated in the code

```java
StringBuffer sb = new StringBuffer("Navin");  // creates StringBuffer with initial value

sb.append("Reddy");           // appends to the end — modifies in place
System.out.println(sb);       // Output: NavinReddy

sb.ensureCapacity(100);       // ensures buffer can hold at least 100 chars
System.out.println(sb);       // Output: NavinReddy (content unchanged, capacity expanded)
```

### Other StringBuffer methods (from commented code)

```java
sb.length();          // returns number of characters currently stored
sb.capacity();        // returns current buffer capacity (default: 16 + initial length)
sb.toString();        // converts StringBuffer to a regular String
sb.deleteCharAt(2);   // removes the character at index 2
sb.insert(0, "Java"); // inserts "Java" at position 0
sb.insert(6, "java"); // inserts "java" at position 6
sb.setLength(30);     // sets length: truncates if shorter, pads with null chars if longer
```

> 💡 Default capacity of a `StringBuffer` is `16 + length of initial string`. So `new StringBuffer("Navin")` starts with capacity `16 + 5 = 21`.

> 💡 Use **`StringBuilder`** when thread safety is not needed (single-threaded apps) — it is faster. Use **`StringBuffer`** in multi-threaded environments.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 04. Strings*
