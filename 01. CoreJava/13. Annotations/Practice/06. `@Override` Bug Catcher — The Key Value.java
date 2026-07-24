class A {
    void showTheDataWhichBelongsToThisClass() {
        System.out.println("in show A");
    }
}

class B extends A {
    @Override
    void showTheDataWhichBelongsToThisClass() {
        System.out.println("in show B");
    }
    // Without @Override a new method is silenty created not an override
}

class Demo {
    public static void main(String A[]) {
        B obj = new B();
        obj.showTheDataWhichBelongsToThisClass();
    }
}