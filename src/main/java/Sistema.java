import TDAs.avl.AvlImpl;
import TDAs.avl.AvlTda;
import TDAs.lista.ListaImpl;
import TDAs.lista.ListaTda;

/**
 * Fachada del sistema que coordina repositorio, historial y solicitudes.
 */
public class Sistema {
    private final RepositorioClientes repositorio = new RepositorioClientes();
    private final HistorialAcciones historial = new HistorialAcciones();
    private final GestorSolicitudes solicitudes = new GestorSolicitudes();

    // -------- Clientes --------
    
    /** Verifica si existe un cliente con el nombre dado. Complejidad: O(n) donde n es la longitud del nombre */
    public boolean existeCliente(String nombre) {
        return repositorio.existeCliente(nombre);
    }

    /** Agrega un cliente y registra la acción en el historial. Complejidad: O(n) donde n es la longitud del nombre */
    public void agregarCliente(String nombre, int scoring) {
        Cliente cliente = new Cliente(nombre, scoring);
        repositorio.agregarCliente(cliente);
        Accion a = new Accion(TipoAccion.AGREGAR_CLIENTE, cliente.getNombre());
        historial.registrarAccion(a);
        cliente.registrarAccion(a);
    }

    /** Agrega un cliente con relaciones iniciales y registra la acción. Complejidad: O(n) donde n es la longitud del nombre */
    public void agregarCliente(String nombre, int scoring, String[] siguiendo, String[] conexiones) {
        Cliente cliente = new Cliente(nombre, scoring);
        cliente.setSiguiendo(siguiendo);
        cliente.setConexiones(conexiones);
        repositorio.agregarCliente(cliente);
        Accion a = new Accion(TipoAccion.AGREGAR_CLIENTE, cliente.getNombre());
        historial.registrarAccion(a);
        cliente.registrarAccion(a);
    }

    /** Elimina un cliente por ID y registra la acción. Complejidad: O(n) donde n es la longitud del nombre */
    public boolean eliminarClientePorId(int id) {
        Cliente existente = repositorio.buscarPorId(id);
        boolean ok = repositorio.eliminarClientePorId(id);
        if (ok) {
            Accion a = new Accion(TipoAccion.ELIMINAR_CLIENTE, existente.getNombre());
            historial.registrarAccion(a);
            existente.registrarAccion(a);
        }
        return ok;
    }

    /** Busca el primer cliente con el nombre dado (case-insensitive). Complejidad: O(n) donde n es la longitud del nombre */
    public Cliente buscarCliente(String nombre) {
        ListaTda<Cliente> clientes = repositorio.buscarPorNombre(nombre);
        if (clientes.longitud() > 0) {
            return clientes.obtener(0);
        }
        return null;
    }

    /** Busca todos los clientes con un nombre específico. Complejidad: O(m) donde m es el número de clientes con ese nombre */
    public ListaTda<Cliente> buscarClientesPorNombre(String nombre) {
        return repositorio.buscarPorNombre(nombre);
    }

    /** Busca un cliente por ID. Complejidad: O(1) */
    public Cliente buscarClientePorId(int id) {
        return repositorio.buscarPorId(id);
    }

    /** Busca clientes por scoring. Complejidad: O(m) donde m es la cantidad de clientes con ese scoring */
    public ListaTda<Cliente> buscarClientesPorScoring(int scoring) {
        return repositorio.buscarPorScoring(scoring);
    }

    /** Lista todos los clientes. Complejidad: O(n) donde n es el total de clientes */
    public ListaTda<Cliente> listarClientes() {
        return repositorio.listarTodos();
    }

    /** Retorna la cantidad total de clientes. Complejidad: O(1) */
    public int cantidadClientes() {
        return repositorio.cantidadClientes();
    }

    // -------- Acciones --------

    /** Deshace la última acción del historial. Complejidad: O(1) */
    public Accion deshacerUltimaAccion() {
        return historial.deshacerUltimaAccion();
    }

    /** Lista todas las acciones registradas. Complejidad: O(n) donde n es la cantidad de acciones */
    public ListaTda<Accion> listarAcciones() {
        return historial.listarAcciones();
    }

    // -------- Solicitudes --------

    /** Crea y encola una solicitud de seguimiento. Complejidad: O(1) amortizado */
    public void solicitarSeguimiento(String solicitante, String objetivo) {
        SolicitudSeguimiento s = new SolicitudSeguimiento(solicitante, objetivo);
        solicitudes.encolar(s);
        Cliente c = buscarCliente(solicitante);
        if (c != null) {
            c.encolarSolicitud(s);
        }
        Accion a = new Accion(TipoAccion.SOLICITAR_SEGUIMIENTO, solicitante + " -> " + objetivo);
        historial.registrarAccion(a);
        if (c != null) {
            c.registrarAccion(a);
        }
    }

