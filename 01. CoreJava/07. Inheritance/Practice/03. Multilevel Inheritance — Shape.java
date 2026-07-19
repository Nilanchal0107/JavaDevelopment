class Shape {
    void draw() {
        System.out.println("Drawing shape");
    }
}

class TwoD extends Shape {
    void area() {
        System.out.println("Calculating 2D area");
    }
}

class Circle extends TwoD {
    void circumference() {
        System.out.println("Calculating circumference");
    }
}

class Test {
    public static void main (String A[]) {
        Circle obj = new Circle();
        obj.draw();
        obj.area();
        obj.circumference();
    }
}