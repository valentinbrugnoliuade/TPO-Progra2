package TDAs.deque;

/**
 * TAD Deque (cola de doble extremo) — Interfaz.
 * Operaciones según especificación:
 * - agregarInicio / agregarFinal
 * - eliminarInicio / eliminarFinal
 * - verInicio / verFinal
 * - estaVacio / tamaño
 */
public interface DequeTda<T> {

    void crearDeque();

    void agregarInicio(T elemento);

    void agregarFinal(T elemento);

    T eliminarInicio();

    T eliminarFinal();

    T verInicio();

    T verFinal();

    boolean estaVacio();

    int tamaño();
}
