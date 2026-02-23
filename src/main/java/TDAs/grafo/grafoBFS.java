package TDAs.grafo;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import TDAs.lista.ListaTda;

/**
 * Algoritmo BFS sobre un grafoTDA para calcular distancia entre vértices.
 */
public class grafoBFS {

    /**
     * Calcula la distancia (cantidad de saltos) entre origen y destino en el grafo.
     * Retorna 0 si origen == destino, -1 si no existe camino.
     * Complejidad: O(V + E) donde V = vértices y E = aristas.
     */
    public static <T> int distancia(grafoTDA<T> grafo, T origen, T destino) {
        if (!grafo.existeVertice(origen) || !grafo.existeVertice(destino)) {
            return -1;
        }
        if (origen.equals(destino)) {
            return 0;
        }

        Set<T> visitados = new HashSet<>();
        Queue<T> cola = new ArrayDeque<>();
        Queue<Integer> niveles = new ArrayDeque<>();

        cola.add(origen);
        niveles.add(0);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            T actual = cola.poll();
            int nivel = niveles.poll();

            ListaTda<T> vecinos = grafo.obtenerVecinos(actual);
            for (int i = 0; i < vecinos.longitud(); i++) {
                T vecino = vecinos.obtener(i);
                if (!visitados.contains(vecino)) {
                    if (vecino.equals(destino)) {
                        return nivel + 1;
                    }
                    visitados.add(vecino);
                    cola.add(vecino);
                    niveles.add(nivel + 1);
                }
            }
        }
        return -1;
    }
}
