class Database {
    private static Database instance;

    private Database() {};

    static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
}

class Test {
    public static void main (String A[]) {
        System.out.println(Database.getInstance() == Database.getInstance());
    }
}