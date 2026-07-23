abstract class Shape {

    public abstract double area();

    public void describe() {
        System.out.println("I am a shape");
    }
}

class Circle extends Shape {
    public double radius;
    public double area () {
        return 3.14 * radius * radius;
    }
}

class Demo {
    public static void main (String A[]) {
        Circle obj = new Circle();
        obj.radius = 5.0;

        obj.describe();
        System.out.println(obj.area());

    }
}