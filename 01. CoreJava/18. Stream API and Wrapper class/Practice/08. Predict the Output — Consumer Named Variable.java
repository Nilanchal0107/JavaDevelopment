import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

class Demo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(4, 5, 7, 3, 2, 6);

        Consumer<Integer> con = n -> System.out.println(n * n);

        nums.forEach(con);
        nums.forEach(n -> System.out.println(n + 10));
    }
}

/*

16
25
49
9
4
36
14
15
17
13
12
16

*/