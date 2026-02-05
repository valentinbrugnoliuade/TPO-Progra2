import java.util.ArrayList;
import java.util.List;

public class ClientesData {
    private List<Cliente> Clientes = new ArrayList<>();

    public List<Cliente> getClientes() {
        return Clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.Clientes = clientes;
    }
}