package TDAs.diccionario;

import TDAs.conjunto.ConjuntoTda;
import TDAs.lista.ListaTda;
// Interfaz de diccionario clave-valor.
public interface DiccionarioTda<K, V> {

    void crearDiccionario();

    void insertar(K clave, V valor);

    V obtener(K clave);

    void eliminar(K clave);

    boolean contiene(K clave);

    int tamaño();

    ConjuntoTda<K> claves();

    ListaTda<V> valores();
}
