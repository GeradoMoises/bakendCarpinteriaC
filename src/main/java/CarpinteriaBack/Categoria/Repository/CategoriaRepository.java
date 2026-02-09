package CarpinteriaBack.Categoria.Repository;

import CarpinteriaBack.Categoria.Model.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

    @Query("select c from Categoria c where lower(c.nombre) like lower(concat('%',:texto,'%'))")
    List<Categoria> buscarPorNombre(@Param("texto") String texto);
}

