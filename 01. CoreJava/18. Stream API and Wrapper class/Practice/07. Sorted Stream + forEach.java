import java.util.*;
import java.util.stream.*;

class Demo {
    public static void main (String A[]) {
        List<Integer> num = Arrays.asList(9,1,5,3,7);
        Stream<Integer> s1 = num.stream()
                        .sorted();
        s1.forEach(n -> System.out.println(n));

        Stream<Integer> s2 = num.parallelStream()
                        .sorted();
        s2.forEach(n -> System.out.println(n));
    }
}