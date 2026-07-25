class Demo {
    static void method1() throws Exception {
        try {
            System.out.println("method1 try");
            method2();
            System.out.println("method1 after method2");
        } catch (ArithmeticException e) {
            System.out.println("method1 catch: " + e.getMessage());
        } finally {
            System.out.println("method1 finally");
        }
    }

    static void method2() throws Exception {
        System.out.println("method2 start");
        throw new ArithmeticException("bad math");
    }

    public static void main(String[] args) {
        try {
            method1();
            System.out.println("main after method1");
        } catch (Exception e) {
            System.out.println("main catch: " + e.getMessage());
        }
        System.out.println("main end");
    }
}

/* *

method1 try
method2 start
method1 catch: bad math
method1 finally
main after method1
main end

1. Does "method1 after method2" print? 
No — method2() throws before that line executes.

2. Which catch handles it? 
method1's own catch block — it specifically catches ArithmeticException, which matches exactly what method2() throws. 
The exception never reaches main.

3. Does "main after method1" print? 
Yes — method1() handled its own exception and returned normally, 
so execution in main continues past the method1() call.
4. Does "main end" print? 
Yes — no exception reached main, so its try-catch isn't triggered, 
and execution proceeds to the final println normally.

*/