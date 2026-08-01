class Demo {
    public static void main (String A[]) {
        int num = 42;
        Integer boxed = num;
        int num1 = boxed.intValue();

        System.out.println(boxed);
        System.out.println(num1);
    }
}