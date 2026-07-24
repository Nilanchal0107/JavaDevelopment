@FunctionalInterface
interface Op {
    int compute(int x);
}

public class Demo {
    static int apply(Op op, int value) {
        return op.compute(value);
    }

    public static void main(String[] args) {
        Op doubler = x -> x * 2;
        Op squarer = x -> x * x;
        Op chain   = x -> doubler.compute(squarer.compute(x));

        System.out.println(apply(doubler, 5));
        System.out.println(apply(squarer, 5));
        System.out.println(apply(chain, 3));
        System.out.println(doubler.compute(3) + squarer.compute(3));
    }
}

/*

10
25
18
15

1. What does `chain.compute(3)` do step by step?
chain.compute(3)
→ squarer.compute(3)
→ 3 * 3 = 9
→ doubler.compute(9)
→ 9 * 2 = 18

2. What is `doubler.compute(3) + squarer.compute(3)`?
doubler.compute(3) = 6, squarer.compute(3) = 9;
6 + 9 = 15

3. Is `Op` a valid `@FunctionalInterface`? Why?
Op is a valid Functional Interface beacuse there is only single abstract method

*/