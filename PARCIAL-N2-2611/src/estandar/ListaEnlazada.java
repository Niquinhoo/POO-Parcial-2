package estandar;

public class ListaEnlazada {
    protected Nodo inicio;
    protected Nodo fin;
    protected int tamano;
    
    public ListaEnlazada(){
        this.inicio = null;
        this.fin = null;
        this.tamano = 0;
    }

    public boolean esVacia(){
        return inicio == null;
    }

    public void insertarInicio(Object dato){
        Nodo nuevo = new Nodo(dato);
        if (esVacia()){
            inicio = nuevo;
            fin = nuevo;
        }else{
            nuevo.siguiente = inicio;
            inicio = nuevo;
        }
        tamano++;
    }

    public void insertarFinal(Object dato){
        Nodo nuevo = new Nodo(dato);
        if (esVacia()){
            inicio = nuevo;
            fin = nuevo;
        }else{
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        tamano++;
    }

    public Object eliminarInicio(){
        if (esVacia()){
            return null;
        }
        Object dato = inicio.dato;
        inicio = inicio.siguiente;
        tamano--;
        if (inicio == null){
            this.fin = null;
        }
        return dato;
    }

    public Object eliminarFinal(){
        if (esVacia()){
            return null;
        }
        Object dato = fin.dato;
        tamano--;
        if (fin == null){
            this.inicio = null;
        }
        return dato;
    }

    public Object verInicio(){
        if (esVacia()){
            return null;
        }
        return inicio.dato;
    }

    public int getTamano(){
        return tamano;
    }

    // Obtener elemento por índice (para recorrer con for)
    public Object obtener(int indice) {
        if (indice < 0 || indice >= tamano) {
            return null;
        }
        Nodo actual = inicio;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    // Eliminar un objeto específico 
    public boolean eliminar(Object dato) {
        if (esVacia()) return false;

        // Caso especial: es el primero
        if (inicio.dato.equals(dato)) {
            eliminarInicio();
            return true;
        }

        // Buscar el objeto
        Nodo actual = inicio;
        while (actual.siguiente != null) {
            if (actual.siguiente.dato.equals(dato)) {
                actual.siguiente = actual.siguiente.siguiente;
                if (actual.siguiente == null) { // Si eliminamos el último
                    fin = actual;
                }
                tamano--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

}
