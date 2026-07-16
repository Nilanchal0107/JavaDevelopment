class Person {
    public String name;
    public int age;
}

class CreateClass {
    public static void main (String A[]) {
        Person obj = new Person();

        obj.name = "Nilanchal";
        obj.age = 20;

        System.out.println("Name: " + obj.name);
        System.out.println("Age: " + obj.age);
    }
}