package TDAs.diccionario;

import TDAs.conjunto.ConjuntoTda;
import TDAs.lista.ListaTda;

/**
 * Interfaz de diccionario clave-valor.
 */
public interface DiccionarioTda<K, V> {

    /** Inicializa el diccionario vacío. Complejidad: O(1) */
    void crearDiccionario();

    /** Inserta o actualiza un par clave-valor. Complejidad: O(1) promedio con HashMap */
    void insertar(K clave, V valor);

    /** Obtiene el valor asociado a una clave. Complejidad: O(1) promedio con HashMap */
    V obtener(K clave);

    /** Elimina un par clave-valor. Complejidad: O(1) promedio con HashMap */
    void eliminar(K clave);

    /** Verifica si la clave existe. Complejidad: O(1) promedio con HashMap */
    boolean contiene(K clave);

    /** Retorna la cantidad de pares clave-valor. Complejidad: O(1) */
    int tamaño();

    /** Retorna un conjunto con todas las claves. Complejidad: O(n) donde n es el número de pares */
    ConjuntoTda<K> claves();

    /** Retorna una lista con todos los valores. Complejidad: O(n) donde n es el número de pares */
    ListaTda<V> valores();
}
