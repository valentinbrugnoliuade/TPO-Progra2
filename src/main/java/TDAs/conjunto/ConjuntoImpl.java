package TDAs.conjunto;

import java.util.HashSet;
import java.util.Set;

import TDAs.lista.ListaImpl;
import TDAs.lista.ListaTda;

/**
 * Implementación de ConjuntoTda (dinámico) usando HashSet.
 */
public class ConjuntoImpl<T> implements ConjuntoTda<T> {

    private Set<T> elementos;

    @Override
    public void crearConjunto() {
        elementos = new HashSet<>();
    }

    @Override
    public void agregar(T elemento) {
        if (elementos == null) {
            throw new IllegalStateException("El conjunto no ha sido creado. Invocar crearConjunto() primero.");
        }
        elementos.add(elemento);
    }

    @Override
    public void eliminar(T elemento) {
        if (elementos != null) {
            elementos.remove(elemento);
        }
    }

    @Override
    public boolean pertenece(T elemento) {
        return elementos != null && elementos.contains(elemento);
    }

    @Override
    public boolean estaVacio() {
        return elementos == null || elementos.isEmpty();
    }

    @Override
    public int tamaño() {
        return elementos == null ? 0 : elementos.size();
    }

    @Override
    public ListaTda<T> listar() {
        ListaTda<T> lista = new ListaImpl<>();
        lista.crearLista();
        if (elementos != null) {
            for (T e : elementos) {
                lista.insertar(lista.longitud(), e);
            }
        }
        return lista;
    }
}
