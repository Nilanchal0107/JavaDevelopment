class Rectangle {
    int area (int length, int width) {
        return length * width;
    }
}

class MethodReturnsValue {
    public static void main (String A[]) {
        Rectangle obj = new Rectangle();
        int area = obj.area (8, 5);

        System.out.println("Area of Rectangle: " + area);
    }
}
