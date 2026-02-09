package CarpinteriaBack.LineaDiseno.Repository;

import CarpinteriaBack.LineaDiseno.Model.LineaDiseno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LineaDisenoRository extends JpaRepository <LineaDiseno, Long> {

    @Query("select l from LineaDiseno l where lower(l.nombre) like lower(concat('%',:texto,'%') )")
    List<LineaDiseno>Buscarpornombre(@Param("texto") String texto);



}
