import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import TDAs.lista.ListaTda;

/**
 * Tests unitarios para validar funcionalidades core del sistema.
 * Iteración 1 - Pruebas básicas.
 */
@DisplayName("Tests del Sistema de Gestión de Clientes")
public class SistemaTest {
    
    private Sistema sistema;
    
    @BeforeEach
    public void setUp() {
        Cliente.setIdCounter(1);
        sistema = new Sistema();
    }
    
    // ========== Tests de Clientes ==========
    
    @Test
    @DisplayName("Agregar cliente exitosamente")
    public void testAgregarCliente() {
        sistema.agregarCliente("Alice", 95);
        Cliente cliente = sistema.buscarClientePorId(1);
        assertNotNull(cliente, "Cliente debe existir después de agregarlo");
        assertEquals("Alice", cliente.getNombre(), "El nombre debe ser 'Alice'");
        assertEquals(95, cliente.getScoring(), "El scoring debe ser 95");
    }
    
    @Test
    @DisplayName("Buscar cliente por nombre")
    public void testBuscarPorNombre() {
        sistema.agregarCliente("Bob", 88);
        ListaTda<Cliente> resultado = sistema.buscarClientesPorNombre("Bob");
        assertEquals(1, resultado.longitud(), "Debe encontrar exactamente 1 cliente");
        assertEquals("Bob", resultado.obtener(0).getNombre(), "Debe ser Bob");
    }
    
    @Test
    @DisplayName("Buscar cliente por ID")
    public void testBuscarPorId() {
        sistema.agregarCliente("Charlie", 80);
        Cliente cliente = sistema.buscarClientePorId(1);
        assertNotNull(cliente, "Debe encontrar el cliente");
        assertEquals("Charlie", cliente.getNombre(), "Debe ser Charlie");
    }
    
    @Test
    @DisplayName("Buscar cliente por scoring")
    public void testBuscarPorScoring() {
        sistema.agregarCliente("Alice", 90);
        sistema.agregarCliente("Bob", 90);
        sistema.agregarCliente("Charlie", 85);
        ListaTda<Cliente> resultado = sistema.buscarClientesPorScoring(90);
        assertEquals(2, resultado.longitud(), "Debe encontrar 2 clientes con scoring 90");
    }
    
    @Test
    @DisplayName("Listar todos los clientes")
    public void testListarTodos() {
        sistema.agregarCliente("Alice", 95);
        sistema.agregarCliente("Bob", 88);
        sistema.agregarCliente("Charlie", 80);
        ListaTda<Cliente> todos = sistema.listarClientes();
        assertEquals(3, todos.longitud(), "Debe haber 3 clientes");
    }
    
    @Test
    @DisplayName("Eliminar cliente por ID")
    public void testEliminarCliente() {
        sistema.agregarCliente("Alice", 95);
        boolean eliminado = sistema.eliminarClientePorId(1);
        assertTrue(eliminado, "Debe eliminar exitosamente");
        assertNull(sistema.buscarClientePorId(1), "Cliente no debe existir después de eliminarlo");
    }
    
    @Test
    @DisplayName("Eliminación no afecta a otros clientes")
    public void testEliminacionNoAfectaOtros() {
        sistema.agregarCliente("Alice", 95);
        sistema.agregarCliente("Bob", 88);
        sistema.eliminarClientePorId(1);
        Cliente bob = sistema.buscarClientePorId(2);
        assertNotNull(bob, "Bob debe seguir existiendo");
        assertEquals("Bob", bob.getNombre(), "Debe ser Bob");
    }
    
    // ========== Tests de Relaciones ==========
    
    @Test
    @DisplayName("Agregar cliente con seguimientos")
    public void testAgregarClienteConSiguiendo() {
        String[] siguiendo = {"Bob", "Charlie"};
        sistema.agregarCliente("Alice", 95, siguiendo, null);
        Cliente alice = sistema.buscarClientePorId(1);
        assertNotNull(alice.getSiguiendo(), "Debe tener seguimientos");
        assertEquals(2, alice.getSiguiendo().length, "Debe seguir a 2 clientes");
    }
    
    @Test
    @DisplayName("Agregar cliente con conexiones")
    public void testAgregarClienteConConexiones() {
        String[] conexiones = {"Bob", "Charlie", "David"};
        sistema.agregarCliente("Alice", 95, null, conexiones);
        Cliente alice = sistema.buscarClientePorId(1);
        assertNotNull(alice.getConexiones(), "Debe tener conexiones");
        assertEquals(3, alice.getConexiones().length, "Debe tener 3 conexiones");
    }

