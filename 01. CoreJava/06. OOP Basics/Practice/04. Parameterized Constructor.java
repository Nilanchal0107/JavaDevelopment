class Student {
    String name;
    int rollno;

    Student() {
        name = "Unknown";
        rollno = 0;
    }

    Student(int rollno, String name) {
        this.name = name;
        this.rollno = rollno;
    }
}

class Parameterized {
    public static void main (String A[]) {
        Student s1 = new Student();
        System.out.println(s1.name + " : " + s1.rollno);

        Student s2 = new Student(1, "Navin");
        System.out.println(s2.name + " : " + s2.rollno);
    }
}