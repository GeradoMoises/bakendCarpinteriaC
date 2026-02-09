package CarpinteriaBack.Categoria.Service;

import CarpinteriaBack.Categoria.Model.Categoria;
import CarpinteriaBack.Categoria.Repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor   //s una anotación de Lombok que sirve para generar automáticamente un constructor con los campos final o con anotaciones como @NonNull.
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> listar() {
        return (List<Categoria>) categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria registrar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }

    public List<Categoria> buscarPorNombre(String texto) {
        return categoriaRepository.buscarPorNombre(texto);
    }
}
