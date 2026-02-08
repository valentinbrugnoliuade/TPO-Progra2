import java.io.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gestor para leer y escribir archivos JSON de clientes.
 * Usa Gson para serialización/deserialización.
 */
public class GestorJSON {

        private final Gson gsonLectura;
        private final Gson gsonEscritura;

        /** Constructor que inicializa Gson para lectura y escritura. Complejidad: O(1) */
        public GestorJSON() {
            this.gsonLectura = new Gson();
            this.gsonEscritura = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
        }

        /** Lee el archivo JSON desde resources/clientes.json. Complejidad: O(n) donde n es el tamaño del archivo */
        public ClientesData leerResource() {
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("clientes.json");

            if (is == null) {
                throw new RuntimeException("No se encontró clientes.json");
            }

            return gsonLectura.fromJson(
                    new InputStreamReader(is),
                    ClientesData.class
            );
        }

        /** Guarda los datos en clientes_out.json. Complejidad: O(n) donde n es el número de clientes */
        public void guardar(ClientesData data) {
            try (FileWriter writer = new FileWriter("clientes_out.json")) {
                gsonEscritura.toJson(data, writer);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar JSON", e);
            }
        }

        /** Lee un archivo JSON desde una ruta específica. Complejidad: O(n) donde n es el tamaño del archivo */
        public ClientesData leerArchivo(String ruta) {
            Gson gson = new Gson();

            try (FileReader reader = new FileReader(ruta)) {
                return gson.fromJson(reader, ClientesData.class);
            } catch (IOException e) {
                throw new RuntimeException("No se pudo leer el archivo: " + ruta, e);
            }
        }

        /** Lee desde resources con validación de integridad de datos. Complejidad: O(n*m) donde n es cantidad de clientes y m es cantidad de referencias */
        public ClientesData leerResourceConValidacion() {
            try {
                ClientesData data = leerResource();
                ValidadorClientes.validar(data.getClientes());
                return data;
            } catch (com.google.gson.JsonSyntaxException e) {
                throw new IllegalArgumentException("JSON mal formado en clientes.json: " + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw e; // Re-lanzar validaciones
            } catch (Exception e) {
                throw new RuntimeException("Error al leer y validar clientes.json: " + e.getMessage(), e);
            }
        }

        /** Lee desde archivo específico con validación de integridad de datos. Complejidad: O(n*m) donde n es cantidad de clientes y m es cantidad de referencias */
        public ClientesData leerArchivoConValidacion(String ruta) {
            try {
                ClientesData data = leerArchivo(ruta);
                ValidadorClientes.validar(data.getClientes());
                return data;
            } catch (com.google.gson.JsonSyntaxException e) {
                throw new IllegalArgumentException("JSON mal formado en " + ruta + ": " + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw e; // Re-lanzar validaciones
            } catch (Exception e) {
                throw new RuntimeException("Error al leer y validar " + ruta + ": " + e.getMessage(), e);
            }
        }
    }

