public class Demo {
    public static void main (String A[]) {
        int a = 42;

        Integer boxed = a;

        int unboxed = boxed;

        System.out.println(boxed);
        System.out.println(unboxed);
    }
}