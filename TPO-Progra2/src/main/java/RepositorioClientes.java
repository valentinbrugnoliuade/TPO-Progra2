import java.util.Locale;

import TDAs.conjunto.ConjuntoImpl;
import TDAs.conjunto.ConjuntoTda;
import TDAs.diccionario.DiccionarioImpl;
import TDAs.diccionario.DiccionarioTda;
import TDAs.lista.ListaImpl;
import TDAs.lista.ListaTda;

public class RepositorioClientes {
    private final DiccionarioTda<String, Cliente> porNombre;
    private final DiccionarioTda<Integer, ConjuntoTda<Cliente>> porScoring;

    public RepositorioClientes() {
        porNombre = new DiccionarioImpl<>();
        porNombre.crearDiccionario();
        porScoring = new DiccionarioImpl<>();
        porScoring.crearDiccionario();
    }

    private String keyNombre(String nombre) {
        if (nombre == null) return null;
        String key = nombre.trim().toLowerCase(Locale.ROOT);
        return key.isEmpty() ? null : key;
    }

    public boolean existeCliente(String nombre) {
        String key = keyNombre(nombre);
        if (key == null) return false;
        return porNombre.contiene(key);
    }

    public void agregarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente inválido (null).");
        }
        String key = keyNombre(cliente.getNombre());
        if (key == null) {
            throw new IllegalArgumentException("Nombre de cliente inválido.");
        }
        if (porNombre.contiene(key)) {
            throw new IllegalArgumentException("Ya existe el cliente: " + cliente.getNombre());
        }

        porNombre.insertar(key, cliente);
        ConjuntoTda<Cliente> conjunto = porScoring.obtener(cliente.getScoring());
        if (conjunto == null) {
            conjunto = new ConjuntoImpl<>();
            conjunto.crearConjunto();
            porScoring.insertar(cliente.getScoring(), conjunto);
        }
        conjunto.agregar(cliente);
    }

    public Cliente buscarPorNombre(String nombre) {
        String key = keyNombre(nombre);
        if (key == null) return null;
        return porNombre.obtener(key);
    }

    public ListaTda<Cliente> buscarPorScoring(int scoring) {
        ConjuntoTda<Cliente> conjunto = porScoring.obtener(scoring);
        if (conjunto == null || conjunto.estaVacio()) {
            ListaTda<Cliente> vacia = new ListaImpl<>();
            vacia.crearLista();
            return vacia;
        }
        return conjunto.listar();
    }

    public boolean eliminarCliente(String nombre) {
        String key = keyNombre(nombre);
        if (key == null) return false;
        Cliente eliminado = porNombre.obtener(key);
        if (eliminado == null) return false;

        porNombre.eliminar(key);
        ConjuntoTda<Cliente> conjunto = porScoring.obtener(eliminado.getScoring());
        if (conjunto != null) {
            conjunto.eliminar(eliminado);
            if (conjunto.estaVacio()) {
                porScoring.eliminar(eliminado.getScoring());
            }
        }
        return true;
    }

    public ListaTda<Cliente> listarTodos() {
        return porNombre.valores();
    }

    public int cantidadClientes() {
        return porNombre.tamaño();
    }
}
