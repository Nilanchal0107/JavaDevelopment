class A { 
    void show() { 
        System.out.println("A"); 
    } 
}

class B extends A { 
    void show() { 
        System.out.println("B"); 
    } 
}

class C extends A { 
    void show() { 
        System.out.println("C"); 
    } 
}

// class D extends B, C { 
//     void show() { 
//         System.out.println("D"); 
//     } 
// }

class Test {
    public static void main (String A[]) {
        B obj1 = new B();
        obj1.show();

        C obj2 = new C();
        obj2.show();
    }
}

/* 

If `D` could extend both `B` and `C`, and you called `new D().show()`,
which `show()` would run and why this causes the Diamond Problem.

Inheriting two classes to one class at one time create a diamond-shaped inheritance diagram.
If both parent have same method then it cause ambiguity
Hence java supports multi-level inheritance but not multiple-inheritance


Explain how Java resolves this for interfaces
Java allows a class to implement multiple interfaces because interfaces (before Java 8) had no method bodies — 
just signatures — so there's no ambiguity about which implementation to inherit.

*/
