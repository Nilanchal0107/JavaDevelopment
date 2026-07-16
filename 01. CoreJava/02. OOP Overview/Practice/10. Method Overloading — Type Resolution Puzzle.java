class Resolver {

    public void show(int a) { 
        System.out.println("int: " + a);         
    }

    public void show(double a) { 
        System.out.println("double: " + a);      
    }
    public void show(int a, int b) { 
        System.out.println("int, int: " + a + ", " + b); 
    }

    public void show(double a, int b) { 
        System.out.println("double, int: " + a + ", " + b); 
    }
}

class MethodOverloading {
    public static void main (String A[]) {
        Resolver r = new Resolver();
        r.show(5); // int: 5
        r.show(5.0); // double: 5.0
        r.show(5, 10); // int, int: 5, 10
        r.show(5.5, 3); // double, int: 5.5, 3
        r.show('A');         // char gets promoted to int — public void show(int a) method is called.
        r.show(5, 3.0);      // Compile Error
    }
}

/*
Why does 'A' not call a show(char) method?
It does not call a show(char) method because the Resolver class does not define a method that accepts a char parameter.
When Java cannot find an exact type match for a method call, it performs widening primitive conversion (automatic type promotion). 
A char data type can be automatically promoted to a wider numerical type. 
Since int is the closest available wider primitive type in the Resolver class, 
the character 'A' is promoted to its ASCII/Unicode integer value (65), 
which perfectly executes the show(int a) method.

Which method does r.show(5, 3.0) resolve to and why?
The compiler searches the Resolver class for a matching method signature:show(int, int): 
Cannot match because a double (3.0) cannot be automatically narrowed down to an int (Java prevents automatic lossy conversions).

show(double, int): Cannot match because the first parameter expects a double, but the second parameter expects an int. 
While the first argument 5 (int) can be promoted to a double, the second argument 3.0 (double) cannot be downcast to match the required int

What is the term for this compile-time resolution of method calls?
compile-time static polymorphism
*/