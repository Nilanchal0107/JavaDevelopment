# 📘 Annotations — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [What is an Annotation?](#1-what-is-an-annotation)

---

## 1. What is an Annotation?

An **annotation** is a form of **metadata** that provides information about the code to the compiler, JVM, or frameworks — without changing the program's logic.

Annotations start with `@` and are placed before the element they annotate (class, method, field, parameter).

### Three Built-in Annotations Demonstrated in Code

---

### `@Deprecated`

Marks a class, method, or field as **outdated**. The compiler issues a warning if deprecated code is used, signalling that it should be replaced with a newer alternative.

```java
@Deprecated
class A {
    public void showTheDataWhichBelongsToThisClass() {
        System.out.println("in show A");
    }
}
```

> ⚠️ Using a `@Deprecated` class or method in your code produces a compiler warning. It still works — the annotation is advisory, not restrictive.

---

### `@Override`

Tells the compiler you **intend** to override a method from the parent class. If the method signature doesn't match any parent method, the compiler throws an error — catching typos early.

```java
class B extends A {
    @Override
    // public void showTheDataWhichBelongToThisClass()  // ❌ typo — compile error with @Override
    public void showTheDataWhichBelongsToThisClass()    // ✅ correct signature
    {
        System.out.println("in show B");
    }
}
```

**Key insight from the code:** Without `@Override`, a typo in the method name silently creates a **new method** instead of overriding — a hard-to-find bug. With `@Override`, the compiler catches it immediately.

**Usage:**

```java
public class Demo {
    public static void main(String[] args) {
        B obj = new B();
        obj.showTheDataWhichBelongsToThisClass();   // Output: in show B
    }
}
```

---

### `@FunctionalInterface`

Marks an interface as a **functional interface** (exactly one abstract method — SAM). The compiler enforces this — if you add a second abstract method, it becomes a compile error.

```java
@FunctionalInterface
interface A {
    void show();
    // void run();   // ❌ compile error — would make it non-functional
}
```

> 💡 `@FunctionalInterface` is covered in depth in the Lambda chapter. It is listed here as a built-in Java annotation.

---

### Summary — Types of Annotations

| Annotation | Applied To | Purpose |
|---|---|---|
| `@Override` | Method | Ensures the method actually overrides a parent method |
| `@Deprecated` | Class, method, field | Marks as outdated; compiler warns users |
| `@SuppressWarnings` | Class, method | Silences specific compiler warnings |
| `@FunctionalInterface` | Interface | Enforces exactly one abstract method |

> 💡 Annotations are also the backbone of frameworks — Spring uses `@Component`, `@Autowired`; JUnit uses `@Test`; Jackson uses `@JsonProperty`. Understanding built-in annotations prepares you for framework annotations.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 13. Annotations*
