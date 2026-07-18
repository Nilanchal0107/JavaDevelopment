class Box {
    int length, width, height;

    Box() {
        length = 1;
        width = 1;
        height = 1;
    }

    Box(int side) {
        this();
        this.length = side;
        this.width = side;
        this.height = side;
    }

    Box(int length, int width, int height) {
        this();
        this.length = length;
        this.width = width;
        this.height = height;
    }

    int volume() {
        return length * width * height;
    }
}

class Test {
    public static void main (String A[]) {
        System.out.println("Volume: " + new Box().volume());
        System.out.println("Volume: " + new Box(5).volume());
        System.out.println("Volume: " + new Box(3, 4, 5).volume());
    }
}