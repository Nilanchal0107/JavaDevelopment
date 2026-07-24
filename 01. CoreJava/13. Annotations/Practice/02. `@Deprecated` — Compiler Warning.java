class OldCalculator {
    @Deprecated
    public int add(int a, int b) {
        return a + b;
    }
}

class NewCalculator {
    public int add(int a, int b) {
        return a + b;
    }
}

class Demo {
    public static void main (String A[]) {
        OldCalculator obj1 = new OldCalculator();
        System.out.println(obj1.add(3,4));


        NewCalculator obj2 = new NewCalculator();
        System.out.println(obj2.add(3,4));
    }
}

/*

Note: .\02. `@Deprecated` ù Compiler Warning.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.

*/