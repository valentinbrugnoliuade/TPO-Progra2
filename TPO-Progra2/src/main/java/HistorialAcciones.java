import TDAs.deque.DequeImpl;
import TDAs.deque.DequeTda;
import TDAs.lista.ListaImpl;
import TDAs.lista.ListaTda;

public class HistorialAcciones {
    private final DequeTda<Accion> pila;

    public HistorialAcciones() {
        pila = new DequeImpl<>();
        pila.crearDeque();
    }

    public void registrarAccion(Accion accion) {
        if (accion == null) {
            throw new IllegalArgumentException("Acción inválida (null).");
        }
        pila.agregarInicio(accion);  // LIFO: push = agregar al inicio
    }

    public Accion deshacerUltimaAccion() {
        return pila.eliminarInicio();  // pop = eliminar del inicio
    }

    public boolean estaVacio() {
        return pila.estaVacio();
    }

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
