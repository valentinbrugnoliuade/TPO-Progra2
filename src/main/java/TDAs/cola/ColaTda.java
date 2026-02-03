package TDAs.cola;

/**
 * TAD Cola (FIFO) — Interfaz.
 * Operaciones: encolar al final, desencolar del inicio.
 */
public interface ColaTda<T> {

    void crearCola();

    void encolar(T elemento);

    T desencolar();

    T verPrimero();

    boolean esVacia();

    int tamaño();
}
