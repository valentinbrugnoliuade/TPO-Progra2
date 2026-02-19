package TDAs.grafo;

import java.util.HashMap;
import java.util.Map;

import TDAs.lista.ListaImpl;
import TDAs.lista.ListaTda;

/**
 * Implementación de grafo dirigido con lista de adyacencia (HashMap + ListaTda).
 * Cada vértice se mapea a una lista de sus vecinos (aristas salientes).
 * - AgregarVertice/ExisteVertice: O(1) promedio
 * - AgregarArista: O(1)
 * - EliminarArista/ExisteArista: O(grado(v))
 * - EliminarVertice: O(V + E)
 */
public class grafoImpl<T> implements grafoTDA<T> {

    private Map<T, ListaTda<T>> adyacencia;

    @Override
    public void crearGrafo() {
        adyacencia = new HashMap<>();
    }

    @Override
    public void agregarVertice(T v) {
        validarCreado();
        if (!adyacencia.containsKey(v)) {
            ListaTda<T> vecinos = new ListaImpl<>();
            vecinos.crearLista();
            adyacencia.put(v, vecinos);
        }
    }

    @Override
    public void eliminarVertice(T v) {
        validarCreado();
        if (!adyacencia.containsKey(v)) {
            return;
        }
        adyacencia.remove(v);
        for (Map.Entry<T, ListaTda<T>> entrada : adyacencia.entrySet()) {
            ListaTda<T> vecinos = entrada.getValue();
            int idx = vecinos.buscar(v);
            if (idx >= 0) {
                vecinos.eliminar(idx);
            }
        }
    }

    @Override
    public void agregarArista(T v, T w) {
        validarCreado();
        if (!adyacencia.containsKey(v) || !adyacencia.containsKey(w)) {
            throw new IllegalArgumentException("Ambos vértices deben existir en el grafo.");
        }
        ListaTda<T> vecinos = adyacencia.get(v);
        if (!vecinos.contiene(w)) {
            vecinos.insertar(vecinos.longitud(), w);
        }
    }

    @Override
    public void eliminarArista(T v, T w) {
        validarCreado();
        if (!adyacencia.containsKey(v)) {
            return;
        }
        ListaTda<T> vecinos = adyacencia.get(v);
        int idx = vecinos.buscar(w);
        if (idx >= 0) {
            vecinos.eliminar(idx);
        }
    }

    @Override
    public boolean existeVertice(T v) {
        return adyacencia != null && adyacencia.containsKey(v);
    }

    @Override
    public boolean existeArista(T v, T w) {
        if (adyacencia == null || !adyacencia.containsKey(v)) {
            return false;
        }
        return adyacencia.get(v).contiene(w);
    }

    @Override
    public ListaTda<T> obtenerVecinos(T v) {
        validarCreado();
        if (!adyacencia.containsKey(v)) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }
        ListaTda<T> original = adyacencia.get(v);
        ListaTda<T> copia = new ListaImpl<>();
        copia.crearLista();
        for (int i = 0; i < original.longitud(); i++) {
            copia.insertar(copia.longitud(), original.obtener(i));
        }
        return copia;
    }

    @Override
    public int obtenerGrado(T v) {
        validarCreado();
        if (!adyacencia.containsKey(v)) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }
        return adyacencia.get(v).longitud();
    }

    @Override
    public ListaTda<T> vertices() {
        validarCreado();
        ListaTda<T> lista = new ListaImpl<>();
        lista.crearLista();
        for (T vertice : adyacencia.keySet()) {
            lista.insertar(lista.longitud(), vertice);
        }
        return lista;
    }

    @Override
    public int cantidadVertices() {
        return adyacencia == null ? 0 : adyacencia.size();
    }

    @Override
    public boolean estaVacio() {
        return adyacencia == null || adyacencia.isEmpty();
    }

    private void validarCreado() {
        if (adyacencia == null) {
            throw new IllegalStateException("El grafo no ha sido creado. Invocar crearGrafo() primero.");
        }
    }
}
