package TDAs.conjunto;

import TDAs.lista.ListaTda;

/**
 * Interfaz de conjunto sin elementos repetidos.
 */
public interface ConjuntoTda<T> {

    /** Inicializa el conjunto vacío. Complejidad: O(1) */
    void crearConjunto();

    /** Agrega un elemento (sin duplicados). Complejidad: O(1) promedio con HashSet */
    void agregar(T elemento);

    /** Elimina un elemento del conjunto. Complejidad: O(1) promedio con HashSet */
    void eliminar(T elemento);

    /** Verifica si el elemento pertenece al conjunto. Complejidad: O(1) promedio con HashSet */
    boolean pertenece(T elemento);

    /** Verifica si el conjunto está vacío. Complejidad: O(1) */
    boolean estaVacio();

    /** Retorna la cantidad de elementos. Complejidad: O(1) */
    int tamaño();

    /** Retorna una lista con todos los elementos. Complejidad: O(n) donde n es el tamaño del conjunto */
    ListaTda<T> listar();
}
