import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class RepositorioClientes {
    private final Map<String, Cliente> porNombre = new HashMap<>();
    private final NavigableMap<Integer, Set<Cliente>> porScoring = new TreeMap<>();

    private String keyNombre(String nombre) {
        if (nombre == null) return null;
        String key = nombre.trim().toLowerCase(Locale.ROOT);
        return key.isEmpty() ? null : key;
    }

    public boolean existeCliente(String nombre) {
        String key = keyNombre(nombre);
        if (key == null) return false;
        return porNombre.containsKey(key);
    }

    public void agregarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente inválido (null).");
        }
        String key = keyNombre(cliente.getNombre());
        if (key == null) {
            throw new IllegalArgumentException("Nombre de cliente inválido.");
        }
        if (porNombre.containsKey(key)) {
            throw new IllegalArgumentException("Ya existe el cliente: " + cliente.getNombre());
        }

        porNombre.put(key, cliente);
        porScoring.computeIfAbsent(cliente.getScoring(), k -> new HashSet<>()).add(cliente);
    }

    public Cliente buscarPorNombre(String nombre) {
        String key = keyNombre(nombre);
        if (key == null) return null;
        return porNombre.get(key);
    }

    public Set<Cliente> buscarPorScoring(int scoring) {
        Set<Cliente> set = porScoring.get(scoring);
        if (set == null) return Collections.emptySet();
        return Collections.unmodifiableSet(set);
    }

    public boolean eliminarCliente(String nombre) {
        String key = keyNombre(nombre);
        if (key == null) return false;
        Cliente eliminado = porNombre.remove(key);
        if (eliminado == null) return false;

        Set<Cliente> set = porScoring.get(eliminado.getScoring());
        if (set != null) {
            set.remove(eliminado);
            if (set.isEmpty()) {
                porScoring.remove(eliminado.getScoring());
            }
        }
        return true;
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(porNombre.values());
    }

    public int cantidadClientes() {
        return porNombre.size();
    }
}
