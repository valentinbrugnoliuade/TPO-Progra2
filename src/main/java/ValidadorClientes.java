import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validador para carga de clientes desde JSON.
 * Verifica duplicados, campos faltantes, referencias inválidas y JSON mal formado.
 */
public class ValidadorClientes {

    /** Complejidad: O(n*m) donde n es cantidad de clientes y m es cantidad de referencias */
    public static void validar(List<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            throw new IllegalArgumentException("La lista de clientes no puede estar vacía.");
        }

        Set<String> nombresVistos = new HashSet<>();
        Set<String> nombresClientes = new HashSet<>();

        for (Cliente cliente : clientes) {
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente nulo encontrado en la lista.");
            }

            String nombre = cliente.getNombre();
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException("Cliente con nombre nulo o vacío.");
            }

            if (nombresVistos.contains(nombre.toLowerCase())) {
                throw new IllegalArgumentException("Cliente duplicado encontrado: '" + nombre + "'");
            }
            nombresVistos.add(nombre.toLowerCase());
            nombresClientes.add(nombre);

            int scoring = cliente.getScoring();
            if (scoring < 0 || scoring > 100) {
                throw new IllegalArgumentException(
                    "Cliente '" + nombre + "' tiene scoring inválido: " + scoring + 
                    " (debe estar entre 0 y 100)."
                );
            }
        }

        for (Cliente cliente : clientes) {
            String nombre = cliente.getNombre();
            
            if (cliente.getSiguiendo() != null) {
                for (String seguido : cliente.getSiguiendo()) {
                    if (seguido == null || seguido.isBlank()) {
                        throw new IllegalArgumentException(
                            "Cliente '" + nombre + "' tiene referencia vacía en 'siguiendo'."
                        );
                    }
                    if (!nombresClientes.contains(seguido)) {
                        throw new IllegalArgumentException(
                            "Cliente '" + nombre + "' sigue a '" + seguido + 
                            "' pero este cliente no existe."
                        );
                    }
                }
            }

            if (cliente.getConexiones() != null) {
                for (String conexion : cliente.getConexiones()) {
                    if (conexion == null || conexion.isBlank()) {
                        throw new IllegalArgumentException(
                            "Cliente '" + nombre + "' tiene referencia vacía en 'conexiones'."
                        );
                    }
                    if (!nombresClientes.contains(conexion)) {
                        throw new IllegalArgumentException(
                            "Cliente '" + nombre + "' está conectado con '" + conexion + 
                            "' pero este cliente no existe."
                        );
                    }
                }
            }
        }
    }
}