    @Test
    @DisplayName("Consultar conexiones (a quién sigue) de un cliente")
    public void testConsultarConexionesDeCliente() {
        sistema.agregarCliente("A", 90, new String[]{"B", "C"}, null);
        sistema.agregarCliente("B", 85);
        sistema.agregarCliente("C", 88);

        ListaTda<String> seguidos = sistema.consultarConexionesDe("A");
        assertEquals(2, seguidos.longitud(), "A debe seguir a 2 clientes");
        assertEquals("B", seguidos.obtener(0), "El primer seguido debe ser B");
        assertEquals("C", seguidos.obtener(1), "El segundo seguido debe ser C");
    }

    @Test
    @DisplayName("Cliente con más seguidores usando ABB/AVL")
    public void testClienteConMasSeguidores() {
        sistema.agregarCliente("A", 90, new String[]{"B", "C"}, null);
        sistema.agregarCliente("B", 85);
        sistema.agregarCliente("C", 88);
        sistema.agregarCliente("D", 70, new String[]{"B"}, null);

        String mayor = sistema.clienteConMasSeguidores();
        assertNotNull(mayor, "Debe existir cliente con más seguidores");
        assertTrue(mayor.contains("B"), "El cliente con más seguidores debe ser B");
        assertTrue(mayor.contains("seguidores=2"), "B debe tener 2 seguidores");
    }

    @Test
    @DisplayName("Obtener clientes en cuarto nivel del ABB/AVL por seguidores")
    public void testClientesEnCuartoNivelPorSeguidores() {
        sistema.agregarCliente("A", 90);
        sistema.agregarCliente("B", 90);
        sistema.agregarCliente("C", 90);
        sistema.agregarCliente("D", 90);
        sistema.agregarCliente("E", 90);
        sistema.agregarCliente("F", 90);
        sistema.agregarCliente("G", 90);
        sistema.agregarCliente("H", 90);

        ListaTda<String> cuartoNivel = sistema.clientesEnCuartoNivelPorSeguidores();
        assertTrue(cuartoNivel.longitud() > 0, "Con 8 nodos debe existir al menos un cliente en nivel 4");
    }
    
    // ========== Tests de Historial ==========
    
    @Test
    @DisplayName("Registrar acción en historial")
    public void testRegistrarAccion() {
        sistema.agregarCliente("Alice", 95);
        ListaTda<Accion> acciones = sistema.listarAcciones();
        assertTrue(acciones.longitud() > 0, "Debe haber al menos una acción registrada");
        assertTrue(acciones.obtener(0).toString().contains("Alice"), "Acción debe mencionar a Alice");
    }
    
    @Test
    @DisplayName("Deshacer última acción")
    public void testDeshacerAccion() {
        sistema.agregarCliente("Alice", 95);
        Accion deshecha = sistema.deshacerUltimaAccion();
        assertNotNull(deshecha, "Debe retornar la acción deshecha");
        assertTrue(deshecha.toString().contains("Alice"), "Acción debe mencionar a Alice");
    }
    
    // ========== Tests de Solicitudes ==========
    
    @Test
    @DisplayName("Crear solicitud de seguimiento")
    public void testSolicitudSeguimiento() {
        sistema.solicitarSeguimiento("Alice", "Bob");
        int pendientes = sistema.cantidadSolicitudesPendientes();
        assertEquals(1, pendientes, "Debe haber 1 solicitud pendiente");
    }
    
    @Test
    @DisplayName("Procesar solicitud FIFO")
    public void testProcesarSolicitud() {
        sistema.solicitarSeguimiento("Alice", "Bob");
        sistema.solicitarSeguimiento("Charlie", "David");
        SolicitudSeguimiento s1 = sistema.procesarSiguienteSolicitud();
        assertEquals("Alice", s1.getSolicitante(), "Debe procesar Alice primero (FIFO)");
        SolicitudSeguimiento s2 = sistema.procesarSiguienteSolicitud();
        assertEquals("Charlie", s2.getSolicitante(), "Debe procesar Charlie después");
    }

    @Test
    @DisplayName("Cliente TDA: cola de solicitudes y pila de acciones por cliente")
    public void testClienteTdaEstructurasInternas() {
        sistema.agregarCliente("Alice", 95);
        sistema.agregarCliente("Bob", 88);

        Cliente alice = sistema.buscarClientePorId(1);
        assertNotNull(alice, "Alice debe existir");

        // Acción del alta registrada en el cliente
        assertTrue(alice.listarAcciones().longitud() >= 1, "Alice debe tener acciones registradas");

        // Solicitud: se encola global y también en la cola del cliente (por nombre)
        sistema.solicitarSeguimiento("Alice", "Bob");
        assertEquals(1, alice.cantidadSolicitudesPendientes(), "Alice debe tener 1 solicitud pendiente en su cola");

        // Al procesar, se desencola global y también se mantiene la cola del cliente consistente
        sistema.procesarSiguienteSolicitud();
        assertEquals(0, alice.cantidadSolicitudesPendientes(), "La cola de Alice debe quedar vacía tras procesar");
    }
    