    /** Procesa la siguiente solicitud en la cola (FIFO). Complejidad: O(1) */
    public SolicitudSeguimiento procesarSiguienteSolicitud() {
        SolicitudSeguimiento s = solicitudes.procesarSiguiente();
        if (s != null) {
            Cliente c = buscarCliente(s.getSolicitante());
            if (c != null) {
                // Mantener consistente la cola del cliente (FIFO por cliente)
                c.procesarSiguienteSolicitud();
            }
            Accion a = new Accion(TipoAccion.PROCESAR_SOLICITUD, s.getSolicitante() + " -> " + s.getObjetivo());
            historial.registrarAccion(a);
            if (c != null) {
                c.registrarAccion(a);
            }
        }
        return s;
    }

    /** Retorna la cantidad de solicitudes pendientes. Complejidad: O(1) */
    public int cantidadSolicitudesPendientes() {
        return solicitudes.cantidadPendientes();
    }

    // -------- Consultas de conexiones y seguidores --------

    /**
     * Retorna la lista de clientes a los que sigue el cliente indicado.
     * Si no existe o no sigue a nadie, retorna lista vacía.
     */
    public ListaTda<String> consultarConexionesDe(String nombreCliente) {
        ListaTda<String> out = new ListaImpl<>();
        out.crearLista();

        Cliente cliente = buscarCliente(nombreCliente);
        if (cliente == null || cliente.getSiguiendo() == null) {
            return out;
        }

        String[] siguiendo = cliente.getSiguiendo();
        for (String seguido : siguiendo) {
            if (seguido != null && !seguido.isBlank()) {
                out.insertar(out.longitud(), seguido.trim());
            }
        }
        return out;
    }

    /**
     * Retorna los clientes ubicados en el nivel indicado del ABB/AVL
     * ordenado por cantidad de seguidores.
     * Convención de niveles: 1 = raíz.
     */
    public ListaTda<String> clientesEnNivelPorSeguidores(int nivel) {
        ListaTda<String> out = new ListaImpl<>();
        out.crearLista();

        AvlTda<ClienteSeguidores> arbol = construirArbolSeguidores();
        ListaTda<ClienteSeguidores> enNivel = arbol.elementosEnNivel(nivel);
        for (int i = 0; i < enNivel.longitud(); i++) {
            out.insertar(out.longitud(), enNivel.obtener(i).toString());
        }
        return out;
    }

    /** Atajo para la consigna: clientes en el cuarto nivel (nivel 4). */
    public ListaTda<String> clientesEnCuartoNivelPorSeguidores() {
        return clientesEnNivelPorSeguidores(4);
    }

    /** Retorna el cliente con más seguidores en formato legible; null si no hay clientes. */
    public String clienteConMasSeguidores() {
        AvlTda<ClienteSeguidores> arbol = construirArbolSeguidores();
        ClienteSeguidores mayor = arbol.mayor();
        return mayor == null ? null : mayor.toString();
    }

    private AvlTda<ClienteSeguidores> construirArbolSeguidores() {
        AvlTda<ClienteSeguidores> arbol = new AvlImpl<>();
        arbol.crearArbol();

        ListaTda<Cliente> clientes = listarClientes();
        for (int i = 0; i < clientes.longitud(); i++) {
            Cliente c = clientes.obtener(i);
            int seguidores = contarSeguidoresDe(c.getNombre());
            arbol.insertar(new ClienteSeguidores(c.getId(), c.getNombre(), seguidores));
        }
        return arbol;
    }

    private int contarSeguidoresDe(String nombreObjetivo) {
        if (nombreObjetivo == null || nombreObjetivo.isBlank()) return 0;
        int total = 0;
        ListaTda<Cliente> clientes = listarClientes();
        for (int i = 0; i < clientes.longitud(); i++) {
            Cliente c = clientes.obtener(i);
            String[] siguiendo = c.getSiguiendo();
            if (siguiendo == null) continue;
            for (String seguido : siguiendo) {
                if (seguido != null && seguido.trim().equalsIgnoreCase(nombreObjetivo.trim())) {
                    total++;
                }
            }
        }
        return total;
    }

    private static class ClienteSeguidores implements Comparable<ClienteSeguidores> {
        private final int id;
        private final String nombre;
        private final int seguidores;

        private ClienteSeguidores(int id, String nombre, int seguidores) {
            this.id = id;
            this.nombre = nombre;
            this.seguidores = seguidores;
        }

        @Override
        public int compareTo(ClienteSeguidores otro) {
            int cmpSeguidores = Integer.compare(this.seguidores, otro.seguidores);
            if (cmpSeguidores != 0) return cmpSeguidores;

            String esteNombre = this.nombre == null ? "" : this.nombre;
            String otroNombre = otro.nombre == null ? "" : otro.nombre;
            int cmpNombre = esteNombre.compareToIgnoreCase(otroNombre);
            if (cmpNombre != 0) return cmpNombre;

            return Integer.compare(this.id, otro.id);
        }

        @Override
        public String toString() {
            return nombre + " (seguidores=" + seguidores + ")";
        }
    }
}

