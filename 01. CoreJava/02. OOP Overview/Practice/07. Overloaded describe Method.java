class Shape {

    void describe (int side) {
        System.out.println("Square with side: " + side + ", Area: " + (side * side));
    }

    void describe(int length, int width) {
        System.out.println("Rectangle with length: " + length + ", width: " + width + ", Area: " + (length * width));
    }

    void describe(double radius) {
        System.out.println("Circle with radius: " + radius + ", Area: " + (3.14 * radius * radius));
    }
}

class OverloadedMethod {
        public static void main (String A[]) {
            Shape obj = new Shape();

            obj.describe(5);
            obj.describe(4, 5);
            obj.describe(4.5);
    }
}
