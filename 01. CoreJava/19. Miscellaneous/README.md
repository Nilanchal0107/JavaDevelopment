# 📘 Miscellaneous Features — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📋 Table of Contents
1. [Parallel Streams (parallelStream)](#1-parallel-streams-parallelstream)
2. [Optional Class (Optional)](#2-optional-class-optional)
3. [Method References (Constructor Reference)](#3-method-references-constructor-reference)
4. [Local Variable Type Inference (var / LVTI)](#4-local-variable-type-inference-var--lvti)
5. [Sealed Classes & Interfaces](#5-sealed-classes--interfaces)
6. [Record Classes (record)](#6-record-classes-record)
7. [Feature Summary Table](#7-feature-summary-table)
8. [⚠️ My Mistakes & Gaps](#8-️-my-mistakes--gaps)

---

## 1. Parallel Streams (parallelStream)

Parallel Streams process elements concurrently using multiple threads from Java's common ForkJoinPool, significantly improving execution performance for CPU-intensive or high-latency tasks on large collections compared to sequential streams.

### Syntax
```java
List<T> list = ...;
int result = list.parallelStream()
                 .map(...)
                 .mapToInt(i -> i)
                 .sum();
```

### Code Example (from `01. StreamEx.java`)
```java
import java.util.*;

class StreamEx {
    public static void main (String A[]) {

        int size = 10_000;
        List<Integer> nums = new ArrayList<>(size);

        Random ran = new Random();

        // Populate list with 10,000 random integers (0 to 99)
        for(int i = 0; i < size; i++)
            nums.add(ran.nextInt(100));

        // 1. Sequential Stream Execution
        long startSeq = System.currentTimeMillis();
        int sum1 = nums.stream()
                    .map(i -> {
                        try {
                            Thread.sleep(1); // Simulating artificial latency per element
                        } catch (Exception e) {
                        }
                        return i * 2;
                    })
                    .mapToInt(i -> i)
                    .sum();
        long endSeq = System.currentTimeMillis();

        // 2. Parallel Stream Execution
        long startPara = System.currentTimeMillis();
        int sum2 = nums.parallelStream()
                    .map(i -> {
                        try {
                            Thread.sleep(1); // Latency executed concurrently across worker threads
                        } catch (Exception e) {
                        }
                        return i * 2;
                    })
                    .mapToInt(i -> i)
                    .sum();
        long endPara = System.currentTimeMillis();

        System.out.println(sum1 + " " + sum2);
        System.out.println("Seq : " + (endSeq - startSeq));   // e.g., ~10,000ms+
        System.out.println("Para : " + (endPara - startPara)); // e.g., drastically lower due to multithreading
    }
}
```

> 💡 Tip: `parallelStream()` is most effective when individual operations involve latency or heavy computation and the collection is large. For small datasets or simple operations, sequential `stream()` is usually faster due to thread management overhead.

---

## 2. Optional Class (Optional)

`Optional<T>` is a container object introduced in Java 8 that may or may not contain a non-null value, providing a functional alternative to explicitly returning or checking for `null` and preventing `NullPointerException`.

### Syntax
```java
Optional<T> optionalValue = collection.stream()
                                     .filter(...)
                                     .findFirst();

T result = optionalValue.orElse(defaultValue);
```

### Code Example (from `02. OptionalEx.java`)
```java
import java.util.*;

class OptionalEx {
    public static void main (String A[]) {
        List<String> names = Arrays.asList("Navin", "John", "Kishor");

        // Search for the first string containing "x"
        String name = names.stream()
                .filter(str -> str.contains("x")) // No element contains "x"
                .findFirst()                      // Returns Optional.empty()
                .orElse("Not Found");             // Safely returns fallback string "Not Found"

        System.out.println(name); // Output: Not Found
    }
}
```

> 💡 Tip: Method chaining with `.orElse()` or `.orElseGet()` guarantees a safe fallback value if the stream search yields no match.

---

## 3. Method References (Constructor Reference)

Method References provide a compact, readable syntax for lambda expressions that simply call an existing method or constructor. A Constructor Reference uses the format `ClassName::new`.

### Syntax
```java
// Lambda syntax:
List<Target> list = names.stream().map(name -> new Target(name)).toList();

// Constructor Reference syntax:
List<Target> list = names.stream().map(Target::new).toList();
```

### Code Example (from `03. MethodRefEx.java`)
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Student {
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "Student [name=" + name + ", age=" + age + "]";
    }
}

class MethodRefEx {
    public static void main (String A[]) {
        List<String> names = Arrays.asList("Navin", "John", "Harsh");
        List<Student> students = new ArrayList<>();

        // Transforming list of String to list of Student objects using constructor reference
        students = names.stream()
                        .map(Student::new) // Refers to new Student(String name)
                        .toList();          // Collects directly to immutable list (Java 16+)

        System.out.println(students);
        // Output: [Student [name=Navin, age=0], Student [name=John, age=0], Student [name=Harsh, age=0]]
    }
}
```

> 💡 Tip: `Student::new` automatically matches the constructor signature corresponding to the type being emitted by the preceding stream step (here, `String`).

---

## 4. Local Variable Type Inference (var / LVTI)

Introduced in Java 10, Local Variable Type Inference (`var`) allows developers to omit explicit type declarations for local variables, letting the Java compiler infer the type automatically from the initialization expression.

### Syntax
```java
var variableName = initialValue;
```

### Code Example (from `04. LVTI.java`)
```java
class Demo {
    // var num = 10; // ⚠️ Compile ERROR: 'var' cannot be used for class fields/instance variables

    public static void main(String[] args) {
        int a = 9;
        var b = 8;          // Compiler infers type 'int'
        String var = "Navin"; // Valid: 'var' is a contextual reserved type name, not a restricted keyword

        int c;               // Standard variable declaration without immediate initialization
        var d = 10;         // Valid: type inferred as int

        // var e;           // ⚠️ Compile ERROR: 'var' requires an immediate initializer

        var nums = new int[10]; // Valid: array type inferred as int[]
    }
}
```

> ⚠️ Warning: `var` can **only** be used for local variables inside methods, code blocks, or loop indexes. It cannot be used for instance variables, class static variables, method parameters, or method return types.

---

## 5. Sealed Classes & Interfaces

Introduced in Java 17, Sealed Classes and Interfaces restrict which other classes or interfaces can extend or implement them, providing precise control over inheritance hierarchies.

### Syntax
```java
sealed class Parent permits ChildA, ChildB {}

final class ChildA extends Parent {}
non-sealed class ChildB extends Parent {}
sealed class ChildC extends Parent permits GrandChild {}
```

### Code Example (from `05. Sealed Class.java`)
```java
// Sealed superclass explicitly permitting only B and C as direct subclasses
sealed class A extends Thread implements Cloneable permits B, C {

}

// Subclasses MUST specify one of three modifiers: non-sealed, final, or sealed

// 1. non-sealed: Opens inheritance hierarchy so any class (like D) can extend B
non-sealed class B extends A {

}

// 2. final: Prevents any further inheritance
final class C extends A {
    
}

// Class D can extend non-sealed class B without restrictions
class D extends B {

}

// Sealed Interface permitting only interface Y
sealed interface X permits Y {

}

// Sub-interface must be marked non-sealed, sealed, or final (if class)
non-sealed interface Y extends X {
    
}

class Demo {
    public static void main (String A[] ) {

    }
}
```

> 💡 Tip: Every class that extends a `sealed` class MUST explicitly choose its inheritance state using one of three modifiers:
> 1. `final` — cannot be extended further.
> 2. `sealed` — continues sealed hierarchy with its own `permits` list.
> 3. `non-sealed` — unseals the hierarchy, allowing unrestricted inheritance.

---

## 6. Record Classes (record)

Introduced as a standard feature in Java 16, a `record` is a special type of class designed to act as an immutable data carrier, automatically eliminating boilerplate code.

### Syntax
```java
record RecordName(Type field1, Type field2) {
    // Optional compact constructor for validation
    public RecordName {
        // validation rules
    }
}
```

### Code Example (from `06. Record Class.java`)
```java
// Record definition: automatically generates private final fields, constructor,
// getters (id(), name()), equals(), hashCode(), and toString()
record Alien (int id, String name) implements Cloneable {

    // Compact Constructor: parameters (int id, String name) are implicit
    public Alien {
        if (id == 0)
            throw new IllegalArgumentException("id cannot be zero");
    }
}

class Demo {
    public static void main(String[] args) {
        Alien a1 = new Alien(1, "Navin");
        Alien a2 = new Alien(1, "Navin");

        // Records provide built-in value-based equality out of the box
        System.out.println(a1.equals(a2)); // Output: true
        System.out.println(a1);            // Output: Alien[id=1, name=Navin]
    }
}
```

> 💡 Tip:
> - Component getter methods in records do **not** use `get` prefix; for component `id`, the getter is `a1.id()`.
> - Records implicitly extend `java.lang.Record` and are implicitly `final`, meaning a record cannot extend any class, nor can another class extend a record. However, records **can** implement interfaces.

---

## 7. Feature Summary Table

| Feature | Introduced In | Primary Purpose | Key Modifier / Keyword | Key Rules & Restrictions |
| :--- | :--- | :--- | :--- | :--- |
| **Parallel Streams** | Java 8 | Concurrent stream processing across multithreaded pool | `.parallelStream()` | Best for CPU-heavy tasks or large collections; overhead on small lists |
| **Optional Class** | Java 8 | Safe container for potentially null values | `Optional<T>`, `.orElse()` | Eliminates NullPointerException checks in Stream pipelines |
| **Constructor Reference** | Java 8 | Concise lambda shorthand for instantiating objects | `ClassName::new` | Automatically matches target functional interface and parameters |
| **LVTI (`var`)** | Java 10 | Local variable type inference by compiler | `var` | Local scope only; requires immediate initializer; cannot be field |
| **Sealed Classes** | Java 17 | Restricted inheritance hierarchy control | `sealed`, `permits` | Subclasses MUST be declared `final`, `sealed`, or `non-sealed` |
| **Record Classes** | Java 16 | Compact immutable data carrier classes | `record` | Fields are `private final`; implicit getters/equals/hashCode/toString |

---

## 8. ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava/19. Miscellaneous*
