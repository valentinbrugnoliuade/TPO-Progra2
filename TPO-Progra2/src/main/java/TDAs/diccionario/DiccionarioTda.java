package TDAs.diccionario;

import java.util.List;

import TDAs.conjunto.ConjuntoTda;

/**
 * TAD Diccionario (simple) — Interfaz.
 * Un par (clave, valor) por clave. Si la clave existe, insertar actualiza el valor.
 */
public interface DiccionarioTda<K, V> {

    void crearDiccionario();

    void insertar(K clave, V valor);

    V obtener(K clave);

    void eliminar(K clave);

    boolean contiene(K clave);

    int tamaño();

    ConjuntoTda<K> claves();

    List<V> valores();
}
