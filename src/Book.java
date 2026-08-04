import estandar.Cola;

public class Book {
    private String title;
    private String isbn;
    private String author;
    private int publicationYear;
    private boolean available;
    private int timesLoaned;
    private Cola waitList;

    public Book(String title, String isbn, String author, int publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.publicationYear = publicationYear;
        this.available = true;
        this.waitList = new Cola();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getTimesLoaned() {
        return timesLoaned;
    }

    public void setTimesLoaned(int timesLoaned) {
        this.timesLoaned = timesLoaned;
    }

    public Cola getWaitList() {
        return waitList;
    }

    public void setWaitList(Cola waitList) {
        if (waitList != null) {
            this.waitList = waitList;
        }
    }

    public boolean lend() {
        if (!available) {
            return false;
        }
        available = false;
        timesLoaned++;
        return true;
    }

    /** Devuelve el libro o lo asigna al primer usuario en espera. */
    public void returnBook() {
        if (waitList.esVacia()) {
            available = true;
            return;
        }
        waitList.desencolar(); //aca deberia de encolar para poder ir directamente al usuario 1 en lista de espera
        available = false;
        timesLoaned++;
    }

    public boolean addToWaitList(String userName) {
        if (userName == null || userName.isBlank()) {
            return false;
        }
        waitList.encolar(userName);
        return true;
    }

    public String peekNextWaitingUser() {
        return (String) waitList.verFrente();
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s - %d - %s - préstamos: %d",
                title, isbn, author, publicationYear,
                available ? "disponible" : "prestado", timesLoaned);
    }
}
