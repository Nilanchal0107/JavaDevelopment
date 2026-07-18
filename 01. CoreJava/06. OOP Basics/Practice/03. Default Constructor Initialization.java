class Student {
    String name;
    int rollno;

    Student() {
        name = "Unknown";
        rollno = 0;
    }
}

class Default {
    public static void main (String A[]) {
        Student s1 = new Student();
        System.out.println(s1.name + " : " + s1.rollno);
    }
}