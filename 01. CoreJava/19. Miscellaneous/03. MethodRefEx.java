import java.util.*;

class MethodRefEx {
    public static void main (String A[]) {
        List<String> names = Arrays.asList("Navin", "John", "Harsh");

        List<String> unames = names.stream()
                .map(String::toUpperCase)
                .toList();

        System.out.println(unames);
    }
}