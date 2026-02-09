package CarpinteriaBack.Cliente.Repository;

import CarpinteriaBack.Cliente.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // =========================
    // BÚSQUEDAS BÁSICAS
    // =========================

    // Buscar cliente por DNI exacto
    Optional<Cliente> findByDni(String dni);

    // Validar si un DNI ya existe
    boolean existsByDni(String dni);

    // =========================
    // BÚSQUEDA POR NOMBRE
    // =========================

    // Buscar por nombre (LIKE, sin importar mayúsculas)
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por apellidos
    List<Cliente> findByApellidosContainingIgnoreCase(String apellidos);

    // =========================
    // BÚSQUEDA POR TEXTO GENERAL
    // (nombre, apellidos, dni, correo, celular)
    // =========================
    @Query("""
        SELECT c FROM Cliente c
        WHERE
          LOWER(c.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
          OR LOWER(c.apellidos) LIKE LOWER(CONCAT('%', :texto, '%'))
          OR c.dni LIKE CONCAT('%', :texto, '%')
          OR LOWER(c.correo) LIKE LOWER(CONCAT('%', :texto, '%'))
          OR c.celular LIKE CONCAT('%', :texto, '%')
    """)
    List<Cliente> buscarPorTexto(@Param("texto") String texto);

    // =========================
    // BÚSQUEDA POR NOMBRE + APELLIDO (combinado)
    // =========================
    @Query("""
        SELECT c FROM Cliente c
        WHERE
          LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))
          AND LOWER(c.apellidos) LIKE LOWER(CONCAT('%', :apellidos, '%'))
    """)
    List<Cliente> buscarPorNombreYApellido(
            @Param("nombre") String nombre,
            @Param("apellidos") String apellidos
    );
}
