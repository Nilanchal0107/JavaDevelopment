class A {
    static int x = 10;
    static {
        x = 20;
        System.out.println("Static block A, x = " + x);
    }
    A() {
        System.out.println("Constructor A");
    }
}

class B extends A {
    static {
        System.out.println("Static block B");
    }
    B() {
        System.out.println("Constructor B");
    }
}

class Demo {
    public static void main(String[] args) {
        System.out.println("main started");
        B obj1 = new B();
        B obj2 = new B();
    }
}

/*

Static block A, x = 20
Static block B
Constructor A
Constructor B
Constructor A
Constructor B

1. In what order do static blocks run when inheritance is involved?
First static block of A runs and then static block of B runs.

2. How many times does each static block run?
When we construct new B() for the first time one time static block of each class runs.
Even though we constructed two B objects the statc block will run only once.

3. What is the value of `A.x` after the static block runs?
A.x = 20 since it get changed in static block of A

*/