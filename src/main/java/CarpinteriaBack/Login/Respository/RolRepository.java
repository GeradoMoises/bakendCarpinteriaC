package CarpinteriaBack.Login.Respository;

import CarpinteriaBack.Login.Model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Rol findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
