/*  Print
     1
     2 3
     4 5 6
     7 8 9 10
     11 12 13 14 15 
         if input is 5
*/

import java.util.Scanner;

public class Pattern13 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        int a = 0;

        for(int i = 0; i < number; i++) {
            for (int j = 0; j < i + 1; j++) {
                a = a + 1;
                System.out.print(a + " ");
            }
            System.out.println("");
        }
        input.close();
    }   
}