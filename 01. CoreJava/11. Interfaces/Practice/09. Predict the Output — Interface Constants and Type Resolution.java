interface A {
    int value = 10;
    void show();
}

interface B extends A {
    int value = 20;
}

class C implements B {
    public void show() {
        System.out.println("A.value = " + A.value);
        System.out.println("B.value = " + B.value);
    }
}

class Demo {
    public static void main(String[] args) {
        A obj = new C();
        obj.show();
        System.out.println(obj instanceof B);
        System.out.println(A.value);
        System.out.println(B.value);
    }
}

/*

A.value = 10
B.value = 20
true
10
20

1. `C` implements `B` which extends `A`. Does `C` also satisfy `instanceof A`?
Yes C also satisfy instanceof A since it had implemented B which was extended from A.

2. Both `A` and `B` define `value`. Why is there no ambiguity when accessing them in `show()`?
there's no ambiguity because you're accessing them explicitly by interface name — A.value and B.value. 
The compiler knows exactly which constant you mean because you specified the interface. 
Ambiguity would only occur if you wrote just value without qualification inside C, 
which would cause a compile error because the compiler can't determine which one you mean.

3. If you tried `System.out.println(C.value)`, which `value` would you get and why?
It's because C directly implements B, so B is the more specific interface. 
When the compiler resolves C.value it finds B.value first through the direct implementation relationship.

*/