package CarpinteriaBack.LineaDiseno.Service;

import CarpinteriaBack.Categoria.Model.Categoria;
import CarpinteriaBack.LineaDiseno.Model.LineaDiseno;
import CarpinteriaBack.LineaDiseno.Repository.LineaDisenoRository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LineaDisenoService {
    private final LineaDisenoRository lineaDisenoRository;

    //lista
    public List<LineaDiseno> listarLineaDiseno() {
        return (List<LineaDiseno>) lineaDisenoRository.findAll();
    }
    //buscar por id
    public Optional <LineaDiseno> buscarporid(Long id){
        return lineaDisenoRository.findById(id);
    }
    //registrar
    public LineaDiseno guardar (LineaDiseno lineaDiseno) {
        return lineaDisenoRository.save(lineaDiseno);
    }
    //eliminar por id
    public  void eliminar (Long id){
        lineaDisenoRository.deleteById(id);
    }

    public List<LineaDiseno> Buscarporpornombre(String texto){
        return lineaDisenoRository.Buscarpornombre(texto);
    }





}
