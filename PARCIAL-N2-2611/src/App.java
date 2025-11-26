import estandar.ListaEnlazada;


public class App {
    public static void main(String[] args) {
        System.out.println("\n--- Sistema de Gestion de Biblioteca ---");

        Library library = new Library("Biblioteca Central");
//harcode de libros
        Book b1 = new Book("Cien Años de Soledad", "ISBN-001", "Gabriel Garcia Marquez", 1967);
        Book b2 = new Book("El Quijote", "ISBN-002", "Miguel de Cervantes", 1605);
        Book b3 = new Book("Clean Code", "ISBN-003", "Robert C. Martin", 2008);
        Book b4 = new Book("El Amor en los Tiempos del Cólera", "ISBN-004", "Gabriel Garcia Marquez", 1985);

        //agregar libros a la biblioteca
        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);
        library.addBook(b4);

        System.out.println(library.getStadistics());

        System.out.println("\n--- Pruebas de busqueda ---");

        //ISBN
        Book encontrado = library.findBookByIsbn("ISBN-002");
        System.out.println("Buscar ISBN-002: " + (encontrado != null ? encontrado.getTitle() : "No encontrado"));

        //Autor
        System.out.println("Buscar Autor 'Marquez':");
        ListaEnlazada librosMarquez = library.findBooksByAuthor("Marquez");
        for(int i=0; i < librosMarquez.getTamano(); i++) {
            Book b = (Book) librosMarquez.obtener(i);
            System.out.println("   -> " + b.getTitle());
        }

        //titulo
        System.out.println("Buscar Título 'Code':");
        ListaEnlazada librosCode = library.findBooksByTitle("Code");
        if (!librosCode.esVacia()) {
            System.out.println("   -> Encontrado: " + ((Book)librosCode.obtener(0)).getTitle());
        } else {
            System.out.println("   -> No encontrado");
        }

        //Prueba prestamos y pila 
        System.out.println("\n--- Prueba de préstamos y deshacer ---");
        // prestamos un libro
        library.lendBook("ISBN-001", "Juan Perez"); 
        System.out.println("Estado 'Cien Años...': " + (b1.isAvailable() ? "DISPONIBLE" : "PRESTADO"));

        //deshaciendo el prestamo para liberar el libro
        System.out.println("\nError, deshaciendo último préstamo...");
        library.undoLastLoan(); // deberia volver a estar disponible
        System.out.println("Estado tras deshacer: " + (b1.isAvailable() ? "DISPONIBLE" : "PRESTADO"));

        //prueba completa de cola de espera
        System.out.println("\n--- Prueba de queue ---");
        library.lendBook("ISBN-003", "Ana Gomez");

        //intento prestar un libro ya prestado 
        System.out.println("\nIntento prestar un libro ya prestado...");
        library.lendBook("ISBN-003", "Carlos Lopez"); // van a cola de espera los 2
        library.lendBook("ISBN-003", "Diana Ruiz");


        //Ana devuelve el libro 
        System.out.println("\nAna devuelve el libro...");
        Book cleanCode = library.findBookByIsbn("ISBN-003");
        cleanCode.returnBook();

        //Verifico quien lo tiene ahora
        System.out.println("\nVerifico quien lo tiene ahora...");
        System.out.println("Estado 'Clean Code': " + (cleanCode.isAvailable() ? "DISPONIBLE" : "PRESTADO"));

        //Elimino el libro
        System.out.println("\nElimino el libro...");
        if(library.removeBook(b2)){
            System.out.println("Libro 'El Quijote' eliminado exitosamente");
        } else {
            System.out.println("Error al eliminar el libro");
        }

        System.out.println("\n--- Prueba de estadísticas ---");
        System.out.println("\n" + library.getStadistics());
    }
}
