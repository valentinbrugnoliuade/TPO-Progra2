import java.time.LocalDateTime;

public class Accion {
    private final TipoAccion tipo;
    private final String detalle;
    private final LocalDateTime fechaHora;

    /** Constructor con timestamp automático. Complejidad: O(1) */
    public Accion(TipoAccion tipo, String detalle) {
        this(tipo, detalle, LocalDateTime.now());
    }

    /** Constructor completo. Complejidad: O(1) */
    public Accion(TipoAccion tipo, String detalle, LocalDateTime fechaHora) {
        if (tipo == null) throw new IllegalArgumentException("Tipo de acción inválido.");
        this.tipo = tipo;
        this.detalle = detalle == null ? "" : detalle;
        this.fechaHora = fechaHora;
    }

    /** Obtiene el tipo de acción. Complejidad: O(1) */
    public TipoAccion getTipo() {
        return tipo;
    }

    /** Obtiene el detalle de la acción. Complejidad: O(1) */
    public String getDetalle() {
        return detalle;
    }

    /** Obtiene la fecha y hora de la acción. Complejidad: O(1) */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    @Override
    public String toString() {
        return "Accion{" +
                "tipo=" + tipo +
                ", detalle='" + detalle + '\'' +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
