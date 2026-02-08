import java.util.ArrayList;
import java.util.List;

/**
 * Clase contenedora para deserialización JSON de clientes.
 */
public class ClientesData {
    private List<Cliente> Clientes = new ArrayList<>();

    /** Obtiene la lista de clientes. Complejidad: O(1) */
    public List<Cliente> getClientes() {
        return Clientes;
    }

    /** Establece la lista de clientes. Complejidad: O(1) */
    public void setClientes(List<Cliente> clientes) {
        this.Clientes = clientes;
    }
}