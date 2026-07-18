class Config {
    static String appName;

    static {
        appName = "MyApp";
        System.out.println("Config Loaded");
    }

    Config() {
        System.out.println("Object Created");
    }
}

class StaticBlock {
    public static void main(String A[]) {
        try {
            Class.forName("Config");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
    }
}