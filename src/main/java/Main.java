import java.util.ArrayList;
import java.util.Scanner;
import TDAs.lista.ListaTda;

/**
 * Punto de entrada con menú interactivo del sistema de clientes.
 */
public class Main {
    private static Sistema sistema = new Sistema();
    private static Scanner sc = new Scanner(System.in);
    private static GestorJSON gestorJSON = new GestorJSON();

    /** Metodo principal del programa. Complejidad: O(n*m) donde n es el numero de operaciones y m es la complejidad de cada operacion */
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║       SISTEMA DE GESTIÓN DE CLIENTES                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        int opcion;
        do {
            mostrarMenu();
            System.out.print("Selecciona una opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Entrada invalida. Ingresa un numero.\n");
                opcion = -1;
            }
        } while (opcion != 0);

        System.out.println("\n[OK] Hasta luego!");
        sc.close();
    }

    /** Muestra el menu de opciones. Complejidad: O(1) */
    private static void mostrarMenu() {
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│                       MENÚ PRINCIPAL                           │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  1.  Agregar cliente                                           │");
        System.out.println("│  2.  Buscar cliente por nombre                                 │");
        System.out.println("│  3.  Buscar cliente por ID                                     │");
        System.out.println("│  4.  Buscar clientes por scoring                               │");
        System.out.println("│  5.  Listar todos los clientes                                 │");
        System.out.println("│  6.  Eliminar cliente                                          │");
        System.out.println("│  7.  Ver historial de acciones                                 │");
        System.out.println("│  8.  Cargar clientes desde JSON                                │");
        System.out.println("│  9.  Guardar clientes a JSON                                   │");
        System.out.println("│  0.  Salir                                                     │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");
    }

    /** Procesa la opcion seleccionada por el usuario. Complejidad: O(1) */
    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarClienteInteractivo();
                break;
            case 2:
                buscarPorNombreInteractivo();
                break;
            case 3:
                buscarPorIdInteractivo();
                break;
            case 4:
                buscarPorScoringInteractivo();
                break;
            case 5:
                listarTodosClientes();
                break;
            case 6:
                eliminarClienteInteractivo();
                break;
            case 7:
                verHistorial();
                break;
            case 8:
                cargarDesdeJSON();
                break;
            case 9:
                guardarAJSON();
                break;
            case 0:
                System.out.println("\n[OK] Opcion valida: Salir");
                break;
            default:
                System.out.println("[ERROR] Opcion invalida. Intenta de nuevo.\n");
        }
    }

    /** Agrega un cliente interactivamente. Complejidad: O(n) donde n es la longitud del nombre */
    private static void agregarClienteInteractivo() {
        System.out.println("\n┌─ AGREGAR CLIENTE ─┐");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Scoring (0-100): ");
        try {
            int scoring = Integer.parseInt(sc.nextLine());
            
            // Preguntar por siguiendo
            System.out.print("¿A quién sigue? (nombres separados por coma, o dejar vacío): ");
            String siguiendoStr = sc.nextLine().trim();
            String[] siguiendo = null;
            if (!siguiendoStr.isEmpty()) {
                String[] temp = siguiendoStr.split(",");
                siguiendo = new String[temp.length];
                for (int i = 0; i < temp.length; i++) {
                    siguiendo[i] = temp[i].trim();
                }
            }
            
            // Preguntar por conexiones
            System.out.print("¿Con quién está conectado? (nombres separados por coma, o dejar vacío): ");
            String conexionesStr = sc.nextLine().trim();
            String[] conexiones = null;
            if (!conexionesStr.isEmpty()) {
                String[] temp = conexionesStr.split(",");
                conexiones = new String[temp.length];
                for (int i = 0; i < temp.length; i++) {
                    conexiones[i] = temp[i].trim();
                }
            }
            
            sistema.agregarCliente(nombre, scoring, siguiendo, conexiones);
            System.out.println("[OK] Cliente agregado exitosamente.");
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Score debe ser un numero.\n");
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage() + "\n");
        }
    }

    /** Busca cliente por nombre. Complejidad: O(m) donde m es el numero de clientes con ese nombre */
    private static void buscarPorNombreInteractivo() {
        System.out.println("\n┌─ BUSCAR POR NOMBRE ─┐");
        System.out.print("Nombre a buscar: ");
        String nombre = sc.nextLine();

        ListaTda<Cliente> clientes = sistema.buscarClientesPorNombre(nombre);
        if (clientes.longitud() > 0) {
            System.out.println("[OK] Encontrados " + clientes.longitud() + " cliente(s):");
            for (int i = 0; i < clientes.longitud(); i++) {
                System.out.println("   " + clientes.obtener(i));
            }
        } else {
            System.out.println("[INFO] No se encontraron clientes con ese nombre.\n");
        }
        System.out.println();
    }

    /** Busca cliente por ID. Complejidad: O(1) */
    private static void buscarPorIdInteractivo() {
        System.out.println("\n┌─ BUSCAR POR ID ─┐");
        System.out.print("ID a buscar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Cliente cliente = sistema.buscarClientePorId(id);
            if (cliente != null) {
                System.out.println("[OK] Cliente encontrado:");
                System.out.println("   " + cliente);
            } else {
                System.out.println("[INFO] No existe cliente con ID " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] El ID debe ser un numero.");
        }
        System.out.println();
    }

    /** Busca clientes por scoring. Complejidad: O(k) donde k es el numero de clientes con ese scoring */
    private static void buscarPorScoringInteractivo() {
        System.out.println("\n┌─ BUSCAR POR SCORING ─┐");
        System.out.print("Scoring a buscar: ");
        try {
            int scoring = Integer.parseInt(sc.nextLine());
            ListaTda<Cliente> clientes = sistema.buscarClientesPorScoring(scoring);
            if (clientes.longitud() > 0) {
                System.out.println("[OK] Encontrados " + clientes.longitud() + " cliente(s) con scoring " + scoring + ":");
                for (int i = 0; i < clientes.longitud(); i++) {
                    System.out.println("   " + clientes.obtener(i));
                }
            } else {
                System.out.println("[INFO] No hay clientes con scoring " + scoring);
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] El scoring debe ser un numero.");
        }
        System.out.println();
    }

    /** Lista todos los clientes. Complejidad: O(n) donde n es el total de clientes */
    private static void listarTodosClientes() {
        System.out.println("\n┌─ LISTAR TODOS LOS CLIENTES ─┐");
        int total = sistema.cantidadClientes();
        if (total > 0) {
            System.out.println("[OK] Total de clientes: " + total);
            ListaTda<Cliente> clientes = sistema.listarClientes();
            for (int i = 0; i < clientes.longitud(); i++) {
                System.out.println("   " + clientes.obtener(i));
            }
        } else {
            System.out.println("[INFO] No hay clientes registrados.");
        }
        System.out.println();
    }

    /** Elimina un cliente. Complejidad: O(n) donde n es la longitud del nombre */
    private static void eliminarClienteInteractivo() {
        System.out.println("\n┌─ ELIMINAR CLIENTE ─┐");
        System.out.print("ID del cliente a eliminar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Cliente cliente = sistema.buscarClientePorId(id);
            if (cliente != null) {
                System.out.print("Eliminar a '" + cliente.getNombre() + "' (S/N)? ");
                String confirmacion = sc.nextLine().toLowerCase();
                if (confirmacion.equals("s") || confirmacion.equals("si")) {
                    boolean eliminado = sistema.eliminarClientePorId(id);
                    if (eliminado) {
                        System.out.println("[OK] Cliente eliminado exitosamente.");
                    }
                } else {
                    System.out.println("[INFO] Operacion cancelada.");
                }
            } else {
                System.out.println("[INFO] No existe cliente con ID " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] El ID debe ser un numero.");
        }
        System.out.println();
    }

    /** Ver historial de acciones. Complejidad: O(n) donde n es la cantidad de acciones */
    private static void verHistorial() {
        System.out.println("\n┌─ HISTORIAL DE ACCIONES ─┐");
        ListaTda<Accion> acciones = sistema.listarAcciones();
        if (acciones.longitud() > 0) {
            System.out.println("[OK] Acciones registradas (" + acciones.longitud() + "):");
            for (int i = 0; i < acciones.longitud(); i++) {
                System.out.println("   " + (i + 1) + ". " + acciones.obtener(i));
            }
        } else {
            System.out.println("[INFO] No hay acciones registradas aun.");
        }
        System.out.println();
    }

    private static void cargarDesdeJSON() {
        System.out.println("\n[INFO] Desea limpiar los clientes actuales antes de cargar (S/N)? ");
        String limpiar = sc.nextLine().toLowerCase();
        if (limpiar.equals("s") || limpiar.equals("si")) {
            sistema = new Sistema();
            System.out.println("[OK] Sistema limpiado.");
        }
        
        System.out.println("┌─ CARGAR DESDE JSON ─┐");
        try {
            // Usar validación integrada
            ClientesData data = gestorJSON.leerResourceConValidacion();
            if (data.getClientes() != null && data.getClientes().size() > 0) {
                int cargados = 0;
                for (Cliente cliente : data.getClientes()) {
                    try {
                        sistema.agregarCliente(cliente.getNombre(), cliente.getScoring(), 
                                             cliente.getSiguiendo(), cliente.getConexiones());
                        cargados++;
                    } catch (Exception e) {
                        System.out.println("[WARN] No se pudo cargar cliente: " + cliente.getNombre() + " - " + e.getMessage());
                    }
                }
                System.out.println("[OK] " + cargados + " cliente(s) cargados exitosamente.");
            } else {
                System.out.println("[INFO] No hay clientes en el archivo JSON.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Datos inválidos en JSON: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] No se puede leer el archivo JSON: " + e.getMessage());
        }
        System.out.println();
    }

    /** Guarda todos los clientes del sistema al archivo JSON. Complejidad: O(n) donde n es el numero de clientes */
    private static void guardarAJSON() {
        System.out.println("\n┌─ GUARDAR A JSON ─┐");
        try {
            ClientesData data = new ClientesData();
            ArrayList<Cliente> clientes = new ArrayList<>();
            
            ListaTda<Cliente> clientesSistema = sistema.listarClientes();
            if (clientesSistema.longitud() > 0) {
                for (int i = 0; i < clientesSistema.longitud(); i++) {
                    clientes.add(clientesSistema.obtener(i));
                }
                data.setClientes(clientes);
                gestorJSON.guardar(data);
                System.out.println("[OK] " + clientes.size() + " cliente(s) guardados en clientes_out.json");
            } else {
                System.out.println("[INFO] No hay clientes para guardar.");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] No se pudo guardar los clientes: " + e.getMessage());
        }
        System.out.println();
    }
}