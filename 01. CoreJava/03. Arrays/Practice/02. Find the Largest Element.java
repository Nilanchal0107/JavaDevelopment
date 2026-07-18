class LargestNumber {
    public static void main (String A[]) {
        int nums[] = {12, 45, 7, 89, 34};

        int largest = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (largest < nums[i]) {
                largest = nums[i];
            }
        }

        System.out.println("Largest element: " + largest);
    }
}