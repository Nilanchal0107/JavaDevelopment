/*  Print
     A
     BB
     CCC
     DDDD
     EEEEE
         if input is 5
*/

import java.util.Scanner;

public class Pattern16 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        for(int i = 0; i < number; i++) {

            char ch = (char) ('A' + i);
            for (int j = 0; j < i + 1; j++) {
                System.out.print(ch + " ");
            
        }
        System.out.println("");
        input.close();
    }   
}
}