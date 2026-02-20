package TDAs.avl;

import TDAs.lista.ListaImpl;
import TDAs.lista.ListaTda;

/**
 * Implementación simple de Árbol AVL.
 * No permite nulls. No inserta duplicados.
 */
public class AvlImpl<T extends Comparable<T>> implements AvlTda<T> {

    private static class Nodo<T> {
        T valor;
        Nodo<T> izq;
        Nodo<T> der;
        int altura; // altura en nodos (hoja = 1)

        Nodo(T valor) {
            this.valor = valor;
            this.altura = 1;
        }
    }

    private boolean creado = false;
    private Nodo<T> raiz;

    @Override
    public void crearArbol() {
        creado = true;
        raiz = null;
    }

    @Override
    public boolean esVacio() {
        return !creado || raiz == null;
    }

    @Override
    public void insertar(T valor) {
        asegurarCreado();
        if (valor == null) throw new IllegalArgumentException("Valor inválido (null).");
        raiz = insertarRec(raiz, valor);
    }

    @Override
    public void eliminar(T valor) {
        asegurarCreado();
        if (valor == null) throw new IllegalArgumentException("Valor inválido (null).");
        raiz = eliminarRec(raiz, valor);
    }

    @Override
    public boolean contiene(T valor) {
        if (!creado || raiz == null || valor == null) return false;
        Nodo<T> act = raiz;
        while (act != null) {
            int cmp = valor.compareTo(act.valor);
            if (cmp == 0) return true;
            act = (cmp < 0) ? act.izq : act.der;
        }
        return false;
    }

    @Override
    public int altura() {
        if (!creado || raiz == null) return -1;
        return raiz.altura - 1; // convertir altura en nodos a altura en aristas
    }

    @Override
    public ListaTda<T> inorden() {
        ListaTda<T> out = nuevaLista();
        if (!creado || raiz == null) return out;
        inordenRec(raiz, out);
        return out;
    }

    @Override
    public ListaTda<T> preorden() {
        ListaTda<T> out = nuevaLista();
        if (!creado || raiz == null) return out;
        preordenRec(raiz, out);
        return out;
    }

    @Override
    public ListaTda<T> postorden() {
        ListaTda<T> out = nuevaLista();
        if (!creado || raiz == null) return out;
        postordenRec(raiz, out);
        return out;
    }

    @Override
    public void imprimirArbol() {
        if (!creado) {
            System.out.println("(AVL no creado)");
            return;
        }
        if (raiz == null) {
            System.out.println("(AVL vacío)");
            return;
        }
        imprimirRec(raiz, 0);
    }

    @Override
    public ListaTda<T> elementosEnNivel(int nivel) {
        ListaTda<T> out = nuevaLista();
        if (!creado || raiz == null) return out;
        if (nivel <= 0) return out;
        elementosEnNivelRec(raiz, 1, nivel, out);
        return out;
    }

    @Override
    public T mayor() {
        if (!creado || raiz == null) return null;
        Nodo<T> act = raiz;
        while (act.der != null) act = act.der;
        return act.valor;
    }

    @Override
    public ListaTda<T> mayoresQueRaiz() {
        ListaTda<T> out = nuevaLista();
        if (!creado || raiz == null) return out;
        T r = raiz.valor;
        // Podríamos ir solo al subárbol derecho, pero dejamos genérico para no depender de duplicados/reglas.
        mayoresRec(raiz, r, out);
        return out;
    }

    // ----------------- Internos -----------------

    private void asegurarCreado() {
        if (!creado) throw new IllegalStateException("El AVL no ha sido creado. Invocar crearArbol() primero.");
    }

    private ListaTda<T> nuevaLista() {
        ListaTda<T> l = new ListaImpl<>();
        l.crearLista();
        return l;
    }

    private int alturaNodo(Nodo<T> n) {
        return n == null ? 0 : n.altura;
    }

    private int factorBalance(Nodo<T> n) {
        return n == null ? 0 : (alturaNodo(n.izq) - alturaNodo(n.der));
    }

    private void actualizarAltura(Nodo<T> n) {
        n.altura = 1 + Math.max(alturaNodo(n.izq), alturaNodo(n.der));
    }

