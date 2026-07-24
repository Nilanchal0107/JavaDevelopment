enum Status {
    Running, Failed, Pending, Success;
}

class Demo {
    public static void main (String A[]) {
        Status[] ss = Status.values();

        for (Status s : ss) {
            System.out.println(s + " : " + s.ordinal());
        }
    }
}