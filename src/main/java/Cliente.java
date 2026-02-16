import java.util.Objects;

import TDAs.cola.ColaImpl;
import TDAs.cola.ColaTda;
import TDAs.cliente.ClienteTda;
import TDAs.deque.DequeImpl;
import TDAs.deque.DequeTda;
import TDAs.lista.ListaImpl;
import TDAs.lista.ListaTda;

public class Cliente implements ClienteTda<SolicitudSeguimiento, Accion> {
    private static int idCounter = 1;
    
    private int id;
    private String nombre;
    private int scoring;
    private String[] siguiendo;
    private String[] conexiones;

    // Estructuras internas (TDAs) por cliente
    private final ColaTda<SolicitudSeguimiento> solicitudes;
    private final DequeTda<Accion> acciones;

    /** Constructor vacío para deserialización JSON. Complejidad: O(1) */
    public Cliente(){
        this.id = idCounter++;
        this.solicitudes = new ColaImpl<>();
        this.solicitudes.crearCola();
        this.acciones = new DequeImpl<>();
        this.acciones.crearDeque();
    }
    
    /** Constructor con validación. Complejidad: O(1) */
    public Cliente(String nombre, int scoring) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.id = idCounter++;
        this.nombre = nombre.trim();
        this.scoring = scoring;
        this.solicitudes = new ColaImpl<>();
        this.solicitudes.crearCola();
        this.acciones = new DequeImpl<>();
        this.acciones.crearDeque();
    }

    /** Obtiene el ID único del cliente. Complejidad: O(1) */
    @Override
    public int getId() {
        return id;
    }

    /** Obtiene el nombre del cliente. Complejidad: O(1) */
    @Override
    public String getNombre() {
        return nombre;
    }

    /** Obtiene el scoring del cliente. Complejidad: O(1) */
    @Override
    public int getScoring() {
        return scoring;
    }

    /** Obtiene el array de clientes que este cliente sigue. Complejidad: O(1) */
    @Override
    public String[] getSiguiendo() {
        return siguiendo;
    }

    /** Obtiene el array de conexiones del cliente. Complejidad: O(1) */
    @Override
    public String[] getConexiones() {
        return conexiones;
    }

    /** Establece el nombre del cliente. Complejidad: O(n) donde n es la longitud del nombre */
    @Override
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    /** Establece el scoring del cliente. Complejidad: O(1) */
    @Override
    public void setScoring(int scoring) {
        this.scoring = scoring;
    }

    /** Establece los clientes que este cliente sigue. Complejidad: O(1) */
    @Override
    public void setSiguiendo(String[] siguiendo) {
        this.siguiendo = siguiendo;
    }

    /** Establece las conexiones del cliente. Complejidad: O(1) */
    @Override
    public void setConexiones(String[] conexiones) {
        this.conexiones = conexiones;
    }

    @Override
    public boolean seguirA(String objetivo) {
        if (objetivo == null || objetivo.isBlank()) {
            throw new IllegalArgumentException("Objetivo inválido.");
        }
        String obj = objetivo.trim();
        if (nombre != null && nombre.equalsIgnoreCase(obj)) {
            throw new IllegalArgumentException("No se permite auto-seguimiento.");
        }

        if (siguiendo == null) {
            siguiendo = new String[0];
        }

        // Ya lo sigue
        for (String s : siguiendo) {
            if (s != null && s.equalsIgnoreCase(obj)) {
                return false;
            }
        }

        if (siguiendo.length >= 2) {
            throw new IllegalStateException("No puede seguir a más de 2 clientes.");
        }

        String[] nuevo = new String[siguiendo.length + 1];
        System.arraycopy(siguiendo, 0, nuevo, 0, siguiendo.length);
        nuevo[siguiendo.length] = obj;
        siguiendo = nuevo;
        return true;
    }

    @Override
    public boolean dejarDeSeguir(String objetivo) {
        if (objetivo == null || objetivo.isBlank()) return false;
        if (siguiendo == null || siguiendo.length == 0) return false;
        String obj = objetivo.trim();

        int idx = -1;
        for (int i = 0; i < siguiendo.length; i++) {
            String s = siguiendo[i];
            if (s != null && s.equalsIgnoreCase(obj)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) return false;

        if (siguiendo.length == 1) {
            siguiendo = new String[0];
            return true;
        }

        String[] nuevo = new String[siguiendo.length - 1];
        int j = 0;
        for (int i = 0; i < siguiendo.length; i++) {
            if (i == idx) continue;
            nuevo[j++] = siguiendo[i];
        }
        siguiendo = nuevo;
        return true;
    }

    @Override
    public int cantidadSiguiendo() {
        return siguiendo == null ? 0 : siguiendo.length;
    }

    @Override
    public void encolarSolicitud(SolicitudSeguimiento solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("Solicitud inválida (null).");
        }
        solicitudes.encolar(solicitud);
    }

    @Override
    public SolicitudSeguimiento procesarSiguienteSolicitud() {
        return solicitudes.desencolar();
    }

    @Override
    public int cantidadSolicitudesPendientes() {
        return solicitudes.tamaño();
    }

    @Override
    public void registrarAccion(Accion accion) {
        if (accion == null) {
            throw new IllegalArgumentException("Acción inválida (null).");
        }
        acciones.agregarInicio(accion);
    }

    @Override
    public Accion deshacerUltimaAccion() {
        return acciones.eliminarInicio();
    }

    @Override
    public ListaTda<Accion> listarAcciones() {
        ListaTda<Accion> lista = new ListaImpl<>();
        lista.crearLista();
        DequeTda<Accion> aux = new DequeImpl<>();
        aux.crearDeque();
        while (!acciones.estaVacio()) {
            Accion a = acciones.eliminarInicio();
            lista.insertar(lista.longitud(), a);
            aux.agregarInicio(a);
        }
        while (!aux.estaVacio()) {
            acciones.agregarInicio(aux.eliminarInicio());
        }
        return lista;
    }
    /** Representación en String del cliente. Complejidad: O(1) */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append("'");
        sb.append(", scoring=").append(scoring);
        
        if (siguiendo != null && siguiendo.length > 0) {
            sb.append(", sigue a: [");
            for (int i = 0; i < siguiendo.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(siguiendo[i]);
            }
            sb.append("]");
        }
        
        if (conexiones != null && conexiones.length > 0) {
            sb.append(", conexiones: [");
            for (int i = 0; i < conexiones.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(conexiones[i]);
            }
            sb.append("]");
        }
        
        sb.append("}");
        return sb.toString();
    }

    /** Compara dos clientes por ID único. Complejidad: O(1) */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return id == cliente.id;
    }

    /** Calcula el hashCode basado en el ID. Complejidad: O(1) */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /** Establece el contador de IDs a un valor específico (para testing). Complejidad: O(1) */
    public static void setIdCounter(int valor) {
        idCounter = valor;
    }
}
