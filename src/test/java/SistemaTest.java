import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

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
}
