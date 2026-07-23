interface Computer {
    void code();
}

class Laptop implements Computer {
    public void code() {
        System.out.println("code, compile, run");
    }
}

class Desktop implements Computer {
    public void code() {
        System.out.println("code, compile, faster");
    }
}

class Developer {
    public void devApp(Computer c) {
        c.code();
    }
}

class Demo {
    public static void main (String A[]) {
        Computer a = new Laptop();
        Computer b = new Desktop();

        Developer lucifer = new Developer();
        lucifer.devApp(a);
        lucifer.devApp(b);
    }
}