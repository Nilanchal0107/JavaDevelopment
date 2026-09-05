/*  Print
     *****
     *   *
     *   *
     *   *
     *****
         if input is 5
*/

import java.util.Scanner;

public class Pattern21 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        for(int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                if (i == 0 || j == 0 || i == number - 1 || j == number - 1)
                    System.out.print("*");
                else
                    System.out.print(" ");
            }
            System.out.println("");
        }
        input.close();
    }   
}