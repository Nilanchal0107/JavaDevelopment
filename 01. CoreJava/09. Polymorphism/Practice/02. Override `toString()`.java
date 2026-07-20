class Student {
    String name = "Navin";
    int marks = 100;

    public String toString() {
        return name + " : " + marks;
    }
}

public class Demo {
    public static void main (String A[]) {
        Student obj = new Student();
        System.out.println(obj);
    }
}