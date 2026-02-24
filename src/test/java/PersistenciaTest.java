import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import TDAs.lista.ListaTda;

/**
 * Tests de persistencia JSON para Iteración 2 y 3.
 * Valida que las relaciones de seguimiento (siguiendo) y las relaciones
 * generales (conexiones) se persisten y recuperan correctamente.
 */
@DisplayName("Tests de Persistencia JSON — Iteraciones 2 y 3")
public class PersistenciaTest {

    private static final String ARCHIVO_SALIDA = "clientes_test_out.json";

    private Sistema sistema;
    private GestorJSON gestor;

    @BeforeEach
    public void setUp() {
        Cliente.setIdCounter(1);
        sistema = new Sistema();
        gestor = new GestorJSON();
    }

    @AfterEach
    public void limpiarArchivo() {
        File archivo = new File(ARCHIVO_SALIDA);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    private ClientesData construirDataDesdeSistema() {
        ClientesData data = new ClientesData();
        ArrayList<Cliente> lista = new ArrayList<>();
        ListaTda<Cliente> clientesSistema = sistema.listarClientes();
        for (int i = 0; i < clientesSistema.longitud(); i++) {
            lista.add(clientesSistema.obtener(i));
        }
        data.setClientes(lista);
        return data;
    }

    private void guardarEnArchivo(ClientesData data) {
        gestor.guardar(data, ARCHIVO_SALIDA);
    }

    private ClientesData cargarDesdeArchivo() {
        return gestor.leerArchivo(ARCHIVO_SALIDA);
    }

    private Cliente buscarEnLista(List<Cliente> clientes, String nombre) {
        for (Cliente c : clientes) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    // ========== Iteración 2 — Persistencia de relaciones de seguimiento ==========

    @Nested
    @DisplayName("Iteración 2 — Persistencia de 'siguiendo'")
    class PersistenciaSiguiendoTests {

        @Test
        @DisplayName("Round-trip: siguiendo se conserva al guardar y cargar")
        public void testRoundTripSiguiendo() {
            sistema.agregarCliente("Alice", 95, new String[]{"Bob", "Charlie"}, null);
            sistema.agregarCliente("Bob", 80, new String[]{"Alice"}, null);
            sistema.agregarCliente("Charlie", 97);

            guardarEnArchivo(construirDataDesdeSistema());
            ClientesData cargada = cargarDesdeArchivo();

            assertNotNull(cargada, "Los datos cargados no deben ser null");
            assertEquals(3, cargada.getClientes().size(), "Deben cargarse 3 clientes");

            Cliente alice = buscarEnLista(cargada.getClientes(), "Alice");
            assertNotNull(alice, "Alice debe existir en los datos cargados");
            assertNotNull(alice.getSiguiendo(), "Alice debe tener array siguiendo");
            assertEquals(2, alice.getSiguiendo().length, "Alice debe seguir a 2 clientes");
            assertTrue(Arrays.asList(alice.getSiguiendo()).contains("Bob"), "Alice debe seguir a Bob");
            assertTrue(Arrays.asList(alice.getSiguiendo()).contains("Charlie"), "Alice debe seguir a Charlie");

            Cliente bob = buscarEnLista(cargada.getClientes(), "Bob");
            assertNotNull(bob.getSiguiendo(), "Bob debe tener array siguiendo");
            assertEquals(1, bob.getSiguiendo().length, "Bob debe seguir a 1 cliente");
            assertEquals("Alice", bob.getSiguiendo()[0], "Bob debe seguir a Alice");
        }

        @Test
        @DisplayName("Cliente sin seguidos se guarda con siguiendo null o vacío")
        public void testRoundTripSinSiguiendo() {
            sistema.agregarCliente("Solo", 70);

            guardarEnArchivo(construirDataDesdeSistema());
            ClientesData cargada = cargarDesdeArchivo();

            Cliente solo = buscarEnLista(cargada.getClientes(), "Solo");
            assertNotNull(solo, "El cliente debe existir");
            // siguiendo puede ser null o vacío, ambos son válidos
            assertTrue(solo.getSiguiendo() == null || solo.getSiguiendo().length == 0,
                    "Cliente sin seguidos debe tener siguiendo null o vacío");
        }

        @Test
        @DisplayName("Scoring se preserva correctamente en el round-trip")
        public void testRoundTripScoring() {
            sistema.agregarCliente("A", 0);
            sistema.agregarCliente("B", 50);
            sistema.agregarCliente("C", 100);

            guardarEnArchivo(construirDataDesdeSistema());
            ClientesData cargada = cargarDesdeArchivo();

            Cliente a = buscarEnLista(cargada.getClientes(), "A");
            Cliente b = buscarEnLista(cargada.getClientes(), "B");
            Cliente c = buscarEnLista(cargada.getClientes(), "C");

            assertEquals(0, a.getScoring(), "Scoring mínimo debe preservarse");
            assertEquals(50, b.getScoring(), "Scoring medio debe preservarse");
            assertEquals(100, c.getScoring(), "Scoring máximo debe preservarse");
        }
    }

    // ========== Iteración 3 — Persistencia de relaciones generales ==========

    @Nested
    @DisplayName("Iteración 3 — Persistencia de 'conexiones'")
    class PersistenciaConexionesTests {

        @Test
        @DisplayName("Round-trip: conexiones se conservan al guardar y cargar")
        public void testRoundTripConexiones() {
            sistema.agregarCliente("Alice", 95, null, new String[]{"Bob", "Charlie"});
            sistema.agregarCliente("Bob", 80, null, new String[]{"Alice", "Charlie"});
            sistema.agregarCliente("Charlie", 97, null, new String[]{"Alice"});

            guardarEnArchivo(construirDataDesdeSistema());
            ClientesData cargada = cargarDesdeArchivo();

            Cliente alice = buscarEnLista(cargada.getClientes(), "Alice");
            assertNotNull(alice.getConexiones(), "Alice debe tener conexiones");
            assertEquals(2, alice.getConexiones().length, "Alice debe tener 2 conexiones");
            assertTrue(Arrays.asList(alice.getConexiones()).contains("Bob"));
            assertTrue(Arrays.asList(alice.getConexiones()).contains("Charlie"));

            Cliente charlie = buscarEnLista(cargada.getClientes(), "Charlie");
            assertNotNull(charlie.getConexiones(), "Charlie debe tener conexiones");
            assertEquals(1, charlie.getConexiones().length, "Charlie debe tener 1 conexión");
            assertEquals("Alice", charlie.getConexiones()[0]);
        }

        @Test
        @DisplayName("Cliente sin conexiones se guarda con conexiones null o vacío")
        public void testRoundTripSinConexiones() {
            sistema.agregarCliente("Aislado", 50);

            guardarEnArchivo(construirDataDesdeSistema());
            ClientesData cargada = cargarDesdeArchivo();

            Cliente aislado = buscarEnLista(cargada.getClientes(), "Aislado");
            assertTrue(aislado.getConexiones() == null || aislado.getConexiones().length == 0,
                    "Cliente sin conexiones debe tener conexiones null o vacío");
        }

        @Test
        @DisplayName("Round-trip completo: siguiendo + conexiones juntos")
        public void testRoundTripSiguiendoYConexiones() {
            sistema.agregarCliente("Alice", 95, new String[]{"Bob"}, new String[]{"Bob", "Charlie"});
            sistema.agregarCliente("Bob", 80, new String[]{"Alice"}, new String[]{"Alice"});
            sistema.agregarCliente("Charlie", 97, new String[]{"Alice", "Bob"}, new String[]{"Alice", "Bob"});

            guardarEnArchivo(construirDataDesdeSistema());
            ClientesData cargada = cargarDesdeArchivo();

            assertEquals(3, cargada.getClientes().size());

            Cliente alice = buscarEnLista(cargada.getClientes(), "Alice");
            assertNotNull(alice.getSiguiendo(), "Alice debe tener siguiendo");
            assertEquals(1, alice.getSiguiendo().length, "Alice sigue a 1");
            assertNotNull(alice.getConexiones(), "Alice debe tener conexiones");
            assertEquals(2, alice.getConexiones().length, "Alice tiene 2 conexiones");

            Cliente charlie = buscarEnLista(cargada.getClientes(), "Charlie");
            assertEquals(2, charlie.getSiguiendo().length, "Charlie sigue a 2");
            assertEquals(2, charlie.getConexiones().length, "Charlie tiene 2 conexiones");
        }
    }

    // ========== Persistencia end-to-end: guardar, cargar y operar ==========

    @Nested
    @DisplayName("End-to-end — Guardar, cargar y operar con relaciones")
    class EndToEndTests {

        @Test
        @DisplayName("Datos cargados desde JSON permiten consultar seguimientos")
        public void testCargarYConsultarSiguiendo() {
            sistema.agregarCliente("A", 90, new String[]{"B", "C"}, null);
            sistema.agregarCliente("B", 85);
            sistema.agregarCliente("C", 80);

            guardarEnArchivo(construirDataDesdeSistema());

            // Simular nuevo sistema cargando desde archivo
            Cliente.setIdCounter(100);
            Sistema sistema2 = new Sistema();
            ClientesData data = cargarDesdeArchivo();
            for (Cliente c : data.getClientes()) {
                sistema2.agregarCliente(c.getNombre(), c.getScoring(),
                        c.getSiguiendo(), c.getConexiones());
            }

            ListaTda<String> seguidos = sistema2.consultarConexionesDe("A");
            assertEquals(2, seguidos.longitud(), "A debe seguir a 2 después de cargar desde JSON");
        }

        @Test
        @DisplayName("Datos cargados desde JSON permiten calcular distancia")
        public void testCargarYCalcularDistancia() {
            sistema.agregarCliente("A", 90, null, new String[]{"B"});
            sistema.agregarCliente("B", 85, null, new String[]{"A", "C"});
            sistema.agregarCliente("C", 80, null, new String[]{"B"});

            guardarEnArchivo(construirDataDesdeSistema());

            Cliente.setIdCounter(100);
            Sistema sistema2 = new Sistema();
            ClientesData data = cargarDesdeArchivo();
            for (Cliente c : data.getClientes()) {
                sistema2.agregarCliente(c.getNombre(), c.getScoring(),
                        c.getSiguiendo(), c.getConexiones());
            }

            assertEquals(0, sistema2.distanciaEntre("A", "A"), "Distancia a sí mismo: 0");
            assertEquals(1, sistema2.distanciaEntre("A", "B"), "A→B: 1 salto");
            assertEquals(2, sistema2.distanciaEntre("A", "C"), "A→B→C: 2 saltos");
        }

        @Test
        @DisplayName("Cargar desde JSON de test con 5 clientes preserva todo")
        public void testCargarDesdeJsonDeTest() {
            GestorJSON gestorTest = new GestorJSON();
            ClientesData data = gestorTest.leerArchivo("src/test/resources/clientes_test.json");

            assertNotNull(data, "Debe poder leer el JSON de test");
            assertEquals(5, data.getClientes().size(), "Debe tener 5 clientes");

            Cliente alice = buscarEnLista(data.getClientes(), "Alice");
            assertNotNull(alice);
            assertEquals(95, alice.getScoring());
            assertEquals(2, alice.getSiguiendo().length, "Alice sigue a 2");
            assertEquals(2, alice.getConexiones().length, "Alice tiene 2 conexiones");

            Cliente eduardo = buscarEnLista(data.getClientes(), "Eduardo");
            assertNotNull(eduardo);
            assertEquals(45, eduardo.getScoring());
            assertEquals(0, eduardo.getSiguiendo().length, "Eduardo no sigue a nadie");
            assertEquals(0, eduardo.getConexiones().length, "Eduardo no tiene conexiones");
        }
    }
}
