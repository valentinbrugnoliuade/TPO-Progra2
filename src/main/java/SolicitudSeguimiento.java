import java.time.LocalDateTime;

public class SolicitudSeguimiento {
    private final String solicitante;
    private final String objetivo;
    private final LocalDateTime fechaHora;

    public SolicitudSeguimiento(String solicitante, String objetivo) {
        if (solicitante == null || solicitante.isBlank())
            throw new IllegalArgumentException("Solicitante inválido.");
        if (objetivo == null || objetivo.isBlank())
            throw new IllegalArgumentException("Objetivo inválido.");

        this.solicitante = solicitante;
        this.objetivo = objetivo;
        this.fechaHora = LocalDateTime.now();
    }

    public String getSolicitante() {
        return solicitante;
    }

    public String getObjetivo() {
        return objetivo;
    }

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
