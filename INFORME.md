# Informe del parcial: sistema de biblioteca

En cada tarea se explica qué pedía el ejercicio, qué se implementó, qué archivos participan y un ejemplo
del código utilizado.

## Relación entre los nombres del ejercicio y el repositorio

El enunciado menciona las estructuras en inglés, pero el repositorio ya tenía
sus equivalentes dentro de `src/estandar`:

| Enunciado | Código utilizado |
|---|---|
| `Node` | `estandar.Nodo` |
| `LinkedList` | `estandar.ListaEnlazada` |
| `Stack` | `estandar.Pila` |
| `Queue` | `estandar.Cola` |

Se reutilizaron esos componentes. No se crearon estructuras duplicadas ni se
usaron `java.util.LinkedList`, `java.util.Stack` o `java.util.Queue`.

---

## Tarea 1 — Implementación de la lista enlazada

### Qué pedía el ejercicio

La lista debía permitir:

- agregar elementos;
- obtener elementos por posición;
- eliminar por valor;
- eliminar todos los elementos;
- verificar si contiene un valor;
- consultar cantidad y estado vacío.

También debía utilizar nodos y mantener correctamente las referencias entre
ellos.

### Qué se hizo

Se reutilizó `src/estandar/Nodo.java` como unidad básica y
`src/estandar/ListaEnlazada.java` como lista principal.

La lista mantiene:

- `inicio`: primer nodo;
- `fin`: último nodo;
- `tamano`: cantidad de elementos.

Se conservaron las operaciones que ya existían y se agregaron o corrigieron:

| Método | Función |
|---|---|
| `insertarInicio` | agrega al principio |
| `insertarFinal` | agrega al final |
| `obtener` | obtiene un elemento por índice |
| `eliminar` | elimina la primera coincidencia |
| `eliminarInicio` | elimina el primer nodo |
| `eliminarFinal` | elimina el último nodo |
| `contiene` | busca un valor |
| `limpiar` | deja la lista vacía |
| `esVacia` | verifica si no hay elementos |
| `getTamano` | devuelve la cantidad de elementos |

### Corrección importante

`eliminarFinal()` estaba descontando el tamaño, pero no actualizaba realmente
el último nodo. Ahora busca el nodo anterior, corta el enlace y lo convierte en
el nuevo `fin`:

```java
while (actual.siguiente != fin) {
    actual = actual.siguiente;
}
actual.siguiente = null;
fin = actual;
tamano--;
```

También se usa una comparación segura para que eliminar o buscar `null` no
produzca un error:

```java
if (Objects.equals(actual.siguiente.dato, dato)) {
    // eliminar el nodo encontrado
}
```

### Ejemplo de uso

Este es el bloque que se ejecuta en `Main.java` para mostrar las operaciones:

```java
ListaEnlazada list = new ListaEnlazada();
list.insertarFinal(10);
list.insertarFinal(20);

System.out.println("Elementos en la lista: " + list.getTamano());
System.out.println("Contiene 'Primer elemento': "
        + list.contiene("Primer elemento"));

list.eliminar(10);
list.limpiar();
System.out.println("¿La lista quedó vacía?: " + list.esVacia());
```

Con esto se comprueba tanto el funcionamiento normal como la lista vacía y
los índices inválidos.

---

## Tarea 2 — Operaciones de préstamo con una pila

### Qué pedía el ejercicio

Había que registrar los préstamos recientes y permitir:

- registrar un préstamo;
- consultar el último préstamo;
- deshacer el último préstamo.

La estructura adecuada es una pila porque el último préstamo realizado debe
ser el primero que se deshace. Este comportamiento se llama LIFO.

### Qué se hizo

Se reutilizó `src/estandar/Pila.java`. La pila se apoya en
`ListaEnlazada`, insertando y quitando por el inicio:

```java
public void apilar(Object dato){
    lista.insertarInicio(dato);
}

public Object desapilar(){
    return lista.eliminarInicio();
}
```

En `Library.java` se agregó el historial:

```java
private final Pila loanHistory;
```

Cuando el libro se presta correctamente, se apila:

```java
if (book.lend()) {
    loanHistory.apilar(book);
    return true;
}
```

Para consultar el último préstamo se usa `tope()`:

```java
public Book getLastLoan() {
    return (Book) loanHistory.tope();
}
```

Para deshacerlo se usa `desapilar()` y se devuelve el libro:

