import estandar.ListaEnlazada;
import estandar.Pila;

/** Administra libros, préstamos y las búsquedas de la biblioteca. */
public class Library {
    private String name;
    private final ListaEnlazada books;
    private final Pila loanHistory;

    public Library(String name) {
        this.name = name;
        this.books = new ListaEnlazada();
        this.loanHistory = new Pila();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListaEnlazada getBooks() {
        return books;
    }

    public Pila getLoanHistory() {
        return loanHistory;
    }

    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }
        books.insertarFinal(book);
        return true;
    }

    public boolean removeBook(Book book) {
        return books.eliminar(book);
    }

    public Book findBookByIsbn(String isbn) {
        if (isbn == null) {
            return null;
        }
        for (int i = 0; i < books.getTamano(); i++) {
            Book book = (Book) books.obtener(i);
            if (isbn.equals(book.getIsbn())) {
                return book;
            }
        }
        return null;
    }

    public ListaEnlazada findBookByDescription(String description) {
        return findBooksByTitle(description);
    }

    public ListaEnlazada findBooksByTitle(String title) {
        ListaEnlazada foundBooks = new ListaEnlazada();
        for (int i = 0; i < books.getTamano(); i++) {
            Book book = (Book) books.obtener(i);
            if (containsIgnoreCase(book.getTitle(), title)) {
                foundBooks.insertarFinal(book);
            }
        }
        return foundBooks;
    }

    public ListaEnlazada findBooksByAuthor(String authorName) {
        ListaEnlazada foundBooks = new ListaEnlazada();
        for (int i = 0; i < books.getTamano(); i++) {
            Book book = (Book) books.obtener(i);
            if (containsIgnoreCase(book.getAuthor(), authorName)) {
                foundBooks.insertarFinal(book);
            }
        }
        return foundBooks;
    }

    public ListaEnlazada getAvailableBooks() {
        return filterByAvailability(true);
    }

    public ListaEnlazada getLoanedBooks() {
        return filterByAvailability(false);
    }

    public boolean lendBook(String isbn, String userName) {
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            return false;
        }
        if (book.lend()) {
            loanHistory.apilar(book);
            return true;
        }
        book.addToWaitList(userName);
        return false;
    }

    public boolean undoLastLoan() {
        Book book = (Book) loanHistory.desapilar();
        if (book == null) {
            return false;
        }
        book.returnBook();
        return true;
    }

    public Book getLastLoan() {
        return (Book) loanHistory.tope();
    }

    public String getStatistics() {
        return String.format(
                "Estadísticas de la Biblioteca:\n" +
                "- Total de Libros: %d\n" +
                "- Libros Disponibles: %d\n" +
                "- Libros Prestados: %d\n",
                books.getTamano(), getAvailableBooks().getTamano(), getLoanedBooks().getTamano());
    }

    /** Alias para conservar el nombre usado por la implementación anterior. */
    public String getStadistics() {
        return getStatistics();
    }

    private ListaEnlazada filterByAvailability(boolean wantedAvailability) {
        ListaEnlazada result = new ListaEnlazada();
        for (int i = 0; i < books.getTamano(); i++) {
            Book book = (Book) books.obtener(i);
            if (book.isAvailable() == wantedAvailability) {
                result.insertarFinal(book);
            }
        }
        return result;
    }

    private boolean containsIgnoreCase(String value, String query) {
        return value != null && query != null
                && value.toLowerCase().contains(query.toLowerCase());
    }

    @Override
    public String toString() {
        return name + " (" + books.getTamano() + " libros)";
    }
}
