import java.util.*;
import java.util.stream.*;

class Demo {
    public static void main (String A[]) {
        List<String> num = new ArrayList<String>();
        num.add("3");
        num.add("7");
        num.add("1");
        num.add("9");
        num.add("2");
        num.add("4");
        num.add("8");
        num.add("6");
        num.add("5");
        num.add("10");

        int result= num.stream()
                                .map(n -> Integer.parseInt(n))
                                .filter(n -> n > 5)
                                .map(n -> n*n)
                                .reduce(0, (c,e) -> c+e);

        System.out.println(result);
    }
}

/* 

- What is the exact result and why?
Numbers > 5: 7, 9, 8, 6, 10
Squares: 49, 81, 64, 36, 100
Sum: 49+81+64+36+100 = 330

- What happens if one String in the list is `"abc"` — at which point does it fail and why?
The exact exception is NumberFormatException, 
thrown by Integer.parseInt("abc") specifically at the .map() step for that element — because "abc" cannot be converted to a number. 
Important detail: streams process lazily but .map() still executes per-element in sequence, 
so it will process any elements before "abc" successfully, 
then throw the moment it reaches "abc" in the parse step — it never even reaches the filter() or 
later steps for that failing element.

*/
