/*

Count digits in a number
Problem Statement: Given an integer N, return the number of digits in N.

Examples
Example 1:
Input:N = 12345
Output:5
Explanation:  The number 12345 has 5 digits.
                        
Example 2:
Input:N = 7789              
Output: 4
Explanation: The number 7789 has 4 digits.  

*/

import java.util.Scanner;

public class Main {
    public static void main (String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a Number: ");
        int number = input.nextInt();

        int n = (int) (Math.log10(number) + 1);

        System.out.println("This number is of " + n + " digits.");

    }
}