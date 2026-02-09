package CarpinteriaBack.LineaDiseno.Controller;

import CarpinteriaBack.LineaDiseno.Model.LineaDiseno;
import CarpinteriaBack.LineaDiseno.Service.LineaDisenoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lineadiseno/api")
public class LineaDisenoController {

    private final LineaDisenoService lineaDisenoService;


    @GetMapping("/listar")
    public List<LineaDiseno> listar(){
        return lineaDisenoService.listarLineaDiseno();
    }


    @PostMapping("/registrar")
    public LineaDiseno registrar(@RequestBody LineaDiseno lineaDiseno){
        return lineaDisenoService.guardar(lineaDiseno);
    }


    @PutMapping("/editar/{id}")
    public LineaDiseno editar(@PathVariable Long id, @RequestBody LineaDiseno lineaDiseno){

        LineaDiseno lineDB = lineaDisenoService.buscarporid(id).orElse(null);

        if(lineDB == null){
            throw new RuntimeException("No existe la línea con id "+ id);
        }

        lineDB.setNombre(lineaDiseno.getNombre());
        lineDB.setDescripcion(lineaDiseno.getDescripcion());
        lineDB.setActivo(lineaDiseno.getActivo());

        return lineaDisenoService.guardar(lineDB);
    }
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id){
        lineaDisenoService.eliminar(id);
    }


    @GetMapping("/buscar")
    public List<LineaDiseno> buscar(@RequestParam String texto){
        return lineaDisenoService.Buscarporpornombre(texto);
    }


}
