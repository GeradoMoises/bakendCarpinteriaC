package CarpinteriaBack.Categoria.Controller;

import CarpinteriaBack.Categoria.Model.Categoria;
import CarpinteriaBack.Categoria.Service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/categoria/api")
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping("/listar")
    public List<Categoria> listar(){
        return categoriaService.listar();
    }
    @PostMapping("/registrar")
    public Categoria registrar(@RequestBody Categoria categoria){
        return categoriaService.registrar(categoria);
    }
    @PutMapping("/editar/{id}")
    public Categoria editar(@PathVariable Long id, @RequestBody Categoria categoria){
        Categoria catDB = categoriaService.buscarPorId(id).orElse(null);
        if(catDB == null){
            throw new RuntimeException("No existe la categoría con id "+id);
        }
        catDB.setNombre(categoria.getNombre());
        catDB.setDescripcion(categoria.getDescripcion());
        catDB.setActivo(categoria.getActivo());

        return categoriaService.registrar(catDB);
    }
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id){
        categoriaService.eliminar(id);
    }

    @GetMapping("/buscar")
    public List<Categoria> buscar(@RequestParam String texto){
        return categoriaService.buscarPorNombre(texto);
    }
}
