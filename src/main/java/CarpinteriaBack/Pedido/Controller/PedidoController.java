package CarpinteriaBack.Pedido.Controller;

import CarpinteriaBack.Pedido.Model.Pedido;
import CarpinteriaBack.Pedido.Model.Pedido.EstadoPedido;
import CarpinteriaBack.Pedido.Service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // ===========================
    // CREAR
    // ===========================
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Pedido pedido) {
        try {
            Pedido guardado = pedidoService.registrarPedido(pedido);
            return ResponseEntity.ok(guardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ===========================
    // LISTAR
    // ===========================
    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listarPedidos();
    }

    // ===========================
    // OBTENER POR ID
    // ===========================
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.obtenerPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ===========================
    // ACTUALIZAR
    // ===========================
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pedido datos) {
        try {
            Pedido actualizado = pedidoService.actualizarPedido(id, datos);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ===========================
    // ELIMINAR
    // ===========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            pedidoService.eliminarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ===========================
    // BUSCAR (texto + estado + empleado + fechas)
    // ===========================
    @GetMapping("/buscar")
    public List<Pedido> buscar(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) EstadoPedido estado,
            @RequestParam(required = false) Integer idEmpleado,
            @RequestParam(required = false) LocalDateTime desde,
            @RequestParam(required = false) LocalDateTime hasta
    ) {
        return pedidoService.buscar(texto, estado, idEmpleado, desde, hasta);
    }

    // ===========================
    // DASHBOARD
    // ===========================
    @GetMapping("/dashboard/contar-por-estado")
    public List<Object[]> contarPorEstado() {
        return pedidoService.contarPorEstado();
    }

    @GetMapping("/dashboard/top-clientes")
    public List<Object[]> topClientes() {
        return pedidoService.topClientesPorPedidos();
    }
}
