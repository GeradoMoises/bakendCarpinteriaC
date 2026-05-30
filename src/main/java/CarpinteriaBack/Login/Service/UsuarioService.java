package CarpinteriaBack.Login.Service;


import CarpinteriaBack.Login.Model.Usuario;
import CarpinteriaBack.Login.Respository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Marca la clase como un servicio de Spring, gestionado por el contenedor (inyección de dependencias)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository; // Repositorio que maneja operaciones CRUD de la entidad Usuario
    private final PasswordEncoder passwordEncoder; // Bean de Spring Security para encriptar y verificar contraseñas

    // Constructor con inyección de dependencias (Spring inyecta el repositorio y el encoder)
    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para login: verifica que el usuario exista, esté activo y que la contraseña coincida
    public Usuario login(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (!usuario.getEstado().name().equals("ACTIVO")) {
            throw new RuntimeException("Usuario inactivo");
        }

        // Compara la contraseña encriptada de la base de datos con la proporcionada
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }
    
    // Devuelve la lista completa de usuarios de la base de datos
    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Verifica si un usuario con el username dado ya existe
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    // Guarda un usuario en la base de datos (nuevo o existente)
    public Usuario saveUsuario(Usuario u) {
        return usuarioRepository.save(u);
    }

    // Busca un usuario por su username
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Busca un usuario por su ID
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id.intValue()); // Convierte Long a Integer porque el repositorio usa Integer
    }

    // Elimina un usuario de la base de datos
    public void deleteUsuario(Usuario u) {
        usuarioRepository.delete(u);
    }

    // Devuelve el PasswordEncoder para encriptar contraseñas fuera de esta clase
    public PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

}
