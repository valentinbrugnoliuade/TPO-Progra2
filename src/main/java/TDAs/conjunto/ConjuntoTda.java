package TDAs.conjunto;

import TDAs.lista.ListaTda;

/**
 * TAD Conjunto (dinámico) — Interfaz.
 * Sin elementos repetidos.
 */
public interface ConjuntoTda<T> {

    void crearConjunto();

    void agregar(T elemento);

    void eliminar(T elemento);

    boolean pertenece(T elemento);

    boolean estaVacio();

    int tamaño();

    /** Devuelve los elementos como lista (para iterar/consultar). */
    ListaTda<T> listar();
}
