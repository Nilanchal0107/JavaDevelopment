class Student {
    String name;
    int marks1, marks2, marks3;

    int getTotal() {
        return marks1 + marks2 + marks3;
    }

    double getAverage() {
        return (double)getTotal() / 3 ;
    }

    String getGrade() {
        double avg = getAverage();
        if (avg >= 90) {
            return "A" ;
        }
        else if (avg >= 75) {
            return "B" ;
        }
        else if (avg >= 60) {
            return "C" ;
        }
        else if (avg >= 45) {
            return "D" ;
        }
        else {
            return "F" ;
        }
    }

    void printReport() {
        System.out.println("Name : " + name);
        System.out.println("Total : " + getTotal());
        System.out.println("Average : " + getAverage());
        System.out.println("Grade : " + getGrade());
    }


 }

class ReportCard {
    public static void main (String A[]) {
        Student obj1 = new Student();
        Student obj2 = new Student();
        Student obj3 = new Student();

        obj1.name = "Nilanchal";
        obj2.name = "Binayak";
        obj3.name = "Rishabh";

        obj1.marks1 = 100;
        obj2.marks1 = 90;
        obj3.marks1 = 99;

        obj1.marks2 = 90;
        obj2.marks2 = 99;
        obj3.marks2 = 100;

        obj1.marks3 = 99;
        obj2.marks3 = 100;
        obj3.marks3 = 90;

        obj1.printReport();
        obj2.printReport();
        obj3.printReport();
    }
}
