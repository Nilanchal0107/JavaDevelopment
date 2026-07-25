class Demo {
    public static void main (String A[]) {
        int j = 0;

        try {
            j = 18 / 0;
        }
        catch (Exception e) {
            System.out.println("Something went wrong");
        }

        System.out.println("Bye");
    }
}