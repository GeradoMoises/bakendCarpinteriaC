package CarpinteriaBack.Empleado.Controller;

import CarpinteriaBack.Empleado.Model.Empleado;
import CarpinteriaBack.Empleado.Service.EmpleadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // Crear empleado
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Empleado empleado) {
        try {
            Empleado guardado = empleadoService.registrarEmpleado(empleado);
            return ResponseEntity.ok(guardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar todos
    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listarEmpleados();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id) {
        try {
            Empleado empleado = empleadoService.obtenerPorId(id);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar por ID
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                        @RequestBody Empleado datos) {
        try {
            Empleado actualizado = empleadoService.actualizarEmpleado(id, datos);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            empleadoService.eliminarEmpleado(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔍 BUSCAR (texto + rol)
    @GetMapping("/buscar")
    public List<Empleado> buscar(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Integer idRol
    ) {
        return empleadoService.buscar(texto, idRol);
    }
}
