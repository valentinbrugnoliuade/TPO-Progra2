import java.io.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GestorJSON {

        private final Gson gsonLectura;
        private final Gson gsonEscritura;

        public GestorJSON() {
            this.gsonLectura = new Gson();
            this.gsonEscritura = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
        }

        //LEER JSON RESOURCE
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

        // ESCRIBIR EN EL JSON
        public void guardar(ClientesData data) {
            try (FileWriter writer = new FileWriter("clientes_out.json")) {
                gsonEscritura.toJson(data, writer);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar JSON", e);
            }
        }

        //LEER NUEVO JSON
        public ClientesData leerArchivo(String ruta) {
            Gson gson = new Gson();

            try (FileReader reader = new FileReader(ruta)) {
                return gson.fromJson(reader, ClientesData.class);
            } catch (IOException e) {
                throw new RuntimeException("No se pudo leer el archivo: " + ruta, e);
            }
        }


}
