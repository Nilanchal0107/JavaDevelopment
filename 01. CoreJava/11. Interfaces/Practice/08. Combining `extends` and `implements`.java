interface Drawable {
    void draw();
}

interface Resizable {
    void resize(int factor);
}

abstract class Widget {
    String color;
    String getColor() {
        return color;
    }
}

class Button extends Widget implements Drawable, Resizable {

    Button() {
        color = "Blue";
    }

    public void draw() {
        System.out.println("Drawing button");
    }

    public void resize(int factor) {
        System.out.println("Resized by factor " + factor);
    }
}

class Demo {
    public static void main (String A[]) {
        Button peace = new Button();
        peace.draw();
        peace.resize(3);
        System.out.println(peace.getColor());
    }
}