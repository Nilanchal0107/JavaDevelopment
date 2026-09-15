# 📘 Maven Projects — Notes

> Based on the Telusko YouTube channel tutorial by Navin Reddy

**Three projects live in this folder, and they tell one story in three chapters:**

| Project | What it teaches |
|---------|-----------------|
| `nilanchal-app` | The bare minimum — a plain Java jar built by Maven |
| `nilanchal-web-app` | Adding real libraries by hand, and packaging a war |
| `SpringBootMavenApp` | Letting a **parent POM** make the hard decisions for you |

Read them in that order. Each one solves a problem the previous one leaves behind.

---

## 📑 Table of Contents

**Part 1 — Why Maven Exists**
1. [The Problem: Life Before Maven](#1-the-problem-life-before-maven)
2. [Finding the Root Cause](#2-finding-the-root-cause)
3. [Enter Maven](#3-enter-maven)

**Part 2 — Reading a pom.xml**
4. [pom.xml — The Heart of the Project](#4-pomxml--the-heart-of-the-project)
5. [Coordinates: groupId, artifactId, version](#5-coordinates-groupid-artifactid-version)
6. [SNAPSHOT — The "Still Cooking" Label](#6-snapshot--the-still-cooking-label)
7. [Dependencies — Borrowing Other People's Code](#7-dependencies--borrowing-other-peoples-code)
8. [Transitive Dependencies — The Free Gifts](#8-transitive-dependencies--the-free-gifts)
9. [Scope — Where a Dependency Is Allowed to Exist](#9-scope--where-a-dependency-is-allowed-to-exist)

**Part 3 — Structure, Build, Output**
10. [The Standard Folder Structure (Convention over Configuration)](#10-the-standard-folder-structure-convention-over-configuration)
11. [Packaging — jar vs war](#11-packaging--jar-vs-war)
12. [The target Folder — Maven's Kitchen](#12-the-target-folder--mavens-kitchen)
13. [The Build Lifecycle](#13-the-build-lifecycle)
14. [Plugins — Maven Does Nothing By Itself](#14-plugins--maven-does-nothing-by-itself)
15. [finalName — Renaming the Final Dish](#15-finalname--renaming-the-final-dish)
16. [Archetypes — Project Templates](#16-archetypes--project-templates)

**Part 4 — The Spring Boot Chapter**
17. [The Next Problem: Managing Versions By Hand](#17-the-next-problem-managing-versions-by-hand)
18. [Parent POM — Inheriting a Whole Setup](#18-parent-pom--inheriting-a-whole-setup)
19. [Starters — Buying the Whole Meal Kit](#19-starters--buying-the-whole-meal-kit)
20. [Properties — One Place for Values You Repeat](#20-properties--one-place-for-values-you-repeat)
21. [The Spring Boot Plugin and the Fat Jar](#21-the-spring-boot-plugin-and-the-fat-jar)
22. [The Maven Wrapper — Which Maven Are You Using?](#22-the-maven-wrapper--which-maven-are-you-using)
23. [Making JSP Work in Spring Boot](#23-making-jsp-work-in-spring-boot)
24. [What the Spring Boot Code Actually Does](#24-what-the-spring-boot-code-actually-does)
25. [.gitignore — Keeping target Out of Git](#25-gitignore--keeping-target-out-of-git)

**Part 5 — Reference**
26. [Comparison: All Three Projects](#26-comparison-all-three-projects)
27. [Command Cheat Sheet](#27-command-cheat-sheet)

---

# Part 1 — Why Maven Exists

## 1. The Problem: Life Before Maven

Let's not start with "Maven is a build tool".

Let's start with the mess it was born to clean up.

Imagine you write a tiny Java program — exactly the placeholder the `nilanchal-app` project started life with:

```java
package com.nilanchal;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
```

Easy. You run `javac App.java`, then `java App`, and you're done.

Now the real world walks in and starts asking for things.

**Problem 1 — You need somebody else's code.**

You want to write a test, so you need JUnit.

JUnit is not part of Java. It lives on the internet as a `.jar` file.

So you open a browser, search "junit jar download", find some page, download `junit-3.8.1.jar`, and drop it in a `lib` folder.

Now you must compile like this:

```
javac -cp lib/junit-3.8.1.jar -d classes src/com/nilanchal/*.java
```

Already ugly. And you have exactly **one** library.

**Problem 2 — Libraries need other libraries.**

Later you want Spring. You download `spring-core.jar`.

You compile, and Java screams: `NoClassDefFoundError: org/apache/commons/logging/Log`.

Spring needs `commons-logging`. So you download that too.

Then that one needs another one. And that one needs two more.

Look at what actually happened in this folder's web app.

We asked for **2** libraries (`spring-core`, `spring-security-web`).

Maven put **12** jars into `target/nilanchal-web-app/WEB-INF/lib/`:

```
commons-logging-1.3.5.jar            micrometer-commons-1.18.0-M1.jar
jspecify-1.0.0.jar                   micrometer-observation-1.18.0-M1.jar
spring-aop-7.1.0-M1.jar              spring-beans-7.1.0-M1.jar
spring-context-7.1.0-M1.jar          spring-core-7.1.0-M1.jar
spring-expression-7.1.0-M1.jar       spring-security-core-7.2.0-M1.jar
spring-security-crypto-7.2.0-M1.jar  spring-security-web-7.2.0-M1.jar
```

Ten jars you never asked for, that you would have had to hunt down **by hand**, one error message at a time.

This is called **jar hell**, and people genuinely lost days of their life to it.

**Problem 3 — "It works on my machine."**

You zip your project and send it to a friend.

Your friend gets errors, because their `lib` folder has `junit-4.0` and yours has `junit-3.8.1`.

There is no written record anywhere of what versions your project actually needs.

The knowledge lives only in your head and in a folder full of mystery jars.

**Problem 4 — Everybody organises code differently.**

Your project puts source in `src/`.

Your friend's project puts it in `source/`.

Another one puts tests right next to the main code.

So every project needs its own custom build script, and a new person joining needs a guided tour before they can compile anything.

**Problem 5 — The build is a ritual, not a command.**

To ship your project you must: compile the main code, compile the test code, run the tests, copy resources, and zip everything into a jar with the right structure.

Miss a step, or do them in the wrong order, and the output is broken.

So teams wrote long, fragile shell scripts that only one person understood.

---

## 2. Finding the Root Cause

Five problems. But are they five *different* problems?

Let's ask the natural question: **what do all of these have in common?**

Downloading jars by hand.

Chasing dependencies-of-dependencies by hand.

Remembering versions in your head.

Inventing your own folder layout.

Writing your own build steps.

In every single case, **a human is manually doing something that is completely mechanical and identical in every Java project on earth**.

> So the root cause is: *the knowledge of how to build a project lives in a human's head and hands, instead of being written down in a file that a machine can read and execute.*

---

## 3. Enter Maven

So how do we solve this?

If the problem is "the build knowledge is in a human's head", the fix is obvious:

**write the build knowledge down in one file, and let a tool do the work.**

That file is `pom.xml`.

That tool is **Maven**.

Here's the mental model:

> Maven is a chef.
> `pom.xml` is the recipe card.
> You never touch the pans — you hand over the recipe and say "cook".

And Maven's genius move is this: the recipe card only has to say *what's different* about your project.

Everything that is the same in every Java project — where source code lives, what order to compile in, how to build a jar — Maven already knows.

That idea has a name, and it's the most important idea in Maven:

> 💡 **Convention over Configuration** — follow the standard layout, and you write almost no configuration at all.

---

# Part 2 — Reading a pom.xml

## 4. pom.xml — The Heart of the Project

**POM** stands for **P**roject **O**bject **M**odel.

It is the one file that describes your entire project to Maven.

Here is the complete `pom.xml` of `nilanchal-app`, annotated:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

  <modelVersion>4.0.0</modelVersion>     <!-- version of the POM format itself, always 4.0.0 -->

  <groupId>com.nilanchal</groupId>       <!-- who made it -->
  <artifactId>nilanchal-app</artifactId> <!-- what it is called -->
  <packaging>jar</packaging>             <!-- what shape the output takes -->
  <version>1.0-SNAPSHOT</version>        <!-- which edition of it -->

  <name>nilanchal-app</name>             <!-- human-friendly display name -->
  <url>http://maven.apache.org</url>     <!-- project website (default from the template) -->

  <dependencies>                         <!-- code we borrow -->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>                                <!-- how to build it -->
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.11.0</version>
        <configuration>
          <source>26</source>            <!-- Java version our source code is written in -->
          <target>26</target>            <!-- Java version the .class files must run on -->
        </configuration>
      </plugin>
    </plugins>
  </build>

</project>
```

Read it again and notice something.

There is **no** instruction saying "compile the code", "run the tests", or "make a jar".

Maven already knows all of that.

The file only says what is *unique* about this project — that's convention over configuration doing its job.

> 💡 Tip: `modelVersion` is `4.0.0` in every POM you will ever see. It describes the version of the POM *format*, not your project.

---

## 5. Coordinates: groupId, artifactId, version

Here's a fair question: Maven downloads libraries from the internet automatically.

But the internet has millions of jars. **How does it know exactly which one you mean?**

Think about how you'd find one specific house on Earth.

You'd need country → city → street → house number.

Maven does the same thing with three values, called **coordinates**:

| Part | Meaning | Analogy | Our value |
|------|---------|---------|-----------|
| `groupId` | The organisation that made it | Your surname / street | `com.nilanchal` |
| `artifactId` | The project's own name | Your first name / house number | `nilanchal-app` |
| `version` | Which release of it | Your age right now | `1.0-SNAPSHOT` |

Together they are written `groupId:artifactId:version`:

```
com.nilanchal:nilanchal-app:1.0-SNAPSHOT
```

That string is globally unique. No two projects on Earth should share it.

**Why is `groupId` written backwards, like `com.nilanchal`?**

Because domain names are already globally unique, and somebody already paid for them.

If you own `nilanchal.com`, nobody else can claim `com.nilanchal`. Free uniqueness — reuse it rather than inventing a new registry.

**Where are coordinates used?** Two places, and this is the beautiful part:

1. To **identify** your project (the top of your POM).
2. To **request** somebody else's project (inside `<dependency>`).

They are the same three fields. Your jar is just a dependency waiting to happen.

And this isn't only theory — Maven physically stamps the coordinates *inside* the artifact it builds.

Crack open `nilanchal-app/target/nilanchal-app-1.0-SNAPSHOT.jar` and you'll find:

```
com/nilanchal/App.class
META-INF/maven/com.nilanchal/nilanchal-app/pom.xml         ← your whole POM, carried along
META-INF/maven/com.nilanchal/nilanchal-app/pom.properties
```

Look at that folder path: `com.nilanchal` / `nilanchal-app`. The coordinates again.

`pom.properties` spells them out in plain text:

```properties
artifactId=nilanchal-app
groupId=com.nilanchal
version=1.0-SNAPSHOT
```

**Why bundle the POM inside the jar?**

Because of Section 8. When *your* jar becomes somebody else's dependency, Maven has to read your POM to discover *your* dependencies.

So every jar carries its own recipe. That's what makes the transitive chain possible.

---

## 6. SNAPSHOT — The "Still Cooking" Label

Our version is `1.0-SNAPSHOT`. Why the weird suffix?

Let's reason it out.

Once you publish version `1.0` and ten people download it, that version must **never change again**.

If you secretly edited `1.0` after they downloaded it, their build would still use the old cached copy, and you'd get "works on my machine" all over again.

So released versions are **frozen forever**. Maven downloads them once and caches them permanently.

But now there's a problem: **while you are still actively developing, your code changes every hour.**

If you had to bump the number on every change, you'd burn through `1.0`, `1.1`, `1.2`… before lunch.

> So the root cause is: Maven caches aggressively, but code that is still under development must not be cached.

How do we solve this?

Add a label that means *"this one is still changing — go check for a fresh copy"*.

That label is `-SNAPSHOT`.

| | Release (`1.0`) | Snapshot (`1.0-SNAPSHOT`) |
|---|---|---|
| Meaning | Finished, frozen | Work in progress |
| Can the contents change? | Never | Yes, any time |
| Maven re-checks for updates | No, cached forever | Yes, regularly |
| Use it for | Shipping to others | Your own daily development |

All three projects here are snapshots, because all three are learning projects still being changed:

```
nilanchal-app       1.0-SNAPSHOT
nilanchal-web-app   1.0-SNAPSHOT
SpringBootMavenApp  0.0.1-SNAPSHOT   ← Spring Initializr's default starting point
```

> 💡 Tip: `1.0-SNAPSHOT` literally means "on the way to 1.0, not there yet." And `0.0.1-SNAPSHOT` means "we have barely started" — which is exactly the honest label for a brand-new project.

> ⚠️ Warning: you'll also see versions like `7.1.0-M1` and `4.0.8` in these POMs. `-M1` means **Milestone 1** — a preview release, not yet final. It's a *real*, frozen version (unlike a snapshot), but the library's authors are still changing their mind about the API. Fine for learning, risky for production.

---

## 7. Dependencies — Borrowing Other People's Code

This is the block that ends the download-by-hand nightmare:

```xml
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>3.8.1</version>
  <scope>test</scope>
</dependency>
```

That's it. Four lines, and JUnit is available to your code.

**So where does the jar actually come from, and where does it go?**

Follow the chain when you run a Maven command:

1. Maven reads the coordinates `junit:junit:3.8.1`.
2. It looks in your **local repository** — a folder on your own machine at `~/.m2/repository` (on Windows: `C:\Users\<you>\.m2\repository`).
3. Not there? It asks the **central repository** — [mvnrepository.com](https://mvnrepository.com) / Maven Central, the giant public warehouse of Java libraries.
4. It downloads the jar and **saves it into `.m2`**.
5. Next time — and for every other project on your machine — it's already local. No download.

That step 4 is why your first Maven build is slow and every build after it is fast.

Think of `.m2` as your personal pantry.

The first time a recipe needs saffron, you go to the market.

After that, it's already in the cupboard.

> 💡 Tip: the comments in `nilanchal-web-app/pom.xml` and `SpringBootMavenApp/pom.xml` both point at `mvnrepository.com`. That's the site you visit to find a library and copy its ready-made `<dependency>` block. You never type coordinates from memory.

Notice what we did **not** have to do: no browser download, no `lib` folder, no `-cp` flag, no jar committed to git.

The `pom.xml` *is* the record of what this project needs. Send someone the POM and they can rebuild your exact setup.

---

## 8. Transitive Dependencies — The Free Gifts

Remember Problem 2, where Spring needed `commons-logging`, which needed something else?

Maven solves that too, and it's worth seeing the proof.

In `nilanchal-web-app/pom.xml` we asked for exactly **two** real libraries:

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>
    <version>7.1.0-M1</version>
    <scope>compile</scope>
</dependency>

<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-web</artifactId>
    <version>7.2.0-M1</version>
    <scope>compile</scope>
</dependency>
```

But the built app in `target/nilanchal-web-app/WEB-INF/lib/` contains **twelve** jars.

Where did the other ten come from?

**They came from the dependencies' own POMs** — the ones bundled inside each jar, exactly like the one we found in our own jar in Section 5.

Here's the insight: every library on Maven Central carries a `pom.xml`, and that POM lists *its* dependencies.

So Maven walks the chain, recursively:

```
you ask for → spring-security-web
              └── its POM needs → spring-web, spring-core, spring-context ...
                                  └── spring-context's POM needs → spring-aop, spring-beans, spring-expression ...
                                                                   └── ... and on, until nothing new is left
```

Dependencies pulled in automatically like this are called **transitive dependencies**.

The analogy: you invite two friends to dinner, and each one brings their own plus-ones, who bring theirs.

Except here it's exactly what you want, because your code genuinely won't run without them.

> 💡 Tip: this is the single biggest reason Maven exists. Manually resolving a dependency tree that's 12 jars deep, with matching versions, is a full day of work. Maven does it in seconds.

> ⚠️ Warning: `micrometer-observation` and `jspecify` are in your build even though you never heard of them. That's normal. But it also means a bug or a vulnerability can enter your app through a library you never chose — which is why `mvn dependency:tree` (see the cheat sheet) is worth knowing early.

---

## 9. Scope — Where a Dependency Is Allowed to Exist

Look closely at our different kinds of dependency:

```xml
<scope>test</scope>      <!-- junit -->
<scope>compile</scope>   <!-- spring-core, tomcat-jasper -->
```

Why would a dependency need a "where am I allowed" setting at all?

Think about JUnit.

`AppTest.java` needs it to compile and run:

```java
import junit.framework.TestCase;

public class AppTest extends TestCase { ... }
```

But the actual program you ship never touches JUnit.

So if JUnit got packed into your final jar, you'd be shipping your test toolkit to your users.

Bigger download, more code on the classpath, more things that can break, all for zero benefit.

> So the root cause is: not every dependency is needed at every stage of the build, and shipping the unneeded ones is pure waste.

The fix is to label each dependency with **where it applies**. That label is `scope`.

| Scope | Available when compiling main code? | Available when testing? | Packed into the final jar/war? | Typical use |
|-------|:---:|:---:|:---:|-------------|
| `compile` (default) | ✅ | ✅ | ✅ | Spring — your app truly needs it at runtime |
| `test` | ❌ | ✅ | ❌ | JUnit — only the tests need it |
| `provided` | ✅ | ✅ | ❌ | Servlet API — the server already has it |
| `runtime` | ❌ | ✅ | ✅ | JDBC drivers — needed to run, not to compile |

That table explains something you can verify right now.

`WEB-INF/lib/` in the built war contains the 12 Spring-related jars — and **no JUnit jar**, even though JUnit is declared in the very same POM.

Scope working exactly as designed.

The Spring Boot project shows the same idea with modern names:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>                                       <!-- no scope → compile → ships -->

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc-test</artifactId>
    <scope>test</scope>                             <!-- test tools stay behind -->
</dependency>
```

> 💡 Tip: `compile` is the default. If you write no `<scope>`, you get `compile`. Writing it explicitly, like the web app does, is just being clear.

> ⚠️ Warning: `provided` is the one beginners get wrong. If the server supplies a library and you also pack your own copy, two versions of the same classes get loaded at once — a genuinely confusing class of bug.

---

# Part 3 — Structure, Build, Output

## 10. The Standard Folder Structure (Convention over Configuration)

Maven never asked us where our code is. It just found it.

**How?**

Because Maven insists on one layout for every project in the world:

```
nilanchal-app/
├── pom.xml                                  ← the recipe
├── src/
│   ├── main/
│   │   └── java/com/nilanchal/              ← code that ships
│   └── test/
│       └── java/com/nilanchal/AppTest.java  ← code that only tests
└── target/                                  ← everything Maven generates
```

Add a `webapp` branch and you get a web project:

```
nilanchal-web-app/
├── pom.xml
├── src/main/webapp/
│   ├── index.jsp                            ← the page users see
│   └── WEB-INF/web.xml                      ← web app configuration
└── target/
```

And the Spring Boot project uses **three** branches at once — the full standard layout:

```
SpringBootMavenApp/
├── pom.xml
├── src/main/java/...                        ← Java source
├── src/main/resources/                      ← config & non-Java files
│   └── application.properties
├── src/main/webapp/                         ← web pages
│   └── greet.jsp
├── src/test/java/...                        ← tests
└── target/
```

**What is `src/main/resources` for, and why isn't it just `src/main/java`?**

Because `application.properties` is not code. It must not be compiled — it must be **copied as-is** into the output.

So Maven needs two different treatments, which means two different folders:

| Folder | Maven's treatment | Ends up as |
|--------|-------------------|------------|
| `src/main/java` | compile it | `.class` files in `target/classes` |
| `src/main/resources` | copy it verbatim | files sitting beside the classes in `target/classes` |

That "copy it beside the classes" part is the key.

It's why your running program can read `application.properties` from the classpath without knowing any file path.

The split between `src/main` and `src/test` matters just as much — it is the whole reason `scope` can work.

Maven compiles them separately, with different classpaths:

- `src/main/java` is compiled **without** the test libraries on the classpath, and goes into the jar.
- `src/test/java` is compiled **with** them, and never goes into the jar.

You can literally see both compilations in the build records:

```
target/maven-status/maven-compiler-plugin/compile/default-compile/inputFiles.lst
target/maven-status/maven-compiler-plugin/testCompile/default-testCompile/inputFiles.lst
```

Two separate compile steps, two separate lists.

**And why does the package `com.nilanchal` match the folders `com/nilanchal`?**

That's a plain Java rule, not a Maven one — a package name *is* a folder path.

Maven just starts counting from `src/main/java`.

> 💡 Tip: this is why a Maven project opens correctly in IntelliJ, Eclipse, VS Code, and on a build server with zero setup. Everyone agreed on the same map, so nobody needs directions.

---

## 11. Packaging — jar vs war

Two of our projects differ in one crucial line.

`nilanchal-app`:

```xml
<packaging>jar</packaging>
```

`nilanchal-web-app`:

```xml
<packaging>war</packaging>
```

**Why does the shape of the output need a name at all?**

Because the two programs are run in fundamentally different ways.

A class with a `main` method, you run yourself:

```
java -jar nilanchal-app-1.0-SNAPSHOT.jar
```

`index.jsp` has no `main` method. **Nobody runs it directly.**

A web server (Tomcat, for example) runs, and you hand it your app.

The server waits for a browser request and then calls into your code.

Two different run-styles need two different package shapes:

| | **JAR** | **WAR** |
|---|---|---|
| Full name | Java **AR**chive | **W**eb **A**pplication **AR**chive |
| Our example | `nilanchal-app` | `nilanchal-web-app` |
| Entry point | a `main` method | an HTTP request from a browser |
| How it runs | `java -jar ...` | deployed into Tomcat / a servlet container |
| Contains | `.class` files + resources | `.class` files **+ JSP/HTML + `WEB-INF/`** |
| Bundles its dependencies? | No (by default) | Yes, in `WEB-INF/lib/` |
| Output file | `nilanchal-app-1.0-SNAPSHOT.jar` | `nilanchal-web-app.war` |

That second-to-last row explains the 12 jars again.

A war must be **self-contained**, because you hand the whole file to a server that knows nothing about your project.

So its dependencies travel inside it, in `WEB-INF/lib/`.

**What is `WEB-INF`, and why that exact name?**

It's a protected folder — the server refuses to serve anything inside it to a browser.

Your compiled classes, your libraries, and your `web.xml` config live there precisely so users can never download them.

`index.jsp` sits *outside* `WEB-INF`, because that one is meant to be visited.

Our `web.xml` is almost empty — just the template's display name:

```xml
<web-app>
  <display-name>Archetype Created Web Application</display-name>
</web-app>
```

> 💡 Tip: `<packaging>` is one word that changes the entire build. Change `jar` to `war` and Maven suddenly starts looking for `src/main/webapp`, bundling dependencies, and producing a `.war`. One line, a completely different recipe.

**Now here's a puzzle to keep in your pocket.**

`SpringBootMavenApp` has a `main` method **and** a `greet.jsp` page.

It has no `<packaging>` line at all — so it defaults to `jar`.

But a plain jar doesn't bundle its dependencies, and a web app can't run without Tomcat…

So how does a *jar* serve web pages? That's [Section 21](#21-the-spring-boot-plugin-and-the-fat-jar).

---

## 12. The target Folder — Maven's Kitchen

Everything Maven generates lands in `target/`. Nothing you wrote is in there.

Let's read `nilanchal-app/target/` like a story of what happened during its last build:

```
target/
├── test-classes/com/nilanchal/AppTest.class ← your test code, compiled (kept separate!)
├── maven-status/...                         ← Maven's record of which files it compiled
├── surefire-reports/                        ← the test results
│   ├── com.nilanchal.AppTest.txt
│   └── TEST-com.nilanchal.AppTest.xml
├── maven-archiver/pom.properties            ← coordinates baked into the artifact
└── nilanchal-app-1.0-SNAPSHOT.jar           ← the final product
```

Notice the jar's filename: `nilanchal-app-1.0-SNAPSHOT.jar`.

That's `artifactId` + `version`. The coordinates show up even in the file name.

And the war side shows the packaging step caught mid-flight:

```
target/
├── nilanchal-web-app/        ← the war's contents, laid out as a folder first
│   ├── index.jsp
│   └── WEB-INF/lib/*.jar
└── nilanchal-web-app.war     ← then zipped into the actual war
```

Maven assembles the exploded folder first, then zips it. A war is just a zip with an agreed-upon structure.

### A stale build, caught in the act

Here is something genuinely instructive sitting in this folder right now.

`nilanchal-app/src/main/java/com/nilanchal/` is **empty** — the placeholder `App.java` was deleted.

`nilanchal-app/target/classes/com/nilanchal/` is **empty** too, so far so consistent.

But look inside the jar that's still sitting in `target/`:

```
com/nilanchal/App.class      ← a compiled class whose source no longer exists
```

**How is that possible?**

Because that jar was built *before* the source was deleted, and nothing has cleaned up after it.

`target/` is not a live mirror of your source. It's a pile of leftovers from the last time you ran a build.

> So the root cause is: build output goes stale silently, because deleting a source file does not delete anything Maven already produced from it.

And that is exactly why `mvn clean` exists, and why you'll almost always type `mvn clean package` instead of `mvn package`.

`clean` deletes `target/` wholesale, so the next build has to prove everything from source.

> ⚠️ Warning: **never commit `target/` to git.** Every file in it is regenerated from your source on the next build. Committing it means storing megabytes of duplicates that go stale immediately — and, as we just saw, stale output can contain classes that no longer exist anywhere in your code.

This gives us a clean rule for the whole project:

> `src/` is written by humans. `target/` is written by Maven. Delete `target/` any time — `mvn clean` does exactly that — and nothing of value is lost.

---

## 13. The Build Lifecycle

We've seen the ingredients. Now: **what order does Maven cook in?**

Maven has a fixed sequence of **phases**, and here's the rule that makes it click:

> Asking for a phase runs **that phase and every phase before it**.

```
validate  →  compile  →  test  →  package  →  verify  →  install  →  deploy
```

| Phase | What it does | Evidence it leaves in `target/` |
|-------|--------------|---------------------------------|
| `validate` | Check the POM makes sense | — |
| `compile` | Compile `src/main/java`, copy `src/main/resources` | `target/classes/` |
| `test` | Compile and run `src/test/java` | `target/test-classes/` + `target/surefire-reports/` |
| `package` | Bundle into a jar or war | `target/*.jar` or `target/*.war` |
| `verify` | Run checks on the package | — |
| `install` | Copy the artifact into your local `.m2` | so your *other* projects can depend on it |
| `deploy` | Upload to a shared remote repository | so your *team* can depend on it |

So when you type `mvn package`, you are silently also running validate, compile, and test.

**And that ordering is a safety feature, not a detail.**

You physically cannot package a jar whose tests are failing, because `test` runs before `package` and a failed test stops the build.

That's the ritual from Problem 5 — turned into a guarantee.

You can read each project's build history straight off its `target/` folder:

| Project | What's in `target/` | So the last command run was… |
|---------|---------------------|------------------------------|
| `nilanchal-app` | test-classes, surefire-reports, **jar** | at least `package` |
| `nilanchal-web-app` | exploded webapp, **war** | at least `package` |
| `SpringBootMavenApp` | only `classes/` and `test-classes/` | just `compile` / `test-compile` — **never packaged yet** |

`nilanchal-app`'s test run left its receipt in `target/surefire-reports/com.nilanchal.AppTest.txt`, produced by this:

```java
public void testApp()
{
    assertTrue( true );   // the archetype's placeholder test — always passes
}
```

> 💡 Tip: `mvn clean install` is the command you'll type most. `clean` wipes `target/` so nothing stale survives, and `install` runs the whole chain up to putting your artifact in `.m2`.

> ⚠️ Warning: `install` puts the artifact on **your machine only**. Your teammate's build still can't see it. Sharing with a team needs `deploy` and a remote repository.

---

## 14. Plugins — Maven Does Nothing By Itself

Here's a surprise worth sitting with.

Maven cannot compile Java.

Maven cannot run tests.

Maven cannot build a jar.

**So what does Maven actually do?**

It manages the lifecycle and calls **plugins** to do the real work.

Maven is the manager; plugins are the workers holding the tools.

| Phase | Plugin that actually does it |
|-------|------------------------------|
| `compile` | `maven-compiler-plugin` |
| `test` | `maven-surefire-plugin` (hence `surefire-reports/`) |
| `package` (jar) | `maven-jar-plugin` |
| `package` (war) | `maven-war-plugin` |
| `clean` | `maven-clean-plugin` |
| (extra, Boot only) | `spring-boot-maven-plugin` |

Those folder names inside `target/` were never random — each one is a plugin signing its work.

Even `target/maven-archiver/` is the archiver machinery that stamps coordinates into your jar.

And now the `<build>` block in `nilanchal-app/pom.xml` finally makes sense:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.11.0</version>
  <configuration>
    <source>26</source>   <!-- "my source code uses Java 26 syntax" -->
    <target>26</target>   <!-- "produce .class files that run on Java 26" -->
  </configuration>
</plugin>
```

We aren't *adding* the compiler plugin — Maven already uses it for every build.

We are **reconfiguring** it, because its default Java version is old and we want Java 26.

Notice that plugins are declared with the exact same `groupId` / `artifactId` / `version` coordinates as dependencies.

Because plugins *are* jars in the same repository. One addressing system for everything.

> 💡 Tip: dependencies are code your *program* uses. Plugins are code your *build* uses. Different jobs, same coordinate system.

> ⚠️ Warning: always pin a plugin `<version>`. Without one, a different Maven version could pick a different plugin version and change your build's behaviour on a machine that isn't yours — the "works on my machine" problem sneaking back in through the side door.

---

## 15. finalName — Renaming the Final Dish

Compare the two output files:

```
nilanchal-app/target/nilanchal-app-1.0-SNAPSHOT.jar
nilanchal-web-app/target/nilanchal-web-app.war
```

The jar carries its version. The war doesn't. **Why?**

Because of one line in the web app's POM:

```xml
<build>
  <finalName>nilanchal-web-app</finalName>
</build>
```

By default Maven names the output `artifactId-version.extension`, which is great for a repository where many versions sit side by side.

But a war is usually **deployed to a server**, and on most servers the file name becomes part of the URL.

`http://localhost:8080/nilanchal-web-app/` is a URL you can type.

`http://localhost:8080/nilanchal-web-app-1.0-SNAPSHOT/` is not a URL anyone wants — and it would change every time you bumped the version.

So `finalName` strips the version off the file name.

> 💡 Tip: `finalName` changes only the output **file name**. The project's real version is still `1.0-SNAPSHOT`, and it is still recorded inside the artifact — check `target/maven-archiver/pom.properties`.

---

## 16. Archetypes — Project Templates

One last question for Part 3: who created all these folders, the placeholder `App.java`, the `index.jsp`, the starter `web.xml`?

Not us. An **archetype** did.

An archetype is a project **template** — a ready-made skeleton you generate from, so a new project starts out already correct.

```
mvn archetype:generate -DgroupId=com.nilanchal -DartifactId=nilanchal-app
```

The give-away is sitting in the web app's own config file:

```xml
<display-name>Archetype Created Web Application</display-name>
```

Along with the `"Hello World!"` print, the `"Hello World!"` heading in `index.jsp`, and that `assertTrue(true)` test.

None of those are real code. They are **proof that the plumbing works** before you write a single line of your own.

| Archetype / generator | Produces | Our project |
|-----------------------|----------|-------------|
| `maven-archetype-quickstart` | a plain Java app (`jar`) | `nilanchal-app` |
| `maven-archetype-webapp` | a web app (`war`, with `src/main/webapp`) | `nilanchal-web-app` |
| [start.spring.io](https://start.spring.io) (Spring Initializr) | a Spring Boot app | `SpringBootMavenApp` |

That third row is the modern version of the same idea.

Spring Initializr is a website that plays the archetype role: you tick the features you want, it hands you a zip with the POM, the folder layout, the wrapper, a `HELP.md`, and a `.gitignore` already in place.

> 💡 Tip: the first thing to do in a generated project is delete the placeholders you don't need and bump the ancient default dependency versions. `nilanchal-app` still carries `junit 3.8.1`, which is from 2006 — the archetype's default, not a deliberate choice. (And notice the Boot project's test uses JUnit 5, `org.junit.jupiter`, instead — a 15-year jump you get for free by starting from a modern template.)

---

# Part 4 — The Spring Boot Chapter

## 17. The Next Problem: Managing Versions By Hand

Parts 1–3 solved a lot. But look hard at the web app's dependency list one more time:

```xml
<artifactId>spring-core</artifactId>
<version>7.1.0-M1</version>
...
<artifactId>spring-security-web</artifactId>
<version>7.2.0-M1</version>
```

Two libraries. Two **different** version numbers. And a question with no easy answer:

**Do Spring Core 7.1.0-M1 and Spring Security 7.2.0-M1 actually work together?**

Think about how you would find out.

You could read both changelogs. You could search forums. You could just build it and pray.

Now scale that up. A real web app needs Spring Web, Spring Security, Jackson for JSON, Hibernate, a connection pool, a logging framework, a validation library, and an embedded server.

That's a dozen libraries, each with its own release schedule, each of which has *its own* opinions about the versions of the others.

And here's the nastiest part: they can all download fine and still break.

Maven doesn't verify compatibility. It just fetches what you named.

You find out you picked wrong at runtime, as a `NoSuchMethodError` — a class was there, but the exact method signature your library expected had changed.

> So the root cause is: *we are being asked to make compatibility decisions that we have no way of verifying — and there's no single library author who has the full picture either.*

So how can we solve this?

Let's reason from the shape of the problem.

Somebody, somewhere, **does** know which versions were tested together — the Spring team runs exactly that test suite before every release.

So the knowledge exists. It's just not in our POM.

What if we could import their answer instead of guessing our own?

We'd want to say something like:

> "Give me the set of versions that Spring Boot 4.0.8 was tested with. I'll name the libraries; you pick the numbers."

That is precisely what a **parent POM** does.

---

## 18. Parent POM — Inheriting a Whole Setup

Look at the very top of `SpringBootMavenApp/pom.xml`:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.0.8</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

Read that closely. It's `groupId` / `artifactId` / `version` — **coordinates again**.

The parent POM is just another artifact in the repository, and we're pointing at it.

**So what does declaring a parent actually buy us?**

The proof is three lines further down:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>                                      <!-- ← where is the version?! -->
```

**There is no `<version>` tag.**

In Part 2 that would have been a build error — Maven cannot fetch a jar without knowing which version.

It works here because the parent POM contains a giant list, roughly:

> "if anybody asks for `spring-boot-starter-webmvc` and doesn't specify a version, the answer is the one that matches Boot 4.0.8."

That mechanism is called **dependency management**, and the effect is enormous:

> **One version number — `4.0.8` — now decides hundreds of others, and they are guaranteed to have been tested together.**

Upgrading your entire stack becomes a one-line edit. Change `4.0.8`, rebuild, done.

Think of it like ordering the tasting menu instead of à la carte.

À la carte, you must know whether the wine goes with the fish.

With the tasting menu, the chef already decided, and the chef has tasted it.

### Inheritance goes further than versions

A parent hands down more than version numbers. It also passes along plugin setup, default configuration, and sensible build settings.

That's how the Boot project gets away with this:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>                                     <!-- no version, no configuration -->
    </plugins>
</build>
```

Compare that to `nilanchal-app`, which had to spell out the compiler plugin's version *and* its `<source>`/`<target>` config by hand.

Same job, far less typing, because the parent already did the configuring.

### What is `<relativePath/>` doing there?

By default, Maven assumes your parent POM is a file sitting in the folder *above* yours — which is how multi-module projects work.

Our parent isn't on our disk at all; it lives in the repository.

The empty `<relativePath/>` means **"don't look on disk, go straight to the repository."**

> 💡 Tip: without that empty tag, Maven would waste a moment looking for `../pom.xml`, and warn you when it found nothing. It's a small thing, and Initializr adds it for you.

### The strange empty tags

The Boot POM contains these odd-looking blanks:

```xml
<name/>
<description/>
<url/>
<licenses>
    <license/>
</licenses>
<developers>
    <developer/>
</developers>
<scm>
    <connection/>
    ...
</scm>
```

An empty `<license/>`? Why would a template generate *that*?

`HELP.md` in the project answers it directly:

> "While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent. To prevent this, the project POM contains empty overrides for these elements."

Now think about what that means.

Inheritance is not selective. You get **everything** the parent declares — including Spring's own licence and Spring's own developer list.

Without these overrides, your project would claim it was written by the Spring team.

> 💡 Tip: an empty tag is a deliberate "no, not that one" — it overrides the inherited value with nothing. This is the cost side of inheritance: convenience for the parts you want, plus a small chore for the parts you don't.

> ⚠️ Warning: `HELP.md` is in `.gitignore` and is meant to be deleted once read. Read it first — for this project it's the only place that explains those empty tags.

---

## 19. Starters — Buying the Whole Meal Kit

The parent POM solved *which version*. But there's a second, sneakier question hiding underneath it.

Go back to the web app for a second. We wanted a Spring web application, so we picked:

```xml
spring-core
spring-security-web
```

**How did we know to pick those two artifacts?**

And a harder follow-up: is that list even complete for a working web app?

To answer honestly you'd need to know that Spring is split into `spring-core`, `spring-beans`, `spring-context`, `spring-web`, `spring-webmvc`, `spring-aop`, `spring-expression`… and which of those you need for *your* use case.

That's not knowledge about your app. That's knowledge about **how the Spring project happens to be chopped into jars internally**.

> So the root cause is: to use a framework, you're forced to learn its internal packaging structure — an implementation detail that has nothing to do with the problem you're solving.

So how do we solve this?

We don't want to shop for parts. We want to state an **intention**:

> "I want to build a web app with Spring MVC. You work out the parts list."

And that's a **starter**:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc-test</artifactId>
    <scope>test</scope>
</dependency>
```

**Here's the part that surprises everyone: a starter jar is basically empty.**

It contains almost no code of its own.

Its entire purpose is its `pom.xml` — a curated list of dependencies.

Which means starters are not a new mechanism at all.

A starter is just **Section 8's transitive dependencies, used deliberately as a feature**.

```
you ask for → spring-boot-starter-webmvc
              └── its POM brings → spring-webmvc, spring-web, spring-core, spring-beans,
                                   spring-context, jackson (JSON), embedded Tomcat,
                                   validation, logging ... all at matching versions
```

The meal-kit analogy: you don't buy flour, yeast, and tomatoes separately and hope the ratios work.

You buy the pizza kit. One purchase, correct proportions, nothing missing.

Notice we also got a **test** starter with `<scope>test</scope>`, bundling JUnit 5, Mockito, AssertJ, and Spring's test helpers — all the test tools, all left out of the final artifact.

Two dependency blocks, and a complete, consistent, ready-to-run web stack.

| | Hand-picked (`nilanchal-web-app`) | Starter (`SpringBootMavenApp`) |
|---|---|---|
| Dependencies you write | one per library, with a version each | one per *capability*, no versions |
| Who decides the parts list | you | the Spring Boot team |
| Who decides the versions | you | the parent POM |
| What you must know | Spring's internal jar layout | what you want to build |
| Risk of a mismatch | real | essentially removed |

> 💡 Tip: starters are named by intent — `spring-boot-starter-webmvc`, `-data-jpa`, `-security`, `-test`. When you need a capability, search for its starter first. Reaching for individual Spring jars usually means you've skipped a rung.

> ⚠️ Warning: a starter pulls in a *lot*. That's the trade: you stop hand-managing versions, and in exchange your dependency tree gets big and mostly invisible. `mvn dependency:tree` is how you look inside when something surprises you.

---

## 20. Properties — One Place for Values You Repeat

Compare how our two jar projects ask for Java 26.

`nilanchal-app` — the manual way, 10 lines:

```xml
<plugin>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.11.0</version>
  <configuration>
    <source>26</source>
    <target>26</target>
  </configuration>
</plugin>
```

`SpringBootMavenApp` — 3 lines:

```xml
<properties>
    <java.version>26</java.version>
</properties>
```

**Same outcome. How?**

Follow the chain:

1. The parent POM already configures `maven-compiler-plugin` for us (Section 18).
2. But it can't know which Java version *we* want.
3. So instead of hardcoding a number, the parent writes `${java.version}` in the plugin config — a placeholder.
4. We fill that placeholder in by declaring the property.

So `<properties>` is Maven's variable system, and `${...}` is how you read a variable back.

**Why is that worth a whole mechanism? Why not just type the number where it's needed?**

Because in `nilanchal-app` we typed `26` **twice** — once in `<source>`, once in `<target>`.

Two copies of one decision.

Change one and forget the other, and you get a confusing mismatch: source compiled as Java 26, targeting some other version.

Now imagine a real POM where a version string appears in eight places.

> So the root cause is: the same value copy-pasted into many places will eventually drift, because nothing links the copies together.

The fix is the same one every programming language reached for: **name the value once, refer to the name everywhere else.**

That's all a property is. A variable for your build file.

> 💡 Tip: `java.version` isn't a magic Maven keyword — it's a name the Spring Boot parent POM chose and reads via `${java.version}`. You can invent your own (`<my.lib.version>1.2.3</my.lib.version>`) and use `${my.lib.version}` in as many dependency blocks as you like.

> 💡 Tip: this is also why Boot projects rarely need a `<build>` section for the compiler. One property replaces the whole plugin block.

---

## 21. The Spring Boot Plugin and the Fat Jar

Time to settle the puzzle from Section 11.

`SpringBootMavenApp` declares no `<packaging>`, so it's a **jar**.

But it serves web pages. And from Part 3 we know two awkward facts:

1. A plain jar does **not** bundle its dependencies.
2. A web app normally needs a server (Tomcat) that you install separately and deploy a war into.

So let's ask the honest beginner question: **how do you even run a web app in the war world?**

You download Tomcat. You install it. You configure it. You copy your war into its `webapps` folder. You start Tomcat. You hope its Java version matches yours.

Every developer on the team does that. Every server does that.

> So the root cause is: the application and the thing that runs the application are two separate installs, so "running my app" depends on someone else's machine being set up correctly.

How do we solve this?

Flip the relationship.

Instead of deploying your app *into* a server, **put the server inside your app**.

Then "run my app" is just `java -jar`, and there is nothing to install but Java itself.

That's Spring Boot's central idea, and this plugin is what makes it physically possible:

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

It hooks into the `package` phase and **repackages** the ordinary jar into an **executable jar** — nicknamed a **fat jar** or **uber jar**.

Roughly:

```
SpringBootMavenApp-0.0.1-SNAPSHOT.jar
├── META-INF/MANIFEST.MF            ← Main-Class: a Spring Boot launcher
├── org/springframework/boot/loader/ ← the launcher's own code
└── BOOT-INF/
    ├── classes/                    ← your compiled code + application.properties
    └── lib/                        ← every dependency jar, embedded Tomcat included
```

Compare the three shapes we've now seen:

| | Plain jar | War | Fat jar (Boot) |
|---|---|---|---|
| Dependencies inside? | ❌ | ✅ in `WEB-INF/lib` | ✅ in `BOOT-INF/lib` |
| Server needed? | n/a | ✅ install Tomcat | ❌ Tomcat is inside |
| How you run it | `java -cp ... MyClass` | deploy to Tomcat | `java -jar app.jar` |
| Where it can run | anywhere with the right classpath | anywhere with a matching server | anywhere with a JVM |

That last row is the whole payoff.

One file, one command, no install, identical on your laptop, your teammate's laptop, and a server.

> 💡 Tip: during development you don't even package. `mvn spring-boot:run` (or `./mvnw spring-boot:run`) compiles and launches in one step. That's the plugin's `run` **goal** — `plugin:goal`, the same shape as `dependency:tree`.

> 💡 Tip: this project's `target/` currently holds only `classes/` and `test-classes/` — so it has been compiled but **never packaged**. Run `./mvnw clean package` and watch `BOOT-INF/` appear.

> ⚠️ Warning: "fat jar" is literal. A Boot web jar is typically 20–40 MB, because an entire web server is riding along inside it. That's the price of not needing one installed.

---

## 22. The Maven Wrapper — Which Maven Are You Using?

We've pinned our Java version. We've pinned every library version via the parent.

But there's one version we've been quietly ignoring: **Maven's own**.

Play it out. You installed Maven 3.9 last year. Your teammate installed 3.6 two years ago. The build server has whatever came with its base image. A new joiner has none at all and has to go install it before they can even compile.

Different Maven versions can resolve dependencies slightly differently, ship different default plugin versions, and require different Java versions.

> So the root cause is: it's the *same* "works on my machine" problem from Part 1, moved up one level — we pinned everything the build consumes, but not the builder itself.

So how do we solve this?

Use the same trick that worked before: write the version down in the project, and let the project fetch what it needs.

That's the **Maven Wrapper**, and it's four files:

```
SpringBootMavenApp/
├── mvnw                                    ← launcher script (Linux/macOS)
├── mvnw.cmd                                ← launcher script (Windows)
└── .mvn/wrapper/maven-wrapper.properties   ← which Maven version to use
```

The properties file is the interesting one:

```properties
wrapperVersion=3.3.4
distributionType=only-script
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.16/apache-maven-3.9.16-bin.zip
```

Read `distributionUrl` and you can see the whole mechanism: it is a **direct download link to Maven 3.9.16**.

So the flow becomes:

1. You type `./mvnw clean package` instead of `mvn clean package`.
2. The script checks whether Maven **3.9.16** is already downloaded locally.
3. If not, it downloads it from that exact URL.
4. It runs your build with *that* Maven — not whatever you have installed.

The result: everyone who clones this project builds it with byte-identical tooling, and **nobody needs Maven installed at all**. Only Java.

> 💡 Tip: the rule in a wrapper project is simple — always type `./mvnw` (or `mvnw.cmd` on Windows), never bare `mvn`. Using `mvn` silently opts out of the guarantee.

> 💡 Tip: `distributionType=only-script` means the wrapper stays lean: just the scripts, no helper jar committed to your repo.

> ⚠️ Warning: `mvnw` and `mvnw.cmd` **must be committed to git**. They're the whole point. Notice `.gitignore` ignores `.mvn/wrapper/maven-wrapper.jar` (a downloadable binary) but never the scripts — that distinction is deliberate.

---

## 23. Making JSP Work in Spring Boot

Here's a real problem this project solves, and it's a great lesson in reading a POM for *intent*.

`spring-boot-starter-webmvc` gives us a full web stack. `greet.jsp` exists. So the page should just work, right?

It doesn't. Out of the box you'd get a 404, or a page that renders the raw JSP text.

**Why?**

Because of something easy to forget: **a JSP is not an HTML file. It's a program.**

`greet.jsp` contains `${wish}` — a value that has to be computed at request time.

So before a browser can see it, someone must translate that `.jsp` into a Java servlet, then compile it.

The tool that does that translation is **Jasper**, Tomcat's JSP engine.

And a standalone Tomcat install has Jasper. But the **embedded** Tomcat inside a Boot fat jar ships without it, because most Boot apps serve JSON or use modern template engines and would never need it.

> So the root cause is: JSP needs a compiler at runtime, and the slimmed-down embedded server doesn't carry one.

The fix is to ask for it explicitly — which is exactly what this POM does:

```xml
<!-- Source: https://mvnrepository.com/artifact/org.apache.tomcat/tomcat-jasper -->
<dependency>
    <groupId>org.apache.tomcat</groupId>
    <artifactId>tomcat-jasper</artifactId>
    <version>11.0.25</version>
    <scope>compile</scope>
</dependency>
```

**And now notice something important about that block: it has a `<version>`.**

The two Boot starters above it don't. This one does.

Why the difference?

Because `tomcat-jasper` is not in the Boot parent's managed list — Spring never planned for you to need it, so it has no opinion about which version pairs with Boot 4.0.8.

> 💡 Tip: **no version means the parent manages it; a version means you're on your own.** Whenever you see an explicit version in a Boot POM, that's a flag: you have taken personal responsibility for that compatibility decision.

### Wiring the view resolver

Getting Jasper is only half of it. Something must connect the controller's answer to the actual file.

`NilanchalController` returns the plain string `"greet"`:

```java
return "greet";
```

That's not a file path. It's a **view name**.

So how does `"greet"` become `greet.jsp`? Through `src/main/resources/application.properties`:

```properties
spring.application.name=SpringBootMavenApp
server.port=8484

spring.mvc.view.prefix=/
spring.mvc.view.suffix=.jsp
```

The rule is literally string concatenation:

```
prefix + view name + suffix
  "/"  +   "greet"  +  ".jsp"   →   /greet.jsp
```

And `/greet.jsp` is exactly where the file sits inside `src/main/webapp`.

**Why split it into a prefix and a suffix instead of just returning the full path from the controller?**

Because then every controller method in the app would repeat `/` and `.jsp` in every return statement.

That's Section 20's lesson again: a value repeated everywhere eventually drifts, and changing your mind means editing every file.

Declare it once as configuration; controllers then speak in view *names* and stay ignorant of where views live or what technology renders them.

Swap to a different template engine later, and you change two properties — not every controller.

And `server.port=8484`?

The default is 8080, which on a learning machine is usually already taken by something else — often a Tomcat you installed for the war project.

So the app runs at:

```
http://localhost:8484/greeting
```

> ⚠️ Warning: JSP inside an **executable jar** is officially unsupported by Spring Boot — the supported combination is JSP with `war` packaging. It often works in a jar; it can also fail in confusing ways. If `greet.jsp` misbehaves, the first thing to try is `mvn spring-boot:run` (which runs from `target/classes`, not from inside a jar), and the real fix is `<packaging>war</packaging>`. For new projects, Thymeleaf is the path of least resistance.

---

## 24. What the Spring Boot Code Actually Does

This is a Maven notes file, so we'll keep this short — Spring itself deserves its own notes.

But the code is in the folder, and one Maven-flavoured question is worth answering: **why does the folder structure matter so much here?**

### The entry point

```java
package com.nilanchal.SpringBootMavenApp;

@SpringBootApplication
public class SpringBootMavenAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMavenAppApplication.class, args);
    }
}
```

A plain `main` method — the same `main` from Section 11.

`SpringApplication.run(...)` boots the framework and starts the embedded Tomcat from Section 21.

`@SpringBootApplication` bundles three jobs, and the third one is the one that touches Maven:

| Part of the annotation | What it does |
|------------------------|--------------|
| configuration | "this class holds setup" |
| auto-configuration | "look at the jars on the classpath and configure sensible defaults" |
| **component scan** | **"search this package and everything below it for my classes"** |

Read that middle one again: auto-configuration **inspects your dependencies**.

Because `spring-boot-starter-webmvc` put Tomcat on the classpath, Boot concludes "this is a web app" and starts a web server.

Your `pom.xml` doesn't just supply code any more — it *drives behaviour*.

And component scan explains the folder layout:

```
com.nilanchal.SpringBootMavenApp            ← the main class lives here
├── service/   GreetingService, IGreetingService
└── web/       NilanchalController
```

Everything sits **below** the main class's package, so scanning finds it all automatically.

> ⚠️ Warning: move a class *outside* `com.nilanchal.SpringBootMavenApp` and Spring will silently stop seeing it. No compile error — just a mysteriously missing feature at runtime. Package placement is functional here, not cosmetic.

### The service, and why there's an interface

```java
public interface IGreetingService {
    String generateGreeting();
}
```

```java
@Service
public class GreetingService implements IGreetingService
{
    @Override
    public String generateGreeting()
    {
        LocalTime time = LocalTime.now();
        int hour = time.getHour();
        if (hour < 12)       return "Good Moring";      // tutorial's spelling
        else if (hour < 16)  return "Good Afternoon";
        else if (hour < 20)  return "Good Evening";
        else                 return "Good Night";
    }
}
```

`@Service` is a label meaning "Spring, this is a worker class — create one and keep it."

An object Spring creates and manages like this is called a **bean**.

### The controller

```java
@Controller
public class NilanchalController
{
    @Autowired
    private IGreetingService service;      // ← the interface, not the class

    @GetMapping("/greeting")
    public String generateWish1(Model model)
    {
        String res = service.generateGreeting();
        model.addAttribute("wish", res);
        return "greet";
    }
}
```

Notice we never wrote `new GreetingService()`.

`@Autowired` says "find a bean that fits this type and put it here" — that's **dependency injection**.

**And why is the field typed as the interface `IGreetingService` rather than `GreetingService`?**

Because the controller only needs the *promise* — "something can generate a greeting."

It doesn't care how. Swap in a different implementation (a fixed greeting for tests, a localised one for another country) and the controller doesn't change by a single character.

This is the same instinct as `<properties>` and the view resolver: **state the intent in one place, keep the details somewhere replaceable.**

### End to end

Put the whole chain together:

```
browser → http://localhost:8484/greeting
        → @GetMapping("/greeting") on NilanchalController
        → service.generateGreeting()  → "Good Afternoon"
        → model.addAttribute("wish", "Good Afternoon")
        → return "greet"
        → view resolver:  "/" + "greet" + ".jsp"  →  /greet.jsp
        → Jasper compiles greet.jsp, substitutes ${wish}
        → browser shows:  Hey! Good Afternoon
```

And `greet.jsp` is the one piece the user actually sees:

```jsp
<h1 ...>Hey! ${wish}</h1>
```

`${wish}` matches the attribute name the controller put in the model. Change one and you must change the other.

> 💡 Tip: every single arrow in that chain was made possible by a line in `pom.xml` — the starter supplied MVC and Tomcat, `tomcat-jasper` supplied the JSP compiler, the parent supplied the versions, the Boot plugin makes it runnable. That's the payoff of Part 4: the POM is the app's foundation, not paperwork.

---

## 25. .gitignore — Keeping target Out of Git

Back in Section 12 we said: never commit `target/`.

`SpringBootMavenApp` is the only project here that actually enforces it, because Initializr shipped a `.gitignore`:

```
HELP.md
target/
.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### STS ###          (Eclipse / Spring Tool Suite files)
### IntelliJ IDEA ###
### NetBeans ###
### VS Code ###
```

Three things worth reading carefully.

**1. `target/` is ignored.** The reason is Section 12 — regenerated output, and it can be actively stale.

**2. `HELP.md` is ignored.** It's generated documentation for *you*, not part of your project. Read it, then delete it.

**3. `.mvn/wrapper/maven-wrapper.jar` is ignored, but `mvnw` and `mvnw.cmd` are not.**

That's the distinction from Section 22, encoded in the ignore file: the jar is a binary the wrapper can download on demand, while the scripts are the wrapper itself and must be present the moment someone clones the repo.

**And why are `.idea/`, `.vscode/`, `.classpath` and friends all in there?**

Because those are *your editor's* opinions — window layouts, local absolute paths, personal settings.

Committing them starts an endless fight where every teammate's editor overwrites everyone else's.

> 💡 Tip: the mental model for a repository is the same rule as Section 12 — commit what a human **wrote**, ignore what a machine can **regenerate**.

> ⚠️ Warning: `nilanchal-app` and `nilanchal-web-app` have **no** `.gitignore`, so their `target/` folders — including 12 Spring jars and a `.war` — are sitting there ready to be committed. A single `.gitignore` at the root of this folder containing `target/` would cover all three projects.

---

# Part 5 — Reference

## 26. Comparison: All Three Projects

| | **nilanchal-app** | **nilanchal-web-app** | **SpringBootMavenApp** |
|---|---|---|---|
| Generated by | `maven-archetype-quickstart` | `maven-archetype-webapp` | Spring Initializr |
| Version | `1.0-SNAPSHOT` | `1.0-SNAPSHOT` | `0.0.1-SNAPSHOT` |
| `<packaging>` | `jar` | `war` | none → `jar` (fat jar) |
| Has a `<parent>`? | ❌ | ❌ | ✅ `spring-boot-starter-parent 4.0.8` |
| Dependency versions | written by hand | written by hand | inherited (except `tomcat-jasper`) |
| Dependencies declared | 1 | 3 | 3 (2 of them starters) |
| Java 26 set via | compiler plugin `<source>/<target>` | not set | `<java.version>` property |
| Source folders used | `main/java`, `test/java` | `main/webapp` | `main/java`, `main/resources`, `main/webapp`, `test/java` |
| Test framework | JUnit 3 (`TestCase`) | JUnit 3 (declared, unused) | JUnit 5 (`@Test` jupiter) |
| Build plugins | `maven-compiler-plugin` | none | `spring-boot-maven-plugin` |
| Has `<finalName>` | ❌ | ✅ | ❌ |
| Maven wrapper | ❌ | ❌ | ✅ `mvnw`, pinned to 3.9.16 |
| `.gitignore` | ❌ | ❌ | ✅ |
| Server | n/a | install Tomcat yourself | embedded, inside the jar |
| How you run it | `java -jar ...` | deploy the war to Tomcat | `./mvnw spring-boot:run` |
| URL | n/a | `localhost:8080/nilanchal-web-app/` | `localhost:8484/greeting` |

Read that table top to bottom and you can watch the trend:

**each project writes less configuration than the last, while doing more.**

That's not an accident. It's convention over configuration (Section 3), applied harder each time — first by Maven's own defaults, then by a parent POM that makes the remaining decisions for you.

---

## 27. Command Cheat Sheet

Run all of these from the folder containing `pom.xml`.

| Command | What it does |
|---------|--------------|
| `mvn clean` | Delete `target/` |
| `mvn compile` | Compile `src/main/java`, copy `src/main/resources` → `target/classes` |
| `mvn test` | Compile and run the tests |
| `mvn package` | Everything above + build the jar/war |
| `mvn clean package` | The same, from a guaranteed-clean slate |
| `mvn clean install` | The same + copy the artifact into your local `.m2` |
| `mvn dependency:tree` | Print the full transitive dependency tree — the tool that explains the 12 jars |
| `mvn -o package` | Offline mode: use only what's already in `.m2`, never touch the network |
| `mvn archetype:generate` | Create a brand-new project from a template |

**Spring Boot projects (use the wrapper):**

| Command | What it does |
|---------|--------------|
| `./mvnw clean package` | Same as `mvn`, but with the project's pinned Maven 3.9.16 (`mvnw.cmd` on Windows) |
| `./mvnw spring-boot:run` | Compile and launch the app with its embedded Tomcat — no packaging step |
| `java -jar target/SpringBootMavenApp-0.0.1-SNAPSHOT.jar` | Run the packaged fat jar |
| `./mvnw dependency:tree` | See what the starters actually pulled in |

> 💡 Tip: `dependency:tree` and `spring-boot:run` are both `plugin:goal` — a plugin's name and one of its jobs. Any plugin goal can be called directly like this, without going through a lifecycle phase.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 02. Maven Projects*
