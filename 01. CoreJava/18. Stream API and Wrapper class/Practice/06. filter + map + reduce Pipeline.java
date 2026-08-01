import java.util.*;
import java.util.stream.*;

class Demo {
    public static void main (String A[]) {
        List<Integer> num = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        int result = num.stream()
                        .filter(n -> n%2!=0)
                        .map(n -> n*n)
                        .reduce(0, (c,e) -> c+e);

        System.out.println(result);
    }
}