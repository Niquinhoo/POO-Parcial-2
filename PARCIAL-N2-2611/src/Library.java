import estandar.ListaEnlazada;
import estandar.Pila;

public class Library {
    private String name;
    private ListaEnlazada books;
    private Pila loanHistory;
    //constructor
    public Library(String name) {
        this.name = name;
        this.books = new ListaEnlazada();
        this.loanHistory = new Pila();
    }
    //getters y setters
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ListaEnlazada getBooks(){
        return books;
    }

    public void setBooks(ListaEnlazada books){
        this.books = books;
    }

    public Pila getLoanHistory(){
        return loanHistory;
    }

    public void setLoanHistory(Pila loanHistory){
        this.loanHistory = loanHistory;
    }

    // llogica
    //añadir libro
    public boolean addBook(Book book){
        if (book != null){
            books.insertarFinal(book);
            return true;
        }
        return false;
    }
    //eliminar libro
    public boolean removeBook(Book book){
        return books.eliminar(book);
    }
    //buscar libro por isbn
    public Book findBookByIsbn(String isbn){
        for (int i = 0; i < books.getTamano(); i++){
            Book b = (Book) books.obtener(i);
            if (b.getIsbn().equals(isbn)){
                return b;
            }
        }
        return null;
    }
    //buscar libros por autor
    public ListaEnlazada findBooksByAuthor(String authorName){
        ListaEnlazada results = new ListaEnlazada();
        for (int i = 0; i < books.getTamano(); i++){
            Book b = (Book) books.obtener(i);
            if (b.getAuthor().toLowerCase().contains(authorName.toLowerCase())){
                results.insertarFinal(b);
            }
        }
        return results;
    }
    //buscar libros por titulo
    public ListaEnlazada findBooksByTitle(String titleFragment){
        ListaEnlazada results = new ListaEnlazada();
        for (int i = 0; i < books.getTamano(); i++) {
            Book b = (Book) books.obtener(i);

            if (b.getTitle().toLowerCase().contains(titleFragment.toLowerCase())) {
                results.insertarFinal(b);
            }
        }
        return results;
    }
    //prestar libro
    public void lendBook(String isbn, String userName){
        Book b = findBookByIsbn(isbn);
        if (b == null){
            System.out.println("El libro " + isbn + " no se encuentra en la biblioteca");
            return;
        }

        if (b.isAvailable()) {
            boolean success = b.lend();
            if (success) {
                loanHistory.apilar(b);
                System.out.println("Libro '" + b.getTitle() + "' prestado a " + userName);
            }
        } else {
            System.out.println("El libro no está disponible. Agregando a " + userName + " a la cola de espera...");
            b.addToWaitList(userName);
        }
    }
    //deshacer prestamo
    public void undoLastLoan(){
        if (loanHistory.esVacia()) {
            System.out.println("No hay préstamos recientes para deshacer.");
            return;
        }
        Book b = (Book) loanHistory.desapilar();
        b.returnBook(); 
        System.out.println("Se deshizo el préstamo del libro: " + b.getTitle());
    }
    //libros disponibles
    public ListaEnlazada getAvailableBooks(){
        ListaEnlazada available = new ListaEnlazada();
        for (int i = 0; i < books.getTamano(); i++) {
            Book b = (Book) books.obtener(i);
            if (b.isAvailable()) {
                available.insertarFinal(b);
            }
        }
        return available;
    }
    //libros prestados
    public ListaEnlazada getLoanedBooks(){
        ListaEnlazada loaned = new ListaEnlazada();
        for (int i = 0; i < books.getTamano(); i++) {
            Book b = (Book) books.obtener(i);
            if (!b.isAvailable()) {
                loaned.insertarFinal(b);
            }
        }
        return loaned;
    }
    //estadisticas
    public String getStadistics(){
        int total = books.getTamano();
        int available = getAvailableBooks().getTamano();
        int loaned = getLoanedBooks().getTamano();
        
        return "=== ESTADÍSTICAS DE LA BIBLIOTECA ===\n" +
               "- Total de Libros: " + total + "\n" +
               "- Disponibles: " + available + "\n" +
               "- Prestados: " + loaned + "\n";
    }


    @Override

    public String toString(){
        return name + " (" + books.getTamano() + " libros)";
    }

}
