# 📘 Hibernate — Notes

> Based on the Telusko YouTube channel tutorial by Navin Reddy

**One running example lives through these notes:** a Maven project called `Hibernate_Demo`, one entity called `Alien` (mapped to a PostgreSQL table `alien_data`), and one `Main` class that saves an `Alien` object to the database without writing a single line of SQL.

We start with *why* an ORM tool is needed at all, then walk through the project's files in the order Hibernate needs them, then close with the session API cheat sheet.

---

## 📑 Table of Contents

**Part 1 — Introduction**
1. [The Problem: JDBC Makes You Write the Same Glue Code Forever](#1-the-problem-jdbc-makes-you-write-the-same-glue-code-forever)
2. [What Is an ORM Tool?](#2-what-is-an-orm-tool)
3. [What Is Hibernate?](#3-what-is-hibernate)

**Part 2 — Project Anatomy**
4. [The Four Pieces of Every Hibernate App](#4-the-four-pieces-of-every-hibernate-app)
5. [pom.xml — Pulling In Hibernate](#5-pomxml--pulling-in-hibernate)
6. [hibernate.cfg.xml — Telling Hibernate Where the Database Is](#6-hibernatecfgxml--telling-hibernate-where-the-database-is)

**Part 3 — The Code, File by File**
7. [Alien.java — The Entity](#7-alienjava--the-entity)
8. [Main.java — Wiring Everything Together](#8-mainjava--wiring-everything-together)
9. [The Session Methods, Line by Line](#9-the-session-methods-line-by-line)

**Part 4 — Wrap-Up**
10. [Conclusion](#10-conclusion)
11. [Cheat Sheet](#11-cheat-sheet)

---

# Part 1 — Introduction

## 1. The Problem: JDBC Makes You Write the Same Glue Code Forever

JDBC (see [05. JDBC](../05.%20JDBC/notes.md)) solved "Java can't talk to a database" — but look at what it still asks you to do for one `Alien` object:

```java
String sql = "INSERT INTO alien_data(aid, uname, tech) VALUES (?, ?, ?)";
PreparedStatement ps = connect.prepareStatement(sql);
ps.setInt(1, alien.getAid());
ps.setString(2, alien.getUname());
ps.setString(3, alien.getTech());
ps.executeUpdate();
```

Now imagine `Alien` has 20 fields, and you need this for insert, update, delete *and* the reverse — turning a `ResultSet` row back into an `Alien` object field by field.

Every entity in the app repeats this same mechanical translation: **object fields ↔ table columns**.

That translation code teaches the computer nothing new each time — it is pure bookkeeping.

**So the root cause is:** Java speaks *objects*, the database speaks *rows and columns*, and without a tool in between, every developer hand-writes the same object-to-row translation for every class.

---

## 2. What Is an ORM Tool?

**ORM** stands for **O**bject-**R**elational **M**apping.

An ORM tool is software that automatically converts between:

- Java objects (fields, references, collections)
- Relational rows (columns, foreign keys, joins)

You describe the mapping **once** — usually with annotations on the class — and the ORM tool generates all the SQL for you afterwards.

```text
┌──────────────────┐                      ┌──────────────────┐
│   Alien object    │  ──── ORM tool ────▶ │  alien_data row   │
│  aid, uname, tech  │  ◀──── maps ────────│  aid | uname | tech│
└──────────────────┘                      └──────────────────┘
```

Think of an ORM tool as a **translator at a business meeting**: you speak your language (Java objects), the other side speaks theirs (SQL rows), and the translator converts both ways so neither side has to learn the other's language.

What you get for free once the mapping exists:

- No hand-written `INSERT` / `UPDATE` / `DELETE` / `SELECT` SQL for basic CRUD.
- The generated SQL adapts automatically if you switch database vendors.
- Object graphs (an `Alien` that owns a `List<Weapon>`) map to joins/foreign keys without you writing the join SQL.

---

## 3. What Is Hibernate?

**Hibernate** is the most widely used ORM tool for Java. It implements **JPA** (Jakarta Persistence API — the `jakarta.persistence.*` annotations like `@Entity` and `@Id`), which is just the *standard* for what an ORM must support in Java. Hibernate is one *implementation* of that standard, the same way MySQL Connector/J is one *implementation* of the JDBC standard.

```text
┌────────────────────┐
│   Your Java code    │   uses jakarta.persistence annotations + Hibernate's Session
└─────────┬───────────┘
          ▼
┌────────────────────┐
│     Hibernate       │   reads @Entity/@Id/@Table, generates SQL, manages the session
└─────────┬───────────┘
          ▼
┌────────────────────┐
│       JDBC          │   Hibernate still uses JDBC underneath to actually talk to the DB
└─────────┬───────────┘
          ▼
┌────────────────────┐
│  PostgreSQL Server   │   localhost:5432 / nilanchal
└────────────────────┘
```

This is the same layering idea as JDBC drivers: JDBC didn't replace the network, it standardized on top of it. Hibernate doesn't replace JDBC, it standardizes — and automates — on top of *that*.

---

# Part 2 — Project Anatomy

## 4. The Four Pieces of Every Hibernate App

The `Hibernate_Demo` project has exactly four moving parts:

| Piece | File | Role |
|---|---|---|
| Dependencies | `pom.xml` | pulls in Hibernate + the PostgreSQL driver |
| Database config | `hibernate.cfg.xml` | URL, username, password, dialect |
| Entity | `Alien.java` | one Java class mapped to one table |
| Runner | `Main.java` | opens a session, does one operation, closes it |

The next three sections open each one up.

---

## 5. pom.xml — Pulling In Hibernate

```xml
<dependencies>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.13</version>
    </dependency>

    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.6.1.Final</version>
    </dependency>
</dependencies>
```

Two dependencies, mirroring the two layers from the diagram in Section 3:

- `postgresql` is the **JDBC driver** — the same role MySQL Connector/J played in the JDBC notes, just for a different vendor.
- `hibernate-core` is **Hibernate itself**. It pulls in the `jakarta.persistence` annotations transitively, which is why `Alien.java` can `import jakarta.persistence.Entity` without a separate dependency.

> 💡 Tip: unlike raw JDBC, you never call `Class.forName(...)` for the driver. Hibernate reads `hibernate.connection.driver_class` from the config file and loads it for you.

---

## 6. hibernate.cfg.xml — Telling Hibernate Where the Database Is

```xml
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">org.postgresql.Driver</property>
        <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/nilanchal</property>
        <property name="hibernate.connection.username">postgres</property>
        <property name="hibernate.connection.password">********</property>

        <property name="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</property>
        <property name="hibernate.show_sql">true</property>

        <property name="hibernate.hbm2ddl.auto">update</property>
    </session-factory>
</hibernate-configuration>
```

This file lives in `src/main/resources`, so it ends up on the classpath automatically — Hibernate finds it by its default name.

| Property | What it does |
|---|---|
| `hibernate.connection.*` | the same four things a raw JDBC `DriverManager.getConnection(url, user, password)` needed |
| `hibernate.dialect` | tells Hibernate **which flavor of SQL** to generate (PostgreSQL, MySQL, Oracle all differ slightly) |
| `hibernate.show_sql` | prints every generated SQL statement to the console — invaluable for seeing what Hibernate is doing behind the scenes |
| `hibernate.hbm2ddl.auto` | controls whether Hibernate creates/updates tables to match your entities (see Section 11 for all the values) |

> ⚠️ Warning: never commit a real database password to Git. This project's actual `hibernate.cfg.xml` has a real one — treat it as a local-only file, not something to share.

> 💡 Tip: `hibernate.dialect` is technically optional in modern Hibernate — it can auto-detect the dialect from the JDBC connection. It is still set explicitly here so the intent is obvious at a glance.

---

# Part 3 — The Code, File by File

## 7. Alien.java — The Entity

```java
@Entity
@Table(name="alien_data")
public class Alien {

    @Id
    private int aid;
    private String uname;
    private String tech;

    // getters, setters, toString()
}
```

Three annotations do all the mapping work:

| Annotation | Meaning |
|---|---|
| `@Entity` | this class is not a plain object — Hibernate should manage it and give it a table |
| `@Table(name="alien_data")` | the table is called `alien_data`, not `Alien` (without this, Hibernate would default to the class name) |
| `@Id` | `aid` is the primary key — every entity needs exactly one |

Fields **without** an annotation (`uname`, `tech`) are not ignored — Hibernate maps every field to a same-named column by default. `@Table` overrides the *table* name; a `@Column` annotation (not used here) would override a *column* name the same way.

Why does Hibernate need a plain field-by-field POJO with getters and setters, instead of, say, a record?

Because Hibernate creates entity instances **via reflection** and modifies their fields directly (or through the setters), often before your code ever sees the object — a no-arg constructor and mutable fields are part of the contract.

> 💡 Tip: `int aid` (a primitive) works here because the entity is always given an id before saving. If Hibernate ever needs to represent "no id yet" (e.g. auto-generated ids), the wrapper `Integer` is the safer choice, since primitives cannot be `null`.

---

## 8. Main.java — Wiring Everything Together

```java
Alien a1 = new Alien();
a1.setAid(104);
a1.setUname("Binayak");
a1.setTech("AIML");

Configuration config = new Configuration();
config.addAnnotatedClass(org.nilanchal.Alien.class);
config.configure();

SessionFactory factory = new Configuration()
        .addAnnotatedClass(org.nilanchal.Alien.class)
        .configure()
        .buildSessionFactory();

Session session = factory.openSession();
Transaction transaction = session.beginTransaction();

session.persist(a1);

transaction.commit();

session.close();
factory.close();
```

Every Hibernate program follows the same shape — compare it to the six JDBC steps:

| Step | Hibernate | JDBC equivalent |
|---|---|---|
| 1 | Build a `Configuration`, register entities, load `hibernate.cfg.xml` | load driver |
| 2 | `buildSessionFactory()` — once per application | — (no JDBC equivalent; this is Hibernate's own expensive setup) |
| 3 | `factory.openSession()` — one per unit of work | `DriverManager.getConnection(...)` |
| 4 | `session.beginTransaction()` | (JDBC has transactions too, but this project's raw JDBC notes didn't use them explicitly) |
| 5 | `session.persist(a1)` / `get` / `merge` / `remove` | `executeUpdate(sql)` / `executeQuery(sql)` |
| 6 | `transaction.commit()` | — |
| 7 | `session.close()`, `factory.close()` | `connect.close()` |

> ⚠️ Warning: this file builds a `Configuration` **twice** — once into `config` (assigned but never used again) and once inline for `factory`. The first block (lines 19–21) is dead code; only the second `new Configuration()...buildSessionFactory()` chain actually matters.

Why is `SessionFactory` separate from `Session`?

Because building a `SessionFactory` is **expensive** — it reads the config, connects, builds the entity mappings — so it is built **once** and reused for the whole application's lifetime.

A `Session` is **cheap** to open and is meant to be short-lived: one per unit of work (e.g. one HTTP request), then closed. This mirrors why real JDBC apps pool connections instead of reconnecting every time (see [05. JDBC, Section 7](../05.%20JDBC/notes.md#7-step-2--establish-the-connection)) — `SessionFactory` *is* effectively that reusable, expensive resource for Hibernate.

Why does a write need a `Transaction`?

Hibernate does not send `persist()` to the database immediately — it queues the change and flushes it when the transaction commits. Without `beginTransaction()` / `commit()`, `persist()` silently does nothing to the database.

---

## 9. The Session Methods, Line by Line

`Main.java` has five operations, four of them commented out, showing every basic CRUD method `Session` offers:

```java
// Alien a1 = session.get(Alien.class, 102);          Eager Fetching
// Alien a1 = session.byId(Alien.class).getReference(103);  Lazy Fetching
// session.merge(a1);
// Alien a1 = session.find(Alien.class, 104);
// session.remove(a1);

session.persist(a1);   // the one that actually runs
```

| Method | SQL family | When it runs |
|---|---|---|
| `session.persist(entity)` | `INSERT` | saves a brand-new object |
| `session.get(Alien.class, id)` | `SELECT` | reads a row **immediately** — always hits the database (**eager fetching**) |
| `session.byId(Alien.class).getReference(id)` | *(none, yet)* | returns a **proxy** without querying the database — only fires the `SELECT` the moment a field is actually accessed (**lazy fetching**) |
| `session.find(Alien.class, id)` | `SELECT` | the JPA-standard equivalent of `get()` |
| `session.merge(entity)` | `UPDATE` (or `INSERT`) | re-attaches a detached object and syncs its changes to the database |
| `session.remove(entity)` | `DELETE` | deletes the row backing this managed entity |

**Eager vs. lazy, in one picture:**

```text
get(id)              → SELECT runs right now         → you always pay the query cost
byId().getReference() → returns an empty-looking proxy → SELECT runs only when you call a1.getUname()
```

Why would you ever want lazy fetching?

If you only need the id to pass to `remove()`, fetching every column with an immediate `SELECT` is wasted work. A proxy lets Hibernate skip the query entirely when the data is never read.

> ⚠️ Warning: accessing a lazy proxy's fields **after** its `Session` is closed throws `LazyInitializationException` — the proxy needs an open session to fire its delayed query.

---

# Part 4 — Wrap-Up

## 10. Conclusion

Let's retell the story in one breath.

JDBC solved "Java can't talk to a database", but left you hand-writing the same object-to-row translation for every entity, every time.

An **ORM tool** automates that translation: describe the mapping once with annotations, and the tool generates the SQL.

**Hibernate** is Java's most common ORM tool, implementing the `jakarta.persistence` (JPA) standard while still using JDBC underneath to actually reach the database.

An entity is a plain class with `@Entity`, `@Table`, and `@Id` telling Hibernate which table and which column is the primary key.

A `SessionFactory` is built once (expensive, config-heavy); a `Session` is opened per unit of work (cheap, short-lived); a `Transaction` wraps writes so they commit atomically.

`persist` / `get` (or `find`) / `merge` / `remove` cover create, read, update, and delete — with `get`/`find` fetching eagerly and `byId().getReference()` deferring the query until the data is actually touched.

### Where this leads

Spring Data JPA builds on exactly this: `Repository` interfaces are a thin, auto-implemented layer over a Hibernate `Session` doing `persist`/`find`/`remove` underneath. Knowing what `Session` and `Transaction` actually do makes Spring Data JPA feel like configuration, not magic.

---

## 11. Cheat Sheet

**Core Hibernate/JPA types:**

| Type | Package | Role |
|---|---|---|
| `Configuration` | `org.hibernate.cfg` | reads `hibernate.cfg.xml`, registers entity classes |
| `SessionFactory` | `org.hibernate` | expensive, built once, thread-safe, produces `Session`s |
| `Session` | `org.hibernate` | cheap, one per unit of work, the main API for CRUD |
| `Transaction` | `org.hibernate` | groups operations so they commit or roll back together |
| `@Entity` | `jakarta.persistence` | marks a class as a mapped table |
| `@Table(name=...)` | `jakarta.persistence` | overrides the default table name (class name) |
| `@Id` | `jakarta.persistence` | marks the primary-key field |
| `@Column(name=...)` | `jakarta.persistence` | overrides a default column name (not used in this project, but common) |

**The standard flow:**

```java
SessionFactory factory = new Configuration()
        .addAnnotatedClass(Alien.class)
        .configure()                       // reads hibernate.cfg.xml
        .buildSessionFactory();            // do this ONCE per app

Session session = factory.openSession();   // one per unit of work
Transaction tx = session.beginTransaction();

session.persist(entity);                   // or get / merge / remove

tx.commit();
session.close();
// factory.close();                        // once, at app shutdown
```

**CRUD methods:**

| Method | Family | Notes |
|---|---|---|
| `persist(obj)` | Create | needs an open transaction to actually hit the DB |
| `get(Class, id)` / `find(Class, id)` | Read (eager) | queries immediately, returns `null` if not found |
| `byId(Class).getReference(id)` | Read (lazy) | returns a proxy; queries only on first field access |
| `merge(obj)` | Update | syncs a detached object's changes back to the DB |
| `remove(obj)` | Delete | needs a managed entity, not just an id |

**`hibernate.hbm2ddl.auto` values:**

| Value | Behavior |
|---|---|
| `validate` | checks the schema matches entities, changes nothing |
| `update` | adds missing tables/columns, never deletes data — used in this project |
| `create` | drops and recreates the schema on every startup |
| `create-drop` | like `create`, plus drops the schema when the `SessionFactory` closes |
| `none` | Hibernate touches nothing; you manage the schema yourself |

> ⚠️ Warning: `create` / `create-drop` destroy data. Only `update` or `validate` belong anywhere near a real database.

**Common errors:**

| Error | Usual cause |
|---|---|
| `HibernateException: No CurrentSessionContext configured` | called `getCurrentSession()` without configuring one; use `openSession()` instead |
| `LazyInitializationException` | touched a lazy proxy's fields after the `Session` closed |
| `TransactionRequiredException` | called `persist()`/`remove()` without an active transaction |
| `PropertyAccessException` | entity is missing a no-arg constructor or a required getter/setter |
| `Unknown entity: ...` | class not registered with `.addAnnotatedClass(...)` or missing `@Entity` |

**Golden rules:**

- Build one `SessionFactory` per application; open and close a `Session` per unit of work.
- Every write needs a `Transaction` — begin it, do the work, commit it.
- Prefer lazy fetching for data you might not need; read it only inside an open session.
- Never use `hbm2ddl.auto=create`/`create-drop` against real data, and never commit real DB credentials to Git.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

-

---

*Notes created from Telusko Java Tutorial — 08. Hibernate*
