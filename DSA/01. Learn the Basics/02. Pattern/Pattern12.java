// Print
//     1      1
//     12    21
//     123  321
//     12344321
//         input = 4

class Solution {
    public void pattern12(int N) {
        int spaces = 2 * (N - 1);

        for (int i = 1; i <= N; i++) {
            
            for (int j = 1; j <= i; j++) {
                System.out.print(j);
            }
            
            for (int j = 1; j <= spaces; j++) {
                System.out.print(" ");
            }
            
            for (int j = i; j >= 1; j--) {
                System.out.print(j);
            }
            
            System.out.println();
            
            spaces -= 2;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int N = 5;
        sol.pattern12(N);
    }
}
