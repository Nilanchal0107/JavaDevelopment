class Counter {
    static int count = 0;

    Counter() {
        count++;
    }

    static int getCount() {
        return count;
    }
}

class CountObject {
    public static void main (String A[]) {
        new Counter();
        System.out.println(Counter.getCount());

        new Counter();
        System.out.println(Counter.getCount());

        new Counter();
        System.out.println(Counter.getCount());
    }
}