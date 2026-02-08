import java.util.Locale;

import TDAs.conjunto.ConjuntoImpl;
import TDAs.conjunto.ConjuntoTda;
import TDAs.diccionario.DiccionarioImpl;
import TDAs.diccionario.DiccionarioTda;
import TDAs.lista.ListaImpl;
import TDAs.lista.ListaTda;

/**
 * Repositorio de clientes con índices por ID, nombre y scoring.
 * Permite múltiples clientes con el mismo nombre pero diferente ID.
 * Utiliza diccionarios para accesos rápidos O(1) promedio.
 */
public class RepositorioClientes {
    private final DiccionarioTda<Integer, Cliente> porId;
    private final DiccionarioTda<String, ListaTda<Cliente>> porNombre;
    private final DiccionarioTda<Integer, ConjuntoTda<Cliente>> porScoring;

    /** Constructor que inicializa los índices. Complejidad: O(1) */
    public RepositorioClientes() {
        porId = new DiccionarioImpl<>();
        porId.crearDiccionario();
        porNombre = new DiccionarioImpl<>();
        porNombre.crearDiccionario();
        porScoring = new DiccionarioImpl<>();
        porScoring.crearDiccionario();
    }

    /** Normaliza un nombre para usarlo como clave (case-insensitive). Complejidad: O(n) donde n es la longitud del nombre */
    private String keyNombre(String nombre) {
        if (nombre == null) return null;
        String key = nombre.trim().toLowerCase(Locale.ROOT);
        return key.isEmpty() ? null : key;
    }

    /** Verifica si existe un cliente con el ID dado. Complejidad: O(1) */
    public boolean existeClientePorId(int id) {
        return porId.contiene(id);
    }

    /** Verifica si existe al menos un cliente con el nombre dado. Complejidad: O(n) donde n es la longitud del nombre */
    public boolean existeCliente(String nombre) {
        String key = keyNombre(nombre);
        if (key == null) return false;
        return porNombre.contiene(key);
    }

    /** Agrega un cliente al repositorio permitiendo múltiples con el mismo nombre. Complejidad: O(n) donde n es la longitud del nombre */
    public void agregarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente inválido (null).");
        }
        String key = keyNombre(cliente.getNombre());
        if (key == null) {
            throw new IllegalArgumentException("Nombre de cliente inválido.");
        }
        if (porId.contiene(cliente.getId())) {
            throw new IllegalArgumentException("Ya existe un cliente con el ID: " + cliente.getId());
        }

        // Agregar al índice por ID
        porId.insertar(cliente.getId(), cliente);

        // Agregar al índice por nombre
        ListaTda<Cliente> clientesDelNombre = porNombre.obtener(key);
        if (clientesDelNombre == null) {
            clientesDelNombre = new ListaImpl<>();
            clientesDelNombre.crearLista();
            porNombre.insertar(key, clientesDelNombre);
        }
        clientesDelNombre.insertar(clientesDelNombre.longitud(), cliente);

        // Agregar al índice por scoring
        ConjuntoTda<Cliente> conjunto = porScoring.obtener(cliente.getScoring());
        if (conjunto == null) {
            conjunto = new ConjuntoImpl<>();
            conjunto.crearConjunto();
            porScoring.insertar(cliente.getScoring(), conjunto);
        }
        conjunto.agregar(cliente);
    }

    /** Busca un cliente por ID. Complejidad: O(1) */
    public Cliente buscarPorId(int id) {
        return porId.obtener(id);
    }

    /** Busca todos los clientes con un nombre específico (case-insensitive). Complejidad: O(m) donde m es el número de clientes con ese nombre */
    public ListaTda<Cliente> buscarPorNombre(String nombre) {
        String key = keyNombre(nombre);
        if (key == null) {
            ListaTda<Cliente> vacia = new ListaImpl<>();
            vacia.crearLista();
            return vacia;
        }
        ListaTda<Cliente> clientes = porNombre.obtener(key);
        if (clientes == null) {
            ListaTda<Cliente> vacia = new ListaImpl<>();
            vacia.crearLista();
            return vacia;
        }
        return clientes;
    }

    /** Busca todos los clientes con un scoring específico. Complejidad: O(m) donde m es el número de clientes con ese scoring */
    public ListaTda<Cliente> buscarPorScoring(int scoring) {
        ConjuntoTda<Cliente> conjunto = porScoring.obtener(scoring);
        if (conjunto == null || conjunto.estaVacio()) {
            ListaTda<Cliente> vacia = new ListaImpl<>();
            vacia.crearLista();
            return vacia;
        }
        return conjunto.listar();
    }

    /** Elimina un cliente del repositorio por ID. Complejidad: O(n) donde n es la longitud del nombre */
    public boolean eliminarClientePorId(int id) {
        Cliente cliente = porId.obtener(id);
        if (cliente == null) return false;

        // Eliminar del índice por ID
        porId.eliminar(id);

        // Eliminar del índice por nombre
        String key = keyNombre(cliente.getNombre());
        if (key != null) {
            ListaTda<Cliente> lista = porNombre.obtener(key);
            if (lista != null) {
                int indice = lista.buscar(cliente);
                if (indice >= 0) {
                    lista.eliminar(indice);
                }
                if (lista.longitud() == 0) {
                    porNombre.eliminar(key);
                }
            }
        }

        // Eliminar del índice por scoring
        ConjuntoTda<Cliente> conjunto = porScoring.obtener(cliente.getScoring());
        if (conjunto != null) {
            conjunto.eliminar(cliente);
            if (conjunto.estaVacio()) {
                porScoring.eliminar(cliente.getScoring());
            }
        }
        return true;
    }

    /** Retorna una lista con todos los clientes. Complejidad: O(n) donde n es el total de clientes */
    public ListaTda<Cliente> listarTodos() {
        return porId.valores();
    }

    /** Retorna la cantidad de clientes. Complejidad: O(1) */
    public int cantidadClientes() {
        return porId.tamaño();
    }
}
