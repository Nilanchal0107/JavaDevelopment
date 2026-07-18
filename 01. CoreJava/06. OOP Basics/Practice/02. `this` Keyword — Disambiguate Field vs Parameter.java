class Rectangle {
    int length, width;

    void setDimensions (int length, int width) {
        this.length = length;
        this.width = width;
    }

    int area() {
        return length * width;
    }
}

class Test {
    public static void main (String A[]) {
        Rectangle a = new Rectangle();
        a.setDimensions(8, 5);
        System.out.println("Area: " + a.area());
    }
}