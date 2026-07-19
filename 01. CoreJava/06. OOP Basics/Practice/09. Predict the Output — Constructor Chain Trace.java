class A {
    public A() {
        super();
        System.out.println("in A");
    }
    public A(int n) {
        super();
        System.out.println("in A int");
    }
}
class B extends A {
    public B() {
        super();
        System.out.println("in B");
    }
    public B(int n) {
        this();
        System.out.println("in B int");
    }
}
public class Demo {
    public static void main(String[] args) {
        B obj = new B(5);
    }
}

/*

in A
in B
in B int

List the exact sequence of constructor calls (with arrows showing the chain).
B(int n) → this() → B() → super() → A() → super() → Object()

Why does this() in B(int n) cause B() to run, which then calls A()?
this() runs the default constructor.
B() calls super() leads to call default constructor of super class i.e A


If you replaced this() with super() in B(int n), what would the output be?
in A
in B int

*/