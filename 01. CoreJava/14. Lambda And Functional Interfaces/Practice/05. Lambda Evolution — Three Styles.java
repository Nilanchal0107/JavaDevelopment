@FunctionalInterface
interface Showable {
    void show(int i);
}

class Demo {
    public static void main (String A[]) {
        Showable obj1 = new Showable() {
            public void show(int i) {
                System.out.println("in show " + i);
            }
        };
        
        Showable obj2 = (int i) -> System.out.println("in show " + i);

        Showable obj3 = i -> System.out.println("in show " + i);

        obj1.show(5);
        obj2.show(5);
        obj3.show(5);
    }
}