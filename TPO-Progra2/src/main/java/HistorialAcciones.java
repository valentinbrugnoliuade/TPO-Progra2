import java.util.ArrayList;
import java.util.List;

import TDAs.deque.DequeImpl;
import TDAs.deque.DequeTda;

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

    public List<Accion> listarAcciones() {
        List<Accion> lista = new ArrayList<>();
        DequeTda<Accion> aux = new DequeImpl<>();
        aux.crearDeque();
        while (!pila.estaVacio()) {
            Accion a = pila.eliminarInicio();
            lista.add(a);
            aux.agregarInicio(a);
        }
        while (!aux.estaVacio()) {
            pila.agregarInicio(aux.eliminarInicio());
        }
        return lista;
    }
}
