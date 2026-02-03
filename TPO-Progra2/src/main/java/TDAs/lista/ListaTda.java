package TDAs.lista;

/**
 * TAD Lista — Interfaz.
 */
public interface ListaTda<T> {

    void crearLista();

    T obtener(int i);

    int longitud();

    void insertar(int i, T elemento);

    void eliminar(int i);

    void reemplazar(int i, T nuevoElemento);

    int buscar(T elemento);

    boolean contiene(T elemento);

    void imprimirLista();

    void concatenar(ListaTda<T> lista2);

    void invertir();
}
