# 📘 Collections — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

---

## 📑 Table of Contents
1. [Collection API Overview](#1-collection-api-overview)
2. [ArrayList — `List` Implementation](#2-arraylist--list-implementation)
3. [Sets — `HashSet` and `TreeSet`](#3-sets--hashset-and-treeset)
4. [Map — `HashMap` and `Hashtable`](#4-map--hashmap-and-hashtable)
5. [Comparator vs Comparable](#5-comparator-vs-comparable)

---

## 1. Collection API Overview

From the code:
```
Collection API → concept (the whole framework)
Collection     → Interface (root interface for List, Set, Queue)
Collections    → Class with static utility methods (sort, shuffle, etc.)
               → Different types of data structures
```

### Hierarchy

```
java.util
├── Collection (interface)
│     ├── List (interface) → ArrayList, LinkedList, Vector
│     ├── Set  (interface) → HashSet, LinkedHashSet, TreeSet
│     └── Queue (interface) → PriorityQueue, LinkedList
└── Map (interface — NOT extends Collection)
      ├── HashMap, LinkedHashMap
      └── Hashtable, TreeMap
```

> 💡 `Map` does not extend `Collection` — it stores key-value pairs instead of single elements.

---

## 2. ArrayList — `List` Implementation

`ArrayList` is a **resizable array** that maintains **insertion order** and **allows duplicates**.

```java
import java.util.ArrayList;
import java.util.List;

List<Integer> nums = new ArrayList<Integer>();

nums.add(6);   // adds to end
nums.add(5);
nums.add(8);
nums.add(2);
// nums.add("5");   // ❌ compile error with generics — type-safe

System.out.println(nums.get(2));     // Output: 8  (index 2 = third element)
System.out.println(nums.indexOf(2)); // Output: 3  (first occurrence of value 2)

for (Object n : nums) {
    int num = (Integer) n;           // explicit cast from Object to Integer
    System.out.println(nums);        // prints the entire list each iteration
}
```

**Output (for-each body):**
```
[6, 5, 8, 2]
[6, 5, 8, 2]
[6, 5, 8, 2]
[6, 5, 8, 2]
```

> ⚠️ The code prints `nums` (the whole list) in each iteration — not `num` (the individual element). This is a bug in the source code, preserved here for accuracy.

> 💡 Use `List<Integer> nums = new ArrayList<>()` (diamond operator) in modern Java instead of specifying the type twice.

### Key `List` Methods

| Method | Description |
|---|---|
| `add(element)` | Appends to end |
| `get(index)` | Returns element at index |
| `indexOf(value)` | Returns first index of value, or -1 |
| `size()` | Number of elements |
| `remove(index)` | Removes element at index |

---

## 3. Sets — `HashSet` and `TreeSet`

A **Set** stores **unique elements** — duplicates are silently ignored.

| Implementation | Order | Duplicates |
|---|---|---|
| `HashSet` | No guaranteed order | ❌ |
| `TreeSet` | Sorted (natural order) | ❌ |

### From the code — `TreeSet` with `Iterator`

```java
import java.util.TreeSet;
import java.util.Collection;
import java.util.Iterator;

Collection<Integer> nums = new TreeSet<Integer>();
nums.add(62);
nums.add(54);
nums.add(82);
nums.add(21);

Iterator<Integer> values = nums.iterator();
while (values.hasNext())
    System.out.println(values.next());
```

**Output (TreeSet — sorted):**
```
21
54
62
82
```

> 💡 `Iterator` provides a manual way to loop: `hasNext()` checks if there is another element; `next()` retrieves and advances. For-each loop (`for (int n : nums)`) is simpler when you don't need to remove elements during iteration.

### HashSet vs TreeSet

```java
Set<Integer> nums = new HashSet<>();    // unordered, O(1) add/contains
Set<Integer> nums = new TreeSet<>();    // sorted, O(log n) add/contains
```

> ⚠️ `HashSet` prints in arbitrary order — do not rely on insertion order. Use `LinkedHashSet` if you need insertion-order preservation.

---

## 4. Map — `HashMap` and `Hashtable`

A **Map** stores **key-value pairs**. Keys are unique — adding a duplicate key overwrites the previous value.

```java
import java.util.Map;
import java.util.Hashtable;

Map<String, Integer> students = new Hashtable<>();

students.put("Navin",  56);
students.put("Harsh",  23);
students.put("Sushil", 67);
students.put("Kiran",  92);
students.put("Harsh",  45);   // overwrites Harsh's value from 23 to 45

System.out.println(students.keySet());   // prints all keys (order depends on implementation)

for (String key : students.keySet()) {
    System.out.println(key + ":" + students.get(key));
}
```

**Output (order varies with Hashtable):**
```
{Navin=56, Harsh=45, Kiran=92, Sushil=67}
Navin:56
Harsh:45
Kiran:92
Sushil:67
```

### `HashMap` vs `Hashtable`

| Feature | `HashMap` | `Hashtable` |
|---|---|---|
| Thread-safe | ❌ (not synchronized) | ✅ (synchronized) |
| Null keys/values | ✅ allows one null key | ❌ no null keys or values |
| Performance | Faster | Slower |
| Modern preference | Use `ConcurrentHashMap` for thread safety | Legacy class |

### Key `Map` Methods

| Method | Description |
|---|---|
| `put(key, value)` | Adds/updates entry |
| `get(key)` | Returns value for key |
| `keySet()` | Returns `Set` of all keys |
| `values()` | Returns `Collection` of all values |
| `containsKey(key)` | Checks if key exists |
| `remove(key)` | Removes entry |

---

## 5. Comparator vs Comparable

Used to define custom **sorting order** for objects.

### `Comparator<T>` — External Sorting

A separate object that defines comparison logic. Does NOT require modifying the `Student` class.

**From the code — lambda Comparator:**

```java
class Student {
    int age;
    String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public String toString() {
        return "Student [age=" + age + ", name=" + name + "]";
    }
}

// Old way — anonymous inner class:
// Comparator<Student> com = new Comparator<Student>() {
//     public int compare(Student i, Student j) {
//         if (i.age > j.age) return 1;
//         else return -1;
//     }
// };

// Lambda way:
Comparator<Student> com = (i, j) -> i.age > j.age ? 1 : -1;

List<Student> studs = new ArrayList<>();
studs.add(new Student(21, "Navin"));
studs.add(new Student(12, "John"));
studs.add(new Student(18, "Parul"));
studs.add(new Student(20, "Kiran"));

Collections.sort(studs, com);   // sort using external Comparator
for (Student s : studs)
    System.out.println(s);
```

> ⚠️ The source code calls `Collections.sort(studs)` (without the comparator) after defining `com` — this would fail because `Student` does not implement `Comparable`. The correct call is `Collections.sort(studs, com)`.

### `Comparable<T>` — Natural Ordering (commented out in code)

Implemented **inside** the class. Defines the "natural" sort order.

```java
class Student implements Comparable<Student> {
    int age;
    // ...
    public int compareTo(Student that) {
        if (this.age > that.age) return 1;
        else return -1;
    }
}
// Then: Collections.sort(studs);  // no Comparator needed
```

### `Comparator` vs `Comparable`

| Feature | `Comparable` | `Comparator` |
|---|---|---|
| Where | Inside the class | Separate class or lambda |
| Method | `compareTo(T other)` | `compare(T o1, T o2)` |
| Sort call | `Collections.sort(list)` | `Collections.sort(list, comparator)` |
| Multiple orderings | ❌ Only one | ✅ As many as needed |
| Modify source class? | ✅ Required | ❌ Not required |

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 01. CoreJava / 17. Collections*
