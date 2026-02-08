package TDAs.cola;

/**
 * Interfaz de cola FIFO (First In, First Out).
 */
public interface ColaTda<T> {

    /** Inicializa la cola vacía. Complejidad: O(1) */
    void crearCola();

    /** Añade un elemento al final de la cola. Complejidad: O(1) amortizado */
    void encolar(T elemento);

    /** Elimina y retorna el primer elemento de la cola. Complejidad: O(1) */
    T desencolar();

    /** Retorna el primer elemento sin eliminarlo. Complejidad: O(1) */
    T verPrimero();

    /** Verifica si la cola está vacía. Complejidad: O(1) */
    boolean esVacia();

    /** Retorna la cantidad de elementos. Complejidad: O(1) */
    int tamaño();
}
