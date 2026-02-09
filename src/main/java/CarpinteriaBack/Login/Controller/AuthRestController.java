package CarpinteriaBack.Login.Controller;

import CarpinteriaBack.Login.Model.Usuario;
import CarpinteriaBack.Login.Service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthRestController {

    private final UsuarioService usuarioService;

    public AuthRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        try {
            Usuario usuario = usuarioService.login(username, password);

            // ✅ SACAR ROL DESDE BD
            String rol = usuario.getEmpleado().getRol().getNombre(); // ADMINISTRADOR

            return ResponseEntity.ok(Map.of(
                    "idUsuario", usuario.getIdUsuario(),
                    "username", usuario.getUsername(),
                    "estado", usuario.getEstado().name(),
                    "rol", rol
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
