import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Student implements Comparable<Student>{
    int age;
    String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public String toString() {
        return "Student [age=" + age + ", name=" + name + "]";
    }

    public int compareTo(Student that) {
        if(this.age > that.age)
            return 1;
        else
            return -1;
    }
}

class Demo {
    public static void main (String A[]) {
        List<Student> studs = new ArrayList<Student>();

        studs.add(new Student(12, "John"));
        studs.add(new Student(18, "Parul"));
        studs.add(new Student(20, "Kiran"));
        studs.add(new Student(21, "Navin"));

        Collections.sort(studs);
        for(Student s: studs)
            System.out.println(s);
    }
}

// Comparable: defines the natural/default sorting order INSIDE the class itself (only one way to sort).
// Comparator: defines sorting logic OUTSIDE the class, allows multiple different sort orders (by age, by name, etc.)
// Use Comparable when there's one obvious default order. Use Comparator when you need flexible/multiple sort criteria.