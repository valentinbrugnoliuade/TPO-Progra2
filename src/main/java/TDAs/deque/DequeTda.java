package TDAs.deque;

/**
 * Interfaz de deque (cola de doble extremo).
 * Permite agregar/eliminar por ambos extremos.
 */
public interface DequeTda<T> {

    /** Inicializa el deque vacío. Complejidad: O(1) */
    void crearDeque();

    /** Agrega un elemento al inicio. Complejidad: O(1) amortizado */
    void agregarInicio(T elemento);

    /** Agrega un elemento al final. Complejidad: O(1) amortizado */
    void agregarFinal(T elemento);

    /** Elimina y retorna el elemento del inicio. Complejidad: O(1) */
    T eliminarInicio();

    /** Elimina y retorna el elemento del final. Complejidad: O(1) */
    T eliminarFinal();

    /** Retorna el elemento del inicio sin eliminarlo. Complejidad: O(1) */
    T verInicio();

    /** Retorna el elemento del final sin eliminarlo. Complejidad: O(1) */
    T verFinal();

    /** Verifica si el deque está vacío. Complejidad: O(1) */
    boolean estaVacio();

    /** Retorna la cantidad de elementos. Complejidad: O(1) */
    int tamaño();
}
