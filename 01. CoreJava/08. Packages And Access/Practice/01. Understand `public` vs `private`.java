class Person {
    public String name;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class Test {
    public static void main (String A[]) {
        Person a = new Person();
        a.name = "Navin";

        a.setAge(30);

        System.out.println(a.name);
        System.out.println(a.getAge());
    }
}

