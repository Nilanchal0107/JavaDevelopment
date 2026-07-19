import java.util.*;
import java.util.ArrayList;

class Test {
    public static void main (String A[]) {
        ArrayList<String> names = new ArrayList<>();

        names.add("Navin");
        names.add("Reddy");
        names.add("Java");

        for (String a : names) {
            System.out.println(a);
        }
    }
}