import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class HistorialAcciones {
    private final Deque<Accion> pila = new ArrayDeque<>();

    public void registrarAccion(Accion accion) {
        if (accion == null) {
            throw new IllegalArgumentException("Acción inválida (null).");
        }
        pila.push(accion);
    }

    public Accion deshacerUltimaAccion() {
        if (pila.isEmpty()) return null;
        return pila.pop();
    }

    public boolean estaVacio() {
        return pila.isEmpty();
    }

    public List<Accion> listarAcciones() {
        return pila.stream().collect(Collectors.toList());
    }
}
