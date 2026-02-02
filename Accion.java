import java.time.LocalDateTime;

public class Accion {
    private final TipoAccion tipo;
    private final String detalle;
    private final LocalDateTime fechaHora;

    public Accion(TipoAccion tipo, String detalle) {
        this(tipo, detalle, LocalDateTime.now());
    }

    public Accion(TipoAccion tipo, String detalle, LocalDateTime fechaHora) {
        if (tipo == null) throw new IllegalArgumentException("Tipo de acción inválido.");
        this.tipo = tipo;
        this.detalle = detalle == null ? "" : detalle;
        this.fechaHora = fechaHora;
    }

    public TipoAccion getTipo() {
        return tipo;
    }

    public String getDetalle() {
        return detalle;
    }

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
