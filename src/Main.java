import estandar.ListaEnlazada;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n--- Sistema de Gestion de Biblioteca ---");

        Library library = new Library("Biblioteca Central");

        // Prueba de la lista enlazada propia
        System.out.println("\n--- Prueba de lista enlazada ---");
        ListaEnlazada lista = new ListaEnlazada();
        lista.insertarFinal("Primer elemento");
        lista.insertarFinal("Segundo elemento");
        System.out.println("Elementos en la lista: " + lista.getTamano());
        System.out.println("Contiene 'Primer elemento': " + lista.contiene("Primer elemento"));
        lista.eliminar("Primer elemento");
        System.out.println("Elementos después de eliminar: " + lista.getTamano());
        lista.limpiar();
        System.out.println("¿La lista quedó vacía?: " + lista.esVacia());

        // Crear libros
        Book b1 = new Book("Cien Años de Soledad", "ISBN-001", "Gabriel Garcia Marquez", 1967);
        Book b2 = new Book("El Quijote", "ISBN-002", "Miguel de Cervantes", 1605);
        Book b3 = new Book("Clean Code", "ISBN-003", "Robert C. Martin", 2008);
        Book b4 = new Book("El Amor en los Tiempos del Cólera", "ISBN-004", "Gabriel Garcia Marquez", 1985);

        // Agregar libros a la biblioteca
        System.out.println("\n--- Agregando libros a la biblioteca ---");
        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);
        library.addBook(b4);
        System.out.println("Libros agregados: " + library.getBooks().getTamano());
        System.out.println(library.getStadistics());

        System.out.println("\n--- Pruebas de busqueda ---");

        // ISBN
        Book encontrado = library.findBookByIsbn("ISBN-002");
        System.out.println("Buscar ISBN-002: " + (encontrado != null ? encontrado.getTitle() : "No encontrado"));

        // Autor
        System.out.println("Buscar Autor 'Marquez':");
        ListaEnlazada librosMarquez = library.findBooksByAuthor("Marquez");
        for (int i = 0; i < librosMarquez.getTamano(); i++) {
            Book b = (Book) librosMarquez.obtener(i);
            System.out.println("   -> " + b.getTitle());
        }

        // Título
        System.out.println("Buscar Título 'Code':");
        ListaEnlazada librosCode = library.findBooksByTitle("Code");
        if (!librosCode.esVacia()) {
            System.out.println("   -> Encontrado: " + ((Book) librosCode.obtener(0)).getTitle());
        } else {
            System.out.println("   -> No encontrado");
        }

        // Prueba de préstamos y pila
        System.out.println("\n--- Prueba de préstamos y deshacer ---");
        library.lendBook("ISBN-001", "Juan Perez");
        System.out.println("Estado 'Cien Años...': "
                + (b1.isAvailable() ? "DISPONIBLE" : "PRESTADO"));
        System.out.println("Préstamos registrados en la pila: "
                + library.getLoanHistory().getTamano());

        System.out.println("\nError, deshaciendo último préstamo...");
        library.undoLastLoan();
        System.out.println("Estado tras deshacer: "
                + (b1.isAvailable() ? "DISPONIBLE" : "PRESTADO"));

        // Prueba completa de cola de espera
        System.out.println("\n--- Prueba de cola de espera ---");
        library.lendBook("ISBN-003", "Ana Gomez");

        System.out.println("Intento prestar un libro ya prestado...");
        library.lendBook("ISBN-003", "Carlos Lopez");
        library.lendBook("ISBN-003", "Diana Ruiz");
        System.out.println("Primer usuario en espera: " + b3.peekNextWaitingUser());
        System.out.println("Usuarios en espera: " + b3.getWaitList().getTamano());

        // Ana devuelve el libro: Carlos recibe el siguiente préstamo
        System.out.println("\nAna devuelve el libro...");
        Book cleanCode = library.findBookByIsbn("ISBN-003");
        cleanCode.returnBook();

        System.out.println("Verifico quien queda ahora en espera...");
        System.out.println("Siguiente usuario: " + cleanCode.peekNextWaitingUser());
        System.out.println("Estado 'Clean Code': "
                + (cleanCode.isAvailable() ? "DISPONIBLE" : "PRESTADO"));

        // Eliminar un libro
        System.out.println("\n--- Eliminando un libro ---");
        if (library.removeBook(b2)) {
            System.out.println("Libro 'El Quijote' eliminado exitosamente");
        } else {
            System.out.println("Error al eliminar el libro");
        }

        // Estadísticas finales
        System.out.println("\n--- Prueba de estadísticas ---");
        System.out.println(library.getStadistics());
        System.out.println("\n--- Pruebas completadas correctamente ---");
    }
}
