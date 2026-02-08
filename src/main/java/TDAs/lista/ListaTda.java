package TDAs.lista;

/**
 * Interfaz de lista con acceso por índice.
 */
public interface ListaTda<T> {

    /** Inicializa la lista vacía. Complejidad: O(1) */
    void crearLista();

    /** Obtiene el elemento en la posición i. Complejidad: O(1) */
    T obtener(int i);

    /** Retorna la cantidad de elementos. Complejidad: O(1) */
    int longitud();

    /** Inserta un elemento en la posición i. Complejidad: O(n) en el peor caso (inserción al inicio) */
    void insertar(int i, T elemento);

    /** Elimina el elemento en la posición i. Complejidad: O(n) en el peor caso */
    void eliminar(int i);

    /** Reemplaza el elemento en la posición i. Complejidad: O(1) */
    void reemplazar(int i, T nuevoElemento);

    /** Busca la primera ocurrencia del elemento. Complejidad: O(n) */
    int buscar(T elemento);

    /** Verifica si el elemento existe en la lista. Complejidad: O(n) */
    boolean contiene(T elemento);

    /** Imprime la lista en consola. Complejidad: O(n) */
    void imprimirLista();

    /** Concatena otra lista al final. Complejidad: O(m) donde m es el tamaño de lista2 */
    void concatenar(ListaTda<T> lista2);

    /** Invierte el orden de los elementos. Complejidad: O(n) */
    void invertir();
}
