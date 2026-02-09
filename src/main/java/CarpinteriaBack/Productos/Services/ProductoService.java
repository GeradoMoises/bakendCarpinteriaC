package CarpinteriaBack.Productos.Services;

import CarpinteriaBack.Categoria.Model.Categoria;
import CarpinteriaBack.Productos.Model.Producto;
import CarpinteriaBack.Productos.Repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;


    // ========= LISTAR =========
    public List<Producto> listar(){
        return productoRepository.findAll();
    }


    // ========= BUSCAR POR ID =========
    public Optional<Producto> buscarPorId(Long id){
        return productoRepository.findById(id);
    }


    // ========= REGISTRAR / ACTUALIZAR =========
    public Producto guardar(Producto producto){
        return productoRepository.save(producto);
    }


    // ========= ELIMINAR =========
    public void eliminar(Long id){
        productoRepository.deleteById(id);
    }


    // ========= BUSCAR POR NOMBRE =========
    public List<Producto> buscarPorNombre(String texto){
        return productoRepository.Buscarpornombre(texto);
    }


    // ========= BUSCAR POR CATEGORIA =========
    public List<Producto> buscarPorCategoria(Long idCategoria){
        return productoRepository.buscarPorCategoria(idCategoria);
    }
}