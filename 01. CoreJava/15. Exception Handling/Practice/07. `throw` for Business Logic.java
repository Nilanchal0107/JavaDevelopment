class Demo {
    static void validateAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150");
        }
        System.out.println("Valid age: " + age);
    }

    public static void main(String[] args) {
        int[] ages = {25, -1, 200};
        for (int age : ages) {
            try {
                validateAge(age);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid: " + e.getMessage());
            }
        }
    }
}