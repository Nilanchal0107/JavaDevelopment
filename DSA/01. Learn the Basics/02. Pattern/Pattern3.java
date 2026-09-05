/*  Print
     1
     12
     123
     1234
     12345
         if input is 5
*/

import java.util.Scanner;

public class Pattern3 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        for(int i = 0; i < number; i++) {
            for (int j = 0; j < i + 1; j++) {
                System.out.print(j+1);
            }
            System.out.println("");
        }
        input.close();
    }   
}