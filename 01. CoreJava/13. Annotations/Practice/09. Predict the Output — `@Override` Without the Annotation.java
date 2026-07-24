class Base {
    public void process() {
        System.out.println("Base process");
    }
}

class Child extends Base {
    // No @Override annotation
    public void proces() {   // typo: 'proces' not 'process'
        System.out.println("Child process");
    }
}

public class Demo {
    public static void main(String[] args) {
        Base obj = new Child();
        obj.process();
    }
}

/* 

Base process

1. Does this compile? What is the output?
The code will compile and the output will be 'Base process'.

2. Is `Child.proces()` an override or a new method?
Child.proces() is a new method

3. What does dynamic dispatch do here — which `process()` runs?
parent class's process() is run.

Now rewrite `Child.proces()` with `@Override` and explain what happens.
It will show compile error

*/
