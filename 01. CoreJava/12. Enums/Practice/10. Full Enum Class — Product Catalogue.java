enum Product {
    LAPTOP("Laptop", 1200.0), PHONE("Phone", 800.0), TABLET("Tablet", 500.0), WATCH("Watch", 250.0);

    private String name;
    private double price;

    private Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static void cheaperThan(double budget) {
        for (Product s : Product.values()) {
            if (s.price < budget) 
                System.out.println(s.getName() + " : " + s.getPrice());
        }
    }
}

class Demo {
    public static void main (String A[]) {
        for (Product s : Product.values()) {
                System.out.println(s.getName() + " : " + s.getPrice());
        }

        Product.cheaperThan(600.0);

        Product w = Product.WATCH;
        w.setPrice(300.0);

        System.out.println(w.getName() + " : " + w.getPrice());
    }
}