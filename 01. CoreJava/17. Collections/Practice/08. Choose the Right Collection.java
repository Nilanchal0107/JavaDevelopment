import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

class Demo {
    /**
     * @param A
     */
    public static void main (String A[]) {

        // 1. Store a list of names where duplicates are allowed and order matters.
        List<String> names = new ArrayList<String>();
        names.add("Nilanchal");
        names.add("Nilanchal");
        names.add("Binayak");
        names.add("Roshan");
        names.add("Rishabh");

        // 2. Store unique usernames, unordered — fast lookup needed.
        Set<String> name = new HashSet<String>();
        name.add("Nilanchal");
        name.add("Nilanchal");
        name.add("Binayak");
        name.add("Roshan");
        name.add("Rishabh");

        // 3. Store unique scores that should always be retrieved in sorted order.
        Collection<Integer> nums = new TreeSet<Integer>(); 
        nums.add(62);
        nums.add(54);
        nums.add(82);
        nums.add(21);

        // 4. Store student name → grade mappings for fast lookup by name.
        Map<String, Integer> student = new HashMap<String, Integer>();
        student.put("Alice", 85);
        student.put("Bob", 92);
        student.put("Carol", 78);

        System.out.println(names);
        System.out.println(name);
        System.out.println(nums);
        System.out.println(student);

    }
}