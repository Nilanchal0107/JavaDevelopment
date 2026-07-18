class StringPool {
    public static void main (String A[]) {
        String a = "Hello";
        String b = "Hello";
        String c = new String("Hello");
        String d = new String("Hello");

        System.out.println(a == b); // true
        System.out.println(a == c); // false
        System.out.println(c == d); // false
        System.out.println(a.equals(c)); // true
        System.out.println(c.equals(d)); // true
        System.out.println(a.hashCode() == c.hashCode()); // true
    }
}

/*

1. Why does `a == b` return `true` but `a == c` return `false`?
== compares the reference point that is memory address.
new String("Hello") creates the object in the Heap, not the String Pool. 
That's the entire reason a == c is false. 
If c were in the String Pool it would point to the same object as a and == would return true

2. Why do `a.hashCode()` and `c.hashCode()` return the same value even though they are different objects?
String.hashCode() is computed from the content of the string using a mathematical formula based on the characters. 
That's why two completely separate String objects with the same content return the same hashCode — content-based, not address-based. 
Memory address based hashCode is the default for Object class, but String overrides it.

3. What does this tell you about when to use `==` vs `.equals()`?
When you want to compare memory address of string then use == .
Use .equals() when you want to compare actual string.
*/