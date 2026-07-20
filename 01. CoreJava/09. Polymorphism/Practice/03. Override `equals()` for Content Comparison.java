class Book {
    String title;
    int isbn;

    public boolean equals(Book that) {
        return this.title.equals(that.title) && this.isbn == that.isbn;
    }
}

public class Demo {
    public static void main (String A[]) {
        Book book1 = new Book();
        book1.title = "One Piece";
        book1.isbn = 98760;

        Book book2 = new Book();
        book2.title = "One Piece";
        book2.isbn = 98760;

        System.out.println(book1.equals(book2));
    }
}