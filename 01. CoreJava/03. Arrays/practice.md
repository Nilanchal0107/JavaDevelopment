# 🧪 Practice Questions — Arrays

> Topics covered: Array declaration & initialization, indexing, `length` property, 2D arrays, jagged arrays, 3D arrays, drawbacks of arrays, array of objects, enhanced for loop (for-each)

---

## 🟢 Easy (5 Questions)

---

### Q1. Store and Print Marks
Declare an integer array `marks` of size 5 and store the following values: `85, 92, 78, 66, 90`.  
Print all the values using a `for` loop.

**Expected Output:**
```
85
92
78
66
90
```

**Concepts:** Array declaration, initialization, for loop with index

---

### Q2. Find the Largest Element
Given an array `int nums[] = {12, 45, 7, 89, 34}`, write a program to find and print the **largest element**.

**Expected Output:**
```
Largest element: 89
```

**Concepts:** Array traversal, conditional comparison

---

### Q3. Array Sum and Average
Declare an array of 6 integers: `{10, 20, 30, 40, 50, 60}`.  
Using a `for` loop, calculate and print the **sum** and **average**.

**Expected Output:**
```
Sum: 210
Average: 35.0
```

**Concepts:** Array traversal, arithmetic, data types

---

### Q4. Print with Enhanced For Loop
Take the array `String fruits[] = {"Apple", "Banana", "Mango", "Orange", "Grapes"}`.  
Print all fruits using an **enhanced for loop** (for-each).

**Expected Output:**
```
Apple
Banana
Mango
Orange
Grapes
```

**Concepts:** Enhanced for loop, String array

---

### Q5. Reverse an Array
Given `int nums[] = {1, 2, 3, 4, 5}`, print the elements in **reverse order** using a for loop.  
Do NOT create a new array — just loop backwards.

**Expected Output:**
```
5 4 3 2 1
```

**Concepts:** Array traversal in reverse, index manipulation

---

## 🟡 Medium (3 Questions)

---

### Q6. 2D Array — Multiplication Table Matrix
Create a 5×5 two-dimensional array and fill it so that `matrix[i][j] = (i+1) * (j+1)`.  
Print it as a formatted table.

**Expected Output:**
```
1   2   3   4   5
2   4   6   8   10
3   6   9   12  15
4   8   12  16  20
5   10  15  20  25
```

**Concepts:** 2D array, nested for loops, formatting

---

### Q7. Array of Objects — Library Books
Create a class `Book` with fields: `title` (String), `author` (String), `price` (double).

In `main`:
1. Create 4 `Book` objects and fill in their details.
2. Store them in a `Book[]` array.
3. Print all books using an **enhanced for loop**.
4. Also find and print the **most expensive book**.

**Concepts:** Array of objects, enhanced for loop, object property access

---

### Q8. Jagged Array — Student Scores per Subject
Create a jagged array where:
- Student 1 has scores in 3 subjects
- Student 2 has scores in 4 subjects
- Student 3 has scores in 2 subjects

Fill the array with values of your choice.  
Print each student's scores and their **total marks** using `nums[i].length` for safe traversal.

**Expected Output (example):**
```
Student 1 scores: 80 75 90  | Total: 245
Student 2 scores: 60 70 85 95 | Total: 310
Student 3 scores: 88 92  | Total: 180
```

**Concepts:** Jagged arrays, `nums[i].length`, nested loops

---

## 🔴 Hard (2 Questions)

---

### Q9. Sort an Array — Bubble Sort
Implement **Bubble Sort** on an integer array `{64, 34, 25, 12, 22, 11, 90}` **without using any Java built-in sort methods**.

After sorting, print:
1. The sorted array in ascending order.
2. The number of **swap operations** performed.

**Expected Output:**
```
Sorted array: 11 12 22 25 34 64 90
Total swaps: 15
```

**Concepts:** Nested for loops, array element swapping, algorithm implementation

---

### Q10. Student Grade Report Using Array of Objects
Create a class `Student` with fields: `rollno` (int), `name` (String), `marks` (int).  
Add a method `getGrade()` that returns a grade String based on marks:
- ≥ 90 → `"A"`, ≥ 75 → `"B"`, ≥ 60 → `"C"`, ≥ 45 → `"D"`, below 45 → `"F"`

In `main`:
1. Create an array of **5 Student objects** and set their data.
2. Print a full report table:
```
Roll No | Name       | Marks | Grade
--------|------------|-------|------
1       | Navin      | 88    | B
2       | Harsh      | 67    | C
3       | Kiran      | 97    | A
4       | Priya      | 42    | F
5       | Arun       | 75    | B
```
3. Find and print the **topper** (student with highest marks) using a loop.
4. Count how many students **failed** (marks < 45).

**Concepts:** Array of objects, enhanced for loop, methods with return type, conditional logic, array traversal

---

*Practice file for 01. CoreJava / 03. Arrays — Telusko Java Tutorial*
