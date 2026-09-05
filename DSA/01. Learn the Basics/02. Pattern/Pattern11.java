/*  Print
     1
     0 1
     1 0 1 
     0 1 0 1
     1 0 1 0 1
         if input is 5
*/

import java.util.Scanner;

public class Pattern11 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        int start;

        for(int i = 0; i < number; i++) {

            if (i % 2 == 0)
                start = 1;
            else
                start = 0;

            for (int j = 0; j < i + 1; j++) {
                System.out.print(start + " ");

                start = 1 - start;
            }
            System.out.println("");
        }
        input.close();
    }   
}