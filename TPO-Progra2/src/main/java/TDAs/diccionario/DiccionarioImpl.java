package TDAs.diccionario;

import java.util.HashMap;
import java.util.Map;

import TDAs.conjunto.ConjuntoImpl;
import TDAs.conjunto.ConjuntoTda;
import TDAs.lista.ListaImpl;
import TDAs.lista.ListaTda;

/**
 * Implementación de DiccionarioTda (simple) usando HashMap.
 */
public class DiccionarioImpl<K, V> implements DiccionarioTda<K, V> {

    private Map<K, V> elementos;

    @Override
    public void crearDiccionario() {
        elementos = new HashMap<>();
    }

    @Override
    public void insertar(K clave, V valor) {
        if (elementos == null) {
            throw new IllegalStateException("El diccionario no ha sido creado. Invocar crearDiccionario() primero.");
        }
        elementos.put(clave, valor);
    }

    @Override
    public V obtener(K clave) {
        return elementos == null ? null : elementos.get(clave);
    }

    @Override
    public void eliminar(K clave) {
        if (elementos != null) {
            elementos.remove(clave);
        }
    }

    @Override
    public boolean contiene(K clave) {
        return elementos != null && elementos.containsKey(clave);
    }

    @Override
    public int tamaño() {
        return elementos == null ? 0 : elementos.size();
    }

    @Override
    public ConjuntoTda<K> claves() {
        ConjuntoTda<K> c = new ConjuntoImpl<>();
        c.crearConjunto();
        if (elementos != null) {
            for (K clave : elementos.keySet()) {
                c.agregar(clave);
            }
        }
        return c;
    }

    @Override
    public ListaTda<V> valores() {
        ListaTda<V> lista = new ListaImpl<>();
        lista.crearLista();
        if (elementos != null) {
            for (V v : elementos.values()) {
                lista.insertar(lista.longitud(), v);
            }
        }
        return lista;
    }
}
