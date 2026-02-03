package TDAs.lista;

import java.util.ArrayList;

/**
 * Implementación de ListaTda usando ArrayList.
 */
public class ListaImpl<T> implements ListaTda<T> {

    private ArrayList<T> elementos;

    @Override
    public void crearLista() {
        elementos = new ArrayList<>();
    }

    @Override
    public T obtener(int i) {
        if (elementos == null || i < 0 || i >= elementos.size()) {
            throw new IndexOutOfBoundsException("Índice: " + i);
        }
        return elementos.get(i);
    }

    @Override
    public int longitud() {
        return elementos == null ? 0 : elementos.size();
    }

    @Override
    public void insertar(int i, T elemento) {
        if (elementos == null) {
            throw new IllegalStateException("La lista no ha sido creada. Invocar crearLista() primero.");
        }
        if (i < 0 || i > elementos.size()) {
            throw new IndexOutOfBoundsException("Índice: " + i);
        }
        elementos.add(i, elemento);
    }

    @Override
    public void eliminar(int i) {
        if (elementos != null && i >= 0 && i < elementos.size()) {
            elementos.remove(i);
        }
    }

    @Override
    public void reemplazar(int i, T nuevoElemento) {
        if (elementos == null || i < 0 || i >= elementos.size()) {
            throw new IndexOutOfBoundsException("Índice: " + i);
        }
        elementos.set(i, nuevoElemento);
    }

    @Override
    public int buscar(T elemento) {
        if (elementos == null) return -1;
        int idx = elementos.indexOf(elemento);
        return idx;
    }

    @Override
    public boolean contiene(T elemento) {
        return buscar(elemento) >= 0;
    }

    @Override
    public void imprimirLista() {
        if (elementos == null) return;
        System.out.print("[");
        for (int i = 0; i < elementos.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(elementos.get(i));
        }
        System.out.println("]");
    }

    @Override
    public void concatenar(ListaTda<T> lista2) {
        if (elementos == null) {
            throw new IllegalStateException("La lista no ha sido creada.");
        }
        if (lista2 != null) {
            for (int i = 0; i < lista2.longitud(); i++) {
                elementos.add(lista2.obtener(i));
            }
        }
    }

    @Override
    public void invertir() {
        if (elementos != null && elementos.size() > 1) {
            java.util.Collections.reverse(elementos);
        }
    }

    @Override
    public String toString() {
        if (elementos == null) return "[]";
        return elementos.toString();
    }
}
