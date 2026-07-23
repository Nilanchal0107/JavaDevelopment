class Library {
    class Book{
        public String title;

        public void read() {
            System.out.println("Reading: " + title);
        }
    }

    static class Catalogue {
        public void list() {
            System.out.println("Listing all books");
        }
    }
}

class Demo {
    public static void main (String A[]) {
        Library lib = new Library();
        Library.Book b = lib.new Book();
        b.title = "Harry Potter";
        b.read();

        Library.Catalogue cat = new Library.Catalogue();
        cat.list();
    }
}