class Session {
    static int sessionCount;
    int sessionId = 0;

    Session() {
        sessionCount++;
        sessionId = sessionCount;
    }

    static void resetCount() {
        sessionCount = 0;
    }

    void info() {
        System.out.println(sessionId);
    }
}

class Test {
    public static void main (String A[]) {
        Session s1 = new Session();
        s1.info();

        Session s2 = new Session();
        s2.info();

        Session s3 = new Session();
        s3.info();

        Session.resetCount();

        Session s4 = new Session();
        s4.info();

        Session s5 = new Session();
        s5.info();
    }
}

/* 

Why do the new sessions after reset start from 1 again, 
even though the earlier objects still exist?
we had used static method to reset static variable Session count 
which is class dependent on class not object dependent.

sessionId is an instance variable stored in each object's own Heap space, 
so resetting sessionCount doesn't touch the already-created objects. s1.sessionId remains 1 forever 
because it was assigned at construction and never changes after that.

*/