# TODO — Iteración 1 (Gestión de Clientes y Acciones)

Checklist para dividir el trabajo en equipo.

> **Estado actual del repo (hoy):** ya existen implementaciones base de `RepositorioClientes`, `HistorialAcciones`, `GestorSolicitudes`, `SolicitudSeguimiento`, `Cliente`, `Accion` y `TipoAccion`.

## Núcleo del sistema
- [x] **TDA (interfaces + implementaciones)**:
  - [x] `cola/ColaTda` + `ColaImpl` (FIFO)
  - [x] `deque/DequeTda` + `DequeImpl` (doble extremo)
  - [x] Integración en `GestorSolicitudes` (Cola) y `HistorialAcciones` (Deque como pila)

- [x] **TAD + invariantes**: definir y documentar invariantes de representación para:
  - Clientes / repositorio
  - Historial de acciones
  - Cola de solicitudes

- [x] **Gestión de clientes (eficiente)**:
  - [x] Búsqueda por **nombre** eficiente (ya)
  - [x] Búsqueda por **scoring** eficiente (ya)
  - [x] Casos borde (según decidan implementar)
  - [x] Operaciones: agregar, eliminar, listar, contar (ya)

- [x] **Historial de acciones (pila)**:
  - [x] Registrar acción (O(1)) (ya)
  - [x] Deshacer última acción (O(1)) (ya)
  - [x] (Opcional) Listar acciones registradas (ya)

- [ ] **Gestión de solicitudes de seguimiento (cola FIFO)**:
  - [x] Encolar solicitudes (ya)
  - [x] Procesar siguiente en orden FIFO (O(1)) (ya)
  - [x] Consultas: si está vacía, cantidad pendientes (ya)
  - [x] Integrado al flujo real (vía `Sistema`/`Main`)
  - [ ] Decisión: ¿permitir auto-seguimiento (solicitante == objetivo)?
  - [ ] Decisión: ¿permitir solicitudes duplicadas (mismo solicitante/objetivo repetido)?

## Persistencia con JSON
- [DONE] **Modelo del JSON**:
  - [x] Definir mapeo de `clientes[]` con `nombre`, `scoring`, `siguiendo[]`, `conexiones[]`
  - [x] `siguiendo` y `conexiones` se cargan en iteración 1 (completado)

- [DONE] **Lector/cargador JSON**:
  - [x] Leer archivo JSON desde ruta
  - [x] Crear clientes y cargarlos en estructuras
  - [x] Construir relaciones (`siguiendo`, `conexiones`)

- [DONE] **Validaciones y errores de carga**:
  - [x] Cliente duplicado (por nombre)
  - [x] Campos faltantes / nulos / vacíos
  - [x] Referencias a clientes inexistentes en listas
  - [x] JSON mal formado (manejo de excepción y mensaje claro)
  - [x] Validación de scoring (0-100)

## Integración / ejecución
- [x] **Fachada o “Sistema”**:
  - [x] Unificar `RepositorioClientes`, `HistorialAcciones`, `GestorSolicitudes`
  - [x] Exponer operaciones “de alto nivel” (agregar/eliminar, registrar/deshacer, encolar/procesar)

- [MOSTLY DONE] **`Main` / interfaz mínima**:
  - [ ] Cargar JSON al inicio automáticamente (opcional - actualmente por menú)
  - [x] Ejecutar algunas operaciones para demostrar funcionamiento

## Tests (para dividir, no implementados acá)
- [DONE] **Pruebas unitarias**:
  - [x] Carga JSON (válido)
  - [x] Carga JSON (inválidos: duplicados, faltantes, referencias inválidas)
  - [x] Búsquedas por nombre / scoring
  - [x] Historial: registrar y deshacer (LIFO)
  - [x] Solicitudes: FIFO
  - [x] Agregar cliente con relaciones
  - [x] Casos borde (cliente inexistente, lista vacía, etc.)

- [DONE] **Datos de prueba**:
  - [x] JSON válido mínimo (clientes.json en resources/)
  - [x] Tests con JSON con duplicados  
  - [x] Tests con campos faltantes / inválidos
  - [x] Tests con referencias a cliente inexistente

## Entrega / prolijidad
- [DONE] **README**:
  - [x] Cómo ejecutar (Maven)
  - [x] Cómo correr tests
  - [x] Formato del JSON esperado
  - [x] Decisiones de diseño + complejidades (por qué Map/TreeMap, pila/cola, etc.)
  - [x] Ejemplos de uso
  - [x] Solución de problemas
