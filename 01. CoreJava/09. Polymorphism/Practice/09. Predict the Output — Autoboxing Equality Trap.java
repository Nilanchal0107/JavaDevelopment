class Demo {
    public static void main(String[] args) {
        Integer a = 127;
        Integer b = 127;
        Integer c = 128;
        Integer d = 128;

        System.out.println(a == b);       // true
        System.out.println(c == d);       // false
        System.out.println(c.equals(d));  // true
    }
}

/*

Why does Line 1 print true but Line 2 prints false?
Integer can cache value from -128 to 127. And to store value outside this range, java creates a new object for same value, == compares reference point. 127 are store in same reference point while 128 is store in two different refference point

What is the Integer cache in Java, and what range does it cover?
Integer cache  consists of pre-existing stored value and it covers range from -128 to 127

Why should you always use .equals() instead of == when comparing wrapper objects?
== compares reference point.
If any value doesn't falls under the range of the cache limit of the wrapper, it will create new object for same values.
.equals() compares exact value instead of reference point


*/