    private Nodo<T> rotacionDerecha(Nodo<T> y) {
        Nodo<T> x = y.izq;
        Nodo<T> t2 = x.der;
        x.der = y;
        y.izq = t2;
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private Nodo<T> rotacionIzquierda(Nodo<T> x) {
        Nodo<T> y = x.der;
        Nodo<T> t2 = y.izq;
        y.izq = x;
        x.der = t2;
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    private Nodo<T> balancear(Nodo<T> n) {
        if (n == null) return null;
        actualizarAltura(n);
        int fb = factorBalance(n);

        // LL
        if (fb > 1 && factorBalance(n.izq) >= 0) {
            return rotacionDerecha(n);
        }
        // LR
        if (fb > 1 && factorBalance(n.izq) < 0) {
            n.izq = rotacionIzquierda(n.izq);
            return rotacionDerecha(n);
        }
        // RR
        if (fb < -1 && factorBalance(n.der) <= 0) {
            return rotacionIzquierda(n);
        }
        // RL
        if (fb < -1 && factorBalance(n.der) > 0) {
            n.der = rotacionDerecha(n.der);
            return rotacionIzquierda(n);
        }
        return n;
    }

    private Nodo<T> insertarRec(Nodo<T> n, T valor) {
        if (n == null) return new Nodo<>(valor);
        int cmp = valor.compareTo(n.valor);
        if (cmp < 0) {
            n.izq = insertarRec(n.izq, valor);
        } else if (cmp > 0) {
            n.der = insertarRec(n.der, valor);
        } else {
            // duplicado: no insertar
            return n;
        }
        return balancear(n);
    }

    private Nodo<T> eliminarRec(Nodo<T> n, T valor) {
        if (n == null) return null;

        int cmp = valor.compareTo(n.valor);
        if (cmp < 0) {
            n.izq = eliminarRec(n.izq, valor);
        } else if (cmp > 0) {
            n.der = eliminarRec(n.der, valor);
        } else {
            // nodo a eliminar
            if (n.izq == null || n.der == null) {
                n = (n.izq != null) ? n.izq : n.der;
            } else {
                Nodo<T> sucesor = minimo(n.der);
                n.valor = sucesor.valor;
                n.der = eliminarRec(n.der, sucesor.valor);
            }
        }
        return balancear(n);
    }

    private Nodo<T> minimo(Nodo<T> n) {
        Nodo<T> act = n;
        while (act.izq != null) act = act.izq;
        return act;
    }

    private void inordenRec(Nodo<T> n, ListaTda<T> out) {
        if (n == null) return;
        inordenRec(n.izq, out);
        out.insertar(out.longitud(), n.valor);
        inordenRec(n.der, out);
    }

    private void preordenRec(Nodo<T> n, ListaTda<T> out) {
        if (n == null) return;
        out.insertar(out.longitud(), n.valor);
        preordenRec(n.izq, out);
        preordenRec(n.der, out);
    }

    private void postordenRec(Nodo<T> n, ListaTda<T> out) {
        if (n == null) return;
        postordenRec(n.izq, out);
        postordenRec(n.der, out);
        out.insertar(out.longitud(), n.valor);
    }

    private void imprimirRec(Nodo<T> n, int nivel) {
        if (n == null) return;
        for (int i = 0; i < nivel; i++) System.out.print("  ");
        System.out.println("- " + n.valor + " (h=" + (n.altura - 1) + ", fb=" + factorBalance(n) + ")");
        imprimirRec(n.izq, nivel + 1);
        imprimirRec(n.der, nivel + 1);
    }

    private void elementosEnNivelRec(Nodo<T> n, int nivelActual, int nivelObjetivo, ListaTda<T> out) {
        if (n == null) return;
        if (nivelActual == nivelObjetivo) {
            out.insertar(out.longitud(), n.valor);
            return;
        }
        elementosEnNivelRec(n.izq, nivelActual + 1, nivelObjetivo, out);
        elementosEnNivelRec(n.der, nivelActual + 1, nivelObjetivo, out);
    }

    private void mayoresRec(Nodo<T> n, T raizValor, ListaTda<T> out) {
        if (n == null) return;
        // Inorden para devolver ordenado
        mayoresRec(n.izq, raizValor, out);
        if (n.valor.compareTo(raizValor) > 0) {
            out.insertar(out.longitud(), n.valor);
        }
        mayoresRec(n.der, raizValor, out);
    }
}

