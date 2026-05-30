package CarpinteriaBack.Login.Controller;

import CarpinteriaBack.Login.Model.Usuario;
import CarpinteriaBack.Login.Service.UsuarioService;
import CarpinteriaBack.Login.dto.LoginRequest;
import CarpinteriaBack.Login.dto.UsuarioCreateDTO;
import CarpinteriaBack.Empleado.Repository.EmpleadoRepository;
import CarpinteriaBack.Empleado.Model.Empleado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EmpleadoRepository empleadoRepository;

    public UsuarioController(UsuarioService usuarioService, EmpleadoRepository empleadoRepository) {
        this.usuarioService = usuarioService;
        this.empleadoRepository = empleadoRepository;
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(
            java.util.Map.of(
                "mensaje", "Login correcto",
                "usuario", usuario.getUsername(),
                "rol", usuario.getEmpleado().getRol().getNombre()
            )
        );
    }

    // LISTAR USUARIOS
 
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.findAllUsuarios();
    }
 // CREAR USUARIO
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioCreateDTO dto) {
        Empleado emp = empleadoRepository.findById(dto.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(usuarioService.getPasswordEncoder().encode(dto.getPassword()));
        usuario.setEmpleado(emp);

        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    // EDITAR USUARIO
    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario datos) {
        Usuario usuarioExistente = usuarioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioExistente.setUsername(datos.getUsername());
        if (datos.getPassword() != null && !datos.getPassword().isEmpty()) {
            usuarioExistente.setPassword(usuarioService.getPasswordEncoder().encode(datos.getPassword()));
        }
        usuarioExistente.setEstado(datos.getEstado());
        Usuario actualizado = usuarioService.saveUsuario(usuarioExistente);
        return ResponseEntity.ok(actualizado);
    }

    // ELIMINAR USUARIO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioService.deleteUsuario(usuario);
        return ResponseEntity.noContent().build();
    }
}