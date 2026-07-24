class A {
    public void show() {
        System.out.println("A");
    }
}

class B extends A {
    @Override
    public void show() {
        System.out.println("B");
    }
}

class C extends B {
    @Override
    public void show() {
        System.out.println("C");
    }
}

class Demo {
    public static void main (String A[]) {
        C obj = new C();
        obj.show();
    }
}