import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(2, 4, 6, 8);

        Stream<Integer> s = nums.stream().filter(n -> n > 3);

        System.out.println("First use:");
        s.forEach(n -> System.out.println(n));

        System.out.println("Second use:");
        s.forEach(n -> System.out.println(n));  // Line X
    }
}

/* 

4
6
8

1. What does `Line X` do — does it print correctly, print nothing, or throw an exception?
It will throw an error of stream has already been used.

2. What is the exact exception name thrown (if any)?
IllegalStateException

3. Why does Java enforce this rule for streams? How does it differ from a `List` which can be iterated multiple times?
A List stores data persistently in memory — you can iterate it as many times as you want because the data just sits there. 
A Stream is not a data structure — it's a one-time pipeline of operations that gets consumed as it executes. Once a terminal operation (like forEach) runs, 
the stream is considered "closed" and cannot be reused, regardless of whether you tried to change anything.

*/