package CarpinteriaBack.Productos.Controllers;


import CarpinteriaBack.LineaDiseno.Model.LineaDiseno;
import CarpinteriaBack.Productos.Model.Producto;

import CarpinteriaBack.Productos.Services.ProductoService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producto/api")
public class ProductoController {

    private final ProductoService productoService;


    // ========= LISTAR =========
    @GetMapping("/listar")
    public List<Producto> listar(){
        return productoService.listar();
    }


    // ========= REGISTRAR =========
    @PostMapping("/registrar")
    public Producto registrar(@RequestBody Producto producto){
        return productoService.guardar(producto);
    }


    // ========= EDITAR =========
    @PutMapping("/editar/{id}")
    public Producto editar(@PathVariable Long id, @RequestBody Producto producto){

        Producto productoDB = productoService.buscarPorId(id).orElse(null);

        if(productoDB == null){
            throw new RuntimeException("No existe el producto con id " + id);
        }

        // campos básicos
        productoDB.setNombre(producto.getNombre());
        productoDB.setDescripcionCard(producto.getDescripcionCard());
        productoDB.setDescripcionCorta(producto.getDescripcionCorta());
        productoDB.setDescripcionLarga(producto.getDescripcionLarga());
        productoDB.setPrecio(producto.getPrecio());
        productoDB.setEtiquetaPrecio(producto.getEtiquetaPrecio());

        productoDB.setLargo(producto.getLargo());
        productoDB.setAncho(producto.getAncho());
        productoDB.setAltura(producto.getAltura());
        productoDB.setPeso(producto.getPeso());

        productoDB.setMaterial(producto.getMaterial());
        productoDB.setImagen1(producto.getImagen1());
        productoDB.setImagen2(producto.getImagen2());
        productoDB.setImagen3(producto.getImagen3());

        productoDB.setActivo(producto.getActivo());


        // relaciones
        productoDB.setCategoria(producto.getCategoria());
        productoDB.setLineaDiseno(producto.getLineaDiseno());

        return productoService.guardar(productoDB);
    }


    // ========= ELIMINAR =========
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id){
        productoService.eliminar(id);
    }


    // ========= BUSCAR POR NOMBRE =========
    @GetMapping("/buscar")
    public List<Producto> buscar(@RequestParam String texto){
        return productoService.buscarPorNombre(texto);
    }


    // ========= BUSCAR POR CATEGORIA =========
    @GetMapping("/buscar-categoria")
    public List<Producto> buscarCategoria(@RequestParam Long idCategoria){
        return productoService.buscarPorCategoria(idCategoria);
    }
    // Dentro de la clase ProductoController

// ... (métodos listar, registrar) ...

    // ========= BUSCAR POR ID =========
    @GetMapping("/buscar/{id}")
    public Producto buscarPorId(@PathVariable Long id){
        return productoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("No existe el producto con id " + id));
    }

// ... (métodos editar, eliminar, buscar, buscarCategoria) ...

}