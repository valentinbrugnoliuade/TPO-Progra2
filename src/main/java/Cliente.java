import java.util.Objects;

public class Cliente {
    private static int idCounter = 1;
    
    private int id;
    private String nombre;
    private int scoring;
    private String[] siguiendo;
    private String[] conexiones;

    /** Constructor vacío para deserialización JSON. Complejidad: O(1) */
    public Cliente(){
        this.id = idCounter++;
    }
    
    /** Constructor con validación. Complejidad: O(1) */
    public Cliente(String nombre, int scoring) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.id = idCounter++;
        this.nombre = nombre.trim();
        this.scoring = scoring;
    }

    /** Obtiene el ID único del cliente. Complejidad: O(1) */
    public int getId() {
        return id;
    }

    /** Obtiene el nombre del cliente. Complejidad: O(1) */
    public String getNombre() {
        return nombre;
    }

    /** Obtiene el scoring del cliente. Complejidad: O(1) */
    public int getScoring() {
        return scoring;
    }

    /** Obtiene el array de clientes que este cliente sigue. Complejidad: O(1) */
    public String[] getSiguiendo() {
        return siguiendo;
    }

    /** Obtiene el array de conexiones del cliente. Complejidad: O(1) */
    public String[] getConexiones() {
        return conexiones;
    }

    /** Establece el nombre del cliente. Complejidad: O(1) */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    /** Establece el scoring del cliente. Complejidad: O(1) */
    public void setScoring(int scoring) {
        this.scoring = scoring;
    }

    /** Establece los clientes que este cliente sigue. Complejidad: O(1) */
    public void setSiguiendo(String[] siguiendo) {
        this.siguiendo = siguiendo;
    }

    /** Establece las conexiones del cliente. Complejidad: O(1) */
    public void setConexiones(String[] conexiones) {
        this.conexiones = conexiones;
    }
    /** Representación en String del cliente. Complejidad: O(1) */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append("'");
        sb.append(", scoring=").append(scoring);
        
        if (siguiendo != null && siguiendo.length > 0) {
            sb.append(", sigue a: [");
            for (int i = 0; i < siguiendo.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(siguiendo[i]);
            }
            sb.append("]");
        }
        
        if (conexiones != null && conexiones.length > 0) {
            sb.append(", conexiones: [");
            for (int i = 0; i < conexiones.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(conexiones[i]);
            }
            sb.append("]");
        }
        
        sb.append("}");
        return sb.toString();
    }

    /** Compara dos clientes por ID único. Complejidad: O(1) */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return id == cliente.id;
    }

    /** Calcula el hashCode basado en el ID. Complejidad: O(1) */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /** Establece el contador de IDs a un valor específico (para testing). Complejidad: O(1) */
    public static void setIdCounter(int valor) {
        idCounter = valor;
    }
}
