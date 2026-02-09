package CarpinteriaBack.Cliente.Controller;

import CarpinteriaBack.Cliente.Model.Cliente;
import CarpinteriaBack.Cliente.Service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Listar todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> lista = clienteService.listarClientes();
        return ResponseEntity.ok(lista);
    }

    // Buscar cliente por DNI
    @GetMapping("/{dni}")
    public ResponseEntity<?> obtenerPorDni(@PathVariable String dni) {
        try {
            Cliente cliente = clienteService.buscarPorDni(dni);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(404)
                    .body(e.getMessage());
        }
    }

    // Registrar nuevo cliente
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Cliente cliente) {
        try {
            Cliente nuevo = clienteService.registrarCliente(cliente);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // Actualizar cliente por DNI
    @PutMapping("/{dni}")
    public ResponseEntity<?> actualizar(
            @PathVariable String dni,
            @RequestBody Cliente cliente) {

        try {
            Cliente actualizado = clienteService.actualizarPorDni(dni, cliente);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // Eliminar cliente por DNI
    @DeleteMapping("/{dni}")
    public ResponseEntity<?> eliminar(@PathVariable String dni) {
        try {
            clienteService.eliminarPorDni(dni);
            return ResponseEntity.ok("Cliente eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(404)
                    .body(e.getMessage());
        }
    }
}
