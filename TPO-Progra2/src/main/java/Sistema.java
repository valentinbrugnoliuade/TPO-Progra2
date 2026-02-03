import TDAs.lista.ListaTda;

/**
 * Fachada del sistema (Iteración 1).
 *
 * Abstracción: provee operaciones de alto nivel para gestionar clientes, registrar/deshacer acciones
 * y procesar solicitudes de seguimiento, delegando en las estructuras internas eficientes.
 *
 * Representación:
 * - repositorio: RepositorioClientes
 * - historial: HistorialAcciones
 * - solicitudes: GestorSolicitudes
 *
 * Invariante:
 * - repositorio, historial y solicitudes no son null
 */
public class Sistema {
    private final RepositorioClientes repositorio = new RepositorioClientes();
    private final HistorialAcciones historial = new HistorialAcciones();
    private final GestorSolicitudes solicitudes = new GestorSolicitudes();

    // -------- Clientes --------

    public boolean existeCliente(String nombre) {
        return repositorio.existeCliente(nombre);
    }

    public void agregarCliente(String nombre, int scoring) {
        Cliente cliente = new Cliente(nombre, scoring);
        repositorio.agregarCliente(cliente);
        historial.registrarAccion(new Accion(TipoAccion.AGREGAR_CLIENTE, cliente.getNombre()));
    }

    public boolean eliminarCliente(String nombre) {
        Cliente existente = repositorio.buscarPorNombre(nombre);
        boolean ok = repositorio.eliminarCliente(nombre);
        if (ok) {
            historial.registrarAccion(new Accion(TipoAccion.ELIMINAR_CLIENTE, existente.getNombre()));
        }
        return ok;
    }

    public Cliente buscarCliente(String nombre) {
        return repositorio.buscarPorNombre(nombre);
    }

    public ListaTda<Cliente> buscarClientesPorScoring(int scoring) {
        return repositorio.buscarPorScoring(scoring);
    }

    public ListaTda<Cliente> listarClientes() {
        return repositorio.listarTodos();
    }

    public int cantidadClientes() {
        return repositorio.cantidadClientes();
    }

    // -------- Acciones --------

    public Accion deshacerUltimaAccion() {
        // En iteración 1 devolvemos la acción para que el caller decida si revierte efectos.
        // Si quieren revertir automáticamente, hay que guardar más info (por ejemplo el Cliente completo al eliminar).
        return historial.deshacerUltimaAccion();
    }

    public ListaTda<Accion> listarAcciones() {
        return historial.listarAcciones();
    }

    // -------- Solicitudes --------

    public void solicitarSeguimiento(String solicitante, String objetivo) {
        SolicitudSeguimiento s = new SolicitudSeguimiento(solicitante, objetivo);
        solicitudes.encolar(s);
        historial.registrarAccion(new Accion(TipoAccion.SOLICITAR_SEGUIMIENTO, solicitante + " -> " + objetivo));
    }

    public SolicitudSeguimiento procesarSiguienteSolicitud() {
        SolicitudSeguimiento s = solicitudes.procesarSiguiente();
        if (s != null) {
            historial.registrarAccion(new Accion(TipoAccion.PROCESAR_SOLICITUD, s.getSolicitante() + " -> " + s.getObjetivo()));
        }
        return s;
    }

    public int cantidadSolicitudesPendientes() {
        return solicitudes.cantidadPendientes();
    }
}

