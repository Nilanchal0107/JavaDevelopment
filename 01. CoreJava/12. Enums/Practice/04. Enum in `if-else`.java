enum Status {
    Running, Failed, Pending, Success;
}

class Demo {
    public static void main (String A[]) {
        Status s = Status.Success;

        if (s == Status.Running)
            System.out.println("All Good");
        else if (s == Status.Failed)
            System.out.println("Try Again");
        else if (s == Status.Pending)
            System.out.println("Please Wait");
        else 
            System.out.println("Done");  
    }
}