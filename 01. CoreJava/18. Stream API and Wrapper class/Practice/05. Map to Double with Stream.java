import java.util.*;
import java.util.stream.*;

class Demo {
    public static void main (String A[]) {
        List<Integer> num = Arrays.asList(3,6,9);
        Stream<Integer> s1 = num.stream()
                                .map(n -> n*3);

        s1.forEach(n -> System.out.println(n));
    }
}