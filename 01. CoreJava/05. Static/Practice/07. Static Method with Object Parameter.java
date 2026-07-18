class Student {
    String name;
    int marks;

    static void printReport(Student s) {
        System.out.println(s.name + " : " + s.marks);
    }
}

class Test {
    public static void main (String A[]) {
        Student s1 = new Student();
        s1.name = "Navin";
        s1.marks = 88;

        Student s2 = new Student();
        s2.name = "Kiran";
        s2.marks = 95;

        Student.printReport(s1);
        Student.printReport(s2);
    } 
}