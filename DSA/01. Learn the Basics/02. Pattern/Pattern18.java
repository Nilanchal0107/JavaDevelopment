/*  Print
     E
     D E
     C D E
     B C D E
     A B C D E 
         if input is 5
*/

import java.util.Scanner;

public class Pattern18 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        for(int i = 0; i < number; i++) {
            char ch = (char) ('A' + (number - (i + 1)));
            for (int j = 0; j < i + 1; j++) {
                System.out.print(ch + " ");
                ch++;
            }
            System.out.println("");
        }
        input.close();
    }   
}