```java
public boolean undoLastLoan() {
    Book book = (Book) loanHistory.desapilar();
    if (book == null) {
        return false;
    }
    book.returnBook();
    return true;
}
```

### Ejemplo de uso

```java
library.lendBook("ISBN-001", "Diego");
System.out.println("Préstamos registrados: "
        + library.getLoanHistory().getTamano());

library.undoLastLoan();
System.out.println("Estado tras deshacer: "
        + (book1.isAvailable() ? "DISPONIBLE" : "PRESTADO"));
```

Si no hay préstamos, `desapilar()` devuelve `null` y la operación termina sin
romper el programa.

---

## Tarea 3 — Espera de libros con una cola

### Qué pedía el ejercicio

Cuando un libro está prestado, los usuarios deben quedar esperando en el
orden en que llegaron. Al devolver el libro, el primero de la fila debe ser
atendido.

La estructura adecuada es una cola FIFO: el primero en entrar es el primero en
salir.

### Qué se hizo

Se reutilizó `src/estandar/Cola.java`. La cola agrega al final y quita desde el
inicio:

```java
public void encolar(Object dato){
    lista.insertarFinal(dato);
}

public Object desencolar(){
    return lista.eliminarInicio();
}
```

En `Book.java` se agregó la cola de espera:

```java
private Cola waitList;
```

Cuando el libro ya está prestado, `Library.lendBook()` agrega el usuario a la
cola:

```java
if (book.lend()) {
    loanHistory.apilar(book);
    return true;
}
book.addToWaitList(userName);
return false;
```

`Book.returnBook()` aplica la regla de asignación:

```java
public void returnBook() {
    if (waitList.esVacia()) {
        available = true;
        return;
    }

    waitList.desencolar();
    available = false;
    timesLoaned++;
}
```

Si no hay usuarios, el libro queda disponible. Si hay usuarios, se quita al
primero de la cola y el libro continúa prestado para esa persona.

### Ejemplo de uso

```java
library.lendBook("ISBN-003", "Ana");
library.lendBook("ISBN-003", "Bruno");
library.lendBook("ISBN-003", "Carla");

System.out.println("Primer usuario en espera: "
        + book3.peekNextWaitingUser());

book3.returnBook();
System.out.println("Siguiente usuario: "
        + book3.peekNextWaitingUser());
```

Ana recibe el préstamo inicial. Cuando lo devuelve, Bruno es atendido primero
y Carla queda como siguiente usuario.

---

## Tarea 4 — Prueba inicial del sistema

### Qué pedía el ejercicio

Había que crear ejemplos que permitieran verificar todas las funciones. No se
pedía un menú de usuario.

### Qué se hizo

`src/Main.java` funciona como prueba ejecutable. Comprueba:

1. operaciones de `ListaEnlazada`;
2. operaciones LIFO de `Pila`;
3. operaciones FIFO de `Cola`;
4. creación y carga de libros;
5. búsqueda por título, autor e ISBN;
6. libros disponibles y prestados;
7. estadísticas;
8. préstamos y deshacer;
9. cola de espera;
10. casos vacíos o sin resultados.

Las verificaciones se muestran por consola y se controlan con condiciones
simples, por ejemplo:

```java
if (!librosCode.esVacia()) {
    System.out.println("Encontrado: " + librosCode.obtener(0));
} else {
    System.out.println("No encontrado");
}
```

De esta forma la ejecución conserva el formato original, mostrando en la
consola qué ocurrió en cada paso.

### Ejecución

Desde la raíz del repositorio:

```powershell
javac -encoding UTF-8 -d bin src\*.java src\estandar\*.java
java -cp bin Main
```

La salida final esperada es:

```text
Pruebas completadas correctamente.
```

---

## Archivos que participan

| Archivo | Responsabilidad |
|---|---|
| `src/estandar/Nodo.java` | nodo enlazado básico |
| `src/estandar/ListaEnlazada.java` | lista y operaciones generales |
| `src/estandar/Pila.java` | historial LIFO de préstamos |
| `src/estandar/Cola.java` | espera FIFO de usuarios |
| `src/Book.java` | datos del libro y su cola de espera |
| `src/Library.java` | libros, búsquedas, préstamos y estadísticas |
| `src/Main.java` | prueba de todas las tareas |

## Resultado final

Las cuatro tareas del ejercicio quedaron integradas sobre los módulos que ya
existían en `estandar`. Se corrigió lo necesario, se evitó duplicar las
estructuras y se dejaron ejemplos ejecutables para comprobar cada parte.
