import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Student {
    int age;
    String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public String toString() {
        return "Student [age=" + age + ", name=" + name + "]";
    }
}

class Demo {
    public static void main (String A[]) {
        List<Student> studs = new ArrayList<Student>();

        Comparator<Student> com = (i, j) -> i.age > j.age ? 1 : -1;

        studs.add(new Student(12, "John"));
        studs.add(new Student(18, "Parul"));
        studs.add(new Student(20, "Kiran"));
        studs.add(new Student(21, "Navin"));

        Collections.sort(studs, com);
        for(Student s: studs)
            System.out.println(s);
    }
}