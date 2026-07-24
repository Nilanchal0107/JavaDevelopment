enum Direction {
    North, South, East, West;
}

class Demo {
    public static void main (String A[]) {
        Direction e = Direction.East;
        System.out.println(e);
        System.out.println(e.ordinal());
    }
}