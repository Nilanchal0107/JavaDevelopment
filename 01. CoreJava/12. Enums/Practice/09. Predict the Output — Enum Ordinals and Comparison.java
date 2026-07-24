enum Season { Spring, Summer, Autumn, Winter; }

public class Demo {
    public static void main(String[] args) {
        Season s1 = Season.Summer;
        Season s2 = Season.Summer;
        Season s3 = Season.Winter;

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1.equals(s2));
        System.out.println(s3.ordinal());
        System.out.println(Season.values().length);

        for (Season s : Season.values()) {
            if (s.ordinal() % 2 == 0)
                System.out.println(s.name());
        }
    }
}

/* 

true
false
true
3
4
Spring
Autumn

1. Why is `s1 == s2` true even though they are objects?
s1 and s2 are constants.
They are reffered to same memory allocation.
That's why it's show true.

2. What does `s3.ordinal()` return?
It will return int 3.

3. Which constants are printed by the loop and why?
Spring and Autumn has ordinal 0 and 2 which are divisible by 2.
The condition was that the modulus of remainder should be 0 which is in case of Spring and Autumn.


*/