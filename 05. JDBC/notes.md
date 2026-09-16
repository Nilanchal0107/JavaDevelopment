# 📘 JDBC — Notes

> Based on the Telusko YouTube channel tutorial by Navin Reddy

**One running example lives through these notes:** a MySQL table called `studentinfo` inside a database called `jdbclearning`, and a Java project called `FirstJDBCApp` that inserts, updates, reads and deletes students in it.

We start with *why* JDBC exists at all, then walk through the steps every JDBC program follows, then read every file in the project in the order it was written, and finally compare `Statement` with `PreparedStatement`.

---

## 📑 Table of Contents

**Part 1 — Introduction**
1. [The Problem: Java Cannot Talk to a Database by Itself](#1-the-problem-java-cannot-talk-to-a-database-by-itself)
2. [Finding the Root Cause](#2-finding-the-root-cause)
3. [What Is JDBC?](#3-what-is-jdbc)
4. [The JDBC Driver](#4-the-jdbc-driver)

**Part 2 — Steps Involved in Developing a JDBC Application**
5. [The Six Steps at a Glance](#5-the-six-steps-at-a-glance)
6. [Step 1 — Load and Register the Driver](#6-step-1--load-and-register-the-driver)
7. [Step 2 — Establish the Connection](#7-step-2--establish-the-connection)
8. [Step 3 — Create a Statement](#8-step-3--create-a-statement)
9. [Step 4 — Execute the Query](#9-step-4--execute-the-query)
10. [Step 5 — Process the Result](#10-step-5--process-the-result)
11. [Step 6 — Close the Resources](#11-step-6--close-the-resources)

**Part 3 — The Code, File by File**
12. [The Table Behind the Code](#12-the-table-behind-the-code)
13. [LaunchClassForNameEx — What Class.forName Really Does](#13-launchclassfornameex--what-classforname-really-does)
14. [LaunchApp01 — INSERT](#14-launchapp01--insert)
15. [LaunchApp02 — UPDATE](#15-launchapp02--update)
16. [LaunchApp03 — SELECT](#16-launchapp03--select)
17. [LaunchApp04 — DELETE](#17-launchapp04--delete)
18. [LaunchApp05 — One Method for Any SQL](#18-launchapp05--one-method-for-any-sql)
19. [jdbcUtil — Stop Repeating Yourself](#19-jdbcutil--stop-repeating-yourself)
20. [LaunchApp06 — try-catch-finally](#20-launchapp06--try-catch-finally)
21. [LaunchApp07 — First PreparedStatement](#21-launchapp07--first-preparedstatement)
22. [LaunchApp08 — PreparedStatement UPDATE](#22-launchapp08--preparedstatement-update)
23. [LaunchApp09 — PreparedStatement DELETE](#23-launchapp09--preparedstatement-delete)
24. [LaunchApp10 — PreparedStatement SELECT](#24-launchapp10--preparedstatement-select)
25. [LaunchBatch — Batch Processing](#25-launchbatch--batch-processing)

**Part 4 — Statement vs PreparedStatement**
26. [The Problem With Statement](#26-the-problem-with-statement)
27. [How PreparedStatement Fixes It](#27-how-preparedstatement-fixes-it)
28. [Comparison Table](#28-comparison-table)

**Part 5 — Wrap-Up**
29. [Conclusion](#29-conclusion)
30. [Cheat Sheet](#30-cheat-sheet)

---

# Part 1 — Introduction

## 1. The Problem: Java Cannot Talk to a Database by Itself

Let's not start with "JDBC is an API".

Let's start with the problem.

Your Java program has student data in variables:

```java
int id = 1;
String name = "Nilanchal";
int age = 19;
String city = "Kalyan";
```

The moment `main()` ends, all of this is gone.

Variables live in RAM, and RAM is wiped when the program stops.

So where do we keep data that must survive?

In a **database**, like MySQL.

But now a new problem appears.

MySQL is a completely separate program.

It runs in its own process, often on a different machine, and it listens on a network port (MySQL uses `3306`).

It does not understand Java objects.

It only understands **SQL**, sent to it using **MySQL's own network protocol** — a private language of bytes that MySQL invented.

So to save one student, your Java program would have to:

1. Open a network socket to `localhost:3306`
2. Speak MySQL's handshake protocol and log in
3. Convert the SQL text into MySQL's byte format
4. Read MySQL's byte-format reply and convert it back into Java values

That is thousands of lines of low-level networking code.

And it gets worse.

Oracle speaks a *different* protocol.

PostgreSQL speaks yet another one.

If your company moves from MySQL to Oracle, you rewrite all of it.

---

## 2. Finding the Root Cause

Let's ask: **what actually went wrong here?**

Talking to a database is not the hard part — we just want to send SQL and get rows back.

The hard part is that every database has its own private way of being talked to, and our Java code is glued directly to that private way.

> So the root cause is: *every database speaks a different low-level protocol, and without a common standard, every Java program must learn each protocol by hand and is locked to one database forever.*

So how do we solve it?

**Agree on one common set of Java methods for "connect, send SQL, read rows", and let each database company write the code that translates those methods into their own protocol.**

That common agreement is **JDBC**.

---

## 3. What Is JDBC?

**JDBC** stands for **J**ava **D**ata**B**ase **C**onnectivity.

It is a standard Java API, living in the `java.sql` package, that lets a Java program connect to any relational database, run SQL, and read the results.

Think of a **universal power adapter** when travelling:

- Your laptop charger (your Java code) has one plug shape.
- Every country (every database) has a different wall socket.
- The adapter (the driver) sits in between, so your charger never changes.

The key design choice:

**`Connection`, `Statement`, `PreparedStatement` and `ResultSet` are interfaces, not classes.**

Why interfaces?

Because Java (Oracle) only writes the *rules* — "a connection must be able to create a statement".

The database company writes the *real code* behind those rules.

So your program is written only against interfaces, and never mentions MySQL's classes directly.

```text
┌────────────────────┐
│   Your Java code   │   uses only java.sql interfaces: Connection, Statement, ResultSet
└─────────┬──────────┘
          ▼
┌────────────────────┐
│   JDBC API         │   java.sql package (comes with the JDK)
│   DriverManager    │   picks the right driver for the URL
└─────────┬──────────┘
          ▼
┌────────────────────┐
│   JDBC Driver      │   mysql-connector-j-26.7.0.jar (written by MySQL)
│                    │   translates JDBC calls → MySQL protocol
└─────────┬──────────┘
          ▼
┌────────────────────┐
│   MySQL Server     │   localhost:3306 / jdbclearning
└────────────────────┘
```

Switching from MySQL to PostgreSQL now means: swap the driver jar and change the URL.

The JDBC code stays almost the same.

---

## 4. The JDBC Driver

A **JDBC driver** is a jar file that contains the database company's implementation of the JDBC interfaces.

The JDK has the *interfaces*, but not the *implementations*.

So without the driver jar, your code compiles but fails at runtime with `ClassNotFoundException` or `No suitable driver found`.

In this project, the MySQL driver (**MySQL Connector/J**) is added in Eclipse as an external jar.

You can see it in `FirstJDBCApp/.classpath`:

```xml
<classpathentry kind="lib" path="C:/Users/nirakar24/Downloads/mysql-connector-j-26.7.0/mysql-connector-j-26.7.0/mysql-connector-j-26.7.0.jar"/>
```

In Eclipse: **right-click project → Build Path → Add External Archives → pick the jar**.

> 💡 Tip: in a Maven project you do not download the jar by hand. Add the dependency `com.mysql:mysql-connector-j` to `pom.xml` and Maven fetches it for you.

> 💡 Tip: MySQL Connector/J is a **Type 4** driver — it is written in pure Java and talks to MySQL directly over the network. That is the type almost every modern driver uses.

---

# Part 2 — Steps Involved in Developing a JDBC Application

## 5. The Six Steps at a Glance

Every JDBC program in this project follows the same six steps.

The code even marks them with comments.

Think of it like making a **phone call**:

| Step | JDBC | Phone call |
|------|------|-----------|
| 1 | Load and register the driver | install the calling app |
| 2 | Establish the connection | dial the number and wait for "hello" |
| 3 | Create a statement | open your mouth to speak |
| 4 | Execute the query | say your question |
| 5 | Process the result | listen to the answer |
| 6 | Close the resources | hang up |

```text
Load Driver ──▶ Get Connection ──▶ Create Statement ──▶ Execute SQL ──▶ Process Result ──▶ Close
```

And here are all six steps in one real file, `LaunchApp01.java`:

```java
package com.nilanchal.jdbclearning;
import java.sql.*;                                            // Connection, DriverManager, Statement, SQLException...
public class LaunchApp01 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		// Step 1: Load and Register the Driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Step 2: Establish the connection
		String url="jdbc:mysql://localhost:3306/jdbclearning";
		String user="root";
		String password="pass@12323";
		Connection connect = DriverManager.getConnection(url, user, password);

		// Step 3: Creating Statement
		Statement statement = connect.createStatement();

		// Step 4: Execute query
		String sql ="INSERT INTO studentinfo(id, sname, sage, scity) VALUES(1, 'Nilanchal', 19, 'Kalyan')";
		int rowAffected=statement.executeUpdate(sql);

		// Step 5: Process the Result
		if (rowAffected == 0)
		{
			System.out.println("Updation Failed");
		}
		else
		{
			System.out.println("Updated Successfully");
		}

		// Step 6: Close the resources
		statement.close();
		connect.close();
	}

}
```

The next six sections open up each step.

---

## 6. Step 1 — Load and Register the Driver

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

**What:** it loads the MySQL driver class into memory.

**Why does loading a class register a driver?**

Because the MySQL `Driver` class has a **static block** that runs the moment the class is loaded.

Inside that static block, the driver registers itself:

```java
// roughly what is inside com.mysql.cj.jdbc.Driver (written by MySQL, not by you)
static {
    DriverManager.registerDriver(new Driver());
}
```

So `Class.forName(...)` → class loads → static block runs → driver is now in `DriverManager`'s list.

Why use a **String** name instead of `new Driver()`?

Because a String can come from a config file.

Your code then has zero compile-time dependency on MySQL's classes, and changing the database needs no code change.

> 💡 Tip: since **JDBC 4.0 (Java 6)**, this step is optional. `DriverManager` automatically finds drivers inside jars on the classpath (via the file `META-INF/services/java.sql.Driver`). It is still taught because older code and interviews expect it.

> ⚠️ Warning: the class name is `com.mysql.cj.jdbc.Driver` (with `cj`). The old name `com.mysql.jdbc.Driver` is from Connector/J 5 and is deprecated.

---

## 7. Step 2 — Establish the Connection

```java
String url="jdbc:mysql://localhost:3306/jdbclearning";
String user="root";
String password="pass@12323";
Connection connect = DriverManager.getConnection(url, user, password);
```

**What:** it opens a real network session with MySQL and logs in.

**How does `DriverManager` know which driver to use?**

It asks every registered driver, "can you handle this URL?".

The MySQL driver says yes to anything starting with `jdbc:mysql:`.

Anatomy of the URL:

```text
jdbc  :  mysql  ://  localhost  :  3306  /  jdbclearning
 │         │           │            │          │
 │         │           │            │          └── database name
 │         │           │            └── port MySQL listens on
 │         │           └── machine where MySQL runs
 │         └── sub-protocol → which driver
 └── protocol → always "jdbc"
```

The returned `Connection` object is your open phone line.

Everything else (statements, results) is created *from* it.

> ⚠️ Warning: opening a connection is **slow** (network + login). That is why real applications reuse connections through a *connection pool* (e.g. HikariCP in Spring Boot) instead of opening a new one per query.

> ⚠️ Warning: never hard-code a real password in source code that goes to Git. Anyone who can read the repo can read the password.

---

## 8. Step 3 — Create a Statement

```java
Statement statement = connect.createStatement();
```

**What:** it creates an object that can carry SQL text to the database over this connection.

Why not just call `connect.execute(sql)`?

Because one connection can have many statements, and each statement keeps its own state — its current result, its update count, its batch.

Separating them keeps that state tidy.

There are two kinds used in this project:

| Created with | Type | Used in |
|---|---|---|
| `connect.createStatement()` | `Statement` | LaunchApp01 – 06 |
| `connect.prepareStatement(query)` | `PreparedStatement` | LaunchApp07 – 10, LaunchBatch |

Part 4 explains why the second one exists.

---

## 9. Step 4 — Execute the Query

SQL comes in two families, and JDBC has a method for each, plus one for "I don't know yet":

| Method | Use for | Returns |
|---|---|---|
| `executeUpdate(sql)` | `INSERT`, `UPDATE`, `DELETE` (change data) | `int` — number of rows affected |
| `executeQuery(sql)` | `SELECT` (read data) | `ResultSet` — the rows |
| `execute(sql)` | any SQL | `boolean` — `true` if the result is a `ResultSet`, `false` if it is an update count |

Why do they return different things?

Because the database *answers* differently.

After an `UPDATE`, it answers "3 rows changed".

After a `SELECT`, it answers with a table of rows.

```java
int rowAffected = statement.executeUpdate("UPDATE studentinfo set sage=20 where id=2");
ResultSet rs    = statement.executeQuery("select * FROM studentinfo");
```

> ⚠️ Warning: calling `executeQuery()` with an `INSERT`, or `executeUpdate()` with a `SELECT`, throws `SQLException`. Match the method to the SQL.

---

## 10. Step 5 — Process the Result

### For INSERT / UPDATE / DELETE

Check the row count:

```java
if (rowAffected == 0)
	System.out.println("Updation Failed");
else
	System.out.println("Updated Successfully");
```

### For SELECT

A `ResultSet` is like a **cursor** — a finger pointing at one row of the result table.

It starts **before** the first row, not on it.

```text
          id  sname      sage  scity
  👉 (start, before row 1)
  row 1   1   Nilanchal  19    Kalyan
  row 2   2   ...        20    ...
          (after last row → next() returns false)
```

`rs.next()` moves the finger one row down and returns `true` if there is a row there.

```java
while(rs.next())                                   // move to next row; stop when no rows are left
{
	System.out.println(rs.getInt(1)                // column 1 → id
	         + " " + rs.getString(2)               // column 2 → sname
	         + " " + rs.getInt(3)                  // column 3 → sage
	         + " " + rs.getString(4));             // column 4 → scity
}
```

Why must we call `next()` before reading the first row?

Because the result might be **empty**.

Starting before row 1 lets a single `while(rs.next())` handle zero rows and many rows with the same code.

> ⚠️ Warning: column numbers start at **1**, not 0. `rs.getInt(0)` throws `SQLException`.

> 💡 Tip: `rs.getString("sname")` reads by column name. It is safer than `getString(2)`, because it does not break if someone adds a column to the table.

---

## 11. Step 6 — Close the Resources

```java
rs.close();
statement.close();
connect.close();
```

Why close?

A connection is not just a Java object — it is an open network socket and a session on the MySQL server.

MySQL allows only a limited number of connections (default `max_connections` is 151).

If every run leaks a connection, the database eventually refuses everyone with `Too many connections`.

Close in **reverse order of opening**: `ResultSet` → `Statement` → `Connection`.

It is like leaving a building: close the room, then the floor, then the front door.

> 💡 Tip: closing a `Connection` normally closes its statements, and closing a `Statement` closes its `ResultSet`. Closing each one explicitly is still the safe habit, because pools keep connections open.

---

# Part 3 — The Code, File by File

All files live in `FirstJDBCApp/src/com/nilanchal/jdbclearning/`, except `LaunchClassForNameEx.java`, which is in the default package.

## 12. The Table Behind the Code

The code expects this table (reconstructed from the queries):

```sql
CREATE DATABASE jdbclearning;
USE jdbclearning;

CREATE TABLE studentinfo (
    id    INT PRIMARY KEY,
    sname VARCHAR(50),
    sage  INT,
    scity VARCHAR(50)
);
```

---

## 13. LaunchClassForNameEx — What Class.forName Really Does

**Purpose:** prove that `Class.forName()` runs a class's static block.

```java
public class LaunchClassForNameEx {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
	{
		Class.forName("Demo");                                    // loads Demo → runs ONLY its static block

		DriverManager.registerDriver(new com.mysql.jdbc.Driver()); // the manual way to register a driver
	}
}

class Demo
{
	static
	{
		System.out.println("Static Block");                       // runs once, when the class is loaded
	}

	{
		System.out.println("Instance block ==> Non static");      // runs only when an object is created
	}
}
```

Output:

```text
Static Block
```

Why is `Instance block` not printed?

Because `Class.forName` only **loads** the class — it never creates an object with `new`.

Instance blocks run per object, static blocks run per class load.

That one fact is the whole reason Step 1 works.

> ⚠️ Warning: `DriverManager.registerDriver(new com.mysql.jdbc.Driver())` registers the driver **twice** — once from the static block (triggered by `new`), and once from the explicit call. It also uses the deprecated class name. `Class.forName("com.mysql.cj.jdbc.Driver")` is the right way.

---

## 14. LaunchApp01 — INSERT

**Purpose:** insert one hard-coded student with `executeUpdate`.

This is the full six-step file shown in [Section 5](#5-the-six-steps-at-a-glance).

```java
String sql ="INSERT INTO studentinfo(id, sname, sage, scity) VALUES(1, 'Nilanchal', 19, 'Kalyan')";
int rowAffected=statement.executeUpdate(sql);   // 1 if inserted
```

> ⚠️ Warning: run it a **second** time and you will not see `Updation Failed`. `id` is the primary key, so MySQL rejects the duplicate and JDBC throws `SQLIntegrityConstraintViolationException`. For INSERT, failure shows up as an **exception**, not as `0`.

---

## 15. LaunchApp02 — UPDATE

**Purpose:** change a student's age. Only the SQL is different from LaunchApp01.

```java
String sql ="UPDATE studentinfo set sage=20 where id=2";
int rowAffected=statement.executeUpdate(sql);   // how many rows matched id=2 and were changed
```

> 💡 Tip: `0` here does not mean "error". It means *no row had id = 2*. Nothing broke — there was simply nothing to update.

---

## 16. LaunchApp03 — SELECT

**Purpose:** read all students. New things: `executeQuery` and `ResultSet`.

```java
String sql ="select * FROM studentinfo";
ResultSet rs = statement.executeQuery(sql);     // rows come back as a ResultSet

while(rs.next())                                // walk the cursor row by row
{
	System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
}

rs.close();                                     // extra resource to close
statement.close();
connect.close();
```

Note that SQL keywords are case-insensitive: `select * FROM` works fine.

---

## 17. LaunchApp04 — DELETE

**Purpose:** delete the student with id 1. Same shape as INSERT and UPDATE.

```java
String sql ="DELETE FROM studentinfo where id=1";
int rowAffected=statement.executeUpdate(sql);   // 1 first time, 0 on every run after that
```

> ⚠️ Warning: a `DELETE` or `UPDATE` **without a `WHERE`** affects every row in the table. JDBC will not warn you.

---

## 18. LaunchApp05 — One Method for Any SQL

**Purpose:** use `execute()` when you do not know in advance whether the SQL is a SELECT or an update.

When would that happen?

Think of a tool where the user types any SQL — the program cannot know which method to call.

```java
String sql ="SELECT * FROM studentinfo";
boolean status = statement.execute(sql);        // true → result is a ResultSet, false → it is an update count

if (status)
{
	System.out.println("If Block");
	ResultSet rs = statement.getResultSet();    // fetch the rows that execute() produced
	while(rs.next())
	{
		System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
	}
}
else
{
	System.out.println("Else Block");
	int rows=statement.getUpdateCount();        // fetch the row count that execute() produced
	if(rows == 0)
		System.out.println("Operation failed");
	else
		System.out.println("Operation performed Successfully");
}
```

Change `sql` to an `UPDATE` and the else block runs instead.

> ⚠️ Warning: the `ResultSet` in this file is never closed. Only `statement` and `connect` are.

---

## 19. jdbcUtil — Stop Repeating Yourself

**The problem:** LaunchApp01 to 05 each copy the same 5 lines for driver loading and connecting.

Change the password once, and you edit 5 files.

**The fix:** move the repeated part into one utility class.

```java
public class jdbcUtil {

	static                                              // runs once, when jdbcUtil is first used
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");  // Step 1, done only once for the whole app
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException   // Step 2, reusable
	{
		String url="jdbc:mysql://localhost:3306/jdbclearning";
		String user="root";
		String password="pass@12323";
		return DriverManager.getConnection(url, user, password);
	}

	public static void closeConnection(Connection connect, Statement statement) throws SQLException  // Step 6, reusable
	{
		statement.close();
		connect.close();
	}
}
```

Why put `Class.forName` in a **static block**?

Because the driver needs registering only once, and a static block runs exactly once — the same trick MySQL's own `Driver` class uses.

Why does `closeConnection` accept a `Statement` but work for a `PreparedStatement` too?

Because `PreparedStatement` **extends** `Statement`, so it can be passed wherever a `Statement` is expected.

> ⚠️ Warning: `closeConnection` has two hidden bugs:
> 1. If `getConnection()` fails, `connect` and `statement` are still `null`, so `statement.close()` throws `NullPointerException` inside `finally`.
> 2. If `statement.close()` throws, `connect.close()` never runs → the connection leaks.
>
> Section 29 shows the modern fix: try-with-resources.

> 💡 Tip: Java class names conventionally start with a capital letter — `JdbcUtil`, not `jdbcUtil`.

---

## 20. LaunchApp06 — try-catch-finally

**Purpose:** same as LaunchApp05, but using `jdbcUtil` and proper exception handling instead of `throws` on `main`.

```java
Connection connect = null;                  // declared OUTSIDE try, so finally can see them
Statement statement = null;

try
{
	connect = jdbcUtil.getConnection();
	statement = connect.createStatement();
	// ... execute and process exactly like LaunchApp05 ...
}
catch (SQLException e)                      // database problems: wrong SQL, wrong password, DB down
{
	e.printStackTrace();
}
catch (Exception e)                         // anything else
{
	e.printStackTrace();
}
finally                                     // runs whether try succeeded or failed
{
	try
	{
		jdbcUtil.closeConnection(connect, statement);
	}
	catch (SQLException e)
	{
		e.printStackTrace();
	}
}
```

Why is closing inside `finally`?

In LaunchApp01, if `executeUpdate` throws, the program jumps out of `main` and the `close()` lines are **skipped**.

`finally` runs no matter what, so the resources always get released.

Why are the variables declared as `null` outside `try`?

Because a variable declared inside `try { }` does not exist inside `finally { }`.

> 💡 Tip: `catch (SQLException e)` must come **before** `catch (Exception e)`. The more specific exception goes first, otherwise the compiler reports it as unreachable.

---

## 21. LaunchApp07 — First PreparedStatement

**Purpose:** insert a student using values typed by the user, with `?` placeholders.

```java
String query ="INSERT INTO studentinfo(id, sname, sage, scity) VALUES (?, ?, ?, ?)";  // ? = a value to fill in later
prestatement = connect.prepareStatement(query);     // SQL is given NOW, before the values are known

Scanner scan = new Scanner(System.in);
System.out.println("Enter your id");
Integer id = scan.nextInt();
System.out.println("Enter your name");
String name = scan.next();
System.out.println("Enter your age");
Integer age = scan.nextInt();
System.out.println("Enter your city");
String city = scan.next();

prestatement.setInt(1, id);                          // 1st ? ← id
prestatement.setString(2, name);                     // 2nd ? ← name (no quotes needed!)
prestatement.setInt(3, age);                         // 3rd ? ← age
prestatement.setString(4, city);                     // 4th ? ← city

int rowAffected = prestatement.executeUpdate();      // NO sql argument — it already has the SQL
```

Notice the differences from `Statement`:

- The SQL is passed to `prepareStatement(query)`, not to `executeUpdate()`.
- Values are set with typed `setXxx(position, value)` methods.
- No `'` quotes around the name and city — the driver handles that.

> ⚠️ Warning: this file **does not compile** as written. Line 10 has a stray character: `PreparedStatement prestatement = null;s`. Remove the `s`.

> ⚠️ Warning: `finally` calls `jdbcUtil.closeConnection(connect, statement)`, but `statement` is always `null` here (it is never used). That throws `NullPointerException`, and `prestatement` is never closed. Pass `prestatement` instead, as LaunchApp08 does.

> ⚠️ Warning: `scan.next()` reads only **one word**. Typing the city `New Delhi` stores just `New`. Use `scan.nextLine()` for text with spaces.

---

## 22. LaunchApp08 — PreparedStatement UPDATE

**Purpose:** update a student's age by id.

```java
String query ="UPDATE studentinfo SET sage=? WHERE id=?";   // ?1 = sage, ?2 = id
prestatement = connect.prepareStatement(query);

prestatement.setInt(2, id);                                 // set 2nd ? first — allowed
prestatement.setInt(1, age);

int rowAffected = prestatement.executeUpdate();
```

The key lesson hidden in this file:

**The number in `setInt(number, value)` is the position of the `?` in the SQL, not the order you call the methods in.**

`sage=?` is the 1st `?`, and `id=?` is the 2nd.

So `setInt(2, id)` can be called before `setInt(1, age)` and it still works.

> ⚠️ Warning: swapping the numbers (`setInt(1, id)`, `setInt(2, age)`) does not throw any error — it silently runs `SET sage=<id> WHERE id=<age>`. Always count the `?`s left to right.

---

## 23. LaunchApp09 — PreparedStatement DELETE

**Purpose:** delete a student by the id the user types. Only one `?`.

```java
String query ="DELETE FROM studentinfo WHERE id=?";
prestatement = connect.prepareStatement(query);

Integer id = scan.nextInt();
prestatement.setInt(1, id);

int rowAffected = prestatement.executeUpdate();     // 0 → no student with that id
```

---

## 24. LaunchApp10 — PreparedStatement SELECT

**Purpose:** fetch **one** student by id.

```java
String query ="SELECT * FROM studentinfo WHERE id=?";
prestatement = connect.prepareStatement(query);

Integer id = scan.nextInt();
prestatement.setInt(1, id);

rs = prestatement.executeQuery();                   // SELECT → executeQuery, still no sql argument

if (rs.next())                                      // "if", not "while" — id is a primary key, so at most 1 row
{
	System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
}
else
{
	System.out.println("There is no record with id = " + id);
}
```

Why `if` instead of `while`?

Because `id` is unique, the result has either 0 or 1 row.

`if (rs.next())` answers both questions at once: *is there a row?* and *move to it*.

> ⚠️ Warning: `rs` is declared and filled but never closed. `jdbcUtil.closeConnection` only knows about the connection and statement.

---

## 25. LaunchBatch — Batch Processing

**The problem:** updating 1,000 students with 1,000 `executeUpdate()` calls means 1,000 trips over the network.

It is like going to the shop 1,000 times to buy 1,000 items.

**The fix:** collect all the operations in a basket, and send them in one trip.

```java
String query ="UPDATE studentinfo SET sage=? WHERE id=?";
prestatement = connect.prepareStatement(query);     // one SQL, prepared once

prestatement.setInt(1, 19);
prestatement.setInt(2, 3);
prestatement.addBatch();                            // put "age 19 for id 3" in the basket

prestatement.setInt(1, 17);
prestatement.setInt(2, 2);
prestatement.addBatch();                            // put "age 17 for id 2" in the basket

prestatement.setInt(1, 20);
prestatement.setInt(2, 3);
prestatement.addBatch();                            // put "age 20 for id 3" in the basket

prestatement.executeBatch();                        // send the whole basket
```

`addBatch()` saves the current set of values and lets you set new ones for the next item.

Final state of the table: student 2 has age **17**, student 3 has age **20** — the second update to id 3 overwrites the first, because the batch runs in order.

> 💡 Tip: `executeBatch()` returns an `int[]` — one row count per item in the batch. This file ignores it, but you can loop over it to check each operation.

> 💡 Tip: MySQL's driver sends batch items one by one by default. Add `?rewriteBatchedStatements=true` to the URL to make it truly combine them into one network trip.

---

# Part 4 — Statement vs PreparedStatement

## 26. The Problem With Statement

LaunchApp01 to 06 used hard-coded values.

But real programs take values **from users**.

So what happens when we build SQL with `Statement` using user input?

We have to glue strings together:

```java
String name = scan.next();
String sql = "SELECT * FROM studentinfo WHERE sname='" + name + "'";
ResultSet rs = statement.executeQuery(sql);
```

This creates three problems.

**Problem 1 — Quote soup.**

Every text value needs `'` around it, inside `"` Java strings.

One missing quote breaks the SQL, and it is very hard to spot.

**Problem 2 — Names with a `'` break the query.**

A student named `D'Souza` produces:

```sql
SELECT * FROM studentinfo WHERE sname='D'Souza'
```

MySQL sees the string end after `D` and reports a syntax error.

**Problem 3 — SQL Injection.**

Now suppose the user types this "name":

```text
x'OR'1'='1
```

The glued SQL becomes:

```sql
SELECT * FROM studentinfo WHERE sname='x'OR'1'='1'
```

`'1'='1'` is always true, so the query returns **every student in the table**.

Replace `SELECT *` with `DELETE`, and the user just wiped the whole table.

The user did not give us *data* — they rewrote our *SQL*.

**Problem 4 — Same work repeated.**

Every time you run a `Statement`, the database must parse, check and plan the SQL from scratch — even if it is the same query with a different id.

> So the root cause is: *with `Statement`, SQL code and user data are mixed into one string, so the database cannot tell which part is our command and which part is the user's value.*

So how do we solve it?

**Send the SQL and the values separately.**

---

## 27. How PreparedStatement Fixes It

```java
String query = "SELECT * FROM studentinfo WHERE sname=?";
PreparedStatement prestatement = connect.prepareStatement(query);   // SQL shape is fixed here
prestatement.setString(1, name);                                    // value is sent as pure data
ResultSet rs = prestatement.executeQuery();
```

The SQL structure is decided **before** any user value arrives.

The `?` is a hole that can only ever hold a **value** — never SQL keywords.

So if the user types `x'OR'1'='1`, the database searches for a student whose name is literally the text `x'OR'1'='1`.

It finds nobody, and nothing bad happens.

Think of a **bank form**: the "Amount" box can only hold an amount.

Writing "and also transfer everything" in that box does not add a new instruction to the form.

How each problem disappears:

| Problem with Statement | Why PreparedStatement fixes it |
|---|---|
| Quote soup | no quotes needed — just `?` and `setString` |
| `D'Souza` breaks the query | the driver sends the value safely, quotes and all |
| SQL injection | values can never change the SQL structure |
| Re-parsing every time | the query can be prepared once and run many times with different values (see `LaunchBatch`) |
| Wrong types | `setInt`, `setString` make the type explicit |

> 💡 Tip: by default, MySQL Connector/J prepares statements on the **client side** — it safely escapes the values itself and sends the final SQL. You are still fully protected from injection. To make MySQL itself pre-compile the query, add `?useServerPrepStmts=true` to the URL.

> ⚠️ Warning: `?` can only replace **values**. It cannot replace table names or column names — `SELECT * FROM ?` does not work.

---

## 28. Comparison Table

| | `Statement` | `PreparedStatement` |
|---|---|---|
| Created with | `connect.createStatement()` | `connect.prepareStatement(sql)` |
| SQL given at | execute time: `executeQuery(sql)` | creation time: `prepareStatement(sql)` |
| Execute call | `executeUpdate(sql)` / `executeQuery(sql)` | `executeUpdate()` / `executeQuery()` — no argument |
| Values | glued into the SQL string | `?` placeholders + `setXxx(index, value)` |
| SQL injection | ❌ vulnerable | ✅ safe |
| Quotes and special characters | handled by you | handled by the driver |
| Readability | messy string concatenation | clean |
| Same query, many values | re-parsed each time | prepared once, reused |
| Batch with different values | awkward | natural (`addBatch()`) |
| Relationship | parent interface | `extends Statement` |
| Good for | fixed SQL with no user input (e.g. `CREATE TABLE`) | almost everything else |
| In this project | LaunchApp01 – 06 | LaunchApp07 – 10, LaunchBatch |

---

# Part 5 — Wrap-Up

## 29. Conclusion

Let's retell the story in one breath.

Java variables die when the program stops, so data must live in a database.

Every database speaks its own private protocol, so Java needed one common API — **JDBC** — and each database vendor ships a **driver** that translates it.

Every JDBC program follows the same six steps: **load driver → connect → create statement → execute → process result → close**.

`executeUpdate` returns a row count, `executeQuery` returns a `ResultSet`, and `execute` returns a `boolean` when you don't know which one you'll get.

Repeating connection code in every file was painful, so it moved into **`jdbcUtil`**.

Crashes skipped the `close()` calls, so closing moved into **`finally`**.

Gluing user input into SQL was messy and dangerous, so **`PreparedStatement`** replaced `Statement`.

Many trips to the database were slow, so **batch processing** grouped them.

### The modern way to close: try-with-resources

The project's `finally` + `jdbcUtil.closeConnection` approach has real bugs (null statements, leaked `ResultSet`s, a failed close skipping the next close).

Java 7 added **try-with-resources**, which closes everything automatically, in reverse order, even when exceptions happen:

```java
String query = "SELECT * FROM studentinfo WHERE id=?";

try (Connection connect = jdbcUtil.getConnection();                     // closed 3rd
     PreparedStatement prestatement = connect.prepareStatement(query))  // closed 2nd
{
	prestatement.setInt(1, 3);

	try (ResultSet rs = prestatement.executeQuery())                    // closed 1st
	{
		if (rs.next())
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
	}
}
catch (SQLException e)
{
	e.printStackTrace();
}
```

No `null` variables, no `finally`, no `closeConnection` helper.

It works because `Connection`, `Statement` and `ResultSet` all implement `AutoCloseable`.

### Where this leads

Raw JDBC is what everything else is built on.

Spring's `JdbcTemplate`, Hibernate and Spring Data JPA all use JDBC underneath — they just write these six steps for you.

Knowing JDBC means you understand what those tools are doing when something goes wrong.

---

## 30. Cheat Sheet

**Core interfaces and classes (`java.sql`):**

| Type | Kind | Role |
|---|---|---|
| `DriverManager` | class | registers drivers, hands out connections |
| `Driver` | interface | implemented by the vendor jar |
| `Connection` | interface | an open session with the database |
| `Statement` | interface | runs plain SQL strings |
| `PreparedStatement` | interface | runs SQL with `?` placeholders (`extends Statement`) |
| `ResultSet` | interface | cursor over the rows of a SELECT |
| `SQLException` | class | checked exception for every database error |

**The six steps:**

```java
Class.forName("com.mysql.cj.jdbc.Driver");                                   // 1. load & register driver
Connection con = DriverManager.getConnection(url, user, password);          // 2. connect
PreparedStatement ps = con.prepareStatement("SELECT * FROM t WHERE id=?");  // 3. create statement
ps.setInt(1, 5);
ResultSet rs = ps.executeQuery();                                           // 4. execute
while (rs.next()) { rs.getInt(1); rs.getString("name"); }                   // 5. process
rs.close(); ps.close(); con.close();                                        // 6. close (reverse order)
```

**MySQL connection URL:**

```text
jdbc:mysql://localhost:3306/jdbclearning
jdbc:mysql://<host>:<port>/<database>?rewriteBatchedStatements=true
```

**Execute methods:**

| Method | SQL | Returns |
|---|---|---|
| `executeUpdate()` | INSERT / UPDATE / DELETE | `int` rows affected |
| `executeQuery()` | SELECT | `ResultSet` |
| `execute()` | anything | `boolean` → then `getResultSet()` or `getUpdateCount()` |
| `executeBatch()` | many queued updates | `int[]` rows affected per item |

**PreparedStatement setters:**

| Method | Java type → SQL type |
|---|---|
| `setInt(i, v)` | `int` → `INT` |
| `setString(i, v)` | `String` → `VARCHAR` |
| `setDouble(i, v)` | `double` → `DOUBLE` |
| `setBoolean(i, v)` | `boolean` → `BOOLEAN` |
| `setDate(i, v)` | `java.sql.Date` → `DATE` |
| `setNull(i, Types.INTEGER)` | → `NULL` |

`i` = position of the `?`, counting from **1**, left to right.

**ResultSet methods:**

| Method | What it does |
|---|---|
| `next()` | move to next row; `false` when no rows left |
| `getInt(1)` / `getInt("sage")` | read column by index (from 1) or by name |
| `getString(2)` / `getString("sname")` | read text column |

**Batch:**

```java
ps.setInt(1, 19); ps.setInt(2, 3); ps.addBatch();   // queue item
ps.setInt(1, 17); ps.setInt(2, 2); ps.addBatch();   // queue item
int[] counts = ps.executeBatch();                    // send all
```

**Common errors:**

| Error | Usual cause |
|---|---|
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | driver jar not on the build path |
| `No suitable driver found for jdbc:...` | typo in URL, or driver jar missing |
| `Communications link failure` | MySQL is not running, or wrong host / port |
| `Access denied for user 'root'@'localhost'` | wrong username or password |
| `Unknown database 'jdbclearning'` | database not created |
| `SQLIntegrityConstraintViolationException: Duplicate entry` | inserting an id that already exists |
| `Column Index out of range, 0 < 1` | used `getInt(0)` — indexes start at 1 |
| `NullPointerException` in `finally` | closing a statement / connection that was never created |

**Golden rules:**

- Use `PreparedStatement` for anything involving user input.
- Close `ResultSet` → `Statement` → `Connection` — prefer try-with-resources.
- `executeUpdate` returning `0` means "no rows matched", not "error". Real errors throw `SQLException`.
- Never hard-code real passwords in code that goes to Git.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 05. JDBC*
