@Deprecated
class OldShape {
    public void draw() {
        System.out.println("Old drawing");
    }
}

class NewShape extends OldShape {
    @Override
    public void draw() {
        System.out.println("New drawing");
    }
}

class Demo {
    public static void main (String A[]) {
        NewShape obj = new NewShape();
        obj.draw();
    }
}

// "Even though `OldShape` is deprecated, 
// its subclass `NewShape` can still extend it and override methods."