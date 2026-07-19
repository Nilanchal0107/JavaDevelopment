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
        id = this.id;
        name = this.name;
        salary = this.salary;
        department = this.department; 
    }

    Employee(Employee other) {}

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
}

