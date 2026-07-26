import java.util.List;
import java.util.ArrayList;

class Demo {
    public static void main (String A[]) {
        List<String> Language = new ArrayList<String>();

        Language.add("Java");
        Language.add("Python");
        Language.add("C++");
        Language.add("JavaScript");

        System.out.println(Language.get(1));        
        System.out.println(Language.indexOf("C++"));     

        for (String i : Language) {
            System.out.println(i);
        }   

    }
}