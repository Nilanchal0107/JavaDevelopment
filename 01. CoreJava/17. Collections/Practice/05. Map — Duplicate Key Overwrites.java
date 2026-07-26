import java.util.HashMap;
import java.util.Map;

class Demo {
    public static void main (String A[]) {
        Map<String, Integer> student = new HashMap<String, Integer>();

        student.put("Harsh",23);
        student.put("Harsh",45);

        System.out.println(student);
    }
}