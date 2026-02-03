package TDAs.cola;
// Interfaz de cola FIFO.
public interface ColaTda<T> {

    void crearCola();

    void encolar(T elemento);

    T desencolar();

    T verPrimero();

    boolean esVacia();

    int tamaño();
}
