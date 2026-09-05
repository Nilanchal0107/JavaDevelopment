/*  Print
     A
     AB
     ABC
     ABCD
     ABCDE
         if input is 5
*/

import java.util.Scanner;

public class Pattern14 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        for(int i = 0; i < number; i++) {
            for (char ch = 'A'; ch <= 'A' + i; ch++) {
                System.out.print(ch + " ");
            
        }
        System.out.println("");
        input.close();
    }   
}
}