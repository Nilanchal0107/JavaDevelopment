# 📘 REST API & Web Services — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy

**One running example lives through these notes:** an **Alien** (`id`, `name`, `points`) that grows step by step. First it is a hard-coded object, then a list, then a list in a repository, and finally rows in a MySQL table that we can create, read, update and delete over HTTP.

Project: `demorest`, built with **Jersey 1.19.4** (`com.sun.jersey`) on Java 8. Every annotation comes from `javax.ws.rs.*`, which is **JAX-RS**, the standard Java API for REST. Jersey is the library that implements it.

---

## 📑 Table of Contents

**Part 1 — Fundamentals**
1. [What Is a Web Service?](#1-what-is-a-web-service)
2. [What Is a REST API?](#2-what-is-a-rest-api)
3. [HTTP Methods ↔ CRUD](#3-http-methods--crud)

**Part 2 — RESTful Web Services Tutorial (Jersey)**
4. [Introduction — JAX-RS and Jersey](#4-introduction--jax-rs-and-jersey)
5. [Creating a Jersey Project in Eclipse](#5-creating-a-jersey-project-in-eclipse)
6. [Running Our First REST Jersey Application](#6-running-our-first-rest-jersey-application)
7. [How to Create a Resource Class](#7-how-to-create-a-resource-class)
8. [List as Resource](#8-list-as-resource)
9. [POSTMAN](#9-postman)
10. [Send a POST Request](#10-send-a-post-request)
11. [PathParams](#11-pathparams)
12. [Working with JSON](#12-working-with-json)
13. [MySQL Repository](#13-mysql-repository)
14. [Update Resource Using PUT Method](#14-update-resource-using-put-method)
15. [Delete Resource Using DELETE Method](#15-delete-resource-using-delete-method)

**Part 3 — Wrap-Up**
16. [Full Request Flow](#16-full-request-flow)
17. [Cheat Sheet](#17-cheat-sheet)
18. [⚠️ My Mistakes & Gaps](#️-my-mistakes--gaps)

---

# Part 1 — Fundamentals

## 1. What Is a Web Service?

A **web service** is a program that other programs call over the network. It sends back **data**, not a web page for a person to look at.

With Servlets & JSP, the server sent **HTML** to a **browser**. A web service sends **data** (XML or JSON) to **any client**: a mobile app, a React front end, another Java server, or Postman.

```
                         ┌────────────────────┐
 Android app  ──────────▶│                    │
 React website ─────────▶│   Web Service      │──▶ Database
 Another server ────────▶│  (same data for    │
 Postman  ──────────────▶│    everyone)       │
                         └────────────────────┘
          ◀──── XML / JSON ────
```

Why use one:

- **One back end, many front ends.** The web app and the mobile app both call the same service.
- **Language independent.** A Python client can call a Java service, because both sides just speak HTTP plus XML or JSON.
- **Loose coupling.** You can rewrite the UI without touching the server.

### Two main styles

| | SOAP | REST |
|---|---|---|
| What it is | A **protocol** with strict rules | An **architectural style** (a set of guidelines) |
| Data format | XML only (SOAP envelope) | JSON, XML, text, anything |
| Contract | WSDL file | Usually none (docs / OpenAPI) |
| Transport | HTTP, SMTP, … | HTTP |
| Weight | Heavy | Light, fast |
| Java API | JAX-WS | **JAX-RS** ← used in these notes |

---

## 2. What Is a REST API?

**REST** stands for **RE**presentational **S**tate **T**ransfer. A **REST API** is a web service that follows REST rules.

The core ideas:

- **Everything is a resource.** An alien, a list of aliens, a user. Each resource has a **URI**.
  ```
  /aliens              → all aliens
  /aliens/alien/101    → the alien with id 101
  ```
- **The HTTP method says what to do** with the resource: `GET`, `POST`, `PUT` or `DELETE`. Put nouns in the URI and let the method be the verb. Write `DELETE /aliens/alien/101`, not `/deleteAlien?id=101`.
- **Representation.** The client never gets the Java object itself. It gets a **representation** of it, such as JSON or XML.
- **Stateless.** Each request carries everything the server needs. The server keeps no session for you.
- **Client–server.** The UI and the data storage are separate.

**API** (Application Programming Interface) is the set of URIs and methods a program exposes so other programs can use it.

---

## 3. HTTP Methods ↔ CRUD

| Operation | HTTP Method | Example URI | Body sent? | In our code |
|---|---|---|---|---|
| **R**ead all | `GET` | `/aliens` | No | `getAliens()` |
| **R**ead one | `GET` | `/aliens/alien/101` | No | `getAlien(id)` |
| **C**reate | `POST` | `/aliens/alien` | Yes (new alien) | `createAlien(a1)` |
| **U**pdate | `PUT` | `/aliens/alien` | Yes (changed alien) | `updateAlien(a1)` |
| **D**elete | `DELETE` | `/aliens/alien/101` | No | `killAlien(id)` |

Common status codes you will see in Postman:

| Code | Meaning |
|---|---|
| `200 OK` | Worked, and there is a body |
| `204 No Content` | Worked, but there is no body |
| `404 Not Found` | No resource at this URI (usually a wrong path) |
| `405 Method Not Allowed` | The URI exists but has no method for this HTTP verb |
| `406 Not Acceptable` | The server can't produce the type the client asked for in `Accept` |
| `415 Unsupported Media Type` | The server can't read the type the client sent in `Content-Type` |
| `500 Internal Server Error` | An exception was thrown in your code |

---

# Part 2 — RESTful Web Services Tutorial (Jersey)

## 4. Introduction — JAX-RS and Jersey

- **JAX-RS** (Java API for RESTful Web Services) is the **specification**: a set of interfaces and annotations like `@Path`, `@GET` and `@Produces`.
- **Jersey** is an **implementation** of that spec. Others include RESTEasy and Apache CXF.

You write code against JAX-RS annotations, and Jersey does the work at runtime.

```
Browser / Postman
      │  HTTP
      ▼
Tomcat  ──▶  Jersey ServletContainer (one servlet)
                    │  looks at URI + HTTP method
                    ▼
             Your resource class method (@Path + @GET …)
                    │  returns a Java object
                    ▼
             Jersey converts it → XML / JSON
```

Under the hood Jersey is **just a servlet**: `com.sun.jersey.spi.container.servlet.ServletContainer`. It is the single entry point that sends each request to the right Java method. This builds directly on the Servlets notes.

| Jersey version | Package | Servlet class |
|---|---|---|
| 1.x (**this project**, 1.19.4) | `com.sun.jersey` | `com.sun.jersey.spi.container.servlet.ServletContainer` |
| 2.x | `org.glassfish.jersey` | `org.glassfish.jersey.servlet.ServletContainer` |
| 3.x | `org.glassfish.jersey` + `jakarta.ws.rs` | same as 2.x |

> ⚠️ Warning: Don't mix these. Jersey 1 jars with Jersey 2 config, or `javax.ws.rs` with `jakarta.ws.rs`, gives a 404 or a `ClassNotFoundException` at startup.

---

## 5. Creating a Jersey Project in Eclipse

1. **File → New → Maven Project** → keep "Create a simple project" **unchecked** → Next.
2. In the archetype filter type **`jersey-quickstart-webapp`**.
   - If it doesn't show, click **Add Archetype** and enter group id `com.sun.jersey.archetypes` (Jersey 1) or `org.glassfish.jersey.archetypes` (Jersey 2), artifact id `jersey-quickstart-webapp`, and a version.
3. Group Id: `com.nilanchal`, Artifact Id: `demorest`, Package: `com.nilanchal.demorest` → **Finish**.
4. Add a server: **Servers** tab → New → **Apache Tomcat** → point it to your Tomcat folder. This creates the `Servers/` project you see in the workspace.

What the archetype generates:

```
demorest/
├── pom.xml
└── src/main/
    ├── java/com/nilanchal/demorest/
    │   └── MyResource.java          ← sample resource
    └── webapp/
        ├── index.jsp                ← home page with a link to the resource
        └── WEB-INF/web.xml          ← registers the Jersey servlet
```

### `pom.xml` — the important parts

```xml
<packaging>war</packaging>                 <!-- web app, deployed to Tomcat -->

<build>
    <finalName>demorest</finalName>        <!-- demorest.war -->
    ...
    <configuration>
        <source>1.8</source>               <!-- Java 8 -->
        <target>1.8</target>
    </configuration>
</build>

<dependencies>
    <dependency>                           <!-- Jersey core + servlet -->
        <groupId>com.sun.jersey</groupId>
        <artifactId>jersey-servlet</artifactId>
        <version>${jersey.version}</version>
    </dependency>
    <dependency>                           <!-- JSON support (added in section 12) -->
        <groupId>com.sun.jersey</groupId>
        <artifactId>jersey-json</artifactId>
        <version>${jersey.version}</version>
    </dependency>
    <dependency>                           <!-- MySQL driver (added in section 13) -->
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>

<properties>
    <jersey.version>1.19.4</jersey.version>
</properties>
```

### `web.xml` — registering Jersey

```xml
<servlet>
    <servlet-name>Jersey Web Application</servlet-name>
    <!-- Jersey's front servlet -->
    <servlet-class>com.sun.jersey.spi.container.servlet.ServletContainer</servlet-class>
    <init-param>
        <!-- which package to scan for @Path classes -->
        <param-name>com.sun.jersey.config.property.packages</param-name>
        <param-value>com.nilanchal.demorest</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>   <!-- create it when Tomcat starts -->
</servlet>
<servlet-mapping>
    <servlet-name>Jersey Web Application</servlet-name>
    <!-- every URI under /webresources/ goes to Jersey -->
    <url-pattern>/webresources/*</url-pattern>
</servlet-mapping>
```

> ⚠️ Warning: Jersey only finds resource classes **inside the scanned package**. A `@Path` class in any other package gives a **404**.

---

## 6. Running Our First REST Jersey Application

Right-click the project → **Run As → Run on Server** → pick Tomcat.

`index.jsp` opens with a link:

```html
<h2>Jersey RESTful Web Application!</h2>
<p><a href="webresources/myresource">Jersey resource</a>
```

Clicking it calls `MyResource`:

```java
@Path("/myresource")                 // URI: /webresources/myresource
public class MyResource {

    @GET                             // runs for HTTP GET
    @Produces("text/plain")          // response Content-Type
    public String getIt() {
        return "Hi there!";          // becomes the response body
    }
}
```

**Building the URL:**

```
http://localhost:8080/demorest/webresources/myresource
                      └──┬───┘ └─────┬─────┘ └───┬────┘
                   context path  url-pattern   @Path
```

Output in the browser: `Hi there!`

> 💡 Tip: A browser address bar can only send **GET**. To test POST, PUT and DELETE you need a tool like Postman (section 9).

---

## 7. How to Create a Resource Class

A **resource class** is a plain Java class with `@Path`. Its methods handle HTTP requests for that URI.

### Step 1 — the model: `Alien.java`

```java
@XmlRootElement                      // lets JAXB convert Alien ⇄ XML (and JSON, see section 12)
public class Alien
{
    private String name;
    private int points;
    private int id;

    // getters and setters are REQUIRED; the converter uses them
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    @Override
    public String toString() {       // readable output for System.out.println
        return "Alien [name=" + name + ", points=" + points + ", id=" + id + "]";
    }
}
```

### Step 2 — the resource: first version of `AlienResources.java`

```java
@Path("aliens")                              // URI: /webresources/aliens
public class AlienResources
{
    @GET
    @Produces(MediaType.APPLICATION_XML)     // same as "application/xml"
    public Alien getAlien()
    {
        System.out.println("getAlien called...");
        Alien al = new Alien();
        al.setName("Nilanchal");
        al.setPoints(100);
        return al;                           // Jersey turns this into XML
    }
}
```

Response for `GET /demorest/webresources/aliens`:

```xml
<alien>
    <id>0</id>
    <name>Nilanchal</name>
    <points>100</points>
</alien>
```

Rules for a resource class:

| Rule | Why |
|---|---|
| Class has `@Path` | Otherwise Jersey ignores it |
| Class is in the scanned package | See `web.xml` |
| Public no-arg constructor | Jersey creates the object itself |
| Method has `@GET` / `@POST` / … | Tells Jersey which verb runs it |
| `@Produces` sets the output type | Must match what the client accepts |
| The model has `@XmlRootElement` + getters/setters | Otherwise you get "MessageBodyWriter not found" (500) |

> 💡 Tip: `id` shows `0` because it was never set and `int` defaults to `0`. Later code uses this: **id 0 means "not found"**.

> ⚠️ Warning: `javax.xml.bind` (JAXB) ships with JDK 8 but was **removed in Java 11**. On a newer JDK, add the `jaxb-api` and `jaxb-runtime` dependencies, or `@XmlRootElement` won't compile.

---

## 8. List as Resource

Returning a **list** works the same way. Jersey wraps the elements in a plural root tag.

### Step 1 — list returned directly from the resource

```java
@GET
@Produces(MediaType.APPLICATION_XML)
public List<Alien> getAlien()
{
    Alien a1 = new Alien();
    a1.setName("Nilanchal");
    a1.setPoints(100);

    Alien a2 = new Alien();
    a2.setName("Neha");
    a2.setPoints(99);

    List<Alien> aliens = Arrays.asList(a1, a2);   // fixed-size list
    return aliens;
}
```

```xml
<aliens>
    <alien><id>0</id><name>Nilanchal</name><points>100</points></alien>
    <alien><id>0</id><name>Neha</name><points>99</points></alien>
</aliens>
```

### Step 2 — move data into a repository

A resource class should only handle HTTP. The data belongs in a **repository** class, the same idea as the Model in MVC.

```java
public class AlienRepository
{
    List<Alien> aliens;

    public AlienRepository()
    {
        aliens = new ArrayList<>();

        Alien a1 = new Alien();
        a1.setId(101);
        a1.setName("Nilanchal");
        a1.setPoints(100);

        Alien a2 = new Alien();
        a2.setId(102);
        a2.setName("Neha");
        a2.setPoints(99);

        aliens.add(a1);
        aliens.add(a2);
    }

    public List<Alien> getAliens() { return aliens; }

    public Alien getAlien(int id) {
        for (Alien a : aliens)
            if (a.getId() == id) return a;
        return new Alien();          // not found → empty alien (id 0)
    }

    public void create(Alien a1) { aliens.add(a1); }
}
```

The resource now just calls it:

```java
AlienRepository repo = new AlienRepository();

@GET
@Produces(MediaType.APPLICATION_XML)
public List<Alien> getAliens() {
    return repo.getAliens();
}
```

> ⚠️ Warning: By default Jersey creates a **new resource object for every request**, which also means a new `AlienRepository`. So an in-memory `create()` **disappears on the next request**. Section 13 fixes this with MySQL. (Making the list `static` would also work.)

---

## 9. POSTMAN

**Postman** is a desktop app for sending **any** HTTP request (GET, POST, PUT, DELETE) and inspecting the response. A browser can only send GET from the address bar.

Using it:

1. Click **New → HTTP Request**.
2. Pick the **method** from the dropdown and paste the **URL**.
3. **Headers** tab:
   - `Accept` — the format **you want back** (`application/json` or `application/xml`). It is matched against `@Produces`.
   - `Content-Type` — the format of **the body you are sending**. It is matched against `@Consumes`.
4. **Body** tab (POST and PUT) → **raw** → choose **JSON** or **XML**. Postman sets `Content-Type` for you.
5. Click **Send** and read the status code, the response body and the response headers.

```
GET  http://localhost:8080/demorest/webresources/aliens
Accept: application/xml
```

> 💡 Tip: Save requests in a **Collection** (Get All, Get One, Create, Update, Delete) so you can rerun the whole CRUD flow in one click.

---

## 10. Send a POST Request

**POST** creates a new resource. The client sends the new alien in the **request body**, and Jersey converts that body into an `Alien` parameter.

```java
@POST
@Path("alien")                                   // POST /webresources/aliens/alien
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public Alien createAlien(Alien a1)               // body → Alien (un-annotated param = request body)
{
    System.out.println("Received Payload for Creation: " + a1);
    repo.create(a1);
    return a1;                                   // echo it back to the client
}
```

Postman:

```
POST http://localhost:8080/demorest/webresources/aliens/alien
Content-Type: application/xml

<alien>
    <id>103</id>
    <name>Ravi</name>
    <points>75</points>
</alien>
```

Console: `Received Payload for Creation: Alien [name=Ravi, points=75, id=103]`

Path nesting: the class `@Path("aliens")` and the method `@Path("alien")` join into `/aliens/alien`.

> 💡 Tip: A method can have **only one** un-annotated parameter. That parameter is the request body.

> 💡 Tip: Add `@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})` to POST and PUT methods. It states which body types are accepted, and anything else gets a clean **415** error. Our code leaves it out, so Jersey accepts any type it has a reader for.

---

## 11. PathParams

To fetch **one** alien, put its id in the URI and read it with `@PathParam`.

```java
@GET
@Path("alien/{id}")                              // {id} is a template variable
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public Alien getAlien(@PathParam("id") int id)   // "101" in the URI → int 101
{
    return repo.getAlien(id);
}
```

```
GET /demorest/webresources/aliens/alien/101
```

```json
{ "id": "101", "name": "Nilanchal", "points": "100" }
```

How it maps:

```
@Path("aliens")  +  @Path("alien/{id}")
       │                     │
/webresources/aliens/alien/101
                           └─▶ @PathParam("id") int id = 101
```

- The name inside `{}` must **exactly match** the name in `@PathParam("...")`.
- Jersey converts the text to the parameter type (`int`, `long`, `String`, …).
- If the text can't be converted (`/alien/abc` for an `int`), you get **404**.
- If no row matches, the repository returns an empty `Alien`, so the response has `id` = `0`.

| Annotation | Reads from | Example |
|---|---|---|
| `@PathParam` | URI path segment | `/alien/{id}` |
| `@QueryParam` | `?key=value` | `/aliens?points=100` |
| `@FormParam` | HTML form body | `name=Ravi` |
| `@HeaderParam` | request header | `Accept` |

---

## 12. Working with JSON

JSON is lighter than XML and is what most front ends use. In Jersey 1 it takes two steps.

**1. Add the dependency** (`pom.xml`):

```xml
<dependency>
    <groupId>com.sun.jersey</groupId>
    <artifactId>jersey-json</artifactId>
    <version>${jersey.version}</version>
</dependency>
```

**2. List JSON in `@Produces`:**

```java
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
```

Now **one method serves both formats**. The client picks one with the `Accept` header:

| Request header | Response |
|---|---|
| `Accept: application/json` | JSON |
| `Accept: application/xml` | XML |
| `Accept: */*` or none | the **first** type in `@Produces`, which is JSON here |

The same header logic applies to input. Send `Content-Type: application/json` with a JSON body and the POST from section 10 works unchanged:

```json
{
    "id": 104,
    "name": "Kiran",
    "points": 60
}
```

> 💡 Tip: `jersey-json` reuses the **JAXB** annotations, so `@XmlRootElement` is still required even when you only use JSON.

> ⚠️ Warning: Jersey 1 JSON (JAXB "mapped" notation) may output numbers as strings (`"points":"100"`) and a single-element list as an object instead of an array. The Jackson provider (`JSONConfiguration.FEATURE_POJO_MAPPING`) avoids this.

---

## 13. MySQL Repository

Now `AlienRepository` stores aliens in MySQL instead of a list. This reuses the JDBC notes.

**Database setup:**

```sql
CREATE DATABASE restdb;
USE restdb;

CREATE TABLE alien (
    id     INT PRIMARY KEY,   -- column 1
    name   VARCHAR(50),       -- column 2
    points INT                -- column 3
);

INSERT INTO alien VALUES (101, 'Nilanchal', 100), (102, 'Neha', 99);
```

**Connection, opened in the constructor:**

```java
public class AlienRepository
{
    Connection con = null;

    public AlienRepository()
    {
        String url = "jdbc:mysql://localhost:3306/restdb?useSSL=false&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "your_password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");                 // load the MySQL 8 driver
            con = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver class not found! Check your pom.xml dependency.");
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.err.println("Database connection credentials failed!");
            e.printStackTrace();
        }
    }
```

**Read all — `Statement` + loop:**

```java
    public List<Alien> getAliens()
    {
        List<Alien> aliens = new ArrayList<>();
        String sql = "SELECT * FROM alien";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {                       // one Alien per row
                Alien a = new Alien();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setPoints(rs.getInt(3));
                aliens.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aliens;
    }
```

**Read one — `if` instead of `while`:**

```java
    public Alien getAlien(int id)
    {
        String sql = "SELECT * FROM alien WHERE id = " + id;
        Alien a = new Alien();                        // stays id 0 if no row found
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setPoints(rs.getInt(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
```

**Create — `PreparedStatement`:**

```java
    public void create(Alien a1)
    {
        String sql = "INSERT INTO alien VALUES (?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, a1.getId());
            st.setString(2, a1.getName());
            st.setInt(3, a1.getPoints());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

The resource class keeps its repository as a field:

```java
@Path("aliens")
public class AlienResources
{
    private AlienRepository repo = new AlienRepository();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Alien> getAliens()
    {
        System.out.println("getAliens list method called...");
        return repo.getAliens();
    }
    ...
}
```

Data now **survives** between requests and server restarts. ✅

> ⚠️ Warning: Jersey creates a new `AlienResources` for every request, so `new AlienRepository()` opens a **new DB connection per request**, and nothing ever closes it. Under load MySQL hits "Too many connections". The fix is to open and close the connection inside each method (try-with-resources) or to use a connection pool.

> ⚠️ Warning: `getAlien` builds SQL with `+ id`. It is safe here only because `id` is an `int`. With a `String` it would be open to **SQL injection**, so prefer `PreparedStatement` everywhere.

> 💡 Tip: `AlienRepository` lives in the same package as `AlienResources` (`com.nilanchal.demorest`), so no import is needed. Importing it from a package it isn't in (for example `com.nilanchal.AlienRepository`) won't compile.

> 💡 Tip: `ClassNotFoundException: com.mysql.cj.jdbc.Driver` means the connector isn't on the classpath. The `cj` driver class needs `mysql-connector-java` **8.x**; 5.x uses `com.mysql.jdbc.Driver`.

---

## 14. Update Resource Using PUT Method

**PUT** updates an existing resource. The client sends the **full, changed** alien in the body.

**Repository:**

```java
public void update(Alien a1)
{
    String sql = "UPDATE alien SET name = ?, points=? WHERE id=?;";
    try {
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, a1.getName());
        st.setInt(2, a1.getPoints());
        st.setInt(3, a1.getId());           // WHERE id = ?
        st.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

**Resource — update if it exists, otherwise create:**

```java
@PUT
@Path("alien")                                       // PUT /webresources/aliens/alien
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public Alien updateAlien(Alien a1)
{
    if (repo.getAlien(a1.getId()).getId() == 0)      // id 0 → no such row
    {
        repo.create(a1);                             // insert it
    }
    else
    {
        repo.update(a1);                             // change it
    }
    return a1;
}
```

Postman:

```
PUT http://localhost:8080/demorest/webresources/aliens/alien
Content-Type: application/json

{ "id": 101, "name": "Nilanchal", "points": 150 }
```

Then `GET /aliens/alien/101` shows `points` = `150`.

**POST vs PUT:**

| | POST | PUT |
|---|---|---|
| Purpose | Create a new resource | Replace or update a resource |
| Idempotent? | ❌ Sending twice tries to insert twice (duplicate key error) | ✅ Sending twice gives the same result |
| Our URI | `/aliens/alien` | `/aliens/alien` |

> 💡 Tip: "Update, or create if missing" is called an **upsert**. PUT allows it because the client supplies the id.

> ⚠️ Warning: The "not found" check relies on `id == 0`, so an alien that really has id `0` can never be updated. Returning `null` (and then a 404) is the cleaner signal.

---

## 15. Delete Resource Using DELETE Method

**DELETE** removes a resource. The id comes from the URI, and there is no body.

**Repository:**

```java
public void delete(int id)
{
    String sql = "DELETE FROM alien WHERE id=?;";
    try {
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, id);
        st.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

**Resource:**

```java
@DELETE
@Path("alien/{id}")                          // DELETE /webresources/aliens/alien/101
public Alien killAlien(@PathParam("id") int id)
{
    Alien a = repo.getAlien(id);             // fetch first, so we can return it

    if (a.getId() != 0)                      // only delete if it exists
    {
        repo.delete(id);
    }

    return a;                                // the deleted alien (or an empty one)
}
```

Postman:

```
DELETE http://localhost:8080/demorest/webresources/aliens/alien/101
```

The response is the deleted alien. A following `GET /aliens/alien/101` returns `id` = `0`.

> 💡 Tip: `killAlien` has no `@Produces`, so Jersey picks a format from the client's `Accept` header using the JAXB writers. Add `@Produces` to make the output type explicit.

> 💡 Tip: Same URI, different method. `GET /alien/101` and `DELETE /alien/101` share a path, and the **HTTP verb** decides which Java method runs.

---

# Part 3 — Wrap-Up

## 16. Full Request Flow

Example: `PUT /demorest/webresources/aliens/alien` with a JSON body.

1. Postman sends the request to Tomcat on port `8080`.
2. Tomcat sees context `/demorest` and `url-pattern` `/webresources/*`, then hands the request to Jersey's `ServletContainer`.
3. Jersey matches the remaining path `aliens/alien` to `@Path("aliens")` + `@Path("alien")`, and the verb to `@PUT`. That selects `updateAlien`.
4. Jersey creates a **new** `AlienResources`, which creates an `AlienRepository`, which opens a MySQL connection.
5. `Content-Type: application/json` → `jersey-json` reads the body into an `Alien` using its setters.
6. `updateAlien` calls `repo.getAlien(id)`, then `repo.update(a1)` or `repo.create(a1)`, which runs the JDBC calls against `restdb.alien`.
7. The method returns `a1`. Jersey reads `Accept`, picks JSON or XML from `@Produces`, and writes the response using the getters.
8. Postman shows `200 OK` and the alien.

```
Postman ─▶ Tomcat ─▶ Jersey ServletContainer ─▶ AlienResources ─▶ AlienRepository ─▶ MySQL
   ▲                        │ (JSON ⇄ Alien)                                            │
   └────────────────────────┴───────────────────── response ◀──────────────────────────┘
```

---

## 17. Cheat Sheet

```java
@Path("aliens")                         // class: base URI
public class AlienResources {

    @GET                                // read
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Alien> getAliens() { ... }

    @GET @Path("alien/{id}")            // read one
    public Alien getAlien(@PathParam("id") int id) { ... }

    @POST @Path("alien")                // create — body → Alien
    @Consumes(MediaType.APPLICATION_JSON)
    public Alien createAlien(Alien a) { ... }

    @PUT @Path("alien")                 // update
    public Alien updateAlien(Alien a) { ... }

    @DELETE @Path("alien/{id}")         // delete
    public Alien killAlien(@PathParam("id") int id) { ... }
}

@XmlRootElement                         // model: needed for XML *and* JSON (Jersey 1)
public class Alien { /* private fields + getters/setters */ }
```

| Annotation | Job |
|---|---|
| `@Path` | Maps a class or method to a URI |
| `@GET` `@POST` `@PUT` `@DELETE` | Which HTTP verb runs the method |
| `@Produces` | Response type, matched to the `Accept` header |
| `@Consumes` | Request body type, matched to the `Content-Type` header |
| `@PathParam` | Reads a `{variable}` from the URI |
| `@XmlRootElement` | Makes a class convertible to and from XML/JSON |

**URL formula:** `http://localhost:8080/<context>/<url-pattern>/<class @Path>/<method @Path>`
→ `http://localhost:8080/demorest/webresources/aliens/alien/101`

**Setup checklist:** Jersey servlet in `web.xml` · resource class in the scanned package · `jersey-json` for JSON · `@XmlRootElement` + getters/setters · `mysql-connector-java` 8.x for `com.mysql.cj.jdbc.Driver`

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created from Telusko Java Tutorial — 07. REST API and Web Services/*
