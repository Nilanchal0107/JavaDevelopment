interface Saveable {

}

@FunctionalInterface
interface Transformable {
    String transform(String s);
}

interface Shape {
    double area();
    String color();
}

class Demo {
    public static void main (String A[]) {
        Transformable obj = s -> s.toUpperCase();
        System.out.println(obj.transform("Hello Java"));
    }
}