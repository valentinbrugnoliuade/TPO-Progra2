package TDAs.conjunto;

import TDAs.lista.ListaTda;
// Interfaz de conjunto sin repetidos.
public interface ConjuntoTda<T> {

    void crearConjunto();

    void agregar(T elemento);

    void eliminar(T elemento);

    boolean pertenece(T elemento);

    boolean estaVacio();

    int tamaño();

    ListaTda<T> listar();
}
