import java.util.*;

class OptionalEx {
    public static void main (String A[]) {
        List<String> names = Arrays.asList("Navin", "John", "Kishor");

        String name = names.stream()
                .filter(str -> str.contains("x"))
                .findFirst()
                .orElse("Not Found");

        System.out.println(name);
    }
}