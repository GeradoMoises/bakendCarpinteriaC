package CarpinteriaBack.Empleado.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import CarpinteriaBack.Login.Model.Rol;
import CarpinteriaBack.Login.Model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(name = "codigo_trabajador", nullable = false, unique = true, length = 20)
    private String codigoTrabajador;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 150)
    private String apellidos;

    @Column(name = "dni", nullable = false, unique = true, length = 15)
    private String dni;

    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String correo;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_contratacion", nullable = false)
    private LocalDate fechaContratacion;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "genero", length = 20)
    private String genero;

    @Column(name = "celular", length = 20)
    private String celular;

    @OneToOne(mappedBy = "empleado")
    @ToString.Exclude     // ⛔ evita ciclo infinito con Usuario
    @JsonIgnore
    private Usuario usuario;
}
