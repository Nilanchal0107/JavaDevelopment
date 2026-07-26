import java.util.HashMap;
import java.util.Map;

class Demo {
    public static void main (String A[]) {
        Map<String, Integer> students = new HashMap<String, Integer>();

        students.put("Alice", 85);
        students.put("Bob", 92);
        students.put("Carol", 78);

        System.out.println(students.keySet());

        for (String key : students.keySet())
            System.out.println(key + ":" + students.get(key));
    }
}