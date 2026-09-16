# 📘 Servlets & JSP — Notes

**One running example lives through these notes:** a simple sign-up form (name, city, and later email and password) that grows project by project, from a plain HTML page to a full MVC app that saves users into MySQL.

We start with *how the web works* (client, server, request, response), then see why static pages are not enough, then learn Servlets, then read every project in the order it was written, and finally rebuild the registration app using **MVC**.

All projects run on **Apache Tomcat 10.1**, so every import uses `jakarta.servlet.*` (older tutorials use `javax.servlet.*`, which is the same API before it was renamed).

---

## 📑 Table of Contents

**Part 1 — Fundamentals**
1. [Client-Server Architecture](#1-client-server-architecture)
2. [HTTP Request and Response](#2-http-request-and-response)
3. [Web Server vs Web Container](#3-web-server-vs-web-container)
4. [Static Response vs Dynamic Response](#4-static-response-vs-dynamic-response)

**Part 2 — Servlets**
5. [What Is a Servlet?](#5-what-is-a-servlet)
6. [The Servlet Hierarchy](#6-the-servlet-hierarchy)
7. [Mapping a URL to a Servlet](#7-mapping-a-url-to-a-servlet)
8. [Servlet Life Cycle](#8-servlet-life-cycle)
9. [GET vs POST](#9-get-vs-post)
10. [sendRedirect vs RequestDispatcher](#10-sendredirect-vs-requestdispatcher)
11. [HttpSession](#11-httpsession)
12. [What Is JSP?](#12-what-is-jsp)

**Part 3 — The Code, Project by Project**
13. [FirstWebApp — A Static Page](#13-firstwebapp--a-static-page)
14. [SecondWebApp — First Dynamic Response](#14-secondwebapp--first-dynamic-response)
15. [ServletLifeCycle — Watching the Container Work](#15-servletlifecycle--watching-the-container-work)
16. [ServletGetPost — Reading Form Data and Deciding](#16-servletgetpost--reading-form-data-and-deciding)
17. [FourthServletApp — Redirecting to a JSP](#17-fourthservletapp--redirecting-to-a-jsp)
18. [RequestDispatchingApp — Servlet to Servlet](#18-requestdispatchingapp--servlet-to-servlet)
19. [RegistrationApp — Servlet + JDBC](#19-registrationapp--servlet--jdbc)
20. [Servletjsp1 — Same Page, Servlet vs JSP](#20-servletjsp1--same-page-servlet-vs-jsp)

**Part 4 — MVC**
21. [The Problem With RegistrationApp](#21-the-problem-with-registrationapp)
22. [What Is MVC?](#22-what-is-mvc)
23. [RegistrationAppMVC — Architecture](#23-registrationappmvc--architecture)
24. [RegistrationAppMVC — File by File](#24-registrationappmvc--file-by-file)
25. [The Full Request Flow](#25-the-full-request-flow)

**Part 5 — Wrap-Up**
26. [Cheat Sheet](#26-cheat-sheet)

---

# Part 1 — Fundamentals

## 1. Client-Server Architecture

Two programs, two roles.

- The **client** *asks* for something. Usually a web browser (Chrome, Edge).
- The **server** *answers*. A program running on some machine, waiting for requests (Tomcat, in our case).

They talk over a network using a common language: **HTTP**.

```
 ┌──────────┐      HTTP Request       ┌──────────────┐
 │  Client  │ ──────────────────────▶ │    Server    │
 │ (Browser)│                         │   (Tomcat)   │
 │          │ ◀────────────────────── │              │
 └──────────┘      HTTP Response      └──────────────┘
```

Key points:

- The client always **starts** the conversation. The server never calls the browser by itself.
- One server handles **many** clients at the same time.
- HTTP is **stateless**: the server forgets you after each response. (That is why we will need `HttpSession` later.)
- A client finds a resource with a **URL**:

```
http://localhost:8080/SecondWebApp/firstServlet
└─┬─┘  └───┬───┘ └┬─┘ └────┬─────┘ └─────┬─────┘
protocol  host   port  context path   resource path
                       (the project)  (page / servlet)
```

In most setups there is also a **database server** behind the web server, making it a 3-tier architecture:

```
Browser  ──▶  Tomcat (Servlets / JSP)  ──▶  MySQL
(client)      (server + business logic)     (data)
```

`RegistrationApp` and `RegistrationAppMVC` follow exactly this shape.

---

## 2. HTTP Request and Response

**Request** (browser → server) contains:

| Part | Example |
|------|---------|
| Method | `GET`, `POST` |
| URL | `/RegistrationAppMVC/Register` |
| Headers | `Content-Type`, `Cookie`, `User-Agent` |
| Body (POST only) | `uname=Rohan&ucity=Pune` |

**Response** (server → browser) contains:

| Part | Example |
|------|---------|
| Status code | `200 OK`, `302 Found`, `404 Not Found`, `500 Internal Server Error` |
| Headers | `Content-Type: text/html`, `Location`, `Set-Cookie` |
| Body | the HTML the browser draws |

In a servlet, these become two Java objects handed to you by Tomcat:
`HttpServletRequest` and `HttpServletResponse`.

---

## 3. Web Server vs Web Container

- A **web server** can serve files (HTML, CSS, images) that already exist on disk.
- A **web container** (a.k.a. servlet container) can also **run Java code** (Servlets and JSP) to build a response on the fly.

Tomcat is both. The container part (called *Catalina*) is responsible for:

- Loading servlet classes and creating their objects
- Calling life-cycle methods (`init`, `service`, `destroy`)
- Creating `request` and `response` objects for every hit
- Mapping URLs to servlets
- Managing sessions
- Converting JSP files into servlets

You **never** write `new MyServlet()` or call `service()` yourself. The container does it.

---

## 4. Static Response vs Dynamic Response

### Static response

The file is already written. The server just picks it up and sends it back **as it is**.

- Same output for every user, every time.
- Example: `FirstWebApp/WebContent/index.html`.
- Only needs a web server.

### Dynamic response

The response **does not exist** until a request arrives. Java code builds it using input from the request, the database, the time, etc.

- Output changes per user / per request.
- Example: `SecondWebApp` prints the name and city *you* typed.
- Needs a web container (Servlet / JSP).

| | Static | Dynamic |
|---|---|---|
| Content | Fixed, pre-written | Generated at request time |
| Technology | HTML, CSS, JS files | Servlet, JSP |
| Depends on user input | No | Yes |
| Handled by | Web server | Web container |
| Example here | `FirstWebApp` | `SecondWebApp` onwards |

---

# Part 2 — Servlets

## 5. What Is a Servlet?

A **Servlet** is a Java class that runs **inside a web container** and produces a dynamic response for an HTTP request.

Think of it as: *"a Java class whose methods are called by Tomcat whenever a certain URL is hit."*

Typical job of a servlet:

1. Read input → `request.getParameter("uname")`
2. Process it → business logic, DB call
3. Send output → write HTML with `response.getWriter()`, or send the user to another page

---

## 6. The Servlet Hierarchy

```
Servlet (interface)            init(), service(), destroy(), getServletConfig(), getServletInfo()
   │
GenericServlet (abstract)      protocol-independent, only service() left abstract
   │
HttpServlet (abstract)         HTTP-specific: doGet(), doPost(), doPut(), doDelete() ...
   │
Your servlet                   extends HttpServlet, overrides what it needs
```

`HttpServlet.service()` looks at the HTTP method and calls the matching `doXxx()`:

- `GET` → `doGet()`
- `POST` → `doPost()`

So you have two choices:

- Override `doGet()` / `doPost()` → handles only that method (`SecondWebApp`, `FourthServletApp`).
- Override `service()` → handles **every** method the same way (`RequestDispatchingApp`, `RegistrationApp`, `RegistrationAppMVC`).

If you override only `doPost()` and someone opens the URL directly in the browser (a GET), they get **405 Method Not Allowed**.

---

## 7. Mapping a URL to a Servlet

Tomcat must know *which URL* runs *which servlet*. Two ways:

**1. Annotation (used in every project here)**

```java
@WebServlet("/Register")
public class Register extends HttpServlet { ... }
```

**2. `web.xml` (older way, deployment descriptor)**

```xml
<servlet>
    <servlet-name>reg</servlet-name>
    <servlet-class>Register</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>reg</servlet-name>
    <url-pattern>/Register</url-pattern>
</servlet-mapping>
```

The HTML form connects to the servlet through its `action`:

```html
<form method="post" action="./Register">
```

`action` must match the `@WebServlet` path **exactly** (case-sensitive), otherwise → **404**.

---

## 8. Servlet Life Cycle

The container controls the whole life of a servlet.

```
 1. Loading        class is loaded           → static block runs          (once)
 2. Instantiation  object is created         → constructor runs           (once)
 3. Initialization init(ServletConfig)       → setup work                 (once)
 4. Service        service(req, res)         → handles a request          (every request)
 5. Destruction    destroy()                 → cleanup before shutdown    (once)
```

Important facts:

- By default the servlet is loaded **lazily**, on the **first request**. (`@WebServlet(value="/x", loadOnStartup=1)` loads it when Tomcat starts.)
- Only **one object** of each servlet exists. Every request runs `service()` on that same object, each in its own **thread**.
- So **instance variables are shared** between all users → avoid storing per-user data in fields.

---

## 9. GET vs POST

| | GET | POST |
|---|---|---|
| Data goes in | URL (`?uname=Rohan&ucity=Pune`) | Request body |
| Visible in address bar | Yes | No |
| Size limit | Yes (URL length) | Practically none |
| Bookmarkable | Yes | No |
| Use for | Fetching / searching | Sending / saving data (sign-up, login) |
| Servlet method | `doGet()` | `doPost()` |
| Default for `<form>` | Yes | Must write `method="post"` |

All forms in these projects use `method="post"` because they submit user data (and passwords).

> POST hides data from the URL, but it is **not encryption**. Only HTTPS protects it on the network.

---

## 10. sendRedirect vs RequestDispatcher

Once a servlet finishes its work, it often wants *another* resource to produce the page.

### `response.sendRedirect(url)` — used in FourthServletApp and RegistrationAppMVC

```
Browser ──req 1──▶ Servlet
Browser ◀─302 + Location: success.jsp── Servlet
Browser ──req 2──▶ success.jsp
Browser ◀────────── HTML
```

- **Two** requests. Browser URL changes.
- The first `request` object is gone → request attributes are lost (use the **session** instead).
- Can point to any URL, even another website.

### `RequestDispatcher` — used in RequestDispatchingApp

```java
RequestDispatcher rd = request.getRequestDispatcher("/SecondServlet");
rd.forward(request, response);   // or rd.include(request, response);
```

- Happens **inside the server**. **One** request. Browser URL does not change.
- The same `request` and `response` objects are passed along.
- Only works for resources inside the same application.

| `forward()` | `include()` |
|---|---|
| Control goes to the second resource; only **its** output is sent | Second resource's output is **inserted** into the first one's output |
| Anything the first servlet writes after forwarding is discarded | First servlet can keep writing before and after |

| | sendRedirect | forward |
|---|---|---|
| Who does it | Browser | Server |
| Number of requests | 2 | 1 |
| URL in address bar | Changes | Same |
| Request data shared | No | Yes |
| Speed | Slower | Faster |

---

## 11. HttpSession

HTTP is stateless, so how does `success.jsp` know your name after a redirect?

A **session** is a per-user storage box kept on the server. Tomcat identifies the user by a `JSESSIONID` cookie.

```java
HttpSession session = request.getSession();        // get existing or create new
session.setAttribute("name", uname);               // store

HttpSession session = request.getSession(false);   // get existing, else null
String name = (String) session.getAttribute("name"); // read (cast needed, returns Object)

session.setMaxInactiveInterval(600);               // expire after 600 s of inactivity
session.invalidate();                              // logout
```

In JSP, `session` is already available as an implicit object.

---

## 12. What Is JSP?

Writing HTML inside Java like this is painful:

```java
writer.println("<tr><td> " + name + "</td> <td> " + ucity + "</td> </tr>");
```

**JSP (Jakarta Server Pages)** flips it: you write **HTML** and put small pieces of **Java** inside.

Behind the scenes, Tomcat **translates the JSP into a servlet** (`success.jsp` → `success_jsp.java`), compiles it, and runs it. You can actually see those generated files in `.metadata/.../work/Catalina/localhost/`.

So a JSP *is* a servlet — just easier to write for the view part.

### JSP tags

| Tag | Name | Purpose | Goes into generated servlet as |
|---|---|---|---|
| `<%@ ... %>` | Directive | Page settings, imports | Class-level settings / imports |
| `<%! ... %>` | Declaration | Declare fields / methods | Instance members of the class |
| `<% ... %>` | Scriptlet | Any Java statements | Inside `_jspService()` |
| `<%= ... %>` | Expression | Print a value | `out.print(...)` |

### Implicit objects (ready to use, no declaration)

`request`, `response`, `session`, `out`, `application`, `config`, `pageContext`, `page`, `exception` (error pages only).

---

# Part 3 — The Code, Project by Project

## 13. FirstWebApp — A Static Page

**Files:** `WebContent/index.html`

Just an HTML page with a scrolling (`<marquee>`) heading on a cyan background.

No Java at all. Tomcat finds `index.html`, reads it, sends it. Every visitor sees exactly the same thing.

**Lesson:** this is a **static response**.

---

## 14. SecondWebApp — First Dynamic Response

**Files:** `WebApp/index.html`, `FirstServletApp.java`

**index.html** — a form with two fields, `uname` and `ucity`:

```html
<form method="post" action="./firstServlet">
```

**FirstServletApp.java**

```java
@WebServlet("/firstServlet")
public class FirstServletApp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) ... {
        String name  = request.getParameter("uname");   // read form field by its "name"
        String ucity = request.getParameter("ucity");

        PrintWriter writer = response.getWriter();      // stream back to the browser
        writer.println("<html> ... <table>");
        writer.println("<tr><td> " + name + "</td> <td> " + ucity + "</td> </tr>");
        writer.println("</table></body></html>");
        writer.close();
    }
}
```

Flow:

1. User submits form → POST to `/firstServlet`.
2. Tomcat calls `doPost()`.
3. `getParameter()` reads the values (names must match the `name` attribute of the `<input>`).
4. HTML is **built by Java** and written to the response.

**Lesson:** the page is generated per user → **dynamic response**. Also shows why writing HTML in `println` gets messy (the reason JSP exists).

---

## 15. ServletLifeCycle — Watching the Container Work

**Files:** `ServletLife.java`

```java
@WebServlet("")                     // "" = mapped to the app's root URL
public class ServletLife extends HttpServlet {
    static { System.out.println("Servlet is loaded..."); }          // 1. loading
    public ServletLife() { System.out.println("Servlet object is created"); } // 2. instantiation
    public void init(ServletConfig config) {                          // 3. init
        super.init(config);                                           //    keeps getServletConfig() working
        System.out.println("Servlet initialized!");
    }
    protected void service(...) { System.out.println("Service method to handle http request..."); } // 4. service
    public void destroy() { System.out.println("Servlet destroyed!"); } // 5. destroy
}
```

What you see in the Tomcat console:

- **First** request → all four messages, in order.
- **Refresh** → only the `service` message again (object already exists).
- **Stop server** → "Servlet destroyed!".

Notes:

- `service()` writes nothing to the response, so the browser shows a blank page — all output goes to the console.
- When overriding `init(ServletConfig)`, always call `super.init(config)` first; otherwise `getServletConfig()` returns `null`. (Or override the no-arg `init()`, which needs no `super` call.)

**Lesson:** static block, constructor, and `init` run **once**; `service` runs **per request**.

---

## 16. ServletGetPost — Reading Form Data and Deciding

**Files:** `WebContent/index.html`, `WebContent/registerSuccess.html`, `ServletApp.java`

```java
@WebServlet("/ServletApp")
public class ServletApp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) ... {
        String name  = request.getParameter("uname");
        String ucity = request.getParameter("ucity");

        if ("Rohan".equals(name) && "Pune".equals(ucity))
            System.out.println("Success! He is the right rohan logged in");
        else
            System.out.println("Diff Rohan logged in");
    }
}
```

The servlet reads the form data and runs a **condition** on it — the first taste of business logic / login-style checks. `registerSuccess.html` is a static "Sign Up Success" page meant to be shown afterwards.

Things to remember (these were bugs in this project, now fixed):

- The form `action` must match the `@WebServlet` path exactly. The form used to post to `./Servlet` while the servlet was at `/ServletApp` → **404**.
- `"Rohan".equals(name)` is null-safe; `name.equals("Rohan")` throws `NullPointerException` if the field is missing.

**Lesson:** `doPost()` handles POST only; input can drive decisions.

---

## 17. FourthServletApp — Redirecting to a JSP

**Files:** `WebContent/index.html`, `WebContent/success.jsp`, `RegisterServletApp.java`

```java
@WebServlet("/Register")
public class RegisterServletApp extends HttpServlet {
    public RegisterServletApp() {
        System.out.println("Servlet obj is created interally by container");
    }
    protected void doPost(...) {
        String name  = request.getParameter("uname");
        String ucity = request.getParameter("ucity");
        System.out.println("Name " + name + " City " + ucity);

        response.sendRedirect("/FourthServletApp/success.jsp");
    }
}
```

- The servlet no longer writes HTML itself (that code is commented out).
- It does the processing, then tells the browser: *"go to `success.jsp`"* (HTTP 302).
- `success.jsp` shows "Sign Up Success".
- The path starts with the **context path** `/FourthServletApp` because `sendRedirect` is handled by the browser, which needs the full path. (`request.getContextPath() + "/success.jsp"` avoids hard-coding it.)

**Lesson:** separate **processing** (servlet) from **display** (JSP) — the first step towards MVC.

---

## 18. RequestDispatchingApp — Servlet to Servlet

**Files:** `index.html`, `FirstServlet.java`, `SecondServlet.java`

**FirstServlet**

```java
String name = request.getParameter("uname");
String city = request.getParameter("ucity");

RequestDispatcher reqDispatch = request.getRequestDispatcher("/SecondServlet");

HttpSession session = request.getSession();
session.setAttribute("name", name);
session.setAttribute("city", city);

reqDispatch.forward(request, response);     // hand over control
// reqDispatch.include(request, response);
```

**SecondServlet**

```java
HttpSession session = request.getSession(false);        // use the existing session
String name = (String) session.getAttribute("name");
writer.println("<h1>Response from Second Servlet </h1><h1>" + name + "</h1>");
```

What happens:

1. Form posts to `/FirstServlet`.
2. `FirstServlet` stores name and city in the **session**.
3. `forward()` passes the same request to `SecondServlet` — the browser still shows `/FirstServlet` in the address bar.
4. `SecondServlet` reads the name from the session and prints it.
5. `FirstServlet` writes nothing after `forward()`. Once `forward()` returns, the response is already committed and closed, so any output written afterwards is silently lost. (An earlier version printed "Response from Servlet One" there, and it never appeared.)

To show output from **both** servlets, write in `FirstServlet` and call `include()` instead of `forward()`.

Note: since `forward()` shares the request, `request.setAttribute(...)` would also work here. The session is needed when the data must survive a **redirect** or later requests.

**Lesson:** `RequestDispatcher` (`forward` / `include`) and `HttpSession`.

---

## 19. RegistrationApp — Servlet + JDBC

**Files:** `WebContent/index.html`, `Register.java`

The form now has 4 fields: `uname`, `email`, `upassword`, `ucity`.

The servlet does **everything** in one `service()` method:

```java
// 1. read input
String uname = request.getParameter("uname");  ... // email, upassword, ucity

// 2. JDBC
Class.forName("com.mysql.cj.jdbc.Driver");
try (Connection connect = DriverManager.getConnection(url, user, password);
     PreparedStatement pstmnt = connect.prepareStatement(
         "INSERT INTO personalinfo (uname, email, upassword, ucity) VALUES (?, ?, ?, ?)")) {
    pstmnt.setString(1, uname); ...
    int RowAffected = pstmnt.executeUpdate();

    // 3. output
    if (RowAffected != 0) writer.println("<h1>Registration Success! </h1>");
    else                  writer.println("<h1>Registration Failed </h1>");
}   // pstmnt and connect are closed here automatically, even on exception
```

Requirements:

- MySQL database `javadevelopment` with table `personalinfo (uname, email, upassword, ucity)`.
- MySQL Connector/J jar in `WEB-INF/lib` (Tomcat needs it at runtime).

Notes:

- `PreparedStatement` with `?` protects against SQL injection (see JDBC notes).
- **try-with-resources** closes the statement and connection in reverse order, even if an exception is thrown. Calling `close()` at the end of a plain `try` skips it on exceptions → connection leak (this project had that bug before).
- Password is saved as plain text and DB credentials are hard-coded — fine for learning, not for real apps.

**Lesson:** a working 3-tier app (browser → servlet → MySQL). But look at how much one class is doing… see Part 4.

---

## 20. Servletjsp1 — Same Page, Servlet vs JSP

**Files:** `WebContent/index.html`, `WebContent/jspApplication.jsp`, `ServletApp.java`

The form posts **directly to a JSP**:

```html
<form method="post" action="jspApplication.jsp">
```

**jspApplication.jsp**

```jsp
<%@ page import="java.util.Date" %>          <%-- directive: import --%>

<%! int age = 18; %>                          <%-- declaration: becomes a field --%>

<%
    String name  = request.getParameter("uname");   // scriptlet: normal Java
    String ucity = request.getParameter("ucity");
    Date date = new Date();
    out.println("Hello " + name);                  // implicit 'out'
%>

<h1><%= date %></h1>                          <%-- expression: prints value --%>
<h2><%= ucity %></h2>
```

**ServletApp.java** does the same job the "servlet way" (`int age` as a field, `Date`, `println` of HTML). It is mapped to `/ServletApp` but the form doesn't use it — it is there for **comparison**.

| Servlet | JSP |
|---|---|
| Java with HTML inside strings | HTML with Java inside tags |
| Must write `getWriter()`, `close()` | `out`, `request` already available |
| Needs compile + redeploy after change | Tomcat re-translates automatically |
| Good for logic (controller) | Good for display (view) |

**Lesson:** JSP tags, implicit objects, and that a JSP is just a servlet written the other way around.

---

# Part 4 — MVC

## 21. The Problem With RegistrationApp

`Register.java` in `RegistrationApp` does three different jobs:

1. Handles the HTTP request (reads parameters)
2. Talks to the database
3. Builds the HTML response

Problems this causes:

- **Hard to change**: a designer who wants to change the success page must edit Java code.
- **Hard to reuse**: the insert logic can't be used from anywhere else.
- **Hard to test**: DB code is tied to the servlet.
- **Repetition**: every servlet would repeat driver loading and connection code.

The fix: split the jobs into separate parts.

---

## 22. What Is MVC?

**MVC = Model – View – Controller**, a design pattern that separates an app into three layers.

| Layer | Responsibility | In a Java web app |
|---|---|---|
| **Model** | Data + business logic + DB access | Plain Java classes (POJO / DAO) |
| **View** | What the user sees | JSP (or HTML) |
| **Controller** | Receives request, calls Model, picks View | Servlet |

```
        ┌──────────────────────────────┐
        │          Controller          │
 req ──▶│           (Servlet)          │
        └───────┬───────────────┬──────┘
         calls  │               │ chooses
                ▼               ▼
        ┌──────────────┐  ┌──────────────┐
        │    Model     │  │     View     │──▶ response
        │ (Java + DB)  │  │    (JSP)     │
        └──────────────┘  └──────────────┘
```

Rules of thumb:

- The **View** never talks to the database.
- The **Model** knows nothing about HTTP, requests, or HTML.
- The **Controller** contains no SQL and no HTML — it just coordinates.

Benefits: clean separation, easy maintenance, reusable model, parallel work (UI dev on JSP, backend dev on Java).

---

## 23. RegistrationAppMVC — Architecture

Same registration feature as `RegistrationApp`, rebuilt with MVC.

```
RegistrationAppMVC
├── src/main/java/main/java/
│   ├── Register.java      ← CONTROLLER  (servlet)
│   ├── Model.java         ← MODEL       (data + register())
│   └── JdbcUtil.java      ← MODEL helper (DB connection)
└── src/main/webapp/
    ├── index.html         ← VIEW (input form)
    ├── success.jsp        ← VIEW (row inserted)
    └── failure.jsp        ← VIEW (row not inserted)
```

Architecture diagram:

```
┌─────────────┐  POST /Register   ┌───────────────────────┐
│ index.html  │ ────────────────▶ │ Register (Controller) │
│  (View)     │                   │  - read parameters    │
└─────────────┘                   │  - fill Model         │
                                  │  - call register()    │
                                  │  - store name in      │
                                  │    session            │
                                  │  - redirect           │
                                  └───┬──────────────┬────┘
                         setters +    │              │ sendRedirect
                         register()   ▼              ▼
                     ┌──────────────────┐   ┌─────────────────────┐
                     │  Model           │   │ success.jsp  (row≠0)│
                     │  - fields        │   │ failure.jsp  (row=0)│
                     │  - register()    │   │   (Views, read name │
                     └────────┬─────────┘   │    from session)    │
                              │             └─────────────────────┘
                              ▼
                     ┌──────────────────┐        ┌────────────────┐
                     │ JdbcUtil         │ ─────▶ │ MySQL          │
                     │ - load driver    │  JDBC  │ javadevelopment│
                     │ - getDBConnection│        │ .personalinfo  │
                     │ - closeResource  │        └────────────────┘
                     └──────────────────┘
```

---

## 24. RegistrationAppMVC — File by File

### View — `index.html`

Form with `uname`, `email`, `upassword`, `ucity`, posting to `./Register`. Identical to `RegistrationApp`.

### Controller — `Register.java`

```java
@WebServlet("/Register")
public class Register extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) ... {
        // 1. read input
        String uname = request.getParameter("uname");  ... // email, upassword, ucity

        // 2. hand data to the Model
        Model model = new Model();
        model.setUname(uname);
        model.setEmail(email);
        model.setUpassword(upassword);
        model.setUcity(ucity);

        // 3. ask the Model to do the work
        int row = model.register();

        // 4. keep the name for the View (survives the redirect)
        HttpSession session = request.getSession();
        session.setAttribute("name", uname);

        // 5. pick the View
        if (row == 0) response.sendRedirect("/RegistrationAppMVC/failure.jsp");
        else          response.sendRedirect("/RegistrationAppMVC/success.jsp");
    }
}
```

No SQL. No HTML. Just coordination.

The name goes into the **session** (not the request) because `sendRedirect` creates a new request.

### Model — `Model.java`

- Private fields `uname`, `upassword`, `email`, `ucity` with **getters/setters** (JavaBean style).
- `register()` holds the business logic:

```java
connect = JdbcUtil.getDBConnection();
pstmnt  = connect.prepareStatement(
    "INSERT INTO personalinfo (uname, email, upassword, ucity) VALUES(?, ?, ?, ?)");
pstmnt.setString(1, uname); ... 
row = pstmnt.executeUpdate();        // 1 if inserted, 0 otherwise
...
finally { JdbcUtil.closeResource(connect, pstmnt); }   // always closes
return row;
```

It has no idea it's being used by a servlet — it could be called from a `main()` method just as well.

### Model helper — `JdbcUtil.java`

```java
static { Class.forName("com.mysql.cj.jdbc.Driver"); }   // driver loaded once, when class loads

public static Connection getDBConnection() { return DriverManager.getConnection(url, user, pass); }

public static void closeResource(Connection connect, Statement stmt) { ... close both if not null ... }
```

Removes repeated connection code — the same idea as `jdbcUtil` in the JDBC notes.

(Small improvement: close the `Statement` **before** the `Connection`.)

### Views — `success.jsp` / `failure.jsp`

```jsp
<% String name = (String) session.getAttribute("name"); %>
<h2>Hey <%= name %>, you have registered to this web app</h2>
```

They only **display**. They read the name the controller put in the session using the implicit `session` object.

---

## 25. The Full Request Flow

1. Browser opens `http://localhost:8080/RegistrationAppMVC/` → Tomcat serves `index.html`.
2. User fills the form and clicks **SignUp** → `POST /RegistrationAppMVC/Register`.
3. Tomcat finds `@WebServlet("/Register")` → creates `Register` (first time only) → calls `service()`.
4. Controller reads the 4 parameters and loads them into a new `Model`.
5. Controller calls `model.register()`.
6. Model gets a connection from `JdbcUtil`, runs the `INSERT`, closes resources, returns rows affected.
7. Controller saves `uname` in the `HttpSession`.
8. Controller sends **302 redirect** to `success.jsp` (row ≠ 0) or `failure.jsp` (row = 0).
9. Browser makes a **new GET** request for that JSP.
10. JSP reads `name` from the session and renders the greeting.

Comparison:

| | RegistrationApp | RegistrationAppMVC |
|---|---|---|
| Classes | 1 servlet does everything | Controller + Model + Util |
| HTML output | `println` in servlet | JSP views |
| DB code | Inside servlet | Inside Model |
| Resource closing | try-with-resources | `finally` block via `JdbcUtil` |
| Reusability | Low | Model reusable anywhere |
| Maintenance | Hard | Easy |

---

# Part 5 — Wrap-Up

## 26. Cheat Sheet

```java
// Map a URL
@WebServlet("/path")
public class X extends HttpServlet { }

// Handle requests
protected void doGet(HttpServletRequest req, HttpServletResponse res)
protected void doPost(HttpServletRequest req, HttpServletResponse res)
protected void service(HttpServletRequest req, HttpServletResponse res)   // all methods

// Input
String v = req.getParameter("fieldName");

// Output
PrintWriter out = res.getWriter();
out.println("<h1>Hi</h1>");
res.setContentType("text/html");

// Navigation
res.sendRedirect(req.getContextPath() + "/page.jsp");        // client-side, 2 requests
req.getRequestDispatcher("/other").forward(req, res);         // server-side, 1 request
req.getRequestDispatcher("/other").include(req, res);

// Request scope (only same request / forward)
req.setAttribute("k", v);   req.getAttribute("k");

// Session scope (survives redirects)
HttpSession s = req.getSession();        s.setAttribute("k", v);
HttpSession s = req.getSession(false);   s.getAttribute("k");
s.invalidate();
```

```jsp
<%@ page import="java.util.Date" %>   directive
<%! int x = 0; %>                     declaration
<% String n = request.getParameter("n"); %>   scriptlet
<%= n %>                              expression
```

**Life cycle:** load → instantiate → `init()` (once) → `service()` (per request) → `destroy()` (once)

**MVC:** Controller = Servlet · Model = Java class + JDBC · View = JSP
