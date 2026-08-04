package estandar;

import java.util.Objects;

public class ListaEnlazada {
    // Tamaño de la lista
    protected Nodo inicio;
    protected Nodo fin;
    protected int tamano;
    
    public ListaEnlazada(){
        this.inicio = null;
        this.fin = null;
        this.tamano = 0;
    }
    //Comprueba si tengo o no elementos en el principio, si tengo, significa que hay datos
    public boolean esVacia(){
        return inicio == null;
    }
    //Agrega un nodo nuevo al principio
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
    //Agrega un nodo nuevo al final
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
    //Elimina y devuelve el primer elemento de la lista y pasa al siguiente para ser el nuevo inicio
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
    //Elimina y devuelve el final elemento de la lista y lo actualiza a un nuevo "Fin"
    public Object eliminarFinal(){
        if (esVacia()){
            return null;
        }
        Object dato = fin.dato;
        if (inicio == fin) {
            inicio = null;
            fin = null;
        } else {
            Nodo actual = inicio;
            while (actual.siguiente != fin) {
                actual = actual.siguiente;
            }
            actual.siguiente = null;
            fin = actual;
        }
        tamano--;
        return dato;
    }
    //Carga el inicio en memoria 
    public Object verInicio(){
        if (esVacia()){
            return null;
        }
        return inicio.dato;
    }
    //Cuenta la cantidad de elementos que hay, en esta funcion no lo hace especificamente
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
        if (esVacia()) {
            return false;
        }

        if (Objects.equals(inicio.dato, dato)) {
            eliminarInicio();
            return true;
        }

        Nodo actual = inicio;
        while (actual.siguiente != null) {
            if (Objects.equals(actual.siguiente.dato, dato)) {
                actual.siguiente = actual.siguiente.siguiente;
                if (actual.siguiente == null) {
                    fin = actual;
                }
                tamano--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }
    // verifica el input, y si existe, devuelve el proximo
    public boolean contiene(Object dato) {
        Nodo actual = inicio;
        while (actual != null) {
            if (Objects.equals(actual.dato, dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }
    //elimina todo
    public void limpiar() {
        inicio = null;
        fin = null;
        tamano = 0;
    }

}
