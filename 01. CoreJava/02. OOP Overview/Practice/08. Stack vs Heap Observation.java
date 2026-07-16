class Counter {
    int count;

    void increment() {
        count++;
    }
}

class StackVsHeap {
    public static void main (String A[]) {
        Counter c1 = new Counter();
        Counter c2 = new Counter();

        c1.increment();
        c1.increment();
        c1.increment();

        c2.increment();

        System.out.println("c1 count: " + c1.count);
        System.out.println("c2 count: " + c2.count);
    }
}

/* 
When you do new Counter(), Java allocates space for that object's instance variables (like count) in the Heap. 
The Stack only holds the reference variables (c1, c2) that point to those Heap locations.
So c1 and c2 are two separate references on the Stack, each pointing to two completely separate Counter objects in the Heap. 
When you call c1.increment(), it modifies only the count field of the first Heap object. 
c2's Heap object is untouched.
*/