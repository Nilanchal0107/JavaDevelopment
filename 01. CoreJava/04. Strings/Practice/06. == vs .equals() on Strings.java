class StringCompare {
    public static void main (String A[]) {
        String s1 = "Navin";
        String s2 = "Navin";
        String s3 = new String("Navin");

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1.equals(s3));
    }
}

/*
why `s1 == s2` is `true`?
== operator compares the reference, 
s1 and s2 both point to the same object in the String Pool 
because Java reuses existing pool entries for identical string literals

but `s1 == s3` is `false`
new String("Navin") explicitly creates a new object in the Heap, 
outside the String Pool, regardless of whether an identical string already exists in the pool.
*/