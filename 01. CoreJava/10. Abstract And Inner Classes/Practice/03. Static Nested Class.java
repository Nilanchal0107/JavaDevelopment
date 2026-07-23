class Computer {
    void start () {
        System.out.println("Computer started");
    }

    static class GPU {
        void render() {
            System.out.println("GPU rendering");
        }
    }
}

class Demo {
    public static void main (String A[]) {
        Computer c = new Computer();
        c.start();
        Computer.GPU gpu = new Computer.GPU();
        gpu.render();
    }
}