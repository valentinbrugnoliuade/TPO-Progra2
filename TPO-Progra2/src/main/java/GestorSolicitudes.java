import java.util.ArrayDeque;
import java.util.Deque;

public class GestorSolicitudes {
    private final Deque<SolicitudSeguimiento> cola = new ArrayDeque<>();

    public void encolar(SolicitudSeguimiento solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("Solicitud inválida (null).");
        }
        cola.addLast(solicitud);
    }

    public SolicitudSeguimiento procesarSiguiente() {
        if (cola.isEmpty()) return null;
        return cola.removeFirst();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int cantidadPendientes() {
        return cola.size();
    }
}
