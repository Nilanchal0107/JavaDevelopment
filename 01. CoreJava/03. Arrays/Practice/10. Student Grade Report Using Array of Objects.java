class Student {
    String name;
    int rollno, marks;

    String getGrade() {
        if (marks >= 90) {
            return "A";
        }
        else if (marks >= 75) {
            return "B";
        }
        else if (marks >= 60) {
            return "C";
        }
        else if (marks >= 45) {
            return "D";
        }
        else {
            return "F";
        }
    }
}

class StudentGrade {
    public static void main (String A[]) {
        Student s1 = new Student();
        s1.name = "Navin";
        s1.rollno = 1;
        s1.marks = 88;

        Student s2 = new Student();
        s2.name = "Harsh";
        s2.rollno = 2;
        s2.marks = 67;

        Student s3 = new Student();
        s3.name = "Kiran";
        s3.rollno = 3;
        s3.marks = 97;

        Student s4 = new Student();
        s4.name = "Priya";
        s4.rollno = 4;
        s4.marks = 42;

        Student s5 = new Student();
        s5.name = "Arun";
        s5.rollno = 5;
        s5.marks = 75;

        Student apsit[] = new Student[5];
        apsit[0] = s1;
        apsit[1] = s2;
        apsit[2] = s3;
        apsit[3] = s4;
        apsit[4] = s5;

        System.out.print("Roll No\t | Name\t | Marks | Grade \n");

        String topper = apsit[0].name;
        int topperMarks = apsit[0].marks;
        int failed = 0 ;

        for (Student s : apsit) {
            System.out.print(s.rollno + "\t |" + s.name + "  |" + s.marks + "\t | " + s.getGrade() + "\n");

            if (topperMarks < s.marks) {
                topper = s.name;
                topperMarks = s.marks;
            }

            if (s.getGrade().equals("F")) {
                failed++;
            }
        }

        System.out.println("Topper: " + topper);
        System.out.println("Number of failed students: " + failed);

    }
}