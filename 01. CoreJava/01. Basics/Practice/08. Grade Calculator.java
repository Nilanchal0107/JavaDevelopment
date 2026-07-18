class GradeCalculator {
    public static void main (String A[]) {
        int marks = 78;

        if (marks >= 90) {
            System.out.println("Grade: A");
        }
        else if (marks >= 75) {
            System.out.println("Grade: B");
        }
        else if (marks >= 60) {
            System.out.println("Grade: C");
        }
        else if (marks >= 45) {
            System.out.println("Grade: D");
        }
        else {
            System.out.println("Grade: F");
        }

        String result = marks >= 45 ? "Passed" : "Failed";
        System.out.println("Result: " + result);
    }
}