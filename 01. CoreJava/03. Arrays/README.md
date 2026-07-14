# 📘 Arrays — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Need of an Array](#1-need-of-an-array)
2. [Creation of an Array](#2-creation-of-an-array)
3. [Multi-Dimensional Arrays](#3-multi-dimensional-arrays)
4. [Jagged and 3D Arrays](#4-jagged-and-3d-arrays)
5. [Drawbacks of Arrays](#5-drawbacks-of-arrays)
6. [Array of Objects](#6-array-of-objects)
7. [Enhanced For Loop (For-Each)](#7-enhanced-for-loop-for-each)

---

## 1. Need of an Array

### Problem Without Arrays
When you need to store multiple related values, you'd declare many separate variables:
```java
int i = 5;
int j = 6;
int k = 7;
```
This becomes unmanageable for 100+ values.

### Solution — Array
An **array** is a container that holds **multiple values of the same type** under a single name.

```java
int num[] = {5, 6, 7};       // Array of 3 integers
int num[] = new int[4];      // Array that can hold 4 integers
```

### Properties of Arrays
- Fixed size — size is set at creation and cannot change.
- **Zero-indexed** — first element is at index `0`.
- All elements must be of the **same data type** (homogeneous).
- Stored in **contiguous memory** locations.

---

## 2. Creation of an Array

### Method 1 — Initialize with values
```java
int nums[] = {3, 7, 2, 4};   // size is 4, automatically determined
```

### Method 2 — Declare size, assign later
```java
int nums[] = new int[4];   // creates array of 4 integers (default: 0)
nums[0] = 4;
nums[1] = 8;
nums[2] = 3;
nums[3] = 9;
```

### Accessing Elements
```java
System.out.println(nums[1]);   // Output: 8
nums[1] = 6;                   // modify element at index 1
```

### Iterating with a For Loop
```java
for (int i = 0; i < 4; i++) {
    System.out.println(nums[i]);
}
```

### Using `.length` Property
```java
for (int i = 0; i < nums.length; i++) {
    System.out.println(nums[i]);
}
```
> 💡 Always prefer `nums.length` over hardcoding the size — it makes code flexible.

---

## 3. Multi-Dimensional Arrays

A **2D array** is an array of arrays — like a table with rows and columns.

### Declaration & Initialization
```java
int nums[][] = new int[3][4];   // 3 rows, 4 columns
```

### Filling with `Math.random()`

> ⚠️ **Bug in original code** — The source file actually contains:

```java
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 4; j++) {
        nums[i][j] = (int)Math.random() * 100;  // ❌ BUG: cast happens before multiply → always 0
        System.out.println(nums[i][j]);
    }
    System.out.println();
}
```

> ⚠️ `(int)Math.random()*100` is a classic Java trap. `Math.random()` returns a `double` between 0.0 and 1.0. Casting it to `int` first gives `0` always, then `0 * 100 = 0`. The correct form is:

```java
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 4; j++) {
        nums[i][j] = (int)(Math.random() * 10);  // ✅ CORRECT: multiply first, then cast
    }
}
```

### Printing a 2D Array

**Using traditional nested for loop:**
```java
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 4; j++) {
        System.out.print(nums[i][j] + " ");
    }
    System.out.println();   // new line after each row
}
```

**Using enhanced for loop:**
```java
for (int[] row : nums) {
    for (int val : row) {
        System.out.print(val + " ");
    }
    System.out.println();
}
```

---

## 4. Jagged and 3D Arrays

### Jagged Array
A **jagged array** is a 2D array where **each row can have a different number of columns**.

```java
int nums[][] = new int[3][];   // 3 rows, column size not fixed yet
nums[0] = new int[3];          // row 0 has 3 columns
nums[1] = new int[4];          // row 1 has 4 columns
nums[2] = new int[2];          // row 2 has 2 columns
```

**Why useful?** Saves memory when rows have different data volumes (e.g., a student's subjects vary by semester).

### Iterating a Jagged Array Safely
```java
for (int i = 0; i < nums.length; i++) {
    for (int j = 0; j < nums[i].length; j++) {   // nums[i].length varies!
        nums[i][j] = (int)(Math.random() * 10);
    }
}
```

### 3D Array
A **3D array** is an array of 2D arrays — think of it as a cube.

```java
int nums[][][] = new int[3][4][5];   // 3 layers, 4 rows, 5 columns
```

---

## 5. Drawbacks of Arrays

| Drawback | Explanation |
|----------|-------------|
| **Fixed size** | Once created, you cannot expand or shrink an array. |
| **Contiguous memory** | All elements must be stored in adjacent memory locations, which can be a problem for large arrays. |
| **Slow searching** | Finding an element requires scanning one by one (linear search). |
| **Homogeneous only** | An array can only store values of the **same data type**. |

> 💡 These limitations are why Java provides **Collections** (like `ArrayList`, `HashMap`) that are dynamic and flexible — covered in later topics.

---

## 6. Array of Objects

You can create an array where each element is an **object** of a class.

### Example — Array of Student Objects
```java
class Student {
    int rollno;
    String name;
    int marks;
}

public class Demo {
    public static void main(String[] args) {
        // Create individual objects
        Student s1 = new Student();
        s1.rollno = 1;
        s1.name = "Navin";
        s1.marks = 88;

        Student s2 = new Student();
        s2.rollno = 2;
        s2.name = "Harsh";
        s2.marks = 67;

        Student s3 = new Student();
        s3.rollno = 3;
        s3.name = "Kiran";
        s3.marks = 97;

        // Create array of objects
        Student students[] = new Student[3];
        students[0] = s1;
        students[1] = s2;
        students[2] = s3;

        // Access via loop
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].name + ": " + students[i].marks);
        }
    }
}
```

**Output:**
```
Navin: 88
Harsh: 67
Kiran: 97
```

> 💡 The array holds **references** to the objects — it doesn't store the object data itself. Each `students[i]` points to the actual object on the heap.

---

## 7. Enhanced For Loop (For-Each)

The **enhanced for loop** (also called for-each loop) provides a cleaner syntax for iterating through arrays and collections.

### Syntax
```java
for (dataType variableName : arrayName) {
    // use variableName
}
```

### For Primitive Arrays
```java
int nums[] = {4, 8, 3, 9};

for (int n : nums) {
    System.out.println(n);
}
// Output: 4  8  3  9
```

### For Object Arrays
```java
for (Student stud : students) {
    System.out.println(stud.name + ": " + stud.marks);
}
```

### For 2D Arrays
```java
for (int[] row : nums) {
    for (int val : row) {
        System.out.print(val + " ");
    }
    System.out.println();
}
```

### Enhanced For vs Traditional For

| Feature | Traditional `for` | Enhanced `for` |
|---|---|---|
| Index access | ✅ Yes (`nums[i]`) | ❌ No |
| Modify elements | ✅ Yes | ❌ No (read-only) |
| Readability | Verbose | Clean & concise |
| Use case | When you need the index | When you just need values |

> ⚠️ Use traditional `for` when you need the **index** or want to **modify** elements. Use enhanced `for` when you only need to **read** values.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 03. Arrays*
