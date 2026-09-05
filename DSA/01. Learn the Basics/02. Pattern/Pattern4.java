/*  Print
     1
     22
     333
     4444
     55555
         if input is 5
*/

import java.util.Scanner;

public class Pattern4 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        for(int i = 0; i < number; i++) {
            for (int j = 0; j < i + 1; j++) {
                System.out.print(i+1);
            }
            System.out.println("");
        }
        input.close();
    }   
}