import TDAs.deque.DequeImpl;
import TDAs.deque.DequeTda;
import TDAs.lista.ListaImpl;
import TDAs.lista.ListaTda;

/**
 * Historial de acciones usando una pila (LIFO).
 * Permite deshacer acciones en orden inverso.
 */
public class HistorialAcciones {
    private final DequeTda<Accion> pila;

    /** Constructor que inicializa la pila. Complejidad: O(1) */
    public HistorialAcciones() {
        pila = new DequeImpl<>();
        pila.crearDeque();
    }

    /** Registra una acción en el historial. Complejidad: O(1) amortizado */
    public void registrarAccion(Accion accion) {
        if (accion == null) {
            throw new IllegalArgumentException("Acción inválida (null).");
        }
        pila.agregarInicio(accion);
    }

    /** Deshace la última acción (LIFO). Complejidad: O(1) */
    public Accion deshacerUltimaAccion() {
        return pila.eliminarInicio();
    }

    /** Verifica si el historial está vacío. Complejidad: O(1) */
    public boolean estaVacio() {
        return pila.estaVacio();
    }

    /** Lista todas las acciones (sin modificar el historial). Complejidad: O(n) donde n es el número de acciones */
    public ListaTda<Accion> listarAcciones() {
        ListaTda<Accion> lista = new ListaImpl<>();
        lista.crearLista();
        DequeTda<Accion> aux = new DequeImpl<>();
        aux.crearDeque();
        while (!pila.estaVacio()) {
            Accion a = pila.eliminarInicio();
            lista.insertar(lista.longitud(), a);
            aux.agregarInicio(a);
        }
        while (!aux.estaVacio()) {
            pila.agregarInicio(aux.eliminarInicio());
        }
        return lista;
    }
}
