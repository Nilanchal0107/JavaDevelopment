enum Status {
    Running, Failed, Pending, Success;
}

class Demo {
    public static void main (String A[]) {
        Status s = Status.Failed;

        switch(s) {
            case Running: 
                System.out.println("All Good");
                break;
            case Failed: 
                System.out.println("Try Again");
                break;
            case Pending: 
                System.out.println("Please Wait");
                break;
            case Success: 
                System.out.println("Done");
                break;
        }    
    }
}