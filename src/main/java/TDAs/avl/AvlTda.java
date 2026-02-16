package TDAs.avl;

import TDAs.lista.ListaTda;

/**
 * TDA Árbol AVL (ABB autobalanceado).
 *
 * Invariante:
 * - Propiedad de ABB: izq < nodo < der (según Comparable)
 * - Para todo nodo: factorBalance = altura(izq) - altura(der) ∈ {-1,0,1}
 */
public interface AvlTda<T extends Comparable<T>> {

    /** Inicializa el árbol vacío. Complejidad: O(1) */
    void crearArbol();

    /** Determina si el árbol está vacío. Complejidad: O(1) */
    boolean esVacio();

    /** Inserta un elemento (si ya existe, no hace nada). Complejidad: O(log n) */
    void insertar(T valor);

    /** Elimina un elemento (si no existe, no hace nada). Complejidad: O(log n) */
    void eliminar(T valor);

    /** Verifica si un valor existe en el árbol. Complejidad: O(log n) */
    boolean contiene(T valor);

    /**
     * Retorna la altura del árbol medida en aristas.
     * - Árbol vacío: -1
     * - Un solo nodo: 0
     * Complejidad: O(1)
     */
    int altura();

    /** Recorrido inorden (ordenado). Complejidad: O(n) */
    ListaTda<T> inorden();

    /** Recorrido preorden. Complejidad: O(n) */
    ListaTda<T> preorden();

    /** Recorrido postorden. Complejidad: O(n) */
    ListaTda<T> postorden();

    /** Imprime el árbol en consola en formato jerárquico (con sangría). Complejidad: O(n) */
    void imprimirArbol();

    // --- Ejercicios (Clase 12) ---

    /** Obtiene el elemento mayor del árbol (máximo). Retorna null si está vacío. Complejidad: O(log n) */
    T mayor();

    /**
     * Devuelve una lista con los elementos mayores que la raíz.
     * Si el árbol está vacío, devuelve lista vacía.
     * Complejidad: O(n) en el peor caso
     */
    ListaTda<T> mayoresQueRaiz();
}

