import java.time.LocalDateTime;

/**
 * Representa una solicitud de seguimiento entre clientes.
 */
public class SolicitudSeguimiento {
    private final String solicitante;
    private final String objetivo;
    private final LocalDateTime fechaHora;

    /** Constructor con timestamp automático. Complejidad: O(1) */
    public SolicitudSeguimiento(String solicitante, String objetivo) {
        if (solicitante == null || solicitante.isBlank())
            throw new IllegalArgumentException("Solicitante inválido.");
        if (objetivo == null || objetivo.isBlank())
            throw new IllegalArgumentException("Objetivo inválido.");

        this.solicitante = solicitante.trim();
        this.objetivo = objetivo.trim();
        this.fechaHora = LocalDateTime.now();
    }

    /** Obtiene el solicitante. Complejidad: O(1) */
    public String getSolicitante() {
        return solicitante;
    }

    /** Obtiene el objetivo. Complejidad: O(1) */
    public String getObjetivo() {
        return objetivo;
    }

    /** Obtiene la fecha y hora de la solicitud. Complejidad: O(1) */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    @Override
    public String toString() {
        return "SolicitudSeguimiento{" +
                "solicitante='" + solicitante + '\'' +
                ", objetivo='" + objetivo + '\'' +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
