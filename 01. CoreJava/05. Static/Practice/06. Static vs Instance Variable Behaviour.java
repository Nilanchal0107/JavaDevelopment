class Mobile {
    String brand;
    static String type;

    void show() {
        System.out.println(brand + " : " + type);
    }
}

class Test {
    public static void main (String A[]) {
        Mobile obj1 = new Mobile();
        Mobile obj2 = new Mobile();

        obj1.brand = "Apple";
        Mobile.type = "Smartphone";

        obj2.brand = "Samsung";
        Mobile.type = "Feature Phone";

        obj1.show();
        obj2.show();

    }
}

/*

why **both** objects reflect the changed value?
static values are class dependent and hance when we change the static string type it got change for both the objects.

*/