import java.util.*;

public class RepositorioClientes {
    private final Map<String, Cliente> porNombre = new HashMap<>();
    private final NavigableMap<Integer, Set<Cliente>> porScoring = new TreeMap<>();

    private String keyNombre(String nombre) {
        return nombre.toLowerCase().trim();
    }

    public boolean existeCliente(String nombre) {
        return porNombre.containsKey(keyNombre(nombre));
    }

    public void agregarCliente(Cliente cliente) {
        String key = keyNombre(cliente.getNombre());
        if (porNombre.containsKey(key)) {
            throw new IllegalArgumentException("Ya existe el cliente: " + cliente.getNombre());
        }

        porNombre.put(key, cliente);
        porScoring.computeIfAbsent(cliente.getScoring(), k -> new HashSet<>()).add(cliente);
    }

    public Cliente buscarPorNombre(String nombre) {
        return porNombre.get(keyNombre(nombre));
    }

    public Set<Cliente> buscarPorScoring(int scoring) {
        return porScoring.getOrDefault(scoring, Collections.emptySet());
    }

    public boolean eliminarCliente(String nombre) {
        String key = keyNombre(nombre);
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
