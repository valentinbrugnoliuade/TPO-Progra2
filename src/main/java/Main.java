import java.util.ArrayList;
import java.util.Scanner;

// Punto de entrada de demo.
public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();

        System.out.println("=== Demo Iteración 1 ===");

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

        Scanner sc = new Scanner(System.in);
        GestorJSON gestor = new GestorJSON();

        //Leer el JSON de resource (base)
        ClientesData data = gestor.leerResource();
        for (Cliente c : data.getClientes()) {
            System.out.println(c);
        }
        // Asegura que la lista exista
        if (data.getClientes() == null) {
            data.setClientes(new ArrayList<>());
        }

        //Se le pide los datos manualmente y se agregan
        Cliente nuevo = new Cliente();

        System.out.println("Nombre: ");
        String nombre = sc.nextLine();
        nuevo.setNombre(nombre);

        System.out.println("Scoring: ");
        int scoring = Integer.parseInt(sc.nextLine());
        nuevo.setScoring(scoring);

        System.out.print("Sigue a: ");
        String siguiendo = sc.nextLine();
        nuevo.setSiguiendo(new String[]{siguiendo});

        System.out.print("Conectado con: ");
        String conexion = sc.nextLine();
        nuevo.setConexiones(new String[]{conexion});

        data.getClientes().add(nuevo);
        gestor.guardar(data);

        //Lee el nuevo archivo guardado clientes_out.json
        ClientesData archivoNuevo = gestor.leerArchivo("clientes_out.json");

        for (Cliente c : archivoNuevo.getClientes()) {
            System.out.println(c);
        }
    }
}