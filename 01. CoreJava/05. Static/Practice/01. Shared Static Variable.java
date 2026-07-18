class Company {
    String empName;
    int empId;
    static String companyName;

    void show() {
        System.out.println(empName + " : " + empId + " : " + companyName);
    }
}

class StaticVar {
    public static void main (String A[]) {
        Company obj1 = new Company();
        obj1.empName = "Navin";
        obj1.empId = 101;

        Company obj2 = new Company();
        obj2.empName = "Reddy";
        obj2.empId = 102;
        
        Company.companyName = "Telusko";

        obj1.show();
        obj2.show();

    }
}