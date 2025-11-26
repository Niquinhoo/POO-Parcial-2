import estandar.Cola;

public class Book {
    private String title;
    private String isbn;
    private String author;
    private int publicationYear;
    private boolean available;
    private int timesLoaned;

    private Cola waitList;
 /// constructor
    public Book(String title, String isbn, String author, int publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.publicationYear = publicationYear;
        this.available = true;
        this.timesLoaned = 0;
        this.waitList = new Cola();
    }

    ///getters y setters
    //title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //isbn
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    //author
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    //publicationYear
    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    //available
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    //timesLoaned
    public int getTimesLoaned() {
        return timesLoaned;
    }

    public void setTimesLoaned(int timesLoaned) {
        this.timesLoaned = timesLoaned;
    }

    //waitList
    public Cola getWaitList() {
        return waitList;
    }

    public void setWaitList(Cola waitList) {
        this.waitList = waitList;
    }

    @Override

    public String toString(){
        return "ISBN: " + isbn + "\n"
                + "Titulo: " + title + "\n"
                + "Autor: " + author + "\n"
                + "Año de publicacion: " + publicationYear + "\n"
                + "Disponible: " + available + "\n"
                + "Veces prestado: " + timesLoaned + "\n";
    }

    public boolean lend(){
        // no se pone el == true porque es booleano de fabrica
        if (available){
            available = false;
            timesLoaned++;
            return true;
        }
        return false;
    }

    public void returnBook(){
        if (!waitList.esVacia()){
            String nextUser = (String) waitList.desencolar();
            System.out.println("El libro se asigno automaticamente a la reserva de " + nextUser);

            timesLoaned++;
        } else{
            available = true;
        }
    }
    
    // Operaciones: agregar un usuario a la espera, ver el primer usuario en espera, quitar un usuario cuando el libro está disponible y prestárselo."

    public void addToWaitList(String userName){
        waitList.encolar(userName);
        System.out.println("El usuario " + userName + " se agrego a la lista de espera, para el libro" + title);
    }
}
