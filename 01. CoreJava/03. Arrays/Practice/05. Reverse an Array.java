class ReverseArray {
    public static void main (String A[]) {
        int nums[] = {1, 2, 3, 4, 5};

        for (int i = nums.length; i > 0; i--) {
            System.out.print(nums[i - 1] + " ");
        }
    }
}