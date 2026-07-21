class Shape {
    void draw() {
        System.out.println("Drawing Shape");
    }
}

class Circle extends Shape {
    void draw() {
        System.out.println("Drawing Circle");
    }
}

class Rectangle extends Shape {
    void draw() {
        System.out.println("Drawing Rectangle");
    }
}

class Triangle extends Shape {
    void draw() {
        System.out.println("Drawing Triangle");
    }
}

class DynamicDispatch {
    public static void main (String A[]) {

        Shape obj1 = new Circle();
        Shape obj2 = new Rectangle();
        Shape obj3 = new Triangle();

        Shape shape[] = new Shape[3];
        shape[0] = obj1;
        shape[1] = obj2;
        shape[2] = obj3;

        for (Shape obj : shape) {
            obj.draw();
        }
    }
}