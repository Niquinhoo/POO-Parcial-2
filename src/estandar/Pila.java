package estandar;

public class Pila {
    private ListaEnlazada lista;

    public Pila(){
        lista = new ListaEnlazada();
    }

    public void apilar(Object dato){
        lista.insertarInicio(dato);
    }

    public Object desapilar(){
        return lista.eliminarInicio();
    }

    public Object tope(){
        return lista.verInicio();
    }

    public boolean esVacia(){
        return lista.esVacia();
    }

    public int getTamano(){
        return lista.getTamano();
    }
}
