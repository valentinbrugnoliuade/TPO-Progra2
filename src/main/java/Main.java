public class Main {
    public static void main(String[] args) {
        // Interfaz mínima (demo) para Iteración 1.
        Sistema sistema = new Sistema();

        System.out.println("=== Demo Iteración 1 ===");

        // Alta + normalización (espacios / mayúsculas)
        sistema.agregarCliente("  Alice   ", 95);
        sistema.agregarCliente("bob", 88);

        System.out.println("Clientes cargados: " + sistema.cantidadClientes());
        System.out.println("Listar clientes: " + sistema.listarClientes());

        System.out.println("Existe ' ALICE ': " + sistema.existeCliente(" ALICE "));
        System.out.println("Buscar 'ALICE': " + sistema.buscarCliente("ALICE"));
        System.out.println("Buscar null (debe dar null): " + sistema.buscarCliente(null));

        System.out.println("Scoring 88: " + sistema.buscarClientesPorScoring(88));
        System.out.println("Scoring inexistente 123 (vacío): " + sistema.buscarClientesPorScoring(123));

        sistema.solicitarSeguimiento("Alice", "Bob");
        System.out.println("Solicitudes pendientes: " + sistema.cantidadSolicitudesPendientes());
        System.out.println("Procesada: " + sistema.procesarSiguienteSolicitud());
        System.out.println("Solicitudes pendientes (luego): " + sistema.cantidadSolicitudesPendientes());

        System.out.println("Acciones registradas: " + sistema.listarAcciones());
        System.out.println("Deshacer última acción (devuelve detalle): " + sistema.deshacerUltimaAccion());
    }
}