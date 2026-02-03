import TDAs.cola.ColaImpl;
import TDAs.cola.ColaTda;

public class GestorSolicitudes {
    private final ColaTda<SolicitudSeguimiento> cola;

    public GestorSolicitudes() {
        cola = new ColaImpl<>();
        cola.crearCola();
    }

    public void encolar(SolicitudSeguimiento solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("Solicitud inválida (null).");
        }
        cola.encolar(solicitud);
    }

    public SolicitudSeguimiento procesarSiguiente() {
        return cola.desencolar();
    }

    public boolean estaVacia() {
        return cola.esVacia();
    }

    public int cantidadPendientes() {
        return cola.tamaño();
    }
}
