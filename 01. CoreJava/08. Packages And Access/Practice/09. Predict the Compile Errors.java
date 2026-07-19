class Secret {
    private int pin = 1234;
    int code = 5678;           // default
    protected String key = "abc";
    public String name = "Secret";
}

class Spy extends Secret {
    void reveal() {
        // System.out.println(pin);    // Not accessible since private
        System.out.println(code);   // accessible since in same package
        System.out.println(key);    // accessible since in accessing as sub class
        System.out.println(name);   // accessible since it is public
    }
}

class Outside {
    void peek() {
        Secret s = new Secret();
        // System.out.println(s.pin);  // Not accessible since private
        System.out.println(s.code); // accessible since in same package
        System.out.println(s.key);  // accessible since in same package
        System.out.println(s.name); // accessible since it is public
    }
}
