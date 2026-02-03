package TDAs.conjunto;

import java.util.List;

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
    List<T> listar();
}
