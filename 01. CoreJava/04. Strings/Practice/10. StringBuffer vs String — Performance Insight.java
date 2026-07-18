class Performance {
    public static void main (String A[]) {
        String s = "x";
        double startTime = 0, endTime = 0;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            s += "x";
        }
        endTime = System.currentTimeMillis();

        double time = endTime - startTime;

        System.out.println("String Performance: " + time);

        StringBuffer sb = new StringBuffer("x");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            sb.append("x");
        }
        endTime = System.currentTimeMillis();
        
        time = endTime - startTime;

        System.out.println("StringBuffer Performance: " + time);
    }
}

/*
1. Why is `StringBuffer` significantly faster for repeated concatenation?
StringBuffer doesn't need to create a new string object while concatenation while on the othe hand
String need to create new object each time the loop runs

2. How many new String objects are created in the `String` loop?
Each times the loop runs a new string opbject is created.
Since the loop ran a 1000 times.
1000 new string object is created.
*/