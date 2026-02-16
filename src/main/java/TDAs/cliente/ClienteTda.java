package TDAs.cliente;

import TDAs.lista.ListaTda;

/**
 * TDA Cliente (genérico).
 *
 * Nota técnica: se parametriza para no depender de clases del "default package".
 *
 * Invariante (esperada):
 * - nombre != null y no vacío (trim)
 * - scoring en [0..100]
 * - siguiendo: longitud <= 2, sin duplicados, sin auto-seguimiento, referencias no vacías (trim)
 */
public interface ClienteTda<S, A> {

    /** Obtiene el ID del cliente. Complejidad: O(1) */
    int getId();

    /** Obtiene el nombre del cliente. Complejidad: O(1) */
    String getNombre();

    /** Obtiene el scoring del cliente. Complejidad: O(1) */
    int getScoring();

    /** Cambia el nombre del cliente. Complejidad: O(n) donde n es la longitud del nombre */
    void setNombre(String nombre);

    /** Cambia el scoring del cliente. Complejidad: O(1) */
    void setScoring(int scoring);

    /** Retorna a quiénes sigue (máximo 2). Complejidad: O(1) */
    String[] getSiguiendo();

    /**
     * Intenta agregar un seguido cumpliendo invariantes.
     * Retorna true si se agregó; false si ya lo seguía.
     * Complejidad: O(1)
     */
    boolean seguirA(String objetivo);

    /**
     * Deja de seguir a un cliente.
     * Retorna true si lo eliminó; false si no existía.
     * Complejidad: O(1)
     */
    boolean dejarDeSeguir(String objetivo);

    /** Cantidad de seguidos actuales. Complejidad: O(1) */
    int cantidadSiguiendo();

    /** Obtiene conexiones del cliente. Complejidad: O(1) */
    String[] getConexiones();

    /** Establece conexiones (pensado para carga inicial). Complejidad: O(1) */
    void setConexiones(String[] conexiones);

    /** Establece siguiendo (pensado para carga inicial validada). Complejidad: O(1) */
    void setSiguiendo(String[] siguiendo);

    // --- Solicitudes (cola FIFO) ---

    /** Encola una solicitud de seguimiento. Complejidad: O(1) amortizado */
    void encolarSolicitud(S solicitud);

    /** Procesa la siguiente solicitud del cliente. Complejidad: O(1) */
    S procesarSiguienteSolicitud();

    /** Retorna pendientes en cola. Complejidad: O(1) */
    int cantidadSolicitudesPendientes();

    // --- Acciones (pila LIFO) ---

    /** Registra una acción del cliente. Complejidad: O(1) amortizado */
    void registrarAccion(A accion);

    /** Deshace la última acción del cliente. Complejidad: O(1) */
    A deshacerUltimaAccion();

    /** Lista acciones del cliente (sin modificar el historial). Complejidad: O(k) */
    ListaTda<A> listarAcciones();
}
