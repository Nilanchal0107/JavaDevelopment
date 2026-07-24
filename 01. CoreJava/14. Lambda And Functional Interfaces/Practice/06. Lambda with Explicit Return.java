@FunctionalInterface
interface Calculator {
    int add (int i, int j);
}

class Demo {
    public static void main (String A[]) {
        Calculator obj1 = (i, j) -> i + j;
        Calculator obj2 = (i, j) -> { return i + j; };

        System.out.println(obj1.add(5,4));
        System.out.println(obj2.add(5,4));
    }
}