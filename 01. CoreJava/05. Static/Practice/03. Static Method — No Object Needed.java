class MathUtil {
    static int square (int n) {
        return n * n;
    }
}

class StaticMethod {
    public static void main (String A[]) {
        System.out.println(MathUtil.square(7));
    }
}