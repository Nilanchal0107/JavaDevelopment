import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Demo {
    public static void main (String A[]) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int num = Integer.parseInt(br.readLine());
            System.out.println("You entered: " + num);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        catch (NumberFormatException e) {
            System.out.println(e);
        }
    }
}

/*

When we use try with resoruces.
The resources inherits methods of class AutoClosable.
So the try block automatically closes the opened resources

 */