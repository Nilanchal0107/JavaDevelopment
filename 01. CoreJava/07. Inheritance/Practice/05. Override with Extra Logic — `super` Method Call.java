class Person {
    void greet() {
        System.out.println("Hello, I am a Person.");
    }
}

class Student extends Person {
    void greet() {
        super.greet();
        System.out.println("Hello, I am a Student.");
    }
}

class Test {
    public static void main (String A[]) {
        Student s1 = new Student();
        s1.greet();
    }
}