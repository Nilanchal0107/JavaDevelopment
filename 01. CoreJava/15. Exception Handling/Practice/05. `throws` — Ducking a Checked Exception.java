class FileLoader {
    public void load() throws ClassNotFoundException {
        Class.forName("SomeClass");
    }
}

class Demo {
    public static void main (String A[]) {
        try {
            new FileLoader().load();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}