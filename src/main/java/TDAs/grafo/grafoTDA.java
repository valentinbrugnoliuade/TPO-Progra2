package TDAs.grafo;

import TDAs.lista.ListaTda;

/**
 * Interfaz de grafo dirigido con lista de adyacencia.
 */
public interface grafoTDA<T> {

    /** Inicializa el grafo vacío. Complejidad: O(1) */
    void crearGrafo();

    /** Agrega un vértice al grafo. Si ya existe, no hace nada. Complejidad: O(1) promedio */
    void agregarVertice(T v);

    /** Elimina un vértice y todas sus aristas (entrantes y salientes). Complejidad: O(V + E) */
    void eliminarVertice(T v);

    /** Agrega una arista dirigida de v hacia w. Ambos vértices deben existir. Complejidad: O(1) */
    void agregarArista(T v, T w);

    /** Elimina la arista dirigida de v hacia w. Complejidad: O(grado(v)) */
    void eliminarArista(T v, T w);

    /** Verifica si el vértice v existe en el grafo. Complejidad: O(1) promedio */
    boolean existeVertice(T v);

    /** Verifica si existe una arista dirigida de v hacia w. Complejidad: O(grado(v)) */
    boolean existeArista(T v, T w);

    /** Retorna la lista de vértices adyacentes (vecinos) a v. Complejidad: O(grado(v)) */
    ListaTda<T> obtenerVecinos(T v);

    /** Retorna el grado de salida del vértice v (cantidad de aristas salientes). Complejidad: O(1) */
    int obtenerGrado(T v);

    /** Retorna la lista de todos los vértices del grafo. Complejidad: O(V) */
    ListaTda<T> vertices();

    /** Retorna la cantidad de vértices del grafo. Complejidad: O(1) */
    int cantidadVertices();

    /** Verifica si el grafo está vacío. Complejidad: O(1) */
    boolean estaVacio();
}
