interface Shape {
    double area();
}

class Circle implements Shape {
    public double r;
    public double area() {
        return 3.14 * r * r;
    }
}

class Rectangle implements Shape {
    double l;
    double w;
    public double area() {
        return l * w;
    }
}

class Demo {
    public static void main (String A[]) {
        Circle obj1 = new Circle();
        obj1.r = 4.5;

        Rectangle obj2 = new Rectangle();
        obj2.l = 10.0;
        obj2.w = 4.0;

        Circle obj3 = new Circle();
        obj3.r = 9.5;

        Shape[] shape = new Shape[3];
        shape[0] = obj1;
        shape[1] = obj2;
        shape[2] = obj3;

        for (Shape obj: shape) {
            System.out.println(obj.area());
        }
    }
}