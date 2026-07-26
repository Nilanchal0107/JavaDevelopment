import java.util.*;

class Demo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));
        Set<Integer>  set  = new HashSet<>(list);
        Set<Integer>  tree = new TreeSet<>(list);

        System.out.println("List size:  " + list.size());
        System.out.println("Set size:   " + set.size());
        System.out.println("Tree first: " + ((TreeSet<Integer>) tree).first());
        System.out.println("Tree last:  " + ((TreeSet<Integer>) tree).last());

        Collections.sort(list);
        System.out.println("Sorted list: " + list);
    }
}

/*

8
7
1
9
[1, 1, 2, 3, 4, 5, 6, 9]

1. Why is `Set size` less than `List size`?
Set doesn't accept dupliacte values while List accepts duplicate values.

2. What is `tree.first()` and `tree.last()`?
tree.first() means smallest value in set i.e. 1 and tree.last() means largest value i.e. 9

3. What does `Collections.sort(list)` produce?
Its produces a sorted list.

*/