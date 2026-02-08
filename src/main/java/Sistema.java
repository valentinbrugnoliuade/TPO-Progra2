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
        historial.registrarAccion(new Accion(TipoAccion.AGREGAR_CLIENTE, cliente.getNombre()));
    }

    /** Agrega un cliente con relaciones iniciales y registra la acción. Complejidad: O(n) donde n es la longitud del nombre */
    public void agregarCliente(String nombre, int scoring, String[] siguiendo, String[] conexiones) {
        Cliente cliente = new Cliente(nombre, scoring);
        cliente.setSiguiendo(siguiendo);
        cliente.setConexiones(conexiones);
        repositorio.agregarCliente(cliente);
        historial.registrarAccion(new Accion(TipoAccion.AGREGAR_CLIENTE, cliente.getNombre()));
    }

    /** Elimina un cliente por ID y registra la acción. Complejidad: O(n) donde n es la longitud del nombre */
    public boolean eliminarClientePorId(int id) {
        Cliente existente = repositorio.buscarPorId(id);
        boolean ok = repositorio.eliminarClientePorId(id);
        if (ok) {
            historial.registrarAccion(new Accion(TipoAccion.ELIMINAR_CLIENTE, existente.getNombre()));
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
        historial.registrarAccion(new Accion(TipoAccion.SOLICITAR_SEGUIMIENTO, solicitante + " -> " + objetivo));
    }

    /** Procesa la siguiente solicitud en la cola (FIFO). Complejidad: O(1) */
    public SolicitudSeguimiento procesarSiguienteSolicitud() {
        SolicitudSeguimiento s = solicitudes.procesarSiguiente();
        if (s != null) {
            historial.registrarAccion(new Accion(TipoAccion.PROCESAR_SOLICITUD, s.getSolicitante() + " -> " + s.getObjetivo()));
        }
        return s;
    }

    /** Retorna la cantidad de solicitudes pendientes. Complejidad: O(1) */
    public int cantidadSolicitudesPendientes() {
        return solicitudes.cantidadPendientes();
    }
}

