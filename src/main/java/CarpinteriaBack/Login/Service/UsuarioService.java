package CarpinteriaBack.Login.Service;


import CarpinteriaBack.Login.Model.Usuario;
import CarpinteriaBack.Login.Respository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // 👈 ESTA LÍNEA FALTABA

    // 👇 CONSTRUCTOR CON INYECCIÓN
    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario login(String username, String password) {

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (!usuario.getEstado().name().equals("ACTIVO")) {
            throw new RuntimeException("Usuario inactivo");
        }

        // 👇 AQUÍ YA EXISTE passwordEncoder
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }



}
