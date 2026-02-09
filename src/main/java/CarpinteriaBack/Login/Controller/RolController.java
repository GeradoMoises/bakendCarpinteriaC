package CarpinteriaBack.Login.Controller;

import CarpinteriaBack.Login.Model.Rol;
import CarpinteriaBack.Login.Respository.RolRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolRepository repo;

    public RolController(RolRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Rol> listar() {
        return repo.findAll();
    }
}
