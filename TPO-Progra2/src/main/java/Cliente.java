import java.util.Objects;

public class Cliente {
    private final String nombre;
    private final int scoring;

    public Cliente(String nombre, int scoring) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
        this.scoring = scoring;
    }

    public String getNombre() {
        return nombre;
    }

    public int getScoring() {
        return scoring;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", scoring=" + scoring +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return nombre.equalsIgnoreCase(cliente.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase());
    }
}
