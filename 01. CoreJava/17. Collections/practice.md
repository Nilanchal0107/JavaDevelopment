# 🧪 Practice Questions — Collections

> Topics covered: Collection API hierarchy (Collection interface, Collections utility class), `ArrayList` (`add`, `get`, `indexOf`, iteration, generics), `HashSet`, `TreeSet` (sorted, no duplicates), `Iterator` (`hasNext`, `next`), `HashMap`, `Hashtable` (key-value, `put`, `get`, `keySet`, duplicate key overwrites), `Comparator` (external, lambda), `Comparable` (internal, `compareTo`), `Collections.sort()`

---

## 🟢 Easy (5 Questions)

---

### Q1. ArrayList — Basic Operations
Create a `List<String>` (ArrayList) and add: `"Java"`, `"Python"`, `"C++"`, `"JavaScript"`.  
Print:
1. The element at index 1
2. The index of `"C++"`
3. The entire list using a for-each loop

**Expected Output:**
```
Python
2
Java
Python
C++
JavaScript
```

**Concepts:** `ArrayList`, `add()`, `get()`, `indexOf()`, for-each iteration

---

### Q2. HashSet — No Duplicates
Create a `Set<Integer>` (HashSet) and add: `5`, `3`, `8`, `5`, `3`, `10`.  
Print all elements using a for-each loop and note that duplicates are removed.

**Expected Output (order may vary):**
```
3
5
8
10
```

**Concepts:** `HashSet`, duplicates silently ignored, unordered output

---

### Q3. TreeSet — Sorted Order
Create a `Collection<Integer>` (TreeSet) and add: `62`, `54`, `82`, `21`.  
Iterate using an `Iterator` and print each element.

**Expected Output:**
```
21
54
62
82
```

**Concepts:** `TreeSet`, automatic sorting, `Iterator` (`hasNext()`, `next()`)

---

### Q4. HashMap — Key-Value Operations
Create a `Map<String, Integer>` (HashMap) and add:
- `"Alice" → 85`
- `"Bob" → 92`
- `"Carol" → 78`

Print all keys using `keySet()`, then loop through and print each `"key:value"` pair.

**Expected Output (order may vary):**
```
[Alice, Bob, Carol]
Alice:85
Bob:92
Carol:78
```

**Concepts:** `HashMap`, `put()`, `keySet()`, `get()`, iteration over map

---

### Q5. Map — Duplicate Key Overwrites
Create a `Map<String, Integer>` and add:
- `"Harsh" → 23`
- `"Harsh" → 45`

Print the map. Verify only one entry for `"Harsh"` exists, with the latest value.

**Expected Output:**
```
{Harsh=45}
```

**Concepts:** Map key uniqueness, overwrite on duplicate key

---

## 🟡 Medium (3 Questions)

---

### Q6. Comparator — Sort Custom Objects
Create a class `Student` with `int age` and `String name`.  
Override `toString()` as `"Student [age=<age>, name=<name>]"`.

Create a `List<Student>` with 4 students (from the notes: Navin/21, John/12, Parul/18, Kiran/20).  
Use a `Comparator` lambda to sort by age ascending.  
Print each student after sorting.

**Expected Output:**
```
Student [age=12, name=John]
Student [age=18, name=Parul]
Student [age=20, name=Kiran]
Student [age=21, name=Navin]
```

**Concepts:** `Comparator` lambda, `Collections.sort(list, comparator)`, `compare()` return value

---

### Q7. Comparable — Natural Ordering
Modify `Student` from Q6 to `implements Comparable<Student>`.  
Implement `compareTo(Student that)` to sort by age ascending.  
Sort using `Collections.sort(studs)` (no Comparator argument needed).

**Expected Output:**
```
Student [age=12, name=John]
Student [age=18, name=Parul]
Student [age=20, name=Kiran]
Student [age=21, name=Navin]
```

In comments, explain the difference between using `Comparable` (this question) vs `Comparator` (Q6) — when would you use each?

**Concepts:** `Comparable`, `compareTo()`, natural ordering, `Collections.sort(list)` without Comparator

---

### Q8. Choose the Right Collection
For each scenario, state which collection type (`ArrayList`, `HashSet`, `TreeSet`, `HashMap`) is best and why:

Write a program demonstrating all four scenarios:
1. Store a list of names where duplicates are allowed and order matters.
2. Store unique usernames, unordered — fast lookup needed.
3. Store unique scores that should always be retrieved in sorted order.
4. Store student name → grade mappings for fast lookup by name.

Use a small example for each and print the contents.

**Concepts:** Collection choice rationale, characteristics of each type

---

## 🔴 Hard (2 Questions)

---

### Q9. Predict the Output — Collections Behaviour
**Before running**, predict the exact output:

```java
import java.util.*;

public class Demo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));
        Set<Integer>  set  = new HashSet<>(list);
        Set<Integer>  tree = new TreeSet<>(list);

        System.out.println("List size:  " + list.size());
        System.out.println("Set size:   " + set.size());
        System.out.println("Tree first: " + ((TreeSet<Integer>) tree).first());
        System.out.println("Tree last:  " + ((TreeSet<Integer>) tree).last());

        Collections.sort(list);
        System.out.println("Sorted list: " + list);
    }
}
```

Answer in comments:
1. Why is `Set size` less than `List size`?
2. What is `tree.first()` and `tree.last()`?
3. What does `Collections.sort(list)` produce?

**Concepts:** List vs Set size (duplicates), `TreeSet.first()`/`last()`, `Collections.sort()`, type casting

---

### Q10. Word Frequency Counter
Write a program that:
1. Has a hardcoded `String sentence = "the quick brown fox jumps over the lazy dog the fox"`.
2. Splits it into words.
3. Uses a `Map<String, Integer>` to count the frequency of each word.
4. Prints each word and its count.
5. Finds and prints the most frequent word.

**Constraints:**
- No Scanner.
- Use `String.split(" ")` to tokenize.
- Use `map.getOrDefault(word, 0)` for clean counting.
- Print in any order.

**Expected Output (partial):**
```
the : 3
fox : 2
quick : 1
...
Most frequent: the (3 times)
```

**Concepts:** `HashMap` as frequency counter, `getOrDefault()`, `keySet()` iteration, map accumulation, finding max in a map

---

*Practice file for 01. CoreJava / 17. Collections — Telusko Java Tutorial*
