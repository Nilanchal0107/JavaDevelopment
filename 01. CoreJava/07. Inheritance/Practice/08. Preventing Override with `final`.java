class Base {
    final void show() {
        System.out.println("Base show");
    }
}

class Child extends Base {
    // void show() {
    //     System.out.println("Child show");
    // }
    // Uncommenting the above causes a compile error:
    // "show() in Child cannot override show() in Base; overridden method is final"
}

class Test {
    public static void main (String A[]) {
        Child obj = new Child();
        obj.show();
    }
}

/* 

final keyword in Base Class for show() method indicates that it is the final method.
It can't be get overriden by child class and hence we face a compile error

*/