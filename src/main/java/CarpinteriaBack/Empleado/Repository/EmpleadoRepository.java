package CarpinteriaBack.Empleado.Repository;

import CarpinteriaBack.Empleado.Model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    Optional<Empleado> findByDni(String dni);

    boolean existsByDni(String dni);

    boolean existsByCorreo(String correo);

    boolean existsByCodigoTrabajador(String codigoTrabajador);
    // 🔍 BUSCAR POR TEXTO (nombre, apellido, dni, código)
    @Query("""
        SELECT e FROM Empleado e
        WHERE 
          LOWER(e.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
          OR LOWER(e.apellidos) LIKE LOWER(CONCAT('%', :texto, '%'))
          OR e.dni LIKE CONCAT('%', :texto, '%')
          OR LOWER(e.codigoTrabajador) LIKE LOWER(CONCAT('%', :texto, '%'))
    """)
    List<Empleado> buscarPorTexto(@Param("texto") String texto);


    // 🔍 BUSCAR POR TEXTO + ROL
    @Query("""
        SELECT e FROM Empleado e
        WHERE 
          (
            LOWER(e.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
            OR LOWER(e.apellidos) LIKE LOWER(CONCAT('%', :texto, '%'))
            OR e.dni LIKE CONCAT('%', :texto, '%')
            OR LOWER(e.codigoTrabajador) LIKE LOWER(CONCAT('%', :texto, '%'))
          )
          AND (:idRol IS NULL OR e.rol.idRol = :idRol)
    """)
    List<Empleado> buscarPorTextoYRol(
            @Param("texto") String texto,
            @Param("idRol") Integer idRol
    );
}