    // ========== Tests de Casos Borde ==========
    
    @Test
    @DisplayName("Buscar cliente inexistente por nombre")
    public void testBuscarClienteInexistente() {
        ListaTda<Cliente> resultado = sistema.buscarClientesPorNombre("NoExiste");
        assertEquals(0, resultado.longitud(), "No debe encontrar nada");
    }
    
    @Test
    @DisplayName("Listar cuando no hay clientes")
    public void testListarSinClientes() {
        ListaTda<Cliente> todos = sistema.listarClientes();
        assertEquals(0, todos.longitud(), "Debe estar vacío");
    }
    
    @Test
    @DisplayName("Cantidad de clientes correcta")
    public void testCantidadClientes() {
        sistema.agregarCliente("A", 90);
        sistema.agregarCliente("B", 85);
        sistema.agregarCliente("C", 95);
        assertEquals(3, sistema.cantidadClientes(), "Debe contar 3 clientes");
    }

    // ========== Iteración 2 — Relaciones de Seguimiento ==========

    @Nested
    @DisplayName("Iteración 2 — Seguimiento entre clientes")
    class SeguimientoTests {

        @Test
        @DisplayName("Cliente puede seguir hasta 2 clientes")
        public void testSeguirHasta2() {
            sistema.agregarCliente("A", 90);
            Cliente a = sistema.buscarClientePorId(1);
            assertTrue(a.seguirA("B"), "Debe poder seguir al primero");
            assertTrue(a.seguirA("C"), "Debe poder seguir al segundo");
            assertEquals(2, a.cantidadSiguiendo(), "Debe seguir a exactamente 2");
        }

        @Test
        @DisplayName("Seguir a un tercero lanza excepción")
        public void testSeguirMasDe2LanzaExcepcion() {
            sistema.agregarCliente("A", 90, new String[]{"B", "C"}, null);
            Cliente a = sistema.buscarClientePorId(1);
            assertThrows(IllegalStateException.class, () -> a.seguirA("D"),
                    "Debe lanzar IllegalStateException al intentar seguir a un tercero");
        }

        @Test
        @DisplayName("Dejar de seguir reduce la cantidad")
        public void testDejarDeSeguir() {
            sistema.agregarCliente("A", 90, new String[]{"B", "C"}, null);
            Cliente a = sistema.buscarClientePorId(1);
            assertTrue(a.dejarDeSeguir("B"), "Debe poder dejar de seguir a B");
            assertEquals(1, a.cantidadSiguiendo(), "Debe quedar siguiendo solo a 1");
        }

        @Test
        @DisplayName("Dejar de seguir a alguien que no sigue retorna false")
        public void testDejarDeSeguirInexistente() {
            sistema.agregarCliente("A", 90, new String[]{"B"}, null);
            Cliente a = sistema.buscarClientePorId(1);
            assertFalse(a.dejarDeSeguir("Z"), "No debería poder dejar de seguir a alguien que no sigue");
        }

        @Test
        @DisplayName("Auto-seguimiento lanza excepción")
        public void testAutoSeguimientoProhibido() {
            sistema.agregarCliente("A", 90);
            Cliente a = sistema.buscarClientePorId(1);
            assertThrows(IllegalArgumentException.class, () -> a.seguirA("A"),
                    "No se permite auto-seguimiento");
        }

        @Test
        @DisplayName("Seguir al mismo cliente dos veces retorna false")
        public void testSeguirDuplicado() {
            sistema.agregarCliente("A", 90);
            Cliente a = sistema.buscarClientePorId(1);
            assertTrue(a.seguirA("B"), "Primera vez debe funcionar");
            assertFalse(a.seguirA("B"), "Segunda vez debe retornar false");
            assertEquals(1, a.cantidadSiguiendo(), "Debe seguir a 1 solo");
        }

        @Test
        @DisplayName("Consultar conexiones de cliente inexistente retorna lista vacía")
        public void testConsultarConexionesClienteInexistente() {
            ListaTda<String> seguidos = sistema.consultarConexionesDe("NoExiste");
            assertEquals(0, seguidos.longitud(), "Debe retornar lista vacía para cliente inexistente");
        }

