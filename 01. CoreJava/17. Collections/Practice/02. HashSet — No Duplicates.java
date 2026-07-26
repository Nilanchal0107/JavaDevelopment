import java.util.Set;
import java.util.HashSet;

class Demo {
    public static void main (String A[]) {
        Set<Integer> nums = new HashSet<Integer>();

        nums.add(5);
        nums.add(3);
        nums.add(8);
        nums.add(5);
        nums.add(3);
        nums.add(10);

        for (int i : nums) {
            System.out.println(i);
        }
    }
}