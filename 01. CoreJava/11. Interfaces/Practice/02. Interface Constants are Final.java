interface Config {
    String APP_NAME = "MyApp";
}

class Demo {
    public static void main (String A[]) {
        System.out.println(Config.APP_NAME);

        // Config.APP_NAME = "App" It will give error because you can only store final and static types of variable in an interface
    }
}