package TDAs.deque;
// Interfaz de deque doble extremo.
public interface DequeTda<T> {

    void crearDeque();

    void agregarInicio(T elemento);

    void agregarFinal(T elemento);

    T eliminarInicio();

    T eliminarFinal();

    T verInicio();

    T verFinal();

    boolean estaVacio();

    int tamaño();
}
