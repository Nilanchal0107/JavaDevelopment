class Base {
    private int a = 1;
    int b = 2;            // default
    protected int c = 3;
    public int d = 4;
}

class Child extends Base {
    void printValues() {
    // System.out.println(a); // a is private and only accessible in same Class Base
    System.out.println(b);    // b is default and accessible in same package
    System.out.println(c);    // c is protected and it is accessible in same package as well as to subclass from different packages
    System.out.println(d);    // d is public and is accessible everwhere
}
}

class Other {
        void printValues() {
        Base obj = new Base();
        // System.out.println(obj.a); // does this compile?
        System.out.println(obj.b);    // default and accessible in same package
        System.out.println(obj.c);    // protected and accessible in same package
        System.out.println(obj.d);    // public and is accessible everwhere
    }
}

class Test {
    public static void main (String A[]) {
        new Other().printValues();
        new Child().printValues();
    }
}