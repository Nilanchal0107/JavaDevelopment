@Deprecated
class LegacyCode {
    public void compute() {
        System.out.println("Computing...");
    }
}

class Demo {
    public static void main (String A[]) {
        LegacyCode comp = new LegacyCode();
        comp.compute();
    }
}

// Annotations do not change what the code does — 
// they provide information to the compiler and tools.