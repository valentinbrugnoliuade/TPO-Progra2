package TDAs.cola;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implementación de cola con ArrayDeque.
 * Todas las operaciones son O(1) o O(1) amortizado.
 */
public class ColaImpl<T> implements ColaTda<T> {

    private Deque<T> elementos;

    @Override
    public void crearCola() {
        elementos = new ArrayDeque<>();
    }

    @Override
    public void encolar(T elemento) {
        if (elementos == null) {
            throw new IllegalStateException("La cola no ha sido creada. Invocar crearCola() primero.");
        }
        elementos.addLast(elemento);
    }

    @Override
    public T desencolar() {
        if (elementos == null || elementos.isEmpty()) {
            return null;
        }
        return elementos.removeFirst();
    }

    @Override
    public T verPrimero() {
        if (elementos == null || elementos.isEmpty()) {
            return null;
        }
        return elementos.peekFirst();
    }

    @Override
    public boolean esVacia() {
        return elementos == null || elementos.isEmpty();
    }

    @Override
    public int tamaño() {
        return elementos == null ? 0 : elementos.size();
    }
}
