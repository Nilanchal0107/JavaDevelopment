import java.util.*;
import java.util.stream.*;

class Demo {
    public static void main (String A[]) {
        List<Integer> num = Arrays.asList(1,2,3,4,5,6,7,8);

        Stream<Integer> s1 = num.stream()
                                .filter(n -> n%2==0 );

        s1.forEach(n -> System.out.println(n));
    }
}