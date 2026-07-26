import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

class Demo {
    public static void main (String A[]) {
        Collection<Integer> nums = new TreeSet<Integer>(); 

        nums.add(62);
        nums.add(54);
        nums.add(82);
        nums.add(21);

        Iterator<Integer> values = nums.iterator();

        while(values.hasNext())
            System.out.println(values.next());
    }
}