# TODO — Iteración 1 (Gestión de Clientes y Acciones)

Checklist para dividir el trabajo en equipo.

> **Estado actual del repo (hoy):** ya existen implementaciones base de `RepositorioClientes`, `HistorialAcciones`, `GestorSolicitudes`, `SolicitudSeguimiento`, `Cliente`, `Accion` y `TipoAccion`.

## Núcleo del sistema
- [ ] **TAD + invariantes**: definir y documentar invariantes de representación para:
  - Clientes / repositorio
  - Historial de acciones
  - Cola de solicitudes

- [ ] **Gestión de clientes (eficiente)**:
  - [x] Búsqueda por **nombre** eficiente (ya)
  - [x] Búsqueda por **scoring** eficiente (ya)
  - [ ] Casos borde: nombre vacío/nulo, duplicados, scoring fuera de rango (si aplica)
  - [x] Operaciones: agregar, eliminar, listar, contar (ya)

- [ ] **Historial de acciones (pila)**:
  - [x] Registrar acción (O(1)) (ya)
  - [x] Deshacer última acción (O(1)) (ya)
  - [x] (Opcional) Listar acciones registradas (ya)

- [ ] **Gestión de solicitudes de seguimiento (cola FIFO)**:
  - [x] Encolar solicitudes (ya)
  - [x] Procesar siguiente en orden FIFO (O(1)) (ya)
  - [x] Consultas: si está vacía, cantidad pendientes (ya)
  - [ ] Integrar esto al flujo real del sistema (que se use desde un “Sistema”/`Main`)

## Persistencia con JSON
- [ ] **Modelo del JSON**:
  - [ ] Definir mapeo de `clientes[]` con `nombre`, `scoring`, `siguiendo[]`, `conexiones[]`
  - [ ] Decidir si `siguiendo` y `conexiones` se cargan en iteración 1 o se deja preparado

- [ ] **Lector/cargador JSON**:
  - [ ] Leer archivo JSON desde ruta
  - [ ] Crear clientes y cargarlos en estructuras
  - [ ] (Si corresponde) Construir relaciones (`siguiendo`, `conexiones`)

- [ ] **Validaciones y errores de carga**:
  - [ ] Cliente duplicado (por nombre)
  - [ ] Campos faltantes / nulos / vacíos
  - [ ] Referencias a clientes inexistentes en listas
  - [ ] JSON mal formado (manejo de excepción y mensaje claro)

## Integración / ejecución
- [ ] **Fachada o “Sistema”**:
  - [ ] Unificar `RepositorioClientes`, `HistorialAcciones`, `GestorSolicitudes`
  - [ ] Exponer operaciones “de alto nivel” (agregar/eliminar, registrar/deshacer, encolar/procesar)

- [ ] **`Main` / interfaz mínima**:
  - [ ] Cargar JSON al inicio (o por comando)
  - [ ] Ejecutar algunas operaciones para demostrar funcionamiento

## Tests (para dividir, no implementados acá)
- [ ] **Pruebas unitarias**:
  - [ ] Carga JSON (válido)
  - [ ] Carga JSON (inválidos: duplicados, faltantes, referencias inválidas)
  - [ ] Búsquedas por nombre / scoring
  - [ ] Historial: registrar y deshacer (LIFO)
  - [ ] Solicitudes: FIFO

- [ ] **Datos de prueba**:
  - [ ] JSON válido mínimo
  - [ ] JSON con duplicados
  - [ ] JSON con campos faltantes
  - [ ] JSON con referencias a cliente inexistente

## Entrega / prolijidad
- [ ] **README**:
  - [ ] Cómo ejecutar (Maven)
  - [ ] Cómo correr tests
  - [ ] Formato del JSON esperado
  - [ ] Decisiones de diseño + complejidades (por qué Map/TreeMap, pila/cola, etc.)

- [ ] **Chequeo final**:
  - [ ] Revisar casos borde
  - [ ] Correr `mvn test`

## Reparto sugerido (rápido)
- **Persona A**: JSON (modelo + lector + validaciones) + JSONs de prueba.
- **Persona B**: integración (Sistema/Main) + tests de estructuras + README.

