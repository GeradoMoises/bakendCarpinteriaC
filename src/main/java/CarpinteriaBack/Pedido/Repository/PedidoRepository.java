package CarpinteriaBack.Pedido.Repository;

import CarpinteriaBack.Pedido.Model.Pedido;
import CarpinteriaBack.Pedido.Model.Pedido.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // =============================
    // 1) FILTROS DIRECTOS (rápidos)
    // =============================

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByCliente_IdCliente(Long idCliente);

    List<Pedido> findByEmpleado_IdEmpleado(Integer idEmpleado);

    // =============================
    // 2) BUSCAR POR TEXTO (observaciones)
    // =============================
    @Query("""
        SELECT p FROM Pedido p
        WHERE LOWER(p.observaciones) LIKE LOWER(CONCAT('%', :texto, '%'))
    """)
    List<Pedido> buscarPorObservacion(@Param("texto") String texto);

    // =============================
    // 3) BUSCAR POR CLIENTE (texto: nombre/apellidos/dni/correo)
    // =============================
    @Query("""
        SELECT p FROM Pedido p
        WHERE
          LOWER(p.cliente.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
          OR LOWER(p.cliente.apellidos) LIKE LOWER(CONCAT('%', :texto, '%'))
          OR p.cliente.dni LIKE CONCAT('%', :texto, '%')
          OR LOWER(p.cliente.correo) LIKE LOWER(CONCAT('%', :texto, '%'))
    """)
    List<Pedido> buscarPorClienteTexto(@Param("texto") String texto);

    // =============================
    // 4) FILTRO COMBINADO (texto + estado + rangos)
    //    - texto busca en cliente + observaciones
    // =============================
    @Query("""
        SELECT p FROM Pedido p
        WHERE
          (
            LOWER(p.cliente.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
            OR LOWER(p.cliente.apellidos) LIKE LOWER(CONCAT('%', :texto, '%'))
            OR p.cliente.dni LIKE CONCAT('%', :texto, '%')
            OR LOWER(p.cliente.correo) LIKE LOWER(CONCAT('%', :texto, '%'))
            OR LOWER(p.observaciones) LIKE LOWER(CONCAT('%', :texto, '%'))
          )
          AND (:estado IS NULL OR p.estado = :estado)
          AND (:idEmpleado IS NULL OR p.empleado.idEmpleado = :idEmpleado)
          AND (:desde IS NULL OR p.fechaPedido >= :desde)
          AND (:hasta IS NULL OR p.fechaPedido <= :hasta)
    """)
    List<Pedido> buscarPedidos(
            @Param("texto") String texto,
            @Param("estado") EstadoPedido estado,
            @Param("idEmpleado") Integer idEmpleado,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta
    );

    // =============================
    // 5) PEDIDOS POR RANGO DE FECHAS (solo fecha_pedido)
    // =============================
    @Query("""
        SELECT p FROM Pedido p
        WHERE p.fechaPedido BETWEEN :desde AND :hasta
        ORDER BY p.fechaPedido DESC
    """)
    List<Pedido> listarPorRangoFechaPedido(
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta
    );

    // =============================
    // 6) PEDIDOS POR FECHA DE ENTREGA (DATE)
    // =============================
    @Query("""
        SELECT p FROM Pedido p
        WHERE p.fechaEntrega BETWEEN :desde AND :hasta
        ORDER BY p.fechaEntrega ASC
    """)
    List<Pedido> listarPorRangoEntrega(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta
    );

    // =============================
    // 7) ÚTIL PARA DASHBOARD: contar por estado
    // =============================
    @Query("""
        SELECT p.estado, COUNT(p)
        FROM Pedido p
        GROUP BY p.estado
    """)
    List<Object[]> contarPorEstado();

    // =============================
    // 8) ÚTIL PARA DASHBOARD: top clientes por cantidad de pedidos
    // =============================
    @Query("""
        SELECT p.cliente.idCliente, p.cliente.nombre, p.cliente.apellidos, COUNT(p)
        FROM Pedido p
        GROUP BY p.cliente.idCliente, p.cliente.nombre, p.cliente.apellidos
        ORDER BY COUNT(p) DESC
    """)
    List<Object[]> topClientesPorPedidos();
}
