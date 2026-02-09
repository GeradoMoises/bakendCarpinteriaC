package CarpinteriaBack.Productos.Controllers;

import CarpinteriaBack.Productos.Model.Producto;
import CarpinteriaBack.Productos.Services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producto/public")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoPublicController {

    private final ProductoService productoService;

    @GetMapping("/listar")
    public List<Producto> listarPublico() {
        return productoService.listar();
    }
    @GetMapping("/{id}")
    public Producto obtenerPublicoPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("No existe el producto con id " + id));
    }

}
