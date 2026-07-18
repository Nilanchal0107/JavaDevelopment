class Immutability {
    public static void main (String A[]) {
        String name = "navin";
        name = name + "reddy";
        System.out.println("hello" + name);
    }
}

/*
why the original `"navin"` object is not changed even though `name` now holds `"navinreddy" `
String name = "navin", "navin" get created in String Pool which is pointed by name.
name + "reddy" doesn't necessarily create the new string in the String Pool 
— concatenation using + at runtime creates a new String object in the Heap, not the pool. 
Only compile-time constant strings go into the pool automatically.
"navin" is still stored in String pool and get used by java when it is needed.

String name only changes it reference not the actual value it holds

*/