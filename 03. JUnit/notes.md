# 📘 JUnit 5 — Notes

> Based on the Telusko YouTube channel tutorial by Navin Reddy

**One running example lives through these notes:** a `ScoreCalculator` from an interview application that gives marks to a candidate.

We start with *why* testing exists at all, zoom into *unit* testing, see where it sits in the life of a software project, and only then open up JUnit 5 — first its architecture, then how to actually write tests with it.

---

## 📑 Table of Contents

**Part 1 — Why Testing Exists**
1. [Introduction: The Problem With Changing Code](#1-introduction-the-problem-with-changing-code)
2. [Finding the Root Cause](#2-finding-the-root-cause)
3. [What Is Testing?](#3-what-is-testing)
4. [Testing vs Unit Testing](#4-testing-vs-unit-testing)
5. [What Makes a Good Unit Test](#5-what-makes-a-good-unit-test)
6. [SDLC — Where Testing Fits](#6-sdlc--where-testing-fits)

**Part 2 — The Framework**
7. [Unit Testing Framework — Why Not Just main()?](#7-unit-testing-framework--why-not-just-main)
8. [What Is JUnit 5?](#8-what-is-junit-5)
9. [JUnit 5 Architecture](#9-junit-5-architecture)
10. [Project Setup — Folders, Dependency, mvn test](#10-project-setup--folders-dependency-mvn-test)

**Part 3 — Writing Tests**
11. [Your First JUnit Test](#11-your-first-junit-test)
12. [Arrange-Act-Assert](#12-arrange-act-assert)
13. [Essential Assertions](#13-essential-assertions)
14. [Reading a Failed Test](#14-reading-a-failed-test)
15. [Testing Exceptions](#15-testing-exceptions)
16. [@BeforeEach — A Fresh Start for Every Test](#16-beforeeach--a-fresh-start-for-every-test)
17. [Lifecycle Annotations](#17-lifecycle-annotations)
18. [Parameterized Tests — One Test, Many Inputs](#18-parameterized-tests--one-test-many-inputs)
19. [Naming Tests](#19-naming-tests)

**Part 4 — Testing Real Projects**
20. [JUnit vs Mockito](#20-junit-vs-mockito)
21. [What to Test in the Interview Project](#21-what-to-test-in-the-interview-project)
22. [What Not to Do](#22-what-not-to-do)

**Part 5 — Reference**
23. [Cheat Sheet](#23-cheat-sheet)
24. [Minimum Learning Checklist](#24-minimum-learning-checklist)

---

# Part 1 — Why Testing Exists

## 1. Introduction: The Problem With Changing Code

Let's not start with "JUnit is a testing framework".

Let's start with the bug it was born to catch.

You build an interview application.

It has a class that calculates a candidate's score:

```java
public class ScoreCalculator {

    public int calculate(int correctAnswers, int marksPerQuestion) {
        return correctAnswers * marksPerQuestion;   // 8 correct × 5 marks = 40
    }
}
```

It works today.

A candidate with 8 correct answers gets 40 marks, exactly as expected.

Two weeks later, a new requirement arrives: *"support unanswered questions."*

You open the class and change it:

```java
public class ScoreCalculator {

    public int calculate(int correctAnswers, int unanswered, int marksPerQuestion) {
        return (correctAnswers - unanswered) * marksPerQuestion;   // 🐛 unanswered should NOT reduce the score
    }
}
```

The code compiles.

The application starts.

No error appears anywhere.

But a candidate with 8 correct answers and 2 unanswered questions now gets **30** instead of **40**.

The old, working calculation broke **silently**.

So how would you even find out?

You would have to test it by hand, again:

1. Start the application
2. Create an interview
3. Submit answers
4. Calculate the expected result in your head
5. Compare the result on screen with your calculation

That takes maybe five minutes for one feature.

Now imagine the app has 50 features.

Every single change could break any of them.

Are you going to spend four hours re-checking all 50 features after every small change?

Nobody does.

So people check only the feature they just touched, and the bug in the *other* feature walks straight into production.

---

## 2. Finding the Root Cause

Let's ask: **what actually went wrong here?**

The code change was not the problem — code *has* to change.

The problem is that nobody re-checked the old behaviour after the change.

And why did nobody re-check it?

Because checking was a manual ritual: slow, boring, and easy to skip.

> So the root cause is: *a new code change can break previously working functionality, and the knowledge of "what correct looks like" lives in a human's head, so re-checking it is too slow to repeat after every change.*

Sound familiar?

It is the same root cause Maven solved for builds: knowledge trapped in a human's head instead of written in a file a machine can run.

So how do we solve it?

**Write the check down as code, and let the machine re-run it every time.**

That is exactly what JUnit gives us: Java programs that automatically verify other Java programs.

---

## 3. What Is Testing?

Now that we have felt the pain, the definition is easy.

**Testing** means checking that software behaves the way we expect it to.

Every test, manual or automated, big or small, has the same four parts:

| Part | In our example |
|------|----------------|
| **Input** | 8 correct answers, 5 marks per question |
| **Expected result** | 40 |
| **Actual result** | whatever the code returns |
| **Verdict** | pass if expected == actual, fail otherwise |

When *you* do those four steps with your eyes and a calculator, it is **manual testing**.

When *code* does those four steps, it is **automated testing**.

| | Manual testing | Automated testing |
|---|---|---|
| Who checks | a human | a program |
| Speed | minutes per check | milliseconds per check |
| Repeatable after every change? | not realistically | yes, for free |
| Gets bored or skips steps? | yes | never |
| Good for | exploring, UI feel, "does this look right?" | re-checking known rules again and again |

> 💡 Tip: automated tests do not replace humans. They take over the *boring, repeatable* checking so humans can do the thinking.

---

## 4. Testing vs Unit Testing

"Testing" is an umbrella word.

Under it are different **levels**, depending on *how much* of the application a test touches at once.

Think of building a car:

- Before assembly, you check each **spark plug** and **brake pad** on its own. → *unit testing*
- Then you bolt the engine to the gearbox and check they work **together**. → *integration testing*
- Then you take the **whole car** for a test drive. → *system / end-to-end testing*
- Finally, the **customer** drives it and says "yes, this is what I ordered". → *acceptance testing*

Why check the spark plug alone first?

Because if the test drive fails, you have no idea *which* of 30,000 parts is guilty.

If the spark plug test fails, you know exactly where the problem is.

Here are the same levels in the interview application:

| Level | What it checks | Interview app example | Usually written by | Speed |
|-------|----------------|-----------------------|--------------------|-------|
| **Unit** | one class or method, in isolation | `calculate(8, 5)` returns `40` | developer | milliseconds |
| **Integration** | several parts working together | `InterviewService` saves an interview to a real database and reads it back | developer | seconds |
| **System / End-to-End** | the whole running application, like a user | log in → create interview → submit answers → see score | QA / automation team | minutes |
| **Acceptance** | does it meet the business need? | the client confirms the scoring rules match their policy | client / product owner | varies |

So what is the difference between *testing* and *unit testing*?

| | Testing (in general) | Unit Testing |
|---|---|---|
| Scope | anything from one method to the whole product | one small unit — usually a single class or method |
| Question it answers | "does the software work?" | "does *this piece* do its job correctly?" |
| Can be manual? | yes | practically always automated |
| Who does it | developers, testers, clients | developers, while writing the code |
| When it fails, you know… | *something* is broken | *exactly which unit* is broken |
| Needs DB / network / full app? | often | no — that is the whole point |

A healthy project has many unit tests, fewer integration tests, and only a few end-to-end tests.

This shape is called the **testing pyramid**:

```text
            /\
           /  \          End-to-End   → few   (slow, expensive, breaks easily)
          /----\
         /      \        Integration  → some
        /--------\
       /          \      Unit         → many  (fast, cheap, points at the exact problem)
      /____________\
```

Why is unit the widest layer?

Because unit tests are the cheapest to write, the fastest to run, and the most precise when they fail.

**JUnit is the tool for that bottom layer.**

---

## 5. What Makes a Good Unit Test

A **unit test** tests one small unit of code — usually a single class or method.

```text
Input -> ScoreCalculator -> Expected score
```

Nothing else should be involved.

That is why a true unit test should normally **avoid**:

- Real databases
- Internet access
- Real AI API calls
- File storage
- Starting the entire Spring application

Why avoid all of these?

Imagine testing a calculator's `+` button, but the test first needs Wi-Fi, a database login, and a 20-second app startup.

Now the test can fail because Wi-Fi dropped — even though `+` works perfectly.

Every outside thing you pull in is one more reason for the test to fail *for the wrong reason*.

Remove them, and a unit test gets four properties:

| Property | Meaning | Why it matters |
|----------|---------|----------------|
| **Fast** | runs in milliseconds | you will actually run 500 tests after every change if it takes 2 seconds, not if it takes 20 minutes |
| **Independent** | does not depend on other tests or their order | one failing test does not cause ten confusing failures |
| **Repeatable** | same result on every machine, every time | "passes on my laptop, fails on the server" is gone |
| **Predictable** | no randomness, no clocks, no network surprises | a failure always means real broken code, never bad luck |

> ⚠️ Warning: a test that sometimes passes and sometimes fails (a *flaky* test) is worse than no test. People learn to ignore it, and then they ignore the real failures too.

---

## 6. SDLC — Where Testing Fits

We know *what* a unit test is.

The next natural question: **at what point in a project do we write it?**

To answer that, we need the big picture of how software gets built.

**SDLC** stands for **S**oftware **D**evelopment **L**ife **C**ycle.

It is the sequence of phases every software project goes through, from "someone has an idea" to "people are using it":

```text
 ┌──────────────┐   ┌──────────┐   ┌────────┐   ┌─────────────┐   ┌─────────┐   ┌────────────┐   ┌─────────────┐
 │ 1.Requirement│──▶│2.Planning│──▶│3.Design│──▶│4.Development│──▶│5.Testing│──▶│6.Deployment│──▶│7.Maintenance│
 └──────────────┘   └──────────┘   └────────┘   └─────────────┘   └─────────┘   └────────────┘   └──────┬──────┘
        ▲                                                                                                │
        └──────────────────────────────── new requirements, bug reports ─────────────────────────────────┘
```

| Phase | Question it answers | Interview app example |
|-------|---------------------|-----------------------|
| **1. Requirement analysis** | *what* must the software do? | "candidates get marks per correct answer; unanswered questions give zero" |
| **2. Planning** | how long, how many people, what risks? | "3 developers, 2 months, AI provider may be slow" |
| **3. Design** | *how* will we build it? | classes like `ScoreCalculator`, `InterviewService`; database tables |
| **4. Development** | write the code | writing `ScoreCalculator.calculate()` — **and its unit tests** |
| **5. Testing** | does the whole thing work? | integration, system and acceptance testing |
| **6. Deployment** | release it to real users | put the app on a server |
| **7. Maintenance** | fix bugs, add features | "support unanswered questions" — the change from Section 1 |

Look at phase 7.

The bug from Section 1 was born *there* — during maintenance, when old code changed.

Maintenance goes on for years, so code keeps changing for years.

That is exactly why tests must stay in the project forever and be re-run after every change, not written once and thrown away.

### Why is unit testing done in Development, not in the Testing phase?

Ask: **how expensive is a bug, depending on when it is found?**

- Found by your unit test while coding → you fix it in **5 minutes**. The code is still fresh in your head.
- Found by a tester in the Testing phase → they write a bug report, you stop your current work, re-read old code, reproduce it, fix it, send it back. **Hours or days.**
- Found by a real candidate in production → wrong scores were given, customers are upset, an emergency fix is needed. **Days, money, and trust.**

The later a bug is found, the more expensive it becomes.

So the smartest move is to catch bugs as **early** (as far *left* in the diagram) as possible.

This idea is called **shift-left testing**, and unit testing is its front line.

### The V-Model — every build phase has a matching test level

The V-Model is a way of drawing the SDLC that makes this connection clear:

```text
  Requirements ─────────────────────────────────▶ Acceptance Testing
      \                                                  /
     System Design ───────────────────────▶ System Testing
          \                                        /
         Module / Class Design ──────▶ Integration Testing
              \                              /
               \                            /
                Coding ────────────▶ Unit Testing   ← JUnit lives here
```

Read it like this: each phase on the left is *verified* by the test level on the right at the same height.

- **Unit testing** checks the smallest design decision: *does this class do its job?*
- **Acceptance testing** checks the biggest one: *did we build what the client asked for?*

> 💡 Tip: in Agile teams, the SDLC phases repeat in small loops every 1–2 weeks instead of once. That makes automated tests even more important — code changes every sprint, so it gets re-checked every sprint.

---

# Part 2 — The Framework

## 7. Unit Testing Framework — Why Not Just main()?

We decided: *write the checks as code.*

So why do we need a framework at all?

Can't we just write a `main()` method?

Let's try:

```java
public class ScoreCalculatorManualCheck {

    public static void main(String[] args) {
        ScoreCalculator calculator = new ScoreCalculator();

        int result = calculator.calculate(8, 5);
        if (result == 40) {
            System.out.println("PASS: 8 correct answers");
        } else {
            System.out.println("FAIL: expected 40 but got " + result);
        }

        int zero = calculator.calculate(0, 5);
        if (zero == 0) {
            System.out.println("PASS: 0 correct answers");
        } else {
            System.out.println("FAIL: expected 0 but got " + zero);
        }
    }
}
```

It works — for two checks.

Now look at what goes wrong as the project grows:

**Problem 1 — Boilerplate everywhere.**

Every check needs the same `if / else / println`. With 300 checks, that is 1,500 lines of copy-paste.

**Problem 2 — One crash stops everything.**

If check #3 throws an exception, checks #4 to #300 never run. You don't know if they pass or fail.

**Problem 3 — Checks share state.**

All checks live in one method, sharing the same variables. Check #2 can be accidentally affected by check #1.

**Problem 4 — A human still reads the output.**

Somebody has to scroll through 300 lines looking for the word `FAIL`. We are back to manual work.

**Problem 5 — Nothing connects it to the build.**

Maven does not know this `main()` is a test. `mvn package` happily builds a jar even when checks fail.

**Problem 6 — Many test classes, many `main()`s.**

With 40 test classes, who runs all 40 `main()` methods, and who combines their results?

> So the root cause is: *every project would have to re-invent the same testing machinery — marking checks, comparing values, isolating checks, running all of them, and reporting results.*

So how do we solve it?

The same way we solve every "everyone re-invents the same thing" problem: **a framework** that provides the machinery once, for everybody.

A **unit testing framework** gives you:

| You need | The framework gives you |
|----------|-------------------------|
| a way to say "this method is a test" | annotations like `@Test` |
| a short way to compare expected vs actual | assertion methods like `assertEquals()` |
| something that finds and runs all tests | a test runner (no `main()` needed) |
| one crash should not stop other tests | each test runs separately; failures are recorded, the runner moves on |
| setup/cleanup around tests | lifecycle annotations like `@BeforeEach` |
| a readable result | a report: *passed, failed, why* |
| build integration | `mvn test`, IDE green/red buttons, build fails when a test fails |

Popular unit testing frameworks: **JUnit** and **TestNG** for Java, **pytest** for Python, **Jest** for JavaScript.

In the Java and Spring Boot world, **JUnit** is the default choice.

---

## 8. What Is JUnit 5?

**JUnit** is a unit testing framework for Java.

A JUnit test specifies:

- The input
- The expected result
- The actual result
- Whether the result is correct

And a JUnit test:

- Does **not** need a `main()` method
- Runs automatically
- Can be executed repeatedly
- Reports passed and failed tests
- Works with Maven and Spring Boot

**JUnit 5** is the modern version, released in 2017.

Its tests are written using the **Jupiter API**, which is why every JUnit 5 import starts with `org.junit.jupiter`:

```java
import org.junit.jupiter.api.Test;
```

### Why does the version matter? JUnit 3 vs 4 vs 5

Remember the `nilanchal-app` Maven project?

Its archetype added `junit` version `3.8.1` — that is **JUnit 3**, from the early 2000s.

In JUnit 3, a test class had to `extends TestCase` and every test method had to start with the word `test`.

JUnit 4 replaced those rules with annotations.

JUnit 5 was then rebuilt from scratch.

When you search online, you will find all three styles mixed together, so it helps to recognise them:

| | JUnit 4 | JUnit 5 (Jupiter) |
|---|---|---|
| Import package | `org.junit` | `org.junit.jupiter.api` |
| Structure | one single jar | Platform + Jupiter + Vintage (see next section) |
| Before every test | `@Before` | `@BeforeEach` |
| After every test | `@After` | `@AfterEach` |
| Once before all tests | `@BeforeClass` | `@BeforeAll` |
| Once after all tests | `@AfterClass` | `@AfterAll` |
| Skip a test | `@Ignore` | `@Disabled` |
| Expect an exception | `@Test(expected = X.class)` | `assertThrows(X.class, () -> ...)` |
| Plug in extra behaviour | `@RunWith` (only **one** allowed) | `@ExtendWith` (many allowed) |
| Visibility | class and methods must be `public` | package-private is fine |
| Minimum Java | Java 5 | Java 8 (lambdas!) |

> ⚠️ Warning: never mix `org.junit.Test` (JUnit 4) and `org.junit.jupiter.api.Test` (JUnit 5) in one test class. Your IDE's auto-import may pick the wrong one, and then the test is silently skipped or behaves strangely. Always check the import.

> 💡 Tip: the newer **JUnit 6** (released in 2025) keeps the same Jupiter API and the same Platform/Jupiter/Vintage architecture, so everything in these notes still applies.

---

## 9. JUnit 5 Architecture

Why was JUnit 5 rebuilt from scratch instead of just adding features to JUnit 4?

### The problem with JUnit 4

JUnit 4 was **one jar** that did **two completely different jobs**:

1. **For you, the developer:** the API to *write* tests (`@Test`, `assertEquals`)
2. **For tools** like IntelliJ, Eclipse and Maven: the machinery to *find and run* tests

Tools needed deep information about tests — names, results, progress.

JUnit 4 did not offer a proper public way to give it, so IDEs reached into JUnit 4's **internal, private code** to get it.

So what happened when the JUnit team wanted to improve those internals?

Every change broke IntelliJ, Eclipse, or Maven.

JUnit 4 became frozen — it could not evolve without breaking the tools that depended on its insides.

On top of that, a JUnit 4 test class could use only **one** `@RunWith`, so you could not combine, for example, Spring's runner and Mockito's runner.

> So the root cause is: *"how to write tests" and "how tools run tests" were glued together in one jar, so neither could change without breaking the other.*

### The fix: split the jobs

JUnit 5 separates those jobs into three sub-projects:

> **JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage**

```text
   ┌────────────┐   ┌──────────────────┐   ┌────────┐   ┌─────────────────┐
   │  IntelliJ  │   │ Maven (Surefire) │   │ Gradle │   │ Console Launcher│      ← TOOLS
   └─────┬──────┘   └────────┬─────────┘   └───┬────┘   └────────┬────────┘
         └───────────────────┴──────┬──────────┴─────────────────┘
                                    ▼   Launcher API
   ┌───────────────────────────────────────────────────────────────────────┐
   │                          JUnit PLATFORM                               │  ← FOUNDATION
   │         discovers tests, runs them, collects & reports results        │
   └───────────────────────────────────────────────────────────────────────┘
                                    ▲   TestEngine API
         ┌──────────────────────────┼─────────────────────────────┐
   ┌─────┴─────────────┐   ┌────────┴──────────┐   ┌──────────────┴───────────┐
   │  JUnit JUPITER    │   │   JUnit VINTAGE   │   │  Other engines           │  ← ENGINES
   │  engine           │   │   engine          │   │  (e.g. Cucumber, Spock)  │
   └─────┬─────────────┘   └────────┬──────────┘   └──────────────────────────┘
         ▼                          ▼
   Your JUnit 5 tests         Old JUnit 3 / 4 tests                                ← YOUR CODE
```

Think of it like a **game console**:

- The **Platform** is the console. It knows how to load and play a game, and how to show it on any TV.
- **Engines** are the game cartridges. Jupiter is one cartridge, Vintage is another.
- **Tools** (IntelliJ, Maven, Gradle) are the TVs. They plug into the console, not into each cartridge.

A new TV works with every game.

A new game works on every TV.

Nobody reaches into anybody else's insides anymore.

### 1. JUnit Platform — the foundation

The Platform is the base layer for **launching** testing frameworks on the JVM.

It defines two public contracts:

- **Launcher API** — what *tools* call to say "discover and run tests, and tell me the results". Maven's Surefire plugin, IntelliJ and Gradle all talk to this.
- **TestEngine API** — what a *testing framework* implements to say "here is how to find and run my kind of tests".

The Platform itself does not know what `@Test` means.

It only knows how to ask engines to do the work, and how to report back to tools.

### 2. JUnit Jupiter — what you actually write

Jupiter is the part you touch every day. It contains two things:

- **The programming model** — the annotations and assertions: `@Test`, `@BeforeEach`, `@ParameterizedTest`, `assertEquals()`, `assertThrows()`
- **The extension model** — `@ExtendWith`, which lets other libraries plug into your tests. This is how Mockito (`MockitoExtension`) and Spring (`SpringExtension`, used inside `@SpringBootTest`) hook in — and unlike JUnit 4, you can use many at once.

It also ships the **Jupiter TestEngine**, which is what finds and runs Jupiter-style tests on the Platform.

### 3. JUnit Vintage — backward compatibility

Vintage is a TestEngine that runs **old JUnit 3 and JUnit 4 tests** on the new Platform.

Why does it exist?

Real companies have thousands of old JUnit 4 tests. Rewriting them all at once is impossible.

With Vintage, old and new tests run side by side in the same `mvn test`, and teams migrate slowly.

> 💡 Tip: a new project does not need Vintage at all. Add it only when you must run old JUnit 3/4 tests.

### The jars behind the names

| Artifact | Layer | What it is for |
|----------|-------|----------------|
| `junit-jupiter-api` | Jupiter | annotations and assertions — needed to **write** tests |
| `junit-jupiter-engine` | Jupiter | the engine — needed to **run** Jupiter tests |
| `junit-jupiter-params` | Jupiter | `@ParameterizedTest` support |
| `junit-jupiter` | Jupiter | one convenient bundle of the three above |
| `junit-platform-launcher` | Platform | the Launcher API used by tools |
| `junit-vintage-engine` | Vintage | runs JUnit 3/4 tests |

### What really happens when you run mvn test

1. Maven reaches the `test` phase of its lifecycle, which is handled by the **Surefire** plugin.
2. Surefire calls the **JUnit Platform Launcher**: "discover and run all tests."
3. The Platform asks every **TestEngine** on the classpath to discover tests.
4. The **Jupiter engine** scans the compiled test classes in `target/test-classes` for methods marked `@Test`.
5. For each test, the engine creates a **new instance** of the test class, runs `@BeforeEach`, the test, then `@AfterEach`.
6. Each result — passed, failed, or error — flows back through the Platform to Surefire.
7. Surefire prints a summary and writes reports to `target/surefire-reports`.
8. If **any** test failed, the build fails — so `mvn package` will not produce a jar from broken code.

That last step is the whole point: a broken rule can no longer slip silently into the build.

---

## 10. Project Setup — Folders, Dependency, mvn test

### Folder structure

Production code and test code are kept **separately**:

```text
project/
├── pom.xml
└── src/
    ├── main/
    │   └── java/
    │       └── ScoreCalculator.java        ← production code (goes into the jar)
    └── test/
        └── java/
            └── ScoreCalculatorTest.java    ← test code (never goes into the jar)
```

Why keep them apart?

Because users of your application need the calculator, not the tests that check it.

Maven compiles `src/test/java` into `target/test-classes`, runs it, and leaves it out of the final jar.

The standard naming convention is:

```text
Production class: ScoreCalculator
Test class:       ScoreCalculatorTest
```

> ⚠️ Warning: this name is not just a style choice. By default, Maven Surefire only runs classes whose names match `Test*`, `*Test`, `*Tests` or `*TestCase`. Name your class `ScoreCalculatorCheck` and `mvn test` will silently run **zero** tests.

> 💡 Tip: when production classes live in a package (e.g. `src/main/java/com/nilanchal/ScoreCalculator.java`), put the test in the **same package** under test (`src/test/java/com/nilanchal/ScoreCalculatorTest.java`). Then the test can see package-private members, and it is easy to find.

### The dependency

In a **plain Maven** project, add the `junit-jupiter` bundle:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.11.4</version>
    <scope>test</scope>              <!-- only available to src/test/java, not shipped in the jar -->
</dependency>
```

In a **Spring Boot** project, the common testing dependency is:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>              <!-- version comes from the Spring Boot parent POM -->
</dependency>
```

It is a starter, so it includes JUnit Jupiter and commonly used testing tools such as **Mockito**, **AssertJ** and Spring's own test support — no versions to manage by hand.

> ⚠️ Warning: if `mvn test` prints `Tests run: 0` even though your tests exist, check that the Surefire plugin is a modern `3.x` version. Very old Surefire versions do not understand the JUnit Platform.

### Running tests

```bash
mvn test                                                  # run all tests
mvn test -Dtest=ScoreCalculatorTest                       # run one test class
mvn test -Dtest=ScoreCalculatorTest#shouldCalculateScore  # run one test method
```

In an IDE, the green ▶ button next to a test class or method does the same thing.

A passing run looks like this:

```text
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running ScoreCalculatorTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] BUILD SUCCESS
```

> 💡 Tip: `mvn package -DskipTests` builds without running tests. It exists for emergencies — using it by habit throws away the safety net you just built.

---

# Part 3 — Writing Tests

## 11. Your First JUnit Test

Production code:

```java
public class ScoreCalculator {

    public int calculate(int correctAnswers, int marksPerQuestion) {
        return correctAnswers * marksPerQuestion;
    }
}
```

Test code:

```java
import org.junit.jupiter.api.Test;                              // marks a method as a test

import static org.junit.jupiter.api.Assertions.assertEquals;    // static import → write assertEquals(), not Assertions.assertEquals()

class ScoreCalculatorTest {                                      // no "public" needed in JUnit 5

    @Test                                                        // "JUnit, run this method as a test"
    void shouldCalculateScore() {                                // no main(), no parameters, returns void
        ScoreCalculator calculator = new ScoreCalculator();      // create the object we want to test

        int actualScore = calculator.calculate(8, 5);            // call the real method

        assertEquals(40, actualScore);                           // pass if 40 == actualScore, fail otherwise
    }
}
```

The important line is:

```java
assertEquals(40, actualScore);
```

Its format is:

```java
assertEquals(expectedValue, actualValue);
```

If both values are equal, the test passes.

Otherwise, it fails.

### But there is no main() — so who calls this method?

The Jupiter engine does.

It uses **reflection** (Java's ability to inspect classes at runtime) to find every method with `@Test`, create an object of the class, and call the method.

That is also why the class and methods do not need to be `public`: reflection can reach them anyway.

> 💡 Tip: a new test should be seen **failing** at least once. Temporarily change `*` to `+` in `calculate()` and run the test. If it still passes, the test is not really checking anything.

---

## 12. Arrange-Act-Assert

Look at the first test again.

It naturally split into three blocks — and that is not an accident.

Almost every good test answers three questions, in order:

1. *What do I need to set up?*
2. *What action am I testing?*
3. *What should be true afterwards?*

These three parts are called **Arrange-Act-Assert** (AAA).

### Arrange

Prepare the object and input:

```java
ScoreCalculator calculator = new ScoreCalculator();
```

### Act

Execute the method being tested:

```java
int result = calculator.calculate(8, 5);
```

### Assert

Verify the result:

```java
assertEquals(40, result);
```

Complete structure:

```java
@Test
void shouldCalculateScore() {
    // Arrange
    ScoreCalculator calculator = new ScoreCalculator();

    // Act
    int result = calculator.calculate(8, 5);

    // Assert
    assertEquals(40, result);
}
```

Remember:

```text
Arrange -> Act -> Assert
```

Why follow this shape?

Because when a test fails six months later, anyone can read it in seconds: *this was the setup, this was the action, this was the expectation.*

> 💡 Tip: a test should have **one** Act. If you find yourself doing Act → Assert → Act → Assert, you are really writing two tests — split them.

---

## 13. Essential Assertions

An **assertion** is a statement that must be true, or the test fails.

Assertions compare the expected behaviour with the actual behaviour.

They all live in the class `org.junit.jupiter.api.Assertions` as static methods.

| Assertion | Purpose |
|---|---|
| `assertEquals(expected, actual)` | Checks that two values are equal |
| `assertNotEquals(unexpected, actual)` | Checks that values are different |
| `assertTrue(condition)` | Checks that a condition is true |
| `assertFalse(condition)` | Checks that a condition is false |
| `assertNull(value)` | Checks that a value is null |
| `assertNotNull(value)` | Checks that a value is not null |
| `assertThrows(type, code)` | Checks that an exception is thrown |
| `assertDoesNotThrow(code)` | Checks that no exception is thrown |

Examples:

```java
assertEquals(40, score);                  // exact value
assertTrue(score >= 0);                   // a rule, not an exact value
assertFalse(interview.isCompleted());     // a boolean state
assertNotNull(interview.getId());         // something was actually created
```

You can import them one by one, or all at once:

```java
import static org.junit.jupiter.api.Assertions.*;
```

### Failure messages

You can provide a message that is shown only when the assertion fails:

```java
assertEquals(
    40,
    score,
    "Score should equal correct answers multiplied by marks"
);
```

Why bother?

Because `expected: <40> but was: <30>` tells you *what* broke, but the message tells a teammate *which rule* broke.

> ⚠️ Warning: the argument order is `(expected, actual)`. Swap them and the test still passes or fails correctly, but the failure message lies to you: `expected: <30> but was: <40>`.

> ⚠️ Warning: `assertEquals` on objects uses the object's `equals()` method. If your class does not override `equals()`, two objects with identical data are still "not equal", because `Object.equals()` compares references.

> ⚠️ Warning: never compare `double` values exactly. `0.1 + 0.2` is `0.30000000000000004`, so `assertEquals(0.3, 0.1 + 0.2)` fails. Pass a tolerance as the third argument: `assertEquals(0.3, 0.1 + 0.2, 0.0001)`.

---

## 14. Reading a Failed Test

A test is only useful if you can understand it when it fails.

Say the unanswered-questions bug from Section 1 made `calculate` return 30.

In the IDE, you will see:

```text
org.opentest4j.AssertionFailedError: expected: <40> but was: <30>
```

And `mvn test` prints:

```text
[ERROR] Failures:
[ERROR]   ScoreCalculatorTest.shouldCalculateScore:14 expected: <40> but was: <30>
[ERROR] Tests run: 2, Failures: 1, Errors: 0, Skipped: 0
[INFO] BUILD FAILURE
```

Read it in this order:

1. **Which test?** → `ScoreCalculatorTest.shouldCalculateScore`
2. **Which line?** → `:14`
3. **Expected** → `40` — what the test says is correct
4. **Actual** → `30` — what the code really returned
5. **Then ask:** is the *code* wrong, or is the *test's expectation* wrong?

If you added a failure message, it appears in front: `Score should equal correct answers multiplied by marks ==> expected: <40> but was: <30>`.

### Failures vs Errors

| | Failure | Error |
|---|---|---|
| Meaning | an assertion was checked and was **false** | the test **crashed** with an unexpected exception before finishing |
| Example | `expected: <40> but was: <30>` | `NullPointerException` in the Act step |
| Usually means | the logic gives a wrong answer | the setup is broken or the code blew up |

---

## 15. Testing Exceptions

Good code does not only return correct answers.

It also **refuses bad input**.

A score calculator should never accept `-1` correct answers:

```java
public class ScoreCalculator {

    public int calculate(int correctAnswers, int marksPerQuestion) {
        if (correctAnswers < 0 || marksPerQuestion < 0) {
            throw new IllegalArgumentException(
                "Values cannot be negative"
            );
        }

        return correctAnswers * marksPerQuestion;
    }
}
```

That refusal is a *rule*, so it deserves a test too.

But how do you test that something **crashes**?

### The problem: calling it directly crashes the test

```java
@Test
void shouldRejectNegativeCorrectAnswers() {
    ScoreCalculator calculator = new ScoreCalculator();
    calculator.calculate(-1, 5);   // throws → the test itself dies with an Error
}
```

The exception escapes the test method, so JUnit reports an **Error** — the exact opposite of what we wanted.

The old workaround was `try / catch` plus a `fail()` call at the end of `try`.

It was ugly, and if you forgot the `fail()`, the test passed even when *no* exception was thrown.

### The fix: assertThrows

```java
@Test
void shouldRejectNegativeCorrectAnswers() {
    ScoreCalculator calculator = new ScoreCalculator();

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,          // the exception type we expect
            () -> calculator.calculate(-1, 5)        // the code that should throw it
        );

    assertEquals(
        "Values cannot be negative",
        exception.getMessage()                       // assertThrows returns the exception → check its message too
    );
}
```

The lambda contains the operation expected to throw an exception:

```java
() -> calculator.calculate(-1, 5)
```

### Why a lambda? Why not just pass calculator.calculate(-1, 5)?

Because Java evaluates method arguments **before** calling the method.

If you wrote `assertThrows(IllegalArgumentException.class, calculator.calculate(-1, 5))`, the exception would be thrown while Java is still preparing the arguments — before `assertThrows` even starts.

The lambda wraps the code **without running it**.

`assertThrows` receives the wrapped code, runs it inside its own `try / catch`, and checks what comes out:

- Expected exception thrown → pass, and it hands the exception back to you
- No exception thrown → fail: `Expected IllegalArgumentException to be thrown, but nothing was thrown`
- A different exception thrown → fail

The opposite check, for input that must be accepted:

```java
assertDoesNotThrow(() -> calculator.calculate(0, 5));
```

> ⚠️ Warning: `assertThrows` also passes for **subclasses** of the expected type. `assertThrows(RuntimeException.class, ...)` passes when an `IllegalArgumentException` is thrown. Expect the most specific type, or use `assertThrowsExactly(...)`.

---

## 16. @BeforeEach — A Fresh Start for Every Test

Suppose every test needs a `ScoreCalculator`.

Repeating `new ScoreCalculator()` as the first line of every method produces unnecessary code.

With 20 tests, that is 20 identical lines — and if the constructor later needs an argument, 20 places to change.

So how do we write the setup **once** and still give every test its own fresh object?

`@BeforeEach` runs before every test:

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreCalculatorTest {

    private ScoreCalculator calculator;            // shared field, filled before each test

    @BeforeEach
    void setUp() {
        calculator = new ScoreCalculator();        // runs before EVERY @Test method
    }

    @Test
    void shouldCalculateScore() {
        assertEquals(40, calculator.calculate(8, 5));
    }

    @Test
    void shouldReturnZeroForNoCorrectAnswers() {
        assertEquals(0, calculator.calculate(0, 5));
    }
}
```

A fresh object is created before each test, preventing one test from affecting another.

Why does "fresh" matter so much?

Imagine an `Interview` object that one test marks as `COMPLETED`.

If the next test reused that same object, it would start with a completed interview it never asked for — and fail for a reason that has nothing to do with its own code.

> 💡 Tip: JUnit 5 creates a **new instance of the test class for every test method**. So fields never leak between tests, even without `@BeforeEach`. `@BeforeEach` is still the clear place for setup that takes more than one line.

---

## 17. Lifecycle Annotations

`@BeforeEach` is one of four lifecycle hooks:

| Annotation | When it runs | Typical use |
|---|---|---|
| `@BeforeEach` | Before every test | create fresh objects |
| `@AfterEach` | After every test | clean up after a test |
| `@BeforeAll` | Once before all tests | expensive one-time setup |
| `@AfterAll` | Once after all tests | release that one-time setup |

Example:

```java
@BeforeEach
void setUp() {
    calculator = new ScoreCalculator();
}

@AfterEach
void cleanUp() {
    calculator = null;
}
```

For a class with two tests, the order of execution is:

```text
@BeforeAll                         (once)
│
├── @BeforeEach
│   └── @Test shouldCalculateScore
│   @AfterEach
│
├── @BeforeEach
│   └── @Test shouldReturnZeroForNoCorrectAnswers
│   @AfterEach
│
@AfterAll                          (once)
```

For normal unit tests, you will mostly use:

```java
@Test
@BeforeEach
```

> ⚠️ Warning: `@BeforeAll` and `@AfterAll` methods must be `static`. Why? They run once *before any test instance exists* — and since JUnit creates a new instance per test, there is no single object to call them on.

> ⚠️ Warning: JUnit does not promise that tests run in the order they are written in the file. Never rely on test order.

---

## 18. Parameterized Tests — One Test, Many Inputs

Scoring should work for many inputs: 0 correct, 1 correct, 4 correct, 8 correct…

Writing a separate `@Test` method for every combination would create four nearly identical methods where only the numbers change.

The root cause: *the test logic is the same, only the data differs.*

So separate the data from the logic.

A **parameterized test** runs the same test with multiple inputs:

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreCalculatorTest {

    @ParameterizedTest                // instead of @Test
    @CsvSource({                      // each string = one run of the test
        "0, 5, 0",
        "1, 5, 5",
        "4, 5, 20",
        "8, 5, 40"
    })
    void shouldCalculateDifferentScores(
            int correctAnswers,       // 1st column
            int marksPerQuestion,     // 2nd column
            int expectedScore         // 3rd column
    ) {
        ScoreCalculator calculator = new ScoreCalculator();

        int actualScore =
            calculator.calculate(correctAnswers, marksPerQuestion);

        assertEquals(expectedScore, actualScore);
    }
}
```

Each line represents:

```text
correctAnswers, marksPerQuestion, expectedScore
```

JUnit runs the method **four times** and reports each run separately, so if only `"4, 5, 20"` fails, you see exactly that row.

Adding a new case is now one line of data, not a whole new method.

> ⚠️ Warning: `@ParameterizedTest` lives in the `junit-jupiter-params` jar. It is included in the `junit-jupiter` bundle and in `spring-boot-starter-test`, but if you only added `junit-jupiter-api`, the import will not compile.

Learn parameterized tests after becoming comfortable with normal `@Test` methods.

---

## 19. Naming Tests

When a test fails, its **name** is the first thing you read — often in a build log, without the code in front of you.

So a test name should describe the **expected behaviour**.

Good names:

```java
shouldCalculateScore()
shouldReturnZeroWhenNoAnswersAreCorrect()
shouldRejectNegativeMarks()
shouldRejectInterviewWithoutQuestions()
shouldPreventUserFromAccessingAnotherUsersInterview()
```

Weak names:

```java
test1()
testCalculate()
testingMethod()
```

Why are they weak?

`testCalculate FAILED` tells you nothing — test *what* about calculate?

`shouldRejectNegativeMarks FAILED` tells you the exact broken rule before you open any file.

A useful pattern is:

```text
shouldExpectedBehaviourWhenCondition
```

Example:

```java
shouldReturnZeroWhenNoAnswersAreCorrect()
```

> 💡 Tip: `@DisplayName("Score is zero when no answers are correct")` on a test method shows a human-readable sentence in IDE reports, while the method name stays a valid Java identifier.

---

# Part 4 — Testing Real Projects

## 20. JUnit vs Mockito

So far, `ScoreCalculator` needed nothing else to work.

Real classes are rarely that lonely.

Suppose `InterviewService` calls an AI provider to evaluate an answer.

A unit test should **not** make a real AI request, because it could be:

- **Slow** — seconds per call, instead of milliseconds
- **Expensive** — every test run costs money
- **Unavailable** — the provider is down, the test fails, your code was fine
- **Different on every execution** — AI gives a different answer each time, so the test is not predictable

That breaks every property of a good unit test from Section 5.

So how do we test `InterviewService` without the real AI?

We replace the AI provider with a **fake** that returns a controlled response we choose.

JUnit and Mockito solve different problems:

| Tool | Responsibility |
|---|---|
| JUnit | Runs tests and checks results |
| Mockito | Creates fake dependencies |

Think of a flight simulator: pilots practise engine failures without an actual engine failing.

Mockito is the simulator for your dependencies.

It plugs into JUnit through the extension model — `@ExtendWith(MockitoExtension.class)` — which is exactly what the JUnit 5 architecture was designed to allow.

You only need Mockito after you begin testing Spring service classes.

---

## 21. What to Test in the Interview Project

Test the **rules** of your application — the things that would hurt if they silently broke.

### Scoring

```text
Correct answer       -> Correct marks
Wrong answer         -> Zero marks
Unanswered question  -> Handled safely
Total score          -> Never exceeds maximum
```

### Interview lifecycle

```text
DRAFT -> IN_PROGRESS -> COMPLETED
```

Test that valid transitions work, and that **invalid transitions are rejected** — for example `COMPLETED -> DRAFT`, or `DRAFT -> COMPLETED` skipping `IN_PROGRESS`.

### Authentication and authorization

Test that:

- Unauthenticated users cannot access protected endpoints
- Users can access their own interviews
- Users cannot access another user's interviews

### AI integration

Do **not** test whether the AI writes an exact sentence.

You do not control the AI's wording, so such a test would be unpredictable.

Test **your application's rules** around the AI instead:

- Invalid AI JSON is rejected
- Missing fields are detected
- Score remains within the allowed range
- Timeout is handled
- Provider failure does not crash the application
- Fallback behaviour works

---

## 22. What Not to Do

### Don't write tests without assertions

This test proves nothing:

```java
@Test
void calculateScore() {
    calculator.calculate(8, 5);    // no assertion → passes even if the result is wrong
}
```

It only proves the method did not crash.

A test that cannot fail is not a test.

### Don't call real AI APIs in unit tests

Unit tests must remain fast and predictable.

Use a fake (Mockito) instead.

### Don't make tests depend on each other

This is dangerous:

```text
Test 2 requires Test 1 to run first
```

JUnit does not guarantee order, and running Test 2 alone would fail.

Each test should create its own required state.

### Don't test private methods directly

Test observable behaviour through public methods.

Private methods are implementation details.

If you refactor a private method but the public behaviour stays the same, no test should break.

### Don't test only successful cases

Bugs love the edges.

Include:

- Empty input
- Null input
- Invalid values
- Boundary values (`0`, the maximum score, one above the maximum)
- Unauthorized access
- Exceptions

### Don't trust AI-generated tests blindly

An AI tool can write a test that *looks* great and checks nothing.

Check that:

1. The expected result is correct.
2. The test covers real requirements.
3. The test fails when the implementation is intentionally broken.
4. Important edge cases are included.

A test that never fails is not a useful safety check.

---

# Part 5 — Reference

## 23. Cheat Sheet

**Annotations:**

| Annotation | Package | What it does |
|------------|---------|--------------|
| `@Test` | `org.junit.jupiter.api` | marks a test method |
| `@BeforeEach` / `@AfterEach` | `org.junit.jupiter.api` | run before / after every test |
| `@BeforeAll` / `@AfterAll` | `org.junit.jupiter.api` | run once before / after all tests (`static`) |
| `@DisplayName` | `org.junit.jupiter.api` | readable test name in reports |
| `@Disabled` | `org.junit.jupiter.api` | skip a test |
| `@ParameterizedTest` | `org.junit.jupiter.params` | run one test with many inputs |
| `@CsvSource` | `org.junit.jupiter.params.provider` | supplies rows of input data |
| `@ExtendWith` | `org.junit.jupiter.api.extension` | plug in extensions like Mockito or Spring |

**Commands:**

| Command | What it does |
|---------|--------------|
| `mvn test` | compile and run all tests |
| `mvn test -Dtest=ScoreCalculatorTest` | run one test class |
| `mvn test -Dtest=ScoreCalculatorTest#shouldCalculateScore` | run one test method |
| `mvn package -DskipTests` | build without running tests (emergencies only) |

**Test template:**

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassNameTest {

    private ClassName objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new ClassName();
    }

    @Test
    void shouldExpectedBehaviourWhenCondition() {
        // Arrange

        // Act

        // Assert
    }
}
```

---

## 24. Minimum Learning Checklist

You know enough JUnit to begin Spring Boot when you can:

- [ ] Explain why automated tests exist (the silent-break problem)
- [ ] Explain the difference between testing and unit testing
- [ ] Place unit testing in the SDLC and explain why earlier is cheaper
- [ ] Name the three parts of JUnit 5 and what each one does
- [ ] Add the testing dependency
- [ ] Create files inside `src/test/java`
- [ ] Run tests with `mvn test`
- [ ] Write a method using `@Test`
- [ ] Follow Arrange-Act-Assert
- [ ] Use `assertEquals()`
- [ ] Use `assertTrue()` and `assertFalse()`
- [ ] Use `assertNotNull()`
- [ ] Test exceptions with `assertThrows()`
- [ ] Use `@BeforeEach`
- [ ] Understand what a unit test is
- [ ] Understand why real APIs and databases are avoided in unit tests
- [ ] Read a failed-test message and identify expected versus actual values

Once you can do these things, continue to HTTP, REST and Spring Boot.

Learn Mockito, controller tests, repository tests and Spring integration tests while implementing the corresponding project layers.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 03. JUnit*
