class JaggedArray {

    public static void main (String A[]) {

    
        int marks[][] = new int[3][];
        marks[0] = new int[]{80, 75, 90};
        marks[1] = new int[]{60, 70, 85, 95};
        marks[2] = new int[]{88, 92};

        for (int i = 0; i < marks.length; i++) {
            int sum = 0;
            System.out.print("Student " + (i + 1) + " scores:");
            for (int j = 0; j < marks[i].length; j++) {
                System.out.print(marks[i][j] + " ");
                sum += marks[i][j];
            }
            System.out.println("  | Total: " + sum);
        }
  
    }

}