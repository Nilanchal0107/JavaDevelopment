@Deprecated
interface OldProcessor {
    void run();
}

@FunctionalInterface
interface NewProcessor {
    void execute();
}

class ModernTask implements NewProcessor {
    @Override
    public void execute() {
        System.out.println("Executing modern task");
    }
}

class Demo {
    public static void main (String A[]) {
        NewProcessor obj = new ModernTask();
        obj.execute();
    }
}

/*

1. Why is `OldProcessor` deprecated?
Because we had given annotation of @deprecated.
We will replace it with NewProcessor and remove the existence of OldProcessor.
You can use OldProcessor but try to avoid using it.

2. Why is `@FunctionalInterface` useful for `NewProcessor`?
@FunctionInterface makes sure that you are only storing one abstract method in the interface so you can use lambda expressions

3. What does `@Override` guarantee in `ModernTask`?
@Override makes sure you don't misspell function's name.

*/