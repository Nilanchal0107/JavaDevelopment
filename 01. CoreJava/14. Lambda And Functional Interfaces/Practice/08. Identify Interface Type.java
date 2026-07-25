interface Saveable {
    // Marker interface — no methods, used only to tag/mark a class with metadata
}

@FunctionalInterface
interface Transformable {
    // Functional interface (SAM) — exactly one abstract method, can be implemented with a lambda
    String transform(String s);
}

interface Shape {
    // Normal interface — has more than one abstract method, cannot use lambda
    double area();
    String color();
}

class Demo {
    public static void main (String A[]) {
        Transformable obj = s -> s.toUpperCase();
        System.out.println(obj.transform("Hello Java"));
    }
}