class Demo {
    public static void main (String A[]) {
        int i = 0;
        int[] nums = new int[5];
        String str = null;

        try {
            System.out.println(18 / i);
            System.out.println(str.length());
            System.out.println(nums[5]);
        }
        catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero");
            System.out.println(0);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
        }
        catch (Exception e) {
            System.out.println("Something went wrong");
        }

        System.out.println("Bye");
    }
}