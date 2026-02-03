package TDAs.deque;

import java.util.ArrayDeque;
import java.util.Deque;
// Implementación con ArrayDeque.
public class DequeImpl<T> implements DequeTda<T> {

    private Deque<T> elementos;

    @Override
    public void crearDeque() {
        elementos = new ArrayDeque<>();
    }

    @Override
    public void agregarInicio(T elemento) {
        if (elementos == null) {
            throw new IllegalStateException("El deque no ha sido creado. Invocar crearDeque() primero.");
        }
        elementos.addFirst(elemento);
    }

    @Override
    public void agregarFinal(T elemento) {
        if (elementos == null) {
            throw new IllegalStateException("El deque no ha sido creado. Invocar crearDeque() primero.");
        }
        elementos.addLast(elemento);
    }

    @Override
    public T eliminarInicio() {
        if (elementos == null || elementos.isEmpty()) {
            return null;
        }
        return elementos.removeFirst();
    }

    @Override
    public T eliminarFinal() {
        if (elementos == null || elementos.isEmpty()) {
            return null;
        }
        return elementos.removeLast();
    }

    @Override
    public T verInicio() {
        if (elementos == null || elementos.isEmpty()) {
            return null;
        }
        return elementos.peekFirst();
    }

    @Override
    public T verFinal() {
        if (elementos == null || elementos.isEmpty()) {
            return null;
        }
        return elementos.peekLast();
    }

    @Override
    public boolean estaVacio() {
        return elementos == null || elementos.isEmpty();
    }

    @Override
    public int tamaño() {
        return elementos == null ? 0 : elementos.size();
    }
}
