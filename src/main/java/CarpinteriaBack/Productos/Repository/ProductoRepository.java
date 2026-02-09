package CarpinteriaBack.Productos.Repository;

import CarpinteriaBack.Productos.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaId(Integer id);

    List<Producto> findByNombreContaining(String text);

    @Query("select p from Producto p where lower(p.nombre) like lower(concat('%',:texto,'%'))")
    List<Producto> Buscarpornombre(@Param("texto") String texto);

    @Query("select p from Producto p where p.categoria.id = :idCategoria")
    List<Producto> buscarPorCategoria(@Param("idCategoria") Long idCategoria);
}
