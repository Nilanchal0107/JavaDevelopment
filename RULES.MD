# 📋 Rules for Generating Java Notes (README.md + practice.md)

> These rules must be followed every time notes are generated for a new Java topic folder.
> The AI must scan all `.java` files in the folder before generating any output.

---

## 🔍 Step 1 — Scan the Code First

Before writing anything:
- Read **every `.java` file** in the folder
- Identify all concepts demonstrated across all files
- Note the order in which concepts are introduced (respect Telusko's teaching sequence)
- Identify any edge cases, tricky behaviors, or non-obvious outputs in the code

Do NOT generate notes from assumptions or general Java knowledge alone.
Every concept in the README must be traceable to something present in the scanned code.

---

## 📘 Step 2 — Generate README.md

### File name: `README.md`

### Required Sections (in this order):

---

### 1. Title & Topic Header
```
# 📘 <Topic Name> — Notes
> Based on the Telusko YouTube channel tutorial by Navin Reddy
```

---

### 2. Table of Contents
- Numbered list of all sections covered
- Each entry must be an anchor link to its section
- Must reflect exactly what is in the code — no extra topics, no skipped topics

---

### 3. One Section Per Concept
Each concept gets its own numbered section. Every section must contain:

#### a) Concept Title (H2)
```
## 1. Concept Name
```

#### b) One-line plain English definition
What is this? Explain it like the reader knows programming but is new to Java.

#### c) Syntax block (where applicable)
````
```java
// syntax here
```
````

#### d) Code example taken directly from the scanned `.java` files
- Use the actual code from the repo, not invented examples
- Add inline comments explaining what each line does
- If the original code lacks comments, add them yourself

#### e) Key insight or warning (where applicable)
Use this format:
```
> 💡 Tip: ...
> ⚠️ Warning: ...
```

Only include a tip/warning if there is a genuine Java-specific behavior worth flagging
(type promotion, fall-through, overflow, sign rules, etc.).
Do NOT add generic filler tips like "practice makes perfect."

---

### 4. Comparison Table (only if the topic has multiple variants)
Examples: primitive types, loop types, access modifiers, constructor types.
Use a markdown table with columns relevant to the concept being compared.
Do NOT create a table just for the sake of having one.

---

### 5. ⚠️ My Mistakes & Gaps (LEAVE BLANK — Manual Section)
```
## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 
```

This section must always be present but always left empty with a single dash.
The student fills this in by hand after doing the practice questions.

---

### 6. Footer
```
*Notes created from Telusko Java Tutorial — <folder path>*
```

---

## 🧪 Step 3 — Generate practice.md

### File name: `practice.md`

### Required Header:
```
# 🧪 Practice Questions — <Topic Name>

> Topics covered: <comma-separated list of all concepts in this folder>
```

---

### Question Distribution (always exactly 10 questions):

| Difficulty | Count | Label |
|------------|-------|-------|
| Easy       | 5     | 🟢    |
| Medium     | 3     | 🟡    |
| Hard       | 2     | 🔴    |

---

### Rules for Writing Questions:

#### Easy Questions (🟢)
- Test one concept at a time
- Hardcode all input values — no Scanner required
- Expected output must be provided exactly
- Must be solvable without looking at the notes

#### Medium Questions (🟡)
- Combine 2–3 concepts from the topic
- May include edge cases (e.g., boundary values, type overflow)
- Expected output must be provided
- At least one question must involve predicting output before running

#### Hard Questions (🔴)
- Must require reasoning, not just syntax recall
- At least one must involve a tricky Java-specific behavior
  (sign of modulo, integer division, fall-through, type promotion, etc.)
- At least one must be a "predict the output" question with explanation required in comments
- No expected output provided for hard questions — student must figure it out

---

### Format for Each Question:

```
### Q<number>. <Short Title>
<Problem statement — clear and specific>

**Constraints:**
- <any restrictions, e.g., "no Math.max allowed", "use only while loop">

**Expected Output:** (omit for hard questions)
\```
<exact expected output>
\```

**Concepts:** <comma-separated list of concepts being tested>
```

---

### Additional Rules for practice.md:

- Do NOT repeat questions that test identical logic
  (e.g., two separate "print even numbers" questions is not allowed)
- Each question must test something different from the others
- At least one hard question must involve a concept where Java behavior
  differs from C (type promotion, modulo sign, boolean-only if condition, etc.)
- Do NOT include Scanner-based input unless the topic folder explicitly covers Scanner
- Questions must be solvable using only concepts covered in this folder
  and all previously covered folders — no future concepts allowed

---

## 📁 Step 4 — Output File Placement

Place both files directly inside the topic folder:
```
<topic-folder>/
├── README.md
├── practice.md
└── *.java files
```

Do NOT place them in a subfolder.
Do NOT rename them.

---

## ✅ Quality Checklist Before Finalizing

Before outputting either file, verify:

- [ ] Every concept in README.md is present in the scanned `.java` files
- [ ] No concept from the `.java` files is missing from README.md
- [ ] Every code example in README.md compiles correctly
- [ ] Table of Contents anchor links match actual section headings exactly
- [ ] practice.md has exactly 10 questions: 5 easy, 3 medium, 2 hard
- [ ] No question in practice.md requires Scanner (unless topic covers it)
- [ ] No question duplicates another in logic or concept tested
- [ ] `⚠️ My Mistakes & Gaps` section is present and left blank
- [ ] Footer is present in README.md with correct folder path
- [ ] Hard questions have no expected output listed

---

*This rules file governs all note generation for Nilanchal's Java learning journey — Telusko tutorial series.*