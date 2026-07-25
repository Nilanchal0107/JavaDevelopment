// Original Code
// class Demo {
//     public static void main (String A[]) {
//         try {
//             int[] arr = new int[3];
//             arr[5] = 10;
//         }
//         catch (Exception e) {
//             System.out.println("General: " + e);
//         }
//         catch (ArrayIndexOutOfBoundsException e) {
//             System.out.println("Array error: " + e);
//         }
//     }
// }

class Demo {
    public static void main (String A[]) {
        try {
            int[] arr = new int[3];
            arr[5] = 10;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array error: " + e);
        }
        catch (Exception e) {
            System.out.println("General: " + e);
        }
    }
}

/* 

Whenever we are using multiple catch exceptions.
We need to catch exceptions in heirarichal manner.
That is we need to catch child exceptions first.
Specific Exception -> Runtime Exeception -> Exception.
If we catch Exception first that it will not execut Specific Exception

*/