        @Test
        @DisplayName("Consultar conexiones de cliente sin seguidos retorna lista vacía")
        public void testConsultarConexionesClienteSinSeguidos() {
            sistema.agregarCliente("A", 90);
            ListaTda<String> seguidos = sistema.consultarConexionesDe("A");
            assertEquals(0, seguidos.longitud(), "Debe retornar lista vacía si no sigue a nadie");
        }

        @Test
        @DisplayName("Agregar cliente con más de 2 seguidos lanza excepción")
        public void testAgregarClienteCon3SiguiendoLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class,
                    () -> sistema.agregarCliente("A", 90, new String[]{"B", "C", "D"}, null),
                    "Sistema debe rechazar más de 2 seguidos al crear cliente");
        }
    }

    @Nested
    @DisplayName("Iteración 2 — ABB/AVL por seguidores")
    class ABBSeguidoresTests {

        @Test
        @DisplayName("Cuarto nivel vacío con pocos clientes")
        public void testCuartoNivelConPocosDatos() {
            sistema.agregarCliente("A", 90);
            sistema.agregarCliente("B", 85);
            ListaTda<String> cuartoNivel = sistema.clientesEnCuartoNivelPorSeguidores();
            assertEquals(0, cuartoNivel.longitud(),
                    "Con solo 2 clientes no debería haber nodo en nivel 4");
        }

        @Test
        @DisplayName("Cliente con más seguidores sin clientes retorna null")
        public void testClienteConMasSeguidoresSinClientes() {
            String mayor = sistema.clienteConMasSeguidores();
            assertNull(mayor, "Sin clientes debe retornar null");
        }

        @Test
        @DisplayName("Cliente con más seguidores refleja conteo correcto")
        public void testClienteConMasSeguidoresConteo() {
            sistema.agregarCliente("A", 90, new String[]{"C"}, null);
            sistema.agregarCliente("B", 85, new String[]{"C"}, null);
            sistema.agregarCliente("C", 80);
            sistema.agregarCliente("D", 70, new String[]{"C"}, null);

            String mayor = sistema.clienteConMasSeguidores();
            assertNotNull(mayor, "Debe existir un cliente con más seguidores");
            assertTrue(mayor.contains("C"), "C debería ser el más seguido");
            assertTrue(mayor.contains("seguidores=3"), "C debería tener 3 seguidores");
        }

        @Test
        @DisplayName("Nivel por seguidores con datos suficientes devuelve resultados")
        public void testNivelPorSeguidoresConDatosSuficientes() {
            for (int i = 1; i <= 15; i++) {
                sistema.agregarCliente("Cliente" + i, 50 + i);
            }
            ListaTda<String> nivel2 = sistema.clientesEnNivelPorSeguidores(2);
            assertTrue(nivel2.longitud() > 0,
                    "Con 15 clientes debe haber al menos 1 nodo en nivel 2");
        }
    }

    // ========== Iteración 3 — Relaciones Generales y Distancia ==========

    @Nested
    @DisplayName("Iteración 3 — Distancia entre clientes (BFS)")
    class DistanciaTests {

        @Test
        @DisplayName("Distancia entre clientes conectados directamente es 1")
        public void testDistanciaDirecta() {
            sistema.agregarCliente("A", 90, null, new String[]{"B"});
            sistema.agregarCliente("B", 85, null, new String[]{"A"});
            assertEquals(1, sistema.distanciaEntre("A", "B"),
                    "Clientes directamente conectados deben tener distancia 1");
        }

        @Test
        @DisplayName("Distancia a sí mismo es 0")
        public void testDistanciaMismoCliente() {
            sistema.agregarCliente("A", 90);
            assertEquals(0, sistema.distanciaEntre("A", "A"),
                    "La distancia de un cliente a sí mismo debe ser 0");
        }

        @Test
        @DisplayName("Distancia -1 cuando no hay camino")
        public void testDistanciaSinCamino() {
            sistema.agregarCliente("A", 90);
            sistema.agregarCliente("B", 85);
            assertEquals(-1, sistema.distanciaEntre("A", "B"),
                    "Sin conexión debe retornar -1");
        }

        @Test
        @DisplayName("Distancia -1 con cliente inexistente")
        public void testDistanciaClienteInexistente() {
            sistema.agregarCliente("A", 90);
            assertEquals(-1, sistema.distanciaEntre("A", "NoExiste"),
                    "Con cliente inexistente debe retornar -1");
        }

        @Test
        @DisplayName("Distancia con 2 saltos a través de intermedio")
        public void testDistanciaDosSaltos() {
            sistema.agregarCliente("A", 90, null, new String[]{"B"});
            sistema.agregarCliente("B", 85, null, new String[]{"A", "C"});
            sistema.agregarCliente("C", 80, null, new String[]{"B"});
            assertEquals(2, sistema.distanciaEntre("A", "C"),
                    "A→B→C debe ser distancia 2");
        }

        @Test
        @DisplayName("Distancia con 3 saltos en cadena")
        public void testDistanciaTresSaltos() {
            sistema.agregarCliente("A", 90, null, new String[]{"B"});
            sistema.agregarCliente("B", 85, null, new String[]{"A", "C"});
            sistema.agregarCliente("C", 80, null, new String[]{"B", "D"});
            sistema.agregarCliente("D", 75, null, new String[]{"C"});
            assertEquals(3, sistema.distanciaEntre("A", "D"),
                    "A→B→C→D debe ser distancia 3");
        }

        @Test
        @DisplayName("BFS encuentra el camino más corto")
        public void testDistanciaCaminoMasCorto() {
            sistema.agregarCliente("A", 90, null, new String[]{"B", "C"});
            sistema.agregarCliente("B", 85, null, new String[]{"A", "D"});
            sistema.agregarCliente("C", 80, null, new String[]{"A", "D"});
            sistema.agregarCliente("D", 75, null, new String[]{"B", "C"});
            // A→B→D (2 saltos) o A→C→D (2 saltos), ambos son el camino más corto
            assertEquals(2, sistema.distanciaEntre("A", "D"),
                    "Debe encontrar el camino más corto (2 saltos)");
        }
    }

    @Nested
    @DisplayName("Iteración 3 — Relaciones generales (grafo)")
    class RelacionesGeneralesTests {

        @Test
        @DisplayName("Agregar cliente con siguiendo y conexiones a la vez")
        public void testClienteConSiguiendoYConexiones() {
            sistema.agregarCliente("A", 90,
                    new String[]{"B"}, new String[]{"B", "C"});
            Cliente a = sistema.buscarClientePorId(1);
            assertNotNull(a.getSiguiendo(), "Debe tener siguiendo");
            assertEquals(1, a.getSiguiendo().length, "Debe seguir a 1");
            assertNotNull(a.getConexiones(), "Debe tener conexiones");
            assertEquals(2, a.getConexiones().length, "Debe tener 2 conexiones");
        }

        @Test
        @DisplayName("Grafo refleja conexiones cargadas para cálculo de distancia")
        public void testGrafoReflejaConexiones() {
            sistema.agregarCliente("A", 90, null, new String[]{"B"});
            sistema.agregarCliente("B", 85, null, new String[]{"A"});
            // Si el grafo se construyó correctamente, la distancia debe ser 1
            int dist = sistema.distanciaEntre("A", "B");
            assertEquals(1, dist, "El grafo debe reflejar la conexión A↔B");
        }

        @Test
        @DisplayName("Conexiones unidireccionales: A→B pero no B→A")
        public void testConexionUnidireccional() {
            sistema.agregarCliente("A", 90, null, new String[]{"B"});
            sistema.agregarCliente("B", 85);
            // A tiene arista hacia B, pero B no hacia A
            assertEquals(1, sistema.distanciaEntre("A", "B"),
                    "A→B debe tener distancia 1");
            assertEquals(-1, sistema.distanciaEntre("B", "A"),
                    "B→A no tiene camino (grafo dirigido)");
        }

        @Test
        @DisplayName("Conexiones bidireccionales permiten ida y vuelta")
        public void testConexionBidireccional() {
            sistema.agregarCliente("A", 90, null, new String[]{"B"});
            sistema.agregarCliente("B", 85, null, new String[]{"A"});
            assertEquals(1, sistema.distanciaEntre("A", "B"));
            assertEquals(1, sistema.distanciaEntre("B", "A"));
        }

        @Test
        @DisplayName("Red con múltiples clientes y conexiones cruzadas")
        public void testRedCompleja() {
            sistema.agregarCliente("A", 95, new String[]{"B"}, new String[]{"B", "C"});
            sistema.agregarCliente("B", 80, new String[]{"A"}, new String[]{"A", "C"});
            sistema.agregarCliente("C", 97, new String[]{"A", "B"}, new String[]{"A", "B"});

            assertEquals(1, sistema.distanciaEntre("A", "B"));
            assertEquals(1, sistema.distanciaEntre("A", "C"));
            assertEquals(1, sistema.distanciaEntre("B", "C"));

            ListaTda<String> seguidosA = sistema.consultarConexionesDe("A");
            assertEquals(1, seguidosA.longitud(), "A sigue solo a B");
            assertEquals("B", seguidosA.obtener(0));
        }
    }
}
