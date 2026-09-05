/*  Print
     4 4 4 4 4 4 4
     4 3 3 3 3 3 4
     4 3 2 2 2 3 4
     4 3 2 1 2 3 4
     4 3 2 2 2 3 4
     4 3 3 3 3 3 4
     4 4 4 4 4 4 4
         if input is 4
*/

import java.util.Scanner;

public class Pattern22 {
    public static void main(String A[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number : ");
        int number = input.nextInt();

        for(int i = 0; i < (number*2) - 1; i++) {
            for (int j = 0; j < (number*2) - 1; j++) {
                int top = i;
                int left = j;
                int bottom = (number*2 - 2) - i;
                int right = (number*2 - 2) - j;

            int minDist = Math.min(Math.min(top, bottom), Math.min(left,right));

            System.out.print((number - minDist) + " ");
        }
        System.out.println();
        input.close();
    }   
}
}