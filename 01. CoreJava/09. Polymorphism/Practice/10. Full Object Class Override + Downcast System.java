class Product {
    int id;
    String name;
    double price;
    
    public String toString() {
        return "[" + id + "] " + name + " - " + "$" + price;
    }
    
    boolean equals(Product P) {
        return this.id == P.id;
    }
}

class DiscountedProduct extends Product {
    double discount;
    
    public String toString() {
        return super.toString() + " discount: " + discount + "%";
    }
}

class Demo {
    public static void main (String A[]) {
        Product p1 = new Product();
        p1.id = 1;
        p1.name = "Ram";
        p1.price = 1000.00; 

        DiscountedProduct p2 = new DiscountedProduct();
        p2.id = 1;
        p2.name = "Ram";
        p2.price = 1000.00; 
        p2.discount = 25.5;

        Product p3 = new Product();
        p3.id = 2;
        p3.name = "Rom";
        p3.price = 9000.00; 

        Product[] product = new Product[3];
        product[0] = p1; 
        product[1] = p2; 
        product[2] = p3;

        for (Product obj : product) {
           if (obj instanceof DiscountedProduct) {
                DiscountedProduct dp = (DiscountedProduct) obj;
                System.out.println("Discount: " + dp.discount);
            }
            System.out.println(obj.toString());
        }

        System.out.println(p1.equals(p2));
    }
}