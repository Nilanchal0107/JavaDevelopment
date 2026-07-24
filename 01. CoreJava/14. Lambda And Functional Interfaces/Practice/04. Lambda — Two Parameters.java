@FunctionalInterface
interface MathOp {
    int operate(int a, int b);
}

class Demo {
    public static void main (String A[]) {
        MathOp add = (a,b) -> a+b;
        MathOp sub = (a,b) -> a-b;
        MathOp multi = (a,b) -> a*b;

        System.out.println(add.operate(10,3));
        System.out.println(sub.operate(10,3));
        System.out.println(multi.operate(10,3));
    }
}