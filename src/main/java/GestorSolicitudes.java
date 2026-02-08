import TDAs.cola.ColaImpl;
import TDAs.cola.ColaTda;

/**
 * Gestor de solicitudes de seguimiento usando cola FIFO.
 */
public class GestorSolicitudes {
    private final ColaTda<SolicitudSeguimiento> cola;

    /** Constructor que inicializa la cola. Complejidad: O(1) */
    public GestorSolicitudes() {
        cola = new ColaImpl<>();
        cola.crearCola();
    }

    /** Encola una solicitud de seguimiento. Complejidad: O(1) amortizado */
    public void encolar(SolicitudSeguimiento solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("Solicitud inválida (null).");
        }
        cola.encolar(solicitud);
    }

    /** Procesa (desencola) la siguiente solicitud. Complejidad: O(1) */
    public SolicitudSeguimiento procesarSiguiente() {
        return cola.desencolar();
    }

    /** Verifica si hay solicitudes pendientes. Complejidad: O(1) */
    public boolean estaVacia() {
        return cola.esVacia();
    }

    /** Retorna la cantidad de solicitudes pendientes. Complejidad: O(1) */
    public int cantidadPendientes() {
        return cola.tamaño();
    }
}
