class SumAverage {
    public static void main (String A[]) {
        int arr[] = {10, 20, 30, 40, 50, 60};

        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        double average = (double) sum / arr.length;

        System.out.println("Sum: " + sum);
        System.out.println("Average: " + average);

        
    }
}