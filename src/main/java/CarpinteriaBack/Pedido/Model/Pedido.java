package CarpinteriaBack.Pedido.Model;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import CarpinteriaBack.Cliente.Model.Cliente;
import CarpinteriaBack.Empleado.Model.Empleado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
   // <-- ESTE ES EL IMPORT QUE TE FALTA


@Data
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    // FK -> cliente.id_cliente
    @ManyToOne
    @JoinColumn(name="id_cliente", nullable=false)
    @JsonIgnoreProperties({"pedidos","hibernateLazyInitializer","handler"})
    private Cliente cliente;

    // FK -> empleado.id_empleado
    @ManyToOne
    @JoinColumn(name="id_empleado", nullable=false)
    @JsonIgnoreProperties({"pedidos","hibernateLazyInitializer","handler"})
    private Empleado empleado;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(name = "observaciones", length = 255)
    private String observaciones;

    // =========================
    // ENUM Estado (igual a MySQL)
    // =========================
    public enum EstadoPedido {
        PENDIENTE,
        EN_PRODUCCION,
        ENTREGADO,
        CANCELADO
    }
}