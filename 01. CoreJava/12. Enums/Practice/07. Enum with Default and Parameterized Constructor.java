enum Laptop {
    Mackbook(2000), XPS(2200), Surface, ThinkPad(1800);

    private int price;

    private Laptop () {
        this.price = 500;
    }

    private Laptop (int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

class Demo {
    public static void main (String A[]) {
        Laptop[] laps = Laptop.values();

        for (Laptop lap : laps) {
            System.out.println(lap.name() + " : " + lap.getPrice());
        }
    }
}