class A {
    public void show1() {
        System.out.println("A show");
    }
}

class B extends A {
    public void show2() {
        System.out.println("B show");
    }
}

class Demo {
    public static void main (String A[]) {
        A obj = new B();
        obj.show1();
        
        if (obj instanceof B) {
            B obj1 = (B) obj;
            obj1.show2();
        }
        
        A obj2 = new A();
        System.out.println(obj2 instanceof B);
        
    }
}