# Changelog

Este archivo resume cambios relevantes del proyecto.

## [Unreleased]

### Added
- Implementación base del dominio/iteración 1:
  - Modelos: `Cliente`, `Accion`, `TipoAccion`, `SolicitudSeguimiento`.
  - Estructuras: `RepositorioClientes` (búsqueda por nombre/scoring), `HistorialAcciones` (pila LIFO), `GestorSolicitudes` (cola FIFO).
- Estructura Maven: `src/main/java` y `src/test/java` (sin tests).
- `pom.xml` con compilación Java 11 y configuración básica de plugins.
- `.gitignore` para ignorar `target/` y archivos de IDE.
- `TODO.md` con checklist de la Iteración 1.

### Changed
- JUnit: se dejó de forzar versión en dependencia y se gestiona con BOM (evita “Overrides version defined in DependencyManagement”).
- `RepositorioClientes`:
  - Normalización de nombre más robusta (trim + colapsar espacios + lowercase con `Locale.ROOT`).
  - Manejo seguro de `null`/vacío en búsquedas y eliminación (sin `NullPointerException`).
  - `buscarPorScoring` devuelve `Set` no modificable para proteger invariantes.
- `Cliente`:
  - Normalización de nombre (trim + colapsar espacios).
  - `equals/hashCode` consistente usando clave normalizada con `Locale.ROOT`.
  - Validación de rango de scoring (0..100).
- Validaciones de casos borde:
  - `HistorialAcciones.registrarAccion(null)` ahora lanza `IllegalArgumentException`.
  - `GestorSolicitudes.encolar(null)` ahora lanza `IllegalArgumentException`.

