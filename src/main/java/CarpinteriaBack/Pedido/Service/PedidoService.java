package CarpinteriaBack.Pedido.Service;

import CarpinteriaBack.Cliente.Model.Cliente;
import CarpinteriaBack.Cliente.Repository.ClienteRepository;
import CarpinteriaBack.Empleado.Model.Empleado;
import CarpinteriaBack.Empleado.Repository.EmpleadoRepository;
import CarpinteriaBack.Pedido.Model.Pedido;
import CarpinteriaBack.Pedido.Model.Pedido.EstadoPedido;
import CarpinteriaBack.Pedido.Repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;

    public PedidoService(PedidoRepository pedidoRepository,ClienteRepository clienteRepository,EmpleadoRepository empleadoRepository ) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
    }

    // ===========================
    // REGISTRAR PEDIDO
    // ===========================
    public Pedido registrarPedido(Pedido pedido) {

        // Validar Cliente
        if (pedido.getCliente() == null || pedido.getCliente().getIdCliente() == null) {
            throw new RuntimeException("El cliente es obligatorio.");
        }

        Cliente cliente = clienteRepository.findById(pedido.getCliente().getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado."));

        // Validar Empleado
        if (pedido.getEmpleado() == null || pedido.getEmpleado().getIdEmpleado() == null) {
            throw new RuntimeException("El empleado es obligatorio.");
        }

        Empleado empleado = empleadoRepository.findById(pedido.getEmpleado().getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado."));

        pedido.setCliente(cliente);
        pedido.setEmpleado(empleado);

        // Defaults
        if (pedido.getFechaPedido() == null) {
            pedido.setFechaPedido(LocalDateTime.now());
        }

        if (pedido.getEstado() == null) {
            pedido.setEstado(EstadoPedido.PENDIENTE);
        }

        return pedidoRepository.save(pedido);
    }

    // ===========================
    // LISTAR
    // ===========================
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    // ===========================
    // OBTENER POR ID
    // ===========================
    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    // ===========================
    // ACTUALIZAR
    // ===========================
    public Pedido actualizarPedido(Long id, Pedido datos) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));

        // Si cambia Cliente
        if (datos.getCliente() != null && datos.getCliente().getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(datos.getCliente().getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado."));
            pedido.setCliente(cliente);
        }

        // Si cambia Empleado
        if (datos.getEmpleado() != null && datos.getEmpleado().getIdEmpleado() != null) {
            Empleado empleado = empleadoRepository.findById(datos.getEmpleado().getIdEmpleado())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado."));
            pedido.setEmpleado(empleado);
        }

        // Actualizar campos
        pedido.setFechaPedido(datos.getFechaPedido() != null ? datos.getFechaPedido() : pedido.getFechaPedido());
        pedido.setFechaEntrega(datos.getFechaEntrega());
        pedido.setEstado(datos.getEstado() != null ? datos.getEstado() : pedido.getEstado());
        pedido.setTotal(datos.getTotal() != null ? datos.getTotal() : pedido.getTotal());
        pedido.setObservaciones(datos.getObservaciones());

        return pedidoRepository.save(pedido);
    }

    // ===========================
    // ELIMINAR
    // ===========================
    public void eliminarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));

        pedidoRepository.delete(pedido);
    }

    // ===========================
    // BUSCAR (texto + estado + empleado + fechas)
    // ===========================
    public List<Pedido> buscar(String texto,
                              EstadoPedido estado,
                              Integer idEmpleado,
                              LocalDateTime desde,
                              LocalDateTime hasta) {

        boolean textoVacio = (texto == null || texto.trim().isEmpty());

        // Si no mandas nada, lista todo
        if (textoVacio && estado == null && idEmpleado == null && desde == null && hasta == null) {
            return pedidoRepository.findAll();
        }

        return pedidoRepository.buscarPedidos(
                texto == null ? "" : texto.trim(),
                estado,
                idEmpleado,
                desde,
                hasta
        );
    }

    // ===========================
    // RANGOS (opcional)
    // ===========================
    public List<Pedido> listarPorRangoFechaPedido(LocalDateTime desde, LocalDateTime hasta) {
        return pedidoRepository.listarPorRangoFechaPedido(desde, hasta);
    }

    public List<Pedido> listarPorRangoEntrega(LocalDate desde, LocalDate hasta) {
        return pedidoRepository.listarPorRangoEntrega(desde, hasta);
    }

    // ===========================
    // DASHBOARD
    // ===========================
    public List<Object[]> contarPorEstado() {
        return pedidoRepository.contarPorEstado();
    }

    public List<Object[]> topClientesPorPedidos() {
        return pedidoRepository.topClientesPorPedidos();
    }
}

