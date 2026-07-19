class Employee {
    private int id;
    private String name, department;
    private double salary;

    Employee() {
        id = 0;
        name = "N/A";
        salary = 0.0;
        department = "Unassigned";
    }

    Employee(int id, String name, double salary, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department; 
    }

    Employee(Employee other) {
        id = other.id;
        name = other.name;
        salary = other.salary;
        department = other.department; 
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        if (salary > 0) {
            this.salary = salary;
        }
    }

    void printInfo() {
        System.out.println(name);
        System.out.println(id);
        System.out.println(salary);
        System.out.println(department);
    }
}

class Test {
    public static void main (String A[]) {
        Employee e1 = new Employee();
        e1.printInfo();

        Employee e2 = new Employee(1, "Navin", 30000.0, "I.T");
        e2.printInfo();

        Employee e2Copy = new Employee(e2);

        e2.setSalary(45000.0);

        e2Copy.printInfo();
    }
}
