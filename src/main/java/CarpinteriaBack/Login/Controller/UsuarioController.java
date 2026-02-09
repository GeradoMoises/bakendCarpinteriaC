package CarpinteriaBack.Login.Controller;


import CarpinteriaBack.Login.Model.Usuario;
import CarpinteriaBack.Login.Service.UsuarioService;
import CarpinteriaBack.Login.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioService.login(
                request.getUsername(),
                request.getPassword()
        );

        return ResponseEntity.ok(
                java.util.Map.of(
                        "mensaje", "Login correcto",
                        "usuario", usuario.getUsername(),
                        "rol", usuario.getEmpleado().getRol().getNombre()
                )
        );
    }

}
