# 📘 Git — Notes

> Version control from first principles: why it exists, how it thinks, and the commands you'll actually use.

**These notes tell one story in four parts:**

| Part | What it answers |
|------|-----------------|
| Part 1 | What went wrong without Git, and why Git was built the way it was |
| Part 2 | How Git thinks: the mental model that makes every command make sense |
| Part 3 | The commands every developer should know, and *why* each one exists |
| Part 4 | Conclusion + a cheat sheet to keep open while you work |

Every real example in here comes from this very repository, `JavaDevelopment`, because it's already a Git repo.

---

## 📑 Table of Contents

**Part 1: Why Git Exists**
1. [The Problem: Life Without Version Control](#1-the-problem-life-without-version-control)
2. [What We Used Before Git](#2-what-we-used-before-git)
3. [Finding the Root Cause](#3-finding-the-root-cause)
4. [Enter Git](#4-enter-git)
5. [How Git Is Different](#5-how-git-is-different)
6. [Git vs GitHub: Not the Same Thing](#6-git-vs-github-not-the-same-thing)

**Part 2: How Git Thinks**
7. [The Three Areas: Working Directory, Staging Area, Repository](#7-the-three-areas-working-directory-staging-area-repository)
8. [What a Commit Really Is](#8-what-a-commit-really-is)
9. [HEAD and Branches Are Just Pointers](#9-head-and-branches-are-just-pointers)

**Part 3: Commands Every Developer Should Know**
10. [First-Time Setup](#10-first-time-setup)
11. [Starting a Repository: init and clone](#11-starting-a-repository-init-and-clone)
12. [Looking Around: status, diff, log](#12-looking-around-status-diff-log)
13. [Saving Work: add and commit](#13-saving-work-add-and-commit)
14. [Ignoring Files: .gitignore](#14-ignoring-files-gitignore)
15. [Renaming and Deleting: mv and rm](#15-renaming-and-deleting-mv-and-rm)
16. [Undoing Mistakes: restore, amend, revert, reset](#16-undoing-mistakes-restore-amend-revert-reset)
17. [Branches: branch, switch, merge](#17-branches-branch-switch-merge)
18. [Merge Conflicts](#18-merge-conflicts)
19. [Remotes: remote, push, fetch, pull](#19-remotes-remote-push-fetch-pull)
20. [Stash: Pausing Unfinished Work](#20-stash-pausing-unfinished-work)
21. [Rebase: Rewriting the Story](#21-rebase-rewriting-the-story)
22. [Tags: Naming a Release](#22-tags-naming-a-release)
23. [Safety Nets: reflog, blame, clean](#23-safety-nets-reflog-blame-clean)
24. [A Real Day-to-Day Workflow](#24-a-real-day-to-day-workflow)

**Part 4: Wrap-Up**
25. [Conclusion](#25-conclusion)
26. [Git Cheat Sheet](#26-git-cheat-sheet)

---

# Part 1: Why Git Exists

## 1. The Problem: Life Without Version Control

Let's not start with "Git is a distributed version control system".

Let's start with the mess.

Imagine you're writing a small Java project, alone, on your laptop.

**Problem 1: You broke something that used to work.**

On Monday, `Calculator.java` works perfectly.

On Tuesday, you add a "percentage" feature and change a few other lines while you're at it.

Now addition is broken too.

You want Monday's version back.

But Monday's version doesn't exist anymore. You saved over it.

**Problem 2: So you start making copies.**

You learn your lesson and begin copying the folder before every big change:

```
JavaProject/
JavaProject_backup/
JavaProject_old/
JavaProject_final/
JavaProject_final_v2/
JavaProject_final_v2_REALLY_final.zip
```

It works for a week.

Then you ask yourself: *which copy has the working login code?*

*What's actually different between `final` and `final_v2`?*

*Why did I change that line?*

The copies have no answers. They are just piles of files with hopeful names.

**Problem 3: A friend joins the project.**

Now there are two of you.

You email the zip to your friend. They edit `Calculator.java`. You also edit `Calculator.java`.

They email their version back.

You copy their file over yours, and **your whole day of work silently disappears**.

Nobody gets an error message. The last person to save simply wins.

**Problem 4: "Who broke it, and why?"**

The app crashes in a demo.

Someone changed the tax calculation. Was it you? Your friend? Last week? Last month?

There is no record of *who* changed *what*, *when*, or *why*.

**Problem 5: Experiments are terrifying.**

You want to try rewriting the whole thing with a cleaner design.

But if the experiment fails halfway, you need to get back to the working code, and you don't trust your pile of copies to get you there.

So you just… don't experiment.

**Problem 6: The laptop dies.**

Coffee, a dropped bag, a dead SSD.

Every copy was on the same disk. Every version of the project is gone at once.

---

## 2. What We Used Before Git

People didn't sit still with these problems.

They built tools, in generations, and each generation fixed some problems and exposed new ones.

That history matters, because **Git's design is basically a list of answers to what went wrong before it.**

### Generation 0: Copy folders and email patches

Exactly what Section 1 describes: dated folders, zip files, and emailing changes around.

Even the Linux kernel, one of the biggest software projects on Earth, was managed for about ten years (1991–2002) mostly by people **emailing patches** to Linus Torvalds, who applied them by hand.

It worked only because Linus was superhumanly disciplined. It did not scale.

### Generation 1: Local version control (SCCS 1972, RCS 1982)

The first idea: *let a program keep the old versions for me.*

Tools like **RCS** kept a hidden history file next to each of your files, storing the list of changes over time.

You could say "give me this file as it was three versions ago" and get it.

**So what were the problems?**

- It lived on **one machine**. Laptop dies → history dies (Problem 6 still unsolved).
- It tracked **one file at a time**, not a whole project. There was no idea of "the project as it was on Monday."
- It had **no real collaboration**. Your friend can't use your machine's history file.

### Generation 2: Centralized version control (CVS 1990, SVN 2000, Perforce)

The next idea: *put the history on one central server, and let everyone connect to it.*

```
                ┌───────────────────────────┐
                │      CENTRAL SERVER       │
                │  (the ONLY full history)  │
                └──────┬───────┬───────┬────┘
                       │       │       │
                   ┌───┘       │       └───┐
                   ▼           ▼           ▼
                 You        Friend      Teammate
             (latest files only, no history)
```

You "check out" the latest files, edit them, and "commit" your changes back to the server.

This was a huge step up.

For the first time, a whole team had one shared history, with who/what/when recorded.

**SVN (Subversion)** in particular ran a massive share of the world's projects through the 2000s.

**Okay, so what were the problems with that?**

- **Single point of failure.** The server goes down for an hour → nobody can commit, nobody can see history. The server's disk dies without a good backup → the entire project history is gone.
- **Everything needs the network.** Want to see the log? Compare with last month? Commit? All of it talks to the server. On a plane, on bad Wi-Fi, you're stuck. And it's slow.
- **Committing = publishing.** A commit goes straight to the server where everyone gets it. So you can't make small private save points while your code is half-done. People made rare, giant commits instead, which made history hard to read and hard to undo.
- **Branching and merging were painful.** In SVN, a branch was a copy of a directory on the server, and merging branches back was so error-prone that many teams simply avoided branches. Which means Problem 5 (safe experiments) wasn't really solved.

### Generation 2.5: BitKeeper, and the day Linux lost its tool

In 2002, the Linux kernel moved from emailed patches to **BitKeeper**, a *distributed* version control system.

"Distributed" meant every developer had a full copy of the history, not just the server.

But BitKeeper was **commercial software** that the Linux community used under a free licence.

In **2005**, after a dispute over someone reverse-engineering its protocol, that free licence was withdrawn.

Suddenly, thousands of kernel developers had no version control tool.

Linus refused to go back to CVS or SVN. He'd seen their problems up close.

So in **April 2005**, he started writing his own.

Within days it could manage its own source code. Within about two months it was managing the Linux kernel.

He called it **Git**.

(Mercurial, another distributed system, was born from the very same event, the same month.)

---

## 3. Finding the Root Cause

Let's line up every problem we've seen:

| Problem | Symptom |
|---------|---------|
| Overwrote Monday's code | No saved versions |
| Pile of copies | Versions exist but have no meaning attached |
| Friend overwrote my work | No safe way to combine two people's changes |
| "Who broke it?" | No record of author, time, reason |
| Scared to experiment | No cheap, safe parallel line of work |
| Laptop died | History stored in exactly one place |
| Central server down | History stored in exactly one place, *again* |
| Rare giant commits | Saving a version was slow and public |

Now ask: **what do these have in common?**

Look at the right column.

Over and over, it's one of two things:

1. **The history lives in one place**, so losing that place, or losing access to it, loses everything.
2. **Making a save point is expensive**: slow, manual, needs a network, or is instantly public. So people do it rarely, or not at all.

> So the root cause is: *project history was treated as something fragile that lives in a single place, and creating a version was so costly that people avoided doing it.*

---

## 4. Enter Git

So how can we solve this?

Flip both halves of the root cause:

- If history living in one place is the problem → **give every developer the entire history on their own machine.**
- If save points being expensive is the problem → **make a save point instant, local, and private**, and let people decide *later* when to share it.

That's Git.

> **Git** is a free, open-source, **distributed version control system**. It records snapshots of your whole project over time, with who made each one, when, and why, and every copy of the project carries the complete history.

Let's unpack that one phrase at a time.

**Version control** = a system that remembers every saved version of your files, so you can see what changed and go back to any point.

**Distributed** = there is no one "master copy" of the history. Your laptop has all of it. Your friend's laptop has all of it. GitHub's server has all of it.

Here's the mental model that works best:

> Git is **save points in a video game**.
> You play (write code), and whenever you reach a good spot, you save.
> Die in the next level (break the code)? Reload the last save.
> Want to try a risky path? Make a separate save slot (a **branch**), and your main save stays untouched.

Git was designed by Linus with a very specific wish list, and every item on it is a direct answer to Section 2:

| Linus's goal | Answers which old problem |
|--------------|---------------------------|
| **Speed** | SVN talking to the server for every operation |
| **Fully distributed** | Single point of failure; can't work offline |
| **Strong support for thousands of parallel branches** | Branching was so painful teams avoided it |
| **Handle huge projects (the Linux kernel) efficiently** | Tools falling over at scale |
| **Protect history from corruption** | Silent data loss |

---

## 5. How Git Is Different

Git isn't "SVN but newer". It thinks about the problem differently in a few key ways.

### 5.1 Every clone is a full backup

```
     Centralized (SVN)                    Distributed (Git)

      ┌──────────┐                           ┌──────────┐
      │  Server  │ ← full history            │  GitHub  │ ← full history
      └────┬─────┘                           └────┬─────┘
       ┌───┼───┐                              ┌───┼───┐
       ▼   ▼   ▼                              ▼   ▼   ▼
      You  A   B  ← latest files only        You  A   B  ← full history each
```

When you `git clone` a project, you don't download "the latest files". You download **every version of every file, ever**.

So if GitHub vanished tomorrow, any single developer's laptop could restore the entire project history.

The "laptop died" problem and the "server died" problem become the same, much smaller, problem: *just clone it again from anyone.*

### 5.2 Almost everything is local, so it's fast and works offline

Since the full history is on your disk:

- `git log` (see history): local, instant
- `git diff` (compare versions): local, instant
- `git commit` (save a version): local, instant
- `git switch` (jump to a branch): local, instant

Only `push`, `pull`, `fetch` and `clone` touch the network, because those are the commands whose *whole job* is to talk to someone else.

You can commit fifty times on a flight with no Wi-Fi, and push when you land.

### 5.3 Commit and publish are two separate steps

In SVN, `commit` meant "save **and** send to everyone."

In Git, those are split:

- `git commit` → save a version **on your machine only**
- `git push` → share your saved versions **with others**

**Why does this matter so much?**

Because now a commit is private and free.

You can save every small step, "tests pass", "renamed variables", "half of the login page", without bothering anyone or breaking their build.

Then, when it's actually ready, you push.

### 5.4 Git stores snapshots, not lists of changes

Older tools mostly thought of history as **a base file plus a list of edits**: "version 1, then +3 lines, then −1 line…"

Git thinks of each commit as **a full photo of the entire project** at that moment.

> Think of a photo album versus a written diary of changes.
> To see what your room looked like in March, the diary makes you replay every change since January.
> The album lets you just open the March page.

> 💡 Tip: "a full photo" doesn't mean Git wastes disk space copying every file every time. If a file didn't change, the new snapshot just points to the copy it already has. Git also compresses everything behind the scenes. You get the simple mental model *and* the efficiency.

### 5.5 History is tamper-evident

Every commit gets an ID computed from its contents: a **hash**, a 40-character fingerprint like this:

```
6f9f7eb...  ← this repository's latest commit, "Maven Notes" (first 7 characters shown)
```

Change even one character in a file, or the author, or the message, and the fingerprint changes completely.

And because each commit's fingerprint *includes the fingerprint of the commit before it*, changing any old commit changes every commit after it.

So history can't be quietly corrupted or edited. Git will notice. (More in [Section 8](#8-what-a-commit-really-is).)

### 5.6 Branches are nearly free

In SVN, a branch was a full directory copy on the server.

In Git, a branch is a **tiny file containing one commit ID**. That's it. A few dozen bytes.

Creating one is instant. Switching is instant. Merging is something Git was built around from day one.

So people actually use branches: one per feature, one per bug fix, one per experiment.

That finally kills Problem 5: experiments are no longer scary.

### 5.7 The staging area

Git adds one step most older tools didn't have: a **staging area**, where you choose exactly which changes go into the next commit.

It feels like an extra annoyance at first. It's actually one of Git's best ideas. See [Section 7](#7-the-three-areas-working-directory-staging-area-repository).

### Comparison

| | Copy folders | SVN (centralized) | **Git (distributed)** |
|---|:---:|:---:|:---:|
| Full history on your machine | ❌ | ❌ | ✅ |
| Works offline | ✅ | ❌ | ✅ |
| Speed of log / diff / commit | n/a | slow (network) | instant (local) |
| Records who / when / why | ❌ | ✅ | ✅ |
| Private save points before sharing | ✅ (messy) | ❌ | ✅ |
| Cheap branches | ❌ | ❌ | ✅ |
| Survives the server dying | ❌ | ❌ | ✅ |
| Detects corrupted history | ❌ | partially | ✅ (hashes) |
| Safely combines two people's work | ❌ | ✅ | ✅ |

---

## 6. Git vs GitHub: Not the Same Thing

Beginners mix these up constantly, so let's settle it.

**Git** is the tool. It runs on your computer. It needs no internet and no account.

**GitHub** is a website that *hosts* Git repositories so people can share them, plus extras like pull requests, issues, and code review.

> Git is your camera and your photo album.
> GitHub is a photo-sharing website where you upload the album so others can see it and add to it.

| | Git | GitHub (also GitLab, Bitbucket) |
|---|---|---|
| What it is | Version control software | A hosting service for Git repos |
| Where it runs | Your machine | In the cloud |
| Needs an account? | No | Yes |
| Works offline? | Yes | No |
| Made by | Linus Torvalds (2005), now open source | GitHub Inc. (2008), owned by Microsoft |
| Pull requests? | **No**, that's not a Git feature | Yes |

This repository proves the split. It exists locally as a Git repo, and it's *also* connected to a copy on GitHub:

```
$ git remote -v
origin  https://github.com/Nilanchal0107/JavaDevelopment.git (fetch)
origin  https://github.com/Nilanchal0107/JavaDevelopment.git (push)
```

Delete the GitHub copy, and every commit is still right here on disk.

> 💡 Tip: GitHub, GitLab and Bitbucket all speak plain Git underneath. Everything you learn in these notes works with any of them.

---

# Part 2: How Git Thinks

If you learn commands without this part, Git feels like random magic spells.

If you learn this part first, almost every command becomes obvious.

## 7. The Three Areas: Working Directory, Staging Area, Repository

Here's a very real situation.

You sit down to fix a bug in `Calculator.java`.

While you're in there, you also start a new feature in `Report.java`, but it's only half-done.

The bug fix is ready to save. The feature is not.

**If "save a version" grabbed every changed file at once, you'd be forced to save the broken half-feature together with the bug fix.**

Then, if the fix later needs to be undone, the half-feature gets undone with it. And the commit message "fixed bug" would be a lie.

> So the root cause is: *the files you've changed and the changes you're ready to save are not always the same set.*

So how do we solve this?

Put a middle step between "I changed it" and "I saved it": a place where you **pick** what goes into the next save.

That place is the **staging area**, and Git has three areas in total:

```
  ┌───────────────────┐   git add    ┌───────────────────┐  git commit  ┌───────────────────┐
  │ WORKING DIRECTORY │ ───────────► │   STAGING AREA    │ ───────────► │    REPOSITORY     │
  │ (files you edit)  │              │ (the next commit, │              │ (.git: all saved  │
  │                   │              │  being assembled) │              │  commits, forever)│
  └───────────────────┘              └───────────────────┘              └─────────┬─────────┘
            ▲                                                                     │
            └─────────────────── git restore / git switch ────────────────────────┘
```

| Area | Also called | What it is |
|------|-------------|------------|
| **Working directory** | working tree | The actual files you see and edit in VS Code |
| **Staging area** | index, cache | A draft of the next commit |
| **Repository** | `.git` folder, history | Every commit ever made, stored permanently |

The analogy that sticks:

> You're **packing a box** to ship.
> Your room is the **working directory**: stuff everywhere.
> The open box is the **staging area**: you put in only what belongs in this shipment (`git add`).
> Taping it shut and writing a label is **committing** (`git commit -m "label"`). Once sealed, it goes on the shelf, the **repository**, forever.

So for our situation:

```
git add Calculator.java               # only the bug fix goes in the box
git commit -m "Fix division by zero"  # seal it; Report.java stays out, still being worked on
```

### The four states of a file

Because of the three areas, every file is always in one of these states:

| State | Meaning | `git status -s` shows |
|-------|---------|:---:|
| **Untracked** | Git has never been told about this file | `??` |
| **Unmodified** | Same as the last commit, so nothing to do | *(not shown)* |
| **Modified** | Changed since the last commit, but not in the box yet | ` M` |
| **Staged** | Changed and placed in the box for the next commit | `M ` |

Here's the real status of this repository while these notes were being written:

```
$ git status -s
 D "02. Maven Projects/README.md"
 M RULES.MD
?? "02. Maven Projects/Notes.md"
?? "DSA/01. Learn the Basics/03. Know Basic Maths/"
```

**How do you read those two columns?**

The **left** column is the staging area. The **right** column is the working directory.

- ` D` → the file was **deleted** in the working directory, but that deletion is not staged yet.
- ` M` → `RULES.MD` was **modified**, not staged.
- `??` → brand-new files Git has never tracked.

Notice the left column is blank everywhere. Nothing is in the box yet. `git commit` right now would save nothing.

> 💡 Tip: the `.git` folder at the root of the project **is** the repository. All history lives inside it. Delete `.git` and your files stay, but every commit, branch, and bit of history is gone. Git is completely self-contained in that one hidden folder.

---

## 8. What a Commit Really Is

We keep saying "commit". So what's actually inside one?

A commit is a small record containing:

| Field | Example | Why it's there |
|-------|---------|----------------|
| **Snapshot** | the whole project's files at that moment | so you can go back to exactly this state |
| **Author** + **date** | `Nilanchal0107`, `2026-09-12` | answers "who did this, and when?" |
| **Message** | `Maven Notes` | answers "why?" |
| **Parent** | the commit that came before | links commits into a history |
| **Hash** | `6f9f7eb…` | a unique fingerprint computed from all of the above |

The **parent** field is what turns separate snapshots into a story.

Each commit points back to the one before it:

```
96106ac  ◄──  189d09f  ◄──  1242ca2  ◄──  0efb54c  ◄──  6f9f7eb
"Practice     "Practice     "Miscellaneous "Solve         "Maven
 Questions     Questions     Notes"         Pattern"       Notes"
 Created"      Solved"
```

That's the real last five commits of this repo (`git log --oneline`), oldest on the left.

**Why do the arrows point backwards?**

Because when you make a commit, its parent already exists, so it can store the parent's ID.

But a parent can't store its children's IDs, because they don't exist yet when it's created, and a commit can never be edited afterwards.

### Why the hash makes history trustworthy

Here's the chain of reasoning:

1. The hash is computed from the commit's content, **including the parent's hash**.
2. So if someone secretly changes an old commit, that commit's hash changes.
3. Its child stored the *old* hash as its parent, so now the child's hash is wrong too.
4. And that child's child. And every commit after it.

> It's like a chain of sealed envelopes, where each envelope has the previous envelope's seal number written inside.
> Tamper with one envelope, and every seal after it stops matching.

So you can't quietly rewrite history. You can only create **new** commits with **new** hashes, and anyone comparing will see the difference.

> 💡 Tip: you almost never need the full 40-character hash. The first 7 characters (`6f9f7eb`) are enough for Git to find the commit in all but the biggest projects.

> ⚠️ Warning: "commits can't be changed" is the reason commands like `--amend` and `rebase` exist and are dangerous. They don't *edit* commits. They create **brand new** commits with new hashes and throw the old ones away. If someone else already has the old ones, your histories now disagree. See [Section 16](#16-undoing-mistakes-restore-amend-revert-reset) and [Section 21](#21-rebase-rewriting-the-story).

---

## 9. HEAD and Branches Are Just Pointers

This is the section that makes branches stop being scary.

**A branch is not a copy of your code.**

**A branch is a sticky note stuck on one commit.**

That's all. A name pointing at a commit ID.

```
                                          main
                                           │
                                           ▼
96106ac ◄── 189d09f ◄── 1242ca2 ◄── 0efb54c ◄── 6f9f7eb
                                           ▲
                                           │
                                          HEAD
```

When you make a new commit while on `main`, Git does two tiny things:

1. Creates the commit, with the current commit as its parent.
2. Moves the `main` sticky note forward to the new commit.

**So what is HEAD?**

`HEAD` is the "📍 you are here" marker.

It normally points at a **branch** (like `main`), which in turn points at a commit.

It tells Git two things: *which commit your files currently match*, and *which branch should move when you commit next*.

Now let's make a second branch and see why it's so cheap:

```
git switch -c experiment     # create a sticky note "experiment" right here, and move HEAD onto it
```

```
                                                   main
                                                    │
96106ac ◄── 189d09f ◄── 1242ca2 ◄── 0efb54c ◄── 6f9f7eb
                                                    │
                                               experiment ◄── HEAD
```

Nothing was copied. Git just wrote one more tiny file.

Commit twice on `experiment`:

```
                                               main
                                                │
... ◄── 0efb54c ◄── 6f9f7eb ◄── a1b2c3d ◄── e4f5a6b
                                                    │
                                               experiment ◄── HEAD
```

`main` hasn't moved. It still points at `6f9f7eb`.

If the experiment fails, `git switch main` and your files are exactly as they were. You can delete the `experiment` note and it's like it never happened.

That's the "separate save slot" from the video game analogy, made real.

> ⚠️ Warning: if you `git switch --detach <hash>` or `git checkout <hash>` to look at an old commit, HEAD points **directly at a commit** instead of a branch. That's called **detached HEAD**. You can look around freely, but if you commit there, no branch moves to remember your commits, and once you switch away they become very hard to find. If you want to keep work from there, create a branch first: `git switch -c rescue-branch`.

---

# Part 3: Commands Every Developer Should Know

Every command below is introduced the same way: what problem it solves, then how to use it.

## 10. First-Time Setup

**Why does Git ask for your name before it lets you commit?**

Because of [Section 8](#8-what-a-commit-really-is): every commit permanently stamps an author.

Git refuses to guess who you are, because a wrong name baked into history can't be fixed without rewriting it.

So once per computer:

```bash
git --version                                          # check Git is installed

git config --global user.name  "Your Name"             # stamped on every commit
git config --global user.email "you@example.com"       # use the same email as your GitHub account

git config --global init.defaultBranch main            # new repos start on "main", not "master"
git config --global core.editor "code --wait"          # open VS Code (not Vim) when Git needs a message

git config --global core.autocrlf true                 # Windows only (see below)

git config --list                                      # show every setting in effect
```

**What is `--global` doing?**

It saves the setting for your user account, in a file at `C:\Users\<you>\.gitconfig`, so it applies to every repository.

Leave out `--global` inside a repo, and the setting applies to **that repo only**. That's useful when you use a work email in one project and a personal email in another.

**Why `core.editor`?**

If you run `git commit` without `-m`, Git opens a text editor for the message.

The default is often **Vim**, and many beginners have been trapped in it with no idea how to quit. (It's `Esc`, then `:wq`, then `Enter`.)

`"code --wait"` opens VS Code instead, and `--wait` tells Git to wait until you close that tab.

**Why `core.autocrlf` on Windows?**

Windows ends each line of text with two invisible characters (CRLF). Linux and macOS use one (LF).

Without this setting, a teammate on a Mac opens your file and Git reports that **every single line** changed, when really only the invisible endings did.

`true` converts to LF when saving into Git, and back to CRLF when giving files to you.

> 💡 Tip: `git config user.name` (no value) prints the current setting. Handy for checking which identity a repo will use before you commit.

---

## 11. Starting a Repository: init and clone

There are exactly two ways to get a Git repository.

### `git init`: turn an existing folder into a repo

You have a folder of code with no history. You want to start tracking it.

```bash
cd MyProject
git init          # creates the hidden .git folder. That's the whole repository.
```

Nothing is tracked yet. Every file shows as untracked (`??`) until you `add` and `commit` it.

### `git clone`: copy an existing repo, including all its history

The project already exists somewhere, like on GitHub.

```bash
git clone https://github.com/Nilanchal0107/JavaDevelopment.git
git clone https://github.com/Nilanchal0107/JavaDevelopment.git my-folder   # choose the folder name
```

`clone` does four things in one go:

1. Downloads **every commit, every branch, every file version** (remember: distributed).
2. Creates the `.git` folder with all of that inside.
3. Remembers where it came from, under the name **`origin`**.
4. Checks out the default branch so your working directory has files in it.

| | `git init` | `git clone <url>` |
|---|---|---|
| Starting point | A folder with no history | A repo that already exists elsewhere |
| History afterwards | Empty | Complete copy |
| Remote `origin` set up? | No, you add it yourself | Yes, automatically |

> ⚠️ Warning: **never run `git init` inside a folder that's already in a repository.** This `JavaDevelopment` folder is already a repo, so running `git init` inside `03. Git` would create a *repo inside a repo*. The outer repo then treats the inner folder strangely and stops tracking its files normally. Before running `init`, run `git status`. If it doesn't say "not a git repository", you're already inside one.

---

## 12. Looking Around: status, diff, log

Before you change anything, you should be able to answer three questions:

- *What state are my files in?* → `git status`
- *What exactly did I change?* → `git diff`
- *What happened before?* → `git log`

These are **read-only**. They never change anything. Run them constantly.

### `git status`: "where am I and what's going on?"

```bash
git status        # full, explained output
git status -s     # short two-column output (see Section 7 for how to read it)
```

It tells you which branch you're on, what's staged, what's modified, what's untracked, and whether you're ahead of or behind the remote.

And it often **tells you the exact command to run next**, like "use `git restore --staged <file>` to unstage."

> 💡 Tip: when you're confused, run `git status` and actually read it. Most "Git is broken" moments are answered in its output.

### `git diff`: "show me the actual lines"

`status` says *which* files changed. `diff` shows *what* changed inside them.

```bash
git diff                   # working directory vs staging area  → changes NOT yet staged
git diff --staged          # staging area vs last commit        → what WILL go into the next commit
git diff HEAD              # working directory vs last commit   → everything, staged or not
git diff main feature      # compare two branches
git diff 0efb54c 6f9f7eb   # compare two commits
```

Output reads like this:

```diff
-        return a / b;                          ← line removed (red)
+        if (b == 0) return 0;                  ← line added (green)
+        return a / b;
```

**Why are there two diffs (`diff` and `diff --staged`)?**

Because of the three areas.

There are two gaps a change can sit in: *edited but not in the box* and *in the box but not sealed*. Each gap needs its own view.

> 💡 Tip: run `git diff --staged` right before every commit. It's the last look inside the box before you tape it shut. It's how you catch the debug `System.out.println` you forgot to remove.

### `git log`: "what happened before?"

```bash
git log                          # full history: hash, author, date, message
git log --oneline                # one line per commit
git log --oneline --graph --all  # draw branches and merges as a picture
git log -p                       # include the diff of each commit
git log -5                       # only the last 5
git log --author="Nilanchal0107" # only one person's commits
git log -- RULES.MD              # only commits that touched this file
git show 6f9f7eb                 # everything about one commit, including its diff
```

Real output from this repo:

```
$ git log --oneline -5
6f9f7eb Maven Notes
0efb54c Solve Pattern
1242ca2 Miscellaneous Notes
189d09f Practice Questions Solved
96106ac Practice Questions Created
```

> 💡 Tip: press `q` to exit a long `git log`. It opens in a scrolling viewer, and beginners often think the terminal froze.

---

## 13. Saving Work: add and commit

### `git add`: put changes in the box

```bash
git add Calculator.java        # stage one file
git add src/                   # stage everything inside a folder
git add .                      # stage everything changed in the current folder and below
git add -A                     # stage everything in the whole repo, including deletions
git add -p                     # go through changes piece by piece and choose y/n for each
```

**Why would you ever need `git add -p`?**

Remember Section 7: the bug fix and the half-done feature.

Now imagine they're in the **same file**. `git add Calculator.java` would stage both.

`-p` (patch) shows you each changed block ("hunk") one at a time and asks: *stage this one?*

So you can split one messy file into two clean commits.

> ⚠️ Warning: `git add` takes a **snapshot of the file at that moment**. If you edit the file again after `add`, the new edit is *not* staged. `git status` will show the file as both staged and modified (`MM`). Just `git add` it again.

### `git commit`: seal the box

```bash
git commit -m "Fix division by zero in Calculator"   # commit with a one-line message
git commit                                           # opens your editor for a longer message
git commit -am "Update tax rate"                     # add + commit in one step (see warning)
```

> ⚠️ Warning: `-a` only stages files Git **already tracks**. Brand-new (untracked `??`) files are silently left out. If you created a new file, you still need `git add` for it.

### Writing a good commit message

**Why care about a message at all? It's just for you, right?**

Six months from now you'll run `git log` looking for the commit that broke something.

Compare what you'll see:

```
Practice Questions Solved
Practice Questions Solved
Practice Questions Solved
```

versus:

```
Solve arrays practice Q1-Q5
Solve strings practice Q6 (StringBuilder reverse)
Fix off-by-one in pattern printing loop
```

The diff already records **what** changed. The message is the only place that records **why**, and *which one* you're looking for.

Conventions most teams use:

- Short subject line, around **50 characters**.
- **Imperative mood**, as if giving an order: "Add login form", not "Added" or "Adding". (It reads as "this commit will… *add login form*".)
- Blank line, then a longer body explaining **why**, if it isn't obvious.

### How big should a commit be?

**One logical change per commit.**

Why? Because a commit is the unit you undo, review, and search for.

If "fix login bug" and "rename 40 variables" are one commit, you can't undo the rename without also undoing the bug fix.

> 💡 Tip: commit small and often. Commits are free and private until you push ([Section 5.3](#53-commit-and-publish-are-two-separate-steps)), so there's no reason to wait until the end of the day.

---

## 14. Ignoring Files: .gitignore

**What shouldn't be in Git at all?**

Recall the rule from the Maven notes (Section 25 there): *commit what a human **wrote**, ignore what a machine can **regenerate***.

Maven's `target/` folder, compiled `.class` files, log files, IDE settings like `.idea/`, and **secrets like passwords and API keys** all don't belong in history.

But they sit right there in your project, showing up as `??` in `git status` every time, begging to be swept in by `git add .`.

So how do we solve this? Write down, once, which files Git should pretend don't exist.

That list is a file named **`.gitignore`**:

```gitignore
# Build output (regenerated by Maven)
target/

# Compiled Java
*.class

# Logs, anywhere in the project
*.log

# ...except this one
!important.log

# IDE / editor settings
.idea/
.vscode/

# Secrets: NEVER commit these
.env
application-secret.properties

# Only the build/ folder at the repo root, not nested ones
/build/
```

| Pattern | Matches |
|---------|---------|
| `target/` | any folder named `target`, and everything inside |
| `*.class` | every file ending in `.class`, in any folder |
| `/build/` | a `build` folder at the **root** only (leading `/`) |
| `docs/**/*.pdf` | PDFs anywhere under `docs/`, any depth |
| `!keep.log` | **un**-ignore this, even though an earlier rule matched |
| `# text` | a comment |

The `.gitignore` itself **should** be committed, so everyone on the team ignores the same things.

> ⚠️ Warning: `.gitignore` only affects **untracked** files. If `target/` was already committed, adding it to `.gitignore` does nothing, because Git keeps tracking what it already tracks. You must untrack it first:
> ```bash
> git rm -r --cached target/     # remove from Git's tracking, but KEEP the files on disk
> git commit -m "Stop tracking target folder"
> ```

> ⚠️ Warning: if you accidentally commit a password and then delete it in the next commit, **it's still in history**. Anyone who clones can find it in the old commit. Treat any committed secret as leaked: change the password or revoke the key immediately.

> 💡 Tip: Git tracks **files**, not folders. An empty folder can't be committed. The common workaround is putting an empty file named `.gitkeep` inside it. (That name is just a convention. Git gives it no special meaning.)

---

## 15. Renaming and Deleting: mv and rm

Look at two lines of this repo's status again:

```
 D "02. Maven Projects/README.md"
?? "02. Maven Projects/Notes.md"
```

To a human, that might look like "renamed README.md to Notes.md."

**To Git, it's two unrelated events: a file vanished, and an unknown file appeared.**

**Why doesn't Git just know it was a rename?**

Because of [Section 5.4](#54-git-stores-snapshots-not-lists-of-changes): Git stores snapshots, not a log of actions.

It never recorded the *act* of renaming. It only sees "before: this file existed" and "after: that file exists."

So Git **detects** renames after the fact: once *both* the deletion and the new file are staged, it compares their contents, and if they're similar enough (50% by default), `git status` shows `renamed:`.

```bash
git mv Old.java New.java        # rename the file AND stage both sides of the rename
git rm Old.java                 # delete the file AND stage the deletion
git rm --cached secrets.txt     # stop tracking, but keep the file on disk
```

`git mv` is just a shortcut for "rename in the file explorer, then `git add` both paths."

Staging the rename you already did by hand works exactly the same:

```bash
git add -A "02. Maven Projects"   # stages the deletion and the new file together
```

> 💡 Tip: if you rename a file *and* rewrite most of it in the same commit, the content drops below the similarity threshold and Git records it as delete + add. History of that file then appears to "start over". Rename in one commit, rewrite in the next, and `git log --follow Notes.md` can trace it back through the rename.

---

## 16. Undoing Mistakes: restore, amend, revert, reset

This is the section people actually come to Git for.

But "undo" isn't one thing. **The right command depends on where the mistake is**, meaning which of the three areas, and whether you've already shared it.

Here's the decision table first, then the reasons:

| Situation | Command | Destroys work? |
|-----------|---------|:---:|
| Edited a file, want it back to the last commit | `git restore <file>` | ⚠️ yes, those edits |
| Staged something by mistake, want it out of the box | `git restore --staged <file>` | no |
| Last commit has a typo in the message / forgot a file (**not pushed**) | `git commit --amend` | no |
| Want to undo a commit **already pushed** | `git revert <hash>` | no |
| Want to throw away recent commits (**not pushed**), keep the changes | `git reset --soft HEAD~1` / `git reset HEAD~1` | no |
| Want to throw away recent commits **and** their changes (**not pushed**) | `git reset --hard HEAD~1` | ⚠️ yes |

### `git restore <file>`: discard edits in the working directory

```bash
git restore Calculator.java     # make the file match the last commit again
git restore .                   # do it for everything
```

> ⚠️ Warning: this is one of the few truly permanent commands. Those edits were never committed, so Git has no copy of them. They're gone.

### `git restore --staged <file>`: take it back out of the box

```bash
git restore --staged Report.java   # unstage; your edits stay in the working directory
```

> 💡 Tip: before Git 2.23 (2019), both of these were done with `git checkout -- <file>` and `git reset HEAD <file>`. You'll still see those in older tutorials and Stack Overflow answers. `restore` was added because `checkout` did too many unrelated jobs.

### `git commit --amend`: fix the commit you just made

```bash
git commit --amend -m "Fix division by zero"   # replace the last commit's message

git add ForgottenFile.java
git commit --amend --no-edit                   # add a forgotten file, keep the same message
```

It feels like editing the last commit. But from Section 8 we know commits can't be edited.

So what really happens is: Git builds a **new** commit with a **new** hash, and `main` moves to it. The old one is abandoned.

### `git revert <hash>`: undo safely by adding an opposite commit

The bad commit has already been pushed. Teammates have pulled it.

```bash
git revert 0efb54c     # create a NEW commit that does the exact opposite of 0efb54c
```

```
before:  ... ◄── 1242ca2 ◄── 0efb54c ◄── 6f9f7eb
after:   ... ◄── 1242ca2 ◄── 0efb54c ◄── 6f9f7eb ◄── 9d8c7b6 "Revert 'Solve Pattern'"
```

History isn't deleted. It grows by one commit that cancels the bad one out.

### `git reset`: move the branch backwards

```bash
git reset --soft  HEAD~1   # undo the commit; changes stay STAGED (in the box)
git reset         HEAD~1   # (--mixed, the default) undo the commit; changes stay in files, UNSTAGED
git reset --hard  HEAD~1   # undo the commit AND erase its changes from your files
```

`HEAD~1` means "one commit before HEAD". `HEAD~3` means three commits back.

Reset literally moves the branch sticky note ([Section 9](#9-head-and-branches-are-just-pointers)) back to an older commit.

The three flags only decide what happens to the **staging area** and **working directory**:

| | Branch moves back | Staging area reset | Working directory reset |
|---|:---:|:---:|:---:|
| `--soft` | ✅ | ❌ | ❌ |
| `--mixed` (default) | ✅ | ✅ | ❌ |
| `--hard` | ✅ | ✅ | ✅ ⚠️ |

### Why revert for shared commits, but reset only for private ones?

Here's the chain of reasoning:

1. `reset` makes the branch forget commits. Your history now **doesn't contain** them.
2. Your teammate already pulled those commits. Their history **does** contain them.
3. Now you push. Git sees your branch is *missing* commits the remote has, and rejects the push.
4. If you force it, their next pull sees history that disappeared under them: duplicated commits, confusing conflicts, lost work.

`revert` never removes anything. It only **adds**. Adding is something everyone's history can always accept.

> A shared history is a **published newspaper**.
> `revert` prints a correction in tomorrow's edition. Everyone's copy stays consistent.
> `reset` tries to tear the page out of yesterday's edition, including the copies already delivered to everyone's houses. It doesn't work, and it makes a mess.
> In your **private diary** (commits nobody else has), tearing out a page is fine.

> ⚠️ Warning: **the golden rule of undoing: never `reset`, `--amend`, or `rebase` commits you've already pushed to a shared branch.** Use `revert`.

> 💡 Tip: even `reset --hard` is usually recoverable for *committed* work, using `git reflog`. See [Section 23](#23-safety-nets-reflog-blame-clean). Uncommitted edits are not.

---

## 17. Branches: branch, switch, merge

### The problem branches solve

You're halfway through a new "export to PDF" feature on `main`. Five files are changed, and nothing compiles yet.

Your team lead messages: *"Production is crashing, fix the login bug NOW."*

**Where do you make the fix?**

On top of your broken half-feature? Then you can't ship the fix without shipping broken code.

Copy the project to a new folder? That's Section 1's pile of copies all over again.

> So the root cause is: *two unrelated pieces of work are forced to share one line of history.*

So how do we solve this? Give each piece of work its **own line of history**, and combine them only when they're ready.

That's a **branch**. And since a branch is just a sticky note ([Section 9](#9-head-and-branches-are-just-pointers)), making one costs nothing.

### The commands

```bash
git branch                     # list local branches (* marks the current one)
git branch -a                  # list local AND remote-tracking branches
git branch feature/pdf-export  # create a branch (does NOT switch to it)
git switch feature/pdf-export  # switch to an existing branch
git switch -c hotfix/login     # create AND switch in one step (most common)
git switch -                   # jump back to the previous branch
git branch -d hotfix/login     # delete a branch that's already merged (safe)
git branch -D experiment       # force-delete, even if not merged (⚠️ discards its commits)
git branch -m old-name new     # rename a branch
```

This repo right now:

```
$ git branch -a
* main
  remotes/origin/main
```

> 💡 Tip: `git checkout <branch>` and `git checkout -b <branch>` are the older versions of `switch` and `switch -c`. They still work, and you'll see them everywhere.

> ⚠️ Warning: Git won't let you switch branches if uncommitted changes would be overwritten by the other branch's version of those files. Commit them, or [stash](#20-stash-pausing-unfinished-work) them first.

So the emergency from above becomes:

```bash
git stash                        # park the half-done PDF work
git switch main
git switch -c hotfix/login       # new line of history for the fix
# ...fix the bug...
git commit -am "Fix null session crash on login"
git switch main
git merge hotfix/login           # bring the fix into main
git switch feature/pdf-export
git stash pop                    # continue the feature, untouched
```

### `git merge`: combine branches

`git merge <other>` brings the commits from `<other>` **into the branch you're currently on**.

There are two kinds of merge, and Git picks automatically.

**1. Fast-forward merge.** `main` hasn't moved since you branched:

```
before:   A ◄── B            ◄── main
                 └── C ◄── D ◄── hotfix

after:    A ◄── B ◄── C ◄── D   ◄── main, hotfix
```

There's nothing to combine. `main` is simply *behind*. Git slides the sticky note forward.

**2. Three-way merge.** Both branches have new commits:

```
before:   A ◄── B ◄── E ◄── F          ◄── main
                 └── C ◄── D            ◄── feature

after:    A ◄── B ◄── E ◄── F ◄── M     ◄── main
                 └── C ◄── D ◄───┘
```

Git looks at three snapshots: the common ancestor `B`, and the two tips `F` and `D`.

Whatever changed on one side only, it takes. Then it creates a **merge commit** `M` with **two parents**.

> 💡 Tip: `git log --oneline --graph --all` draws exactly these pictures for your real repo. It's the best way to *see* branches.

---

## 18. Merge Conflicts

**When can't Git merge automatically?**

Remember how three-way merge works: take the change from whichever side changed.

Now: both branches changed **the same lines of the same file**, in different ways.

`main` says `TAX = 0.18`. `feature` says `TAX = 0.12`.

Which one is right? That's a business decision. Git has no way to know.

So Git **stops and asks you**. That's a merge conflict.

> 💡 Tip: a conflict is not an error, and nothing is broken. It's Git refusing to guess when guessing wrong could silently ship a bug.

Git writes both versions into the file with markers:

```java
public class Tax {
<<<<<<< HEAD
    static final double RATE = 0.18;     // your current branch's version (main)
=======
    static final double RATE = 0.12;     // the incoming branch's version (feature)
>>>>>>> feature
}
```

### How to resolve it

```bash
git status                     # lists files under "both modified"
# 1. Open each file. Decide the correct final code (one side, the other, or a mix).
# 2. Delete ALL the marker lines: <<<<<<<, =======, >>>>>>>
# 3. Compile and test.
git add Tax.java               # staging a file = "I've resolved this one"
git commit                     # finishes the merge (Git pre-fills the message)
```

Changed your mind mid-way?

```bash
git merge --abort              # put everything back exactly as it was before the merge
```

> ⚠️ Warning: Git only checks that you `add`-ed the file. It does **not** check that you removed the markers. Commit a file still containing `<<<<<<<` and your Java won't compile. Search for `<<<<<<<` before committing. VS Code also highlights conflicts with "Accept Current / Accept Incoming / Accept Both" buttons.

> 💡 Tip: the best way to handle conflicts is to have fewer of them. Keep branches short-lived and merge `main` into them often, so they never drift far apart.

---

## 19. Remotes: remote, push, fetch, pull

So far everything happened on your machine. Now: **how does your work get to other people, and theirs to you?**

A **remote** is a named link to another copy of the repository, usually on GitHub.

`origin` is simply the default name `clone` gives to "where I cloned from". It's not special; it's just a convention.

### `git remote`: manage the links

```bash
git remote -v                                   # list remotes and their URLs
git remote add origin https://github.com/user/repo.git   # link a repo you created with init
git remote set-url origin <new-url>             # change the URL
git remote remove origin                        # remove the link
```

### `git push`: send your commits

```bash
git push -u origin main    # first time: push AND remember origin/main as this branch's upstream
git push                   # after that, just this
git push origin feature/x  # push a specific branch
```

**What does `-u` (set upstream) really do?**

It links your local `main` to `origin/main`.

After that, plain `git push` and `git pull` know where to go, and `git status` can tell you "Your branch is ahead of 'origin/main' by 2 commits."

### `git fetch`: download without touching your work

```bash
git fetch            # download new commits from origin, but do NOT change your files or branches
```

After a fetch, the downloaded commits sit on **remote-tracking branches** like `origin/main`, which is Git's memory of "what `main` looked like on origin last time I checked."

Your own `main` is untouched. You can inspect before deciding anything:

```bash
git log main..origin/main --oneline     # "what do they have that I don't?"
```

### `git pull`: download and combine

```bash
git pull             # = git fetch + git merge origin/main (into your current branch)
git pull --rebase    # = git fetch + git rebase (see Section 21) → no extra merge commit
```

| | `fetch` | `pull` |
|---|---|---|
| Downloads new commits | ✅ | ✅ |
| Changes your branch / files | ❌ | ✅ |
| Can cause merge conflicts | ❌ | ✅ |
| Safe to run any time | ✅ | mostly, but commit or stash first |

> `fetch` is bringing the mail in from the mailbox and leaving it on the table.
> `pull` is bringing it in, opening it, and filing it into your folders straight away.

### "Push rejected"? Here's why

```
! [rejected]        main -> main (fetch first)
```

**Why won't Git just push?**

Because a teammate pushed commits you don't have.

If Git accepted your push, `origin/main` would move to *your* commit, and *their* commits would no longer be part of the branch. Their work would be silently dropped. That's Problem 3 from Section 1, exactly what version control exists to prevent.

So Git makes you combine first:

```bash
git pull          # get their commits and merge them with yours (resolve conflicts if any)
git push          # now your push includes both
```

> ⚠️ Warning: `git push --force` overwrites the remote branch with yours, deleting any commits there you don't have. If you ever truly must force-push (for example, after rebasing your *own* feature branch), use `git push --force-with-lease`. It refuses if someone pushed something you haven't seen. Never force-push to `main`.

> 💡 Tip: habit that prevents most pain: **`git pull` before you start work, and before you push.**

---

## 20. Stash: Pausing Unfinished Work

**Problem:** you're mid-change, and you need to switch branches *right now*. But your code is half-done and doesn't deserve a commit.

Committing garbage just to switch pollutes history. Switching might fail or drag your edits along.

**So how do we solve this?** Put the unfinished changes on a temporary shelf, get a clean working directory, and take them back later.

That shelf is the **stash**.

```bash
git stash                          # shelve tracked changes (staged + unstaged); files go back to last commit
git stash -u                       # also shelve untracked (new) files
git stash push -m "pdf export wip" # shelve with a name, so you remember what it is
git stash list                     # see everything on the shelf
git stash pop                      # take the latest back AND remove it from the shelf
git stash apply                    # take it back but KEEP a copy on the shelf
git stash apply stash@{2}          # take back a specific one
git stash drop stash@{0}           # delete one from the shelf
git stash clear                    # ⚠️ delete them all
```

> ⚠️ Warning: plain `git stash` **ignores untracked files**. Your brand-new `PdfExporter.java` stays in the working directory and follows you to the other branch. Use `-u` when you have new files.

> ⚠️ Warning: the stash is a shelf, not storage. Stashes are easy to forget, are local only (never pushed), and have vague names. If the work matters for more than an hour, a commit on its own branch is safer.

---

## 21. Rebase: Rewriting the Story

### The problem

You branch `feature` off `main`. While you work, teammates add commits to `main`.

When you merge, you get a merge commit. Do that for every feature, every day, and `git log --graph` turns into train tracks:

```
*   Merge branch 'feature-c'
|\
| * Add C
* |   Merge branch 'feature-b'
|\ \
| * | Add B
...
```

The history is *true*, but it's hard to read. "What actually changed, in what order?" is buried under merge noise.

### The idea

What if, instead of combining two diverged lines, you **picked up your commits and replayed them on top of the latest `main`**, as if you'd started your work just now?

```
before:   A ◄── B ◄── E ◄── F           ◄── main
                 └── C ◄── D             ◄── feature

git switch feature
git rebase main

after:    A ◄── B ◄── E ◄── F ◄── C' ◄── D'   ◄── feature
                             ▲
                            main
```

Now `main` can fast-forward to `D'`. One clean straight line, no merge commit.

**Why are they `C'` and `D'`, not `C` and `D`?**

Because of Section 8. A commit's hash includes its parent. `C` had parent `B`. The replayed commit has parent `F`.

Different parent → different content → **different hash → a brand-new commit**. The originals are abandoned.

### The commands

```bash
git rebase main                # replay current branch's commits on top of main
git rebase --continue          # after fixing a conflict during rebase (and git add)
git rebase --abort             # give up, go back to before the rebase
git pull --rebase              # pull without creating a merge commit

git rebase -i HEAD~3           # interactive: reorder, squash, reword, or drop the last 3 commits
```

`rebase -i` is how people turn "wip", "wip 2", "fix typo" into one clean commit before sharing.

### Merge vs rebase

| | `merge` | `rebase` |
|---|---|---|
| History shape | true, branching, with merge commits | clean straight line |
| Changes existing commits? | ❌ never | ✅ creates new ones |
| Safe on shared branches? | ✅ always | ❌ **only on your own unpushed/unshared commits** |
| Conflicts | resolved once | may be resolved once per replayed commit |

> ⚠️ Warning: **the golden rule of rebase: never rebase commits that other people have already pulled.** Rebasing throws away the old commits and makes new ones (new hashes). Anyone who built on the old ones now has a history that no longer matches yours, which is the exact same mess as `reset` in Section 16. Rebase your private feature branch freely; never rebase `main`.

> 💡 Tip: if you're a beginner, `merge` is never wrong. It's always safe. Learn rebase when your team asks for linear history.

---

## 22. Tags: Naming a Release

Commit hashes like `6f9f7eb` are precise but meaningless to humans.

Branches have names, but they **move**. `main` next week points somewhere else.

**How do you permanently mark "this exact commit is version 1.0 that we shipped"?**

With a **tag**: a name that points at one commit and **never moves**.

(Remember the Maven notes: `1.0` is a frozen release, `1.0-SNAPSHOT` is still changing. A tag is how Git freezes that "1.0" moment.)

```bash
git tag                                   # list tags
git tag v1.0                              # lightweight tag on the current commit (just a name)
git tag -a v1.0 -m "First release"        # annotated tag: stores tagger, date, message (preferred)
git tag -a v0.9 6f9f7eb -m "Beta"         # tag an older commit
git show v1.0                             # see the tag and its commit
git push origin v1.0                      # push one tag
git push origin --tags                    # push all tags
git tag -d v1.0                           # delete a local tag
```

> ⚠️ Warning: `git push` does **not** push tags by default. Forgetting to push tags is a very common "but I tagged it!" moment.

> 💡 Tip: use annotated tags (`-a`) for real releases. They record who made the release and when, just like a commit. Lightweight tags are fine for private bookmarks.

---

## 23. Safety Nets: reflog, blame, clean

### `git reflog`: the undo button for Git itself

You ran `git reset --hard HEAD~3`. Three commits vanished from `git log`. Panic.

**But are they actually gone?**

No. The commits still exist inside `.git`. Only the branch *stopped pointing at them*.

And Git keeps a private diary of **every place HEAD has pointed**, including after resets, rebases, amends, and branch switches. That diary is the **reflog**.

```bash
git reflog
```

```
e4f5a6b HEAD@{0}: reset: moving to HEAD~3
9a8b7c6 HEAD@{1}: commit: Add PDF footer
...
```

Found the lost commit? Bring it back:

```bash
git reset --hard 9a8b7c6          # move the branch back to it
# or, safer:
git branch rescued 9a8b7c6        # put a new sticky note on it and look first
```

> ⚠️ Warning: the reflog is **local only** (not shared, not on GitHub) and entries eventually expire (by default after about 30–90 days). And it can only rescue work that was **committed** at some point. `git restore` on uncommitted edits is still permanent.

> 💡 Tip: this is the strongest argument for committing often. Anything you've committed is very hard to truly lose.

### `git blame`: who wrote this line, and why?

The tax rate is wrong. **Who changed it, when, and what were they trying to do?**

```bash
git blame src/Tax.java             # every line, with the commit, author, and date that last changed it
git blame -L 10,20 src/Tax.java    # only lines 10–20
```

Then `git show <hash>` on that commit to read its message and full diff.

> 💡 Tip: despite the name, use it to find *context*, not someone to blame. Very often the author was you, eight months ago. That's exactly why good commit messages matter (Section 13).

### `git clean`: delete untracked files

```bash
git clean -n       # DRY RUN: list untracked files that would be deleted
git clean -f       # delete untracked files
git clean -fd      # also delete untracked folders
```

> ⚠️ Warning: untracked files have never been committed, so `clean` deletions **can't** be recovered with reflog or anything else. Always run `-n` first and read the list.

---

## 24. A Real Day-to-Day Workflow

Here's how it all fits together on a normal team using GitHub.

```bash
# 1. Start from an up-to-date main
git switch main
git pull

# 2. One branch per task
git switch -c feature/login-validation

# 3. Work in small steps
#    ...edit code...
git status
git diff
git add -p
git diff --staged
git commit -m "Validate empty username on login form"
#    ...repeat as many times as you like, all private...

# 4. Share the branch
git push -u origin feature/login-validation

# 5. On GitHub: open a Pull Request → teammates review → fix feedback → push again → merge

# 6. Clean up
git switch main
git pull                                  # now includes your merged work
git branch -d feature/login-validation
```

**Why go through a branch and pull request instead of committing straight to `main`?**

Because `main` is the one line everyone builds on, and often what gets deployed.

A branch keeps unfinished work out of it ([Section 17](#17-branches-branch-switch-merge)).

A pull request adds a human check (review) and usually automated checks (tests) before anything touches `main`.

> 💡 Tip: a **Pull Request** (GitLab calls it a **Merge Request**) is a GitHub/GitLab feature, not a Git command ([Section 6](#6-git-vs-github-not-the-same-thing)). Underneath, merging a PR is just `git merge` done on the server.

For a solo learning repo like this one, the simplified loop is completely fine:

```bash
git status
git add .
git commit -m "Add Git notes"
git push
```

---

# Part 4: Wrap-Up

## 25. Conclusion

Go back to the six problems from Section 1 and look at what each one became:

| Problem without version control | Git's answer | Section |
|---------------------------------|--------------|:---:|
| Overwrote Monday's working code | Every commit is a snapshot you can return to | 8, 16 |
| Pile of meaningless copies | One history, each version with an author, date, and message | 8, 12 |
| Friend's save silently wiped my work | Merging, conflicts, and push rejection when histories differ | 17, 18, 19 |
| "Who broke it, and why?" | `log`, `blame`, `show` | 12, 23 |
| Scared to experiment | Branches are free; the main line stays untouched | 9, 17 |
| Laptop / server died | Every clone is a full copy of all history | 5.1 |

And under all of them, the root cause from Section 3 is gone on both sides:

- **History no longer lives in one place.** It lives everywhere the repo is cloned.
- **A save point is no longer expensive.** A commit is instant, local, and private until you push.

If you remember only a few ideas, remember these:

1. **Three areas**: working directory → `add` → staging area → `commit` → repository.
2. **A commit is a snapshot with a parent and a fingerprint.** Commits are never edited, only replaced with new ones.
3. **Branches and HEAD are just pointers.** Branching is cheap, so branch for everything.
4. **Commit ≠ push.** Commit often and privately. Push when ready.
5. **Never rewrite shared history.** `revert` for pushed commits; `reset`, `amend`, `rebase` only for private ones.
6. **When lost, `git status`. When something vanished, `git reflog`.**

Everything else is a detail layered on those six.

---

## 26. Git Cheat Sheet

### Setup

| Command | What it does |
|---------|--------------|
| `git config --global user.name "Name"` | Set the author name for commits |
| `git config --global user.email "you@x.com"` | Set the author email |
| `git config --global init.defaultBranch main` | New repos start on `main` |
| `git config --global core.editor "code --wait"` | Use VS Code for commit messages |
| `git config --global core.autocrlf true` | Windows line-ending handling |
| `git config --list` | Show all settings |

### Create

| Command | What it does |
|---------|--------------|
| `git init` | Make the current folder a repository |
| `git clone <url>` | Copy a remote repo with its full history |
| `git clone <url> <folder>` | Clone into a named folder |

### Inspect (read-only, always safe)

| Command | What it does |
|---------|--------------|
| `git status` / `git status -s` | What's modified, staged, untracked |
| `git diff` | Unstaged changes |
| `git diff --staged` | Staged changes (what the next commit contains) |
| `git diff <a> <b>` | Compare two commits or branches |
| `git log --oneline` | Compact history |
| `git log --oneline --graph --all` | History with branches drawn |
| `git log -p -- <file>` | History of one file, with diffs |
| `git log --follow <file>` | History of a file across renames |
| `git show <hash>` | One commit in full |
| `git blame <file>` | Who last changed each line |

### Stage & commit

| Command | What it does |
|---------|--------------|
| `git add <file>` | Stage a file |
| `git add .` / `git add -A` | Stage everything (folder / whole repo) |
| `git add -p` | Stage chosen pieces interactively |
| `git commit -m "msg"` | Commit staged changes |
| `git commit -am "msg"` | Stage tracked files + commit (skips new files) |
| `git commit --amend` | Replace the last commit (**unpushed only**) |
| `git mv <old> <new>` | Rename and stage |
| `git rm <file>` | Delete and stage |
| `git rm --cached <file>` | Stop tracking, keep the file on disk |

### Undo

| Command | What it does |
|---------|--------------|
| `git restore <file>` | ⚠️ Discard unstaged edits |
| `git restore --staged <file>` | Unstage, keep edits |
| `git revert <hash>` | New commit that undoes `<hash>` (safe for pushed) |
| `git reset --soft HEAD~1` | Undo last commit, keep changes staged |
| `git reset HEAD~1` | Undo last commit, keep changes unstaged |
| `git reset --hard HEAD~1` | ⚠️ Undo last commit and erase its changes |
| `git reflog` | Every place HEAD has been: find "lost" commits |
| `git clean -n` / `git clean -fd` | Preview / ⚠️ delete untracked files and folders |

### Branch & merge

| Command | What it does |
|---------|--------------|
| `git branch` / `git branch -a` | List local / all branches |
| `git branch <name>` | Create a branch |
| `git switch <name>` | Switch to a branch |
| `git switch -c <name>` | Create and switch |
| `git switch -` | Back to the previous branch |
| `git merge <branch>` | Merge `<branch>` into the current branch |
| `git merge --abort` | Cancel a conflicted merge |
| `git branch -d <name>` | Delete a merged branch |
| `git branch -D <name>` | ⚠️ Force-delete a branch |
| `git branch -m <old> <new>` | Rename a branch |

### Remote

| Command | What it does |
|---------|--------------|
| `git remote -v` | List remotes |
| `git remote add origin <url>` | Link a remote |
| `git push -u origin <branch>` | First push + set upstream |
| `git push` | Push commits |
| `git push origin --delete <branch>` | Delete a remote branch |
| `git push --force-with-lease` | Safer force push (**own branches only**) |
| `git fetch` | Download without changing your work |
| `git pull` | Fetch + merge |
| `git pull --rebase` | Fetch + rebase |

### Stash

| Command | What it does |
|---------|--------------|
| `git stash` / `git stash -u` | Shelve changes (/ include untracked) |
| `git stash push -m "msg"` | Shelve with a name |
| `git stash list` | Show the shelf |
| `git stash pop` | Restore latest and remove it |
| `git stash apply` | Restore latest and keep it |
| `git stash drop` | Delete one stash |

### Rebase & tags

| Command | What it does |
|---------|--------------|
| `git rebase main` | Replay current branch on top of `main` (**unshared only**) |
| `git rebase -i HEAD~3` | Squash / reorder / reword last 3 commits |
| `git rebase --continue` / `--abort` | Continue after conflict / cancel |
| `git cherry-pick <hash>` | Copy one commit onto the current branch |
| `git tag -a v1.0 -m "msg"` | Create an annotated tag |
| `git push origin --tags` | Push all tags |

### Reading `git status -s`

| Code | Meaning |
|:---:|---------|
| `??` | Untracked |
| ` M` | Modified, not staged |
| `M ` | Modified and staged |
| `MM` | Staged, then modified again |
| `A ` | New file, staged |
| ` D` / `D ` | Deleted, not staged / staged |
| `R ` | Renamed, staged |
| `UU` | Merge conflict, both modified |

### Shorthand

| Symbol | Means |
|--------|-------|
| `HEAD` | The commit you're on right now |
| `HEAD~1`, `HEAD~2` | One / two commits before HEAD |
| `origin` | Default name of the remote you cloned from |
| `origin/main` | Git's last-known copy of `main` on origin |
| `main..feature` | Commits in `feature` that aren't in `main` |
| `stash@{0}` | Most recent stash |
| `HEAD@{2}` | Where HEAD was two moves ago (from reflog) |

> 💡 Tip: `git <command> --help` opens the full manual for any command. `git status` tells you what to do next more often than you'd expect.

---

## ⚠️ My Mistakes & Gaps

> This section is filled in manually after solving practice questions.
> Do NOT auto-generate this section.

- 

---

*Notes created for the JavaDevelopment learning journey: 03. Git*
