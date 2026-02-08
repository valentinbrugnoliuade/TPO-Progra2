import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests para validador de clientes JSON.
 * Verifica manejo de errores y casos especiales.
 */
@DisplayName("Tests del Validador de Clientes")
public class ValidadorClientesTest {
    
    @Test
    @DisplayName("Válido: Lista correcta de clientes")
    public void testListaValida() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente("Alice", 95));
        clientes.add(new Cliente("Bob", 88));
        
        assertDoesNotThrow(() -> ValidadorClientes.validar(clientes), 
            "No debe lanzar excepción con datos válidos");
    }
    
    @Test
    @DisplayName("Error: Cliente duplicado por nombre")
    public void testClienteDuplicado() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente("Alice", 95));
        clientes.add(new Cliente("Alice", 88));
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> ValidadorClientes.validar(clientes));
        assertTrue(ex.getMessage().contains("duplicado"), "Debe mencionar duplicado");
    }
    
    @Test
    @DisplayName("Error: Scoring fuera de rango")
    public void testScoringInvalido() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente = new Cliente("Alice", 95);
        cliente.setScoring(150); // Inválido
        clientes.add(cliente);
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> ValidadorClientes.validar(clientes));
        assertTrue(ex.getMessage().contains("scoring inválido"), "Debe mencionar scoring inválido");
    }
    
    @Test
    @DisplayName("Error: Scoring negativo")
    public void testScoringNegativo() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente = new Cliente("Alice", 95);
        cliente.setScoring(-10);
        clientes.add(cliente);
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> ValidadorClientes.validar(clientes));
        assertTrue(ex.getMessage().contains("scoring inválido"), "Debe rechazar scoring negativo");
    }
    
    @Test
    @DisplayName("Error: Lista nula")
    public void testListaNula() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> ValidadorClientes.validar(null));
        assertTrue(ex.getMessage().contains("vacía"), "Debe rechazar lista nula");
    }
    
    @Test
    @DisplayName("Error: Lista vacía")
    public void testListaVacia() {
        List<Cliente> clientes = new ArrayList<>();
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> ValidadorClientes.validar(clientes));
        assertTrue(ex.getMessage().contains("vacía"), "Debe rechazar lista vacía");
    }
    
    @Test
    @DisplayName("Error: Cliente nulo en lista")
    public void testClienteNulo() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(null);
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> ValidadorClientes.validar(clientes));
        assertTrue(ex.getMessage().contains("nulo"), "Debe rechazar cliente nulo");
    }
    
    @Test
    @DisplayName("Error: Referencia a cliente inexistente en 'siguiendo'")
    public void testReferenciaInexistenteEnSiguiendo() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente alice = new Cliente("Alice", 95);
        alice.setSiguiendo(new String[]{"NoExiste"});
        clientes.add(alice);
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> ValidadorClientes.validar(clientes));
        assertTrue(ex.getMessage().contains("no existe"), "Debe rechazar referencia inexistente");
    }
    
    @Test
    @DisplayName("Error: Referencia a cliente inexistente en 'conexiones'")
    public void testReferenciaInexistenteEnConexiones() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente alice = new Cliente("Alice", 95);
        alice.setConexiones(new String[]{"NoExiste"});
        clientes.add(alice);
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> ValidadorClientes.validar(clientes));
        assertTrue(ex.getMessage().contains("no existe"), "Debe rechazar referencia inexistente");
    }
    
    @Test
    @DisplayName("Válido: Siguiendo y conexiones vacíos")
    public void testSiguiendoYConexionesVacios() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente alice = new Cliente("Alice", 95);
        alice.setSiguiendo(new String[]{});
        alice.setConexiones(new String[]{});
        clientes.add(alice);
        
        assertDoesNotThrow(() -> ValidadorClientes.validar(clientes), 
            "Debe aceptar arrays vacíos");
    }
    
    @Test
    @DisplayName("Válido: Siguiendo y conexiones nulos")
    public void testSiguiendoYConexionesNulos() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente alice = new Cliente("Alice", 95);
        // Se dejan nulos por defecto
        clientes.add(alice);
        
        assertDoesNotThrow(() -> ValidadorClientes.validar(clientes), 
            "Debe aceptar referencias nulas");
    }
    
    @Test
    @DisplayName("Válido: Referencias válidas cruzadas")
    public void testReferenciasValidas() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente alice = new Cliente("Alice", 95);
        Cliente bob = new Cliente("Bob", 88);
        
        alice.setSiguiendo(new String[]{"Bob"});
        alice.setConexiones(new String[]{"Bob"});
        
        clientes.add(alice);
        clientes.add(bob);
        
        assertDoesNotThrow(() -> ValidadorClientes.validar(clientes), 
            "Debe aceptar referencias válidas");
    }
    
    @Test
    @DisplayName("Error: Referencia vacía en 'siguiendo'")
    public void testReferenciaVaciaEnSiguiendo() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente alice = new Cliente("Alice", 95);
        alice.setSiguiendo(new String[]{""});
        clientes.add(alice);
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> ValidadorClientes.validar(clientes));
        assertTrue(ex.getMessage().contains("vacía"), "Debe rechazar referencia vacía");
    }
    
    @Test
    @DisplayName("Error: Scoring en límite superior inválido (101)")
    public void testScoringLimiteAlto() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente = new Cliente("Alice", 95);
        cliente.setScoring(101);
        clientes.add(cliente);
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> ValidadorClientes.validar(clientes));
        assertTrue(ex.getMessage().contains("scoring inválido"), "Debe rechazar > 100");
    }
    
    @Test
    @DisplayName("Válido: Scoring en límites válidos (0 y 100)")
    public void testScoringLimitesValidos() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente min = new Cliente("Min", 0);
        Cliente max = new Cliente("Max", 100);
        clientes.add(min);
        clientes.add(max);
        
        assertDoesNotThrow(() -> ValidadorClientes.validar(clientes), 
            "Debe aceptar scoring 0 y 100");
    }
}
