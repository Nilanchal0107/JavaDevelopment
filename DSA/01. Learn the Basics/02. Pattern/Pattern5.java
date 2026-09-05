/*  Print
     *****
     ****
     ***
     **
     *
         if input is 5
*/

import java.util.Scanner;

public class Pattern5 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        for(int i = 0; i < number; i++) {
            for (int j = number; j > i ; j--) {
                System.out.print("*");
            }
            System.out.println("");
        }
        input.close();
    }   
}