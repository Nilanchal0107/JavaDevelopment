class Book {
    String title, author;
    double price;
}

class LibraryBooks {
    public static void main(String[] args) {
        Book[] books = new Book[4];

        books[0] = new Book();
        books[1] = new Book();
        books[2] = new Book();
        books[3] = new Book();

        books[0].title = "The Great Gatsby";
        books[0].author = "F. Scott Fitzgerald";
        books[0].price = 10.99;

        books[1].title = "To Kill a Mockingbird";
        books[1].author = "Harper Lee";
        books[1].price = 12.99;

        books[2].title = "1984";
        books[2].author = "George Orwell";
        books[2].price = 11.99;

        books[3].title = "Pride and Prejudice";
        books[3].author = "Jane Austen";
        books[3].price = 9.99;

        for (Book book : books) {
            System.out.println(book.title + " by " + book.author + " - " + book.price);
        }

        double maxPrice = books[0].price;
        String maxPriceBook = books[0].title;
        for (Book book : books) {
            if (book.price > maxPrice) {
                maxPrice = book.price;
                maxPriceBook = book.title;
            }
        }

        System.out.println("The most expensive book: " + maxPriceBook);
    }
}