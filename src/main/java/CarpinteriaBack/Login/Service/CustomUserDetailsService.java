package CarpinteriaBack.Login.Service;


import CarpinteriaBack.Login.Model.Usuario;
import CarpinteriaBack.Login.Respository.UsuarioRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no existe: " + username));

        if (u.getEstado() != null &&
                u.getEstado().name().equalsIgnoreCase("INACTIVO")) {
            throw new DisabledException("Usuario INACTIVO");
        }

        String rol = u.getEmpleado().getRol().getNombre(); // ADMINISTRADOR

        List<SimpleGrantedAuthority> auths =
                List.of(new SimpleGrantedAuthority("ROLE_" + rol));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                auths
        );
    }
}
