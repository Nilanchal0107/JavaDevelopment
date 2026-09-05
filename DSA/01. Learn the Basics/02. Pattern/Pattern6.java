/*  Print
     12345
     1234
     123
     12
     1
         if input is 5
*/

import java.util.Scanner;

public class Pattern6 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        for(int i = number; i > 0; i--) {
            for (int j = 0; j < i ; j++) {
                System.out.print(j + 1);
            }
            System.out.println("");
        }
        input.close();
    }